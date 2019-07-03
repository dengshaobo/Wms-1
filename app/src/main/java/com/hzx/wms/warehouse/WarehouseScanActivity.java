package com.hzx.wms.warehouse;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
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
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialogSure;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WarehouseScanActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.edt_warehouse_warehouseHw)
    EditText edtWarehouseWarehouseHw;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    int id;
    WarehouseDifferentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_scan);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        SoundPlayUtils.init(this);
        //软键盘回车搜索
        EditSearchAction action = new EditSearchAction();
        action.searchAction(this, edtWarehouseWarehouseHw);
        action.setListener(this::intentNext);

        id = getIntent().getIntExtra("id", 0);
        //初始化状态view
        loadView = loadView(recyclerView);
        errorView = errorView(recyclerView);
        emptyView = emptyView(recyclerView);
        //初始化列表
        LinearLayoutManager manager = new LinearLayoutManager(WarehouseScanActivity.this);
        recyclerView.setLayoutManager(manager);
        adapter = new WarehouseDifferentAdapter(R.layout.activity_warehouse_different_item, null);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

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
                        RxLogTool.e("xxxxxxxxx" + searchBeanBaseBean.toString());
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("应上架数量:" + searchBeanBaseBean.getData().getNum() + "\n");
                        int count = 0;
                        for (SearchBean.ListBean info : searchBeanBaseBean.getData().getList()) {
                            count = count + Integer.parseInt(info.getNum());
                            stringBuffer.append(info.getWare_location() + "货位已上架:" + info.getNum() + "\n");
                        }
                        stringBuffer.append("已上架数量:" + count);
                        RxDialogSure sure = new RxDialogSure(this);
                        sure.getTitleView().setText("差异");
                        sure.getContentView().setGravity(Gravity.LEFT);
                        sure.getContentView().setText(stringBuffer.toString());
                        sure.getSureView().setOnClickListener(view1 -> sure.dismiss());
                        sure.show();
                    }, throwable -> {
                        RxLogTool.e("xxxxxxxxx");
                    });
        });

        errorView.setOnClickListener(view -> getData("1", "100"));
    }

    @Override
    public void intentNext(String message) {
        if (message == null) {
            SoundPlayUtils.play(8);
            return;
        }
        if (message.length() > 7) {
            RxToast.warning("请扫描或输入正确得货位");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("location", message);
        bundle.putInt("id", id);
        RxActivityTool.skipActivity(this, WarehouseScanDetailsActivity.class, bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData("1", "100");
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

                }, throwable -> {
                    adapter.setEmptyView(errorView);
                });
    }


    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
