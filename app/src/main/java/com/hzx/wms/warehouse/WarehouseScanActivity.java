package com.hzx.wms.warehouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.app.Constants;
import com.hzx.wms.bean.DifferentBean;
import com.hzx.wms.bean.SearchBean;
import com.hzx.wms.http.Api;
import com.hzx.wms.http.HttpUtils;
import com.hzx.wms.http.RxUtils;
import com.hzx.wms.utils.EditSearchAction;
import com.hzx.wms.utils.RecycleViewDivider;
import com.hzx.wms.utils.SoundPlayUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialogSure;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qinl
 * @date 2019/7/3
 */
public class WarehouseScanActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.edt_warehouse_warehouseHw)
    EditText edtWarehouseWarehouseHw;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.text_title)
    TextView textTitle;

    private int id;
    private String inCode;
    private WarehouseDifferentAdapter adapter;
    private final static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_scan);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        SoundPlayUtils.init(this);
        EditSearchAction action = new EditSearchAction();
        action.searchAction(this, edtWarehouseWarehouseHw);
        action.setListener(this::intentNext);
        id = getIntent().getIntExtra("id", 0);
        inCode = getIntent().getStringExtra("in_code");
        textTitle.setText(String.format("%s入库", inCode));
        loadView = loadView(recyclerView);
        errorView = errorView(recyclerView);
        emptyView = emptyView(recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(WarehouseScanActivity.this);
        recyclerView.setLayoutManager(manager);
        adapter = new WarehouseDifferentAdapter(R.layout.activity_warehouse_different_item, null);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        errorView.setOnClickListener(view -> getData("1", "100"));
        //（差异）点击查看入库详细数据
        adapter.setOnItemClickListener((adapter, view, position) -> {
            DifferentBean data = (DifferentBean) adapter.getData().get(position);
            HashMap<String, String> params = new HashMap<>(1);
            params.put("bar_code", data.getWare().getBar_code());
            HttpUtils.getInstance().createService(Api.class)
                    .search(id, params)
                    .compose(RxUtils.handleGlobalError(this))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                    .subscribe(searchBeanBaseBean -> {
                        StringBuilder stringBuffer = new StringBuilder();
                        stringBuffer.append("应上架数量:").append(searchBeanBaseBean.getData().getNum()).append("\n");
                        int count = 0;
                        for (SearchBean.ListBean info : searchBeanBaseBean.getData().getList()) {
                            count = count + Integer.parseInt(info.getNum());
                            stringBuffer.append(info.getWare_location()).append("货位已上架:").append(info.getNum()).append("\n");
                        }
                        stringBuffer.append("已上架数量:").append(count);
                        RxDialogSure sure = new RxDialogSure(this);
                        sure.getTitleView().setText("差异");
                        sure.getContentView().setGravity(Gravity.START);
                        sure.getContentView().setText(stringBuffer.toString());
                        sure.getSureView().setOnClickListener(view1 -> sure.dismiss());
                        sure.show();
                    }, throwable -> {
                    });
        });
        getData("1", "100");
    }

    @Override
    public void intentNext(String message) {
        if (message == null) {
            SoundPlayUtils.play(8);
            return;
        }
        if (message.length() > Constants.WAREHOUSE_LENGTH) {
            RxToast.warning("请扫描或输入正确的货位");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("location", message);
        bundle.putInt("id", id);
        RxActivityTool.skipActivityForResult(this, WarehouseScanDetailsActivity.class, bundle, REQUEST_CODE);
    }

    private void getData(String page, String limit) {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("page", page);
        params.put("limit", limit);
        HttpUtils.getInstance().createService(Api.class)
                .getDifferent(id, params)
                .retry(1)
                .compose(RxUtils.handleGlobalError(this))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    adapter.setEmptyView(loadView);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(listBaseBean -> {
                    //更新数据
                    adapter.setNewData(listBaseBean.getData());
                    //设置空view
                    if (adapter.getData().size() == 0) {
                        adapter.setEmptyView(emptyView);
                    }
                }, throwable -> adapter.setEmptyView(errorView));
    }


    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            getData("1", "100");
        }
    }
}
