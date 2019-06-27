package com.hzx.wms.warehouse;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.bean.RuKuDetailsBean;
import com.hzx.wms.http.Api;
import com.hzx.wms.http.HttpUtils;
import com.hzx.wms.http.RxUtils;
import com.hzx.wms.utils.EditSearchAction;
import com.hzx.wms.utils.GsonUtils;
import com.hzx.wms.utils.RecycleViewDivider;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialogEditSureCancel;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class WarehouseScanDetailsActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.text_post)
    TextView textPost;
    @Bind(R.id.text_warehouse)
    TextView textWarehouse;
    @Bind(R.id.edt_warehouse_warehouseNum)
    EditText edtWarehouseWarehouseNum;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    WarehouseScanDetailsAdapter adapter;
    String location;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_scan_details);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        location = getIntent().getStringExtra("location");
        textWarehouse.setText(String.format("货位：%s", location));
        id = getIntent().getIntExtra("id", 0);
        //软键盘回车搜索
        EditSearchAction action = new EditSearchAction();
        action.searchAction(this, edtWarehouseWarehouseNum);
        action.setListener(this::intentNext);

        //初始化状态view
        loadView = loadView(recyclerView);
        errorView = errorView(recyclerView);
        emptyView = emptyView(recyclerView);
        //初始化列表
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new WarehouseScanDetailsAdapter(R.layout.activity_warehouse_scan_details_item, null);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            RuKuDetailsBean bean = (RuKuDetailsBean) adapter.getData().get(position);
            if (view.getId() == R.id.img_edit) {
                RxDialogSureCancel dialogSureCancel = new RxDialogSureCancel(this);
                dialogSureCancel.getTitleView().setText("提示");
                dialogSureCancel.getContentView().setText(String.format("是否删除%s", bean.getBar_code()));
                dialogSureCancel.getSureView().setOnClickListener(view1 -> {
                    adapter.remove(position);
                    dialogSureCancel.dismiss();
                });
                dialogSureCancel.getCancelView().setOnClickListener(view12 -> {
                    dialogSureCancel.dismiss();
                });
                dialogSureCancel.show();
            }
        });
    }

    @Override
    public void intentNext(String message) {
//        if (message.length() < 7) {
//            RxToast.warning("请扫描或输入正确得条码");
//            return;
//        }
        showNumDialog(message);
    }

    private void showNumDialog(String message) {
        RxDialogEditSureCancel numDialog = new RxDialogEditSureCancel(this);
        numDialog.getTitleView().setText(String.format("编辑%s数量", message));
        numDialog.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        numDialog.getCancelView().setText("继续编辑效期");
        numDialog.getSureView().setOnClickListener(view -> {
            hideKeyboard(numDialog.getSureView());
            String num = numDialog.getEditText().getText().toString().trim();
            if ("".equals(num)) {
                RxToast.warning("未输入数量");
                return;
            }
            RuKuDetailsBean data = new RuKuDetailsBean();
            data.setNum(Integer.parseInt(num));
            data.setWare_location(location);
            data.setBar_code(message);
            data.setMaturity_date("");
            adapter.addData(data);
            recyclerView.smoothScrollToPosition(adapter.getData().size());
            numDialog.cancel();
        });
        numDialog.getCancelView().setOnClickListener(view -> {
            hideKeyboard(numDialog.getSureView());
            String num = numDialog.getEditText().getText().toString().trim();
            showDateDialog(Integer.parseInt(num), location, message);
            numDialog.cancel();
        });
        numDialog.show();
    }


    private void showDateDialog(int num, String warehouse, String message) {
        TimePickerView pvTime = new TimePickerBuilder(this, (date, v) -> {
            String time = String.valueOf(date.getTime() / 1000);
            RuKuDetailsBean data = new RuKuDetailsBean();
            data.setNum(num);
            data.setWare_location(warehouse);
            data.setBar_code(message);
            data.setMaturity_date(time);
            adapter.addData(data);
            recyclerView.smoothScrollToPosition(adapter.getData().size());
        }).build();
        pvTime.show();
    }

    private void updateRuKuData(List<RuKuDetailsBean> data) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), GsonUtils.GsonString(data));
        HttpUtils.getInstance().createService(Api.class)
                .updateRukuData(id, requestBody)
                .compose(RxUtils.handleGlobalError(this))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> loading.show())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> loading.cancel())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(baseBean -> {
                    RxToast.success(baseBean.getMsg());
                    adapter.setNewData(null);
                    finish();
                }, throwable -> {
                });
    }

    @OnClick({R.id.img_back, R.id.text_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                if (adapter.getData().size() > 0) {
                    RxDialogSureCancel dialogSureCancel = new RxDialogSureCancel(this);
                    dialogSureCancel.getTitleView().setText("提示");
                    dialogSureCancel.getContentView().setText("有数据没有提交，是否退出？");
                    dialogSureCancel.getSureView().setOnClickListener(view1 -> {
                        dialogSureCancel.dismiss();
                        finish();
                    });
                    dialogSureCancel.getCancelView().setOnClickListener(view1 -> dialogSureCancel.dismiss());
                    dialogSureCancel.show();
                }
                break;
            case R.id.text_post:
                if (adapter.getData().size() == 0) {
                    RxToast.warning("没有数据可以提交");
                    return;
                }
                updateRuKuData(adapter.getData());
                break;
        }
    }
}