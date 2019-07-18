package com.hzx.wms.review;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.app.Constants;
import com.hzx.wms.bean.ReviewBean;
import com.hzx.wms.http.Api;
import com.hzx.wms.http.HttpUtils;
import com.hzx.wms.http.RxUtils;
import com.hzx.wms.utils.EditSearchAction;
import com.hzx.wms.utils.RecycleViewDivider;
import com.hzx.wms.utils.SoundPlayUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.vondear.rxtool.RxSPTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialogSure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qinl
 * @date 2019/7/3
 */
public class MyReviewDetailsActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.text_warehouse)
    TextView textWarehouse;
    @Bind(R.id.edt_warehouse_warehouseNum)
    EditText edtWarehouseWarehouseNum;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private static final String NUM = "1";

    MineReviewTaskDetailsAdapter adapter;
    String mailNo;
    List<String> list = new ArrayList<>();
    List<String> checkList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review_details);
        ButterKnife.bind(this);

        mailNo = getIntent().getExtras().getString("mailNo");
        textWarehouse.setText(String.format("运单号：%s", mailNo));
        //软键盘回车搜索
        EditSearchAction action = new EditSearchAction();
        action.searchAction(this, edtWarehouseWarehouseNum);
        action.setListener(this::intentNext);
        initView();

        SoundPlayUtils.init(this);
    }

    RxDialogSure sure;

    private void initView() {
        //初始化状态view
        loadView = loadView(recyclerView);
        errorView = errorView(recyclerView);
        emptyView = emptyView(recyclerView);
        //初始化列表
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        adapter = new MineReviewTaskDetailsAdapter(R.layout.activity_my_check_details_item, null);
        recyclerView.setAdapter(adapter);
        adapter.setPreLoadNumber(8);
        adapter.setEnableLoadMore(true);
        errorView.setOnClickListener(v -> getMainNoDetails(mailNo));
        getMainNoDetails(mailNo);
    }

    @Override
    public void intentNext(String message) {
        if (sure != null) {
            sure.dismiss();
        }
        if (message == null) {
            SoundPlayUtils.play(8);
            return;
        }
        if (message.length() < Constants.WAREHOUSE_LENGTH) {
            RxToast.warning("扫描正确的条码", 4000);
            SoundPlayUtils.play(5);
            return;
        }
        for (ReviewBean info : adapter.getData()) {
            list.add(info.getBar_code());
            if (message.equals(info.getBar_code())) {
                if (info.getConfirm_num() < info.getNum()) {
                    info.setConfirm_num(info.getConfirm_num() + 1);
                    adapter.notifyItemChanged(adapter.getData().indexOf(info));
                }
                if (info.getConfirm_num() == info.getNum()) {
                    RxToast.warning(info.getBar_code() + "已复核完成");
                    if (!checkList.contains(info.getBar_code())) {
                        checkList.add(info.getBar_code());
                    }
                }

            }
        }

        if (!list.contains(message)) {
            SoundPlayUtils.play(5);
            sure = new RxDialogSure(this);
            sure.getTitleView().setText("异常");
            sure.getContentView().setText(String.format("条码%s不存在，请扫描正确的条码", message));
            sure.getSureView().setOnClickListener(v -> {
                edtWarehouseWarehouseNum.setText("");
                list.clear();
                sure.dismiss();
            });
            sure.show();
        }

        if (checkList.size() == adapter.getData().size()) {
            check(mailNo);
        }
    }

    private void getMainNoDetails(String msg) {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("page", "1");
        params.put("limit", "100");
        params.put("logistics_no", msg);
        HttpUtils.getInstance().createService(Api.class)
                .query(params)
                .compose(RxUtils.handleGlobalError(this))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> adapter.setEmptyView(loadView))
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> loading.cancel())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(listBaseBean -> {
                    if (!Constants.CODE_SUCCESS.equals(listBaseBean.getCode())) {
                        SoundPlayUtils.play(5);
                        RxToast.error(listBaseBean.getMsg(), 5000);
                        finish();
                        return;
                    }
                    if (listBaseBean.getData().get(0).getPrepare_out_order().getCheck_user_id() != Integer.parseInt(RxSPTool.getString(this, "id"))) {
                        adapter.setEmptyView(errorView);
                        SoundPlayUtils.play(5);
                        RxToast.error("复核人员错误", 5000);
                        finish();
                        return;
                    }
                    adapter.setNewData(listBaseBean.getData());
                    //设置空view
                    if (adapter.getData().size() == 0) {
                        adapter.setEmptyView(emptyView);
                    }
                }, throwable -> finish());
    }

    private void check(String mailNo) {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("logistics_no", mailNo);
        HttpUtils.getInstance().createService(Api.class)
                .check(params)
                .compose(RxUtils.handleGlobalError(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> loading.cancel())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(baseBean -> {
                    SoundPlayUtils.play(7);
                    finish();
                }, throwable -> {});
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
