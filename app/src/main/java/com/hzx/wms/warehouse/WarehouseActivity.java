package com.hzx.wms.warehouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.bean.RuKuBean;
import com.hzx.wms.http.Api;
import com.hzx.wms.http.HttpUtils;
import com.hzx.wms.http.RxUtils;
import com.hzx.wms.utils.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxSPTool;

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
public class WarehouseActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private View loadView;
    private View errorView;
    private View emptyView;
    WarehouseAdapter adapter;

    private final static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        //初始化状态view
        loadView = loadView(recyclerView);
        errorView = errorView(recyclerView);
        emptyView = emptyView(recyclerView);
        //初始化列表
        LinearLayoutManager manager = new LinearLayoutManager(WarehouseActivity.this);
        recyclerView.setLayoutManager(manager);
        adapter = new WarehouseAdapter(R.layout.activity_warehouse_item, null);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            RuKuBean bean = (RuKuBean) adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt("id", bean.getId());
            bundle.putString("in_code", bean.getIn_code());
            RxActivityTool.skipActivityForResult(WarehouseActivity.this, WarehouseScanActivity.class, bundle, REQUEST_CODE);
        });

        errorView.setOnClickListener(view -> getData("1", "100"));

        refreshLayout.setOnRefreshListener(refreshLayout -> getData("1", "100"));

        getData("1", "100");
    }


    private void getData(String page, String limit) {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("scan_user_id", RxSPTool.getString(this, "id"));
        params.put("page", page);
        params.put("limit", limit);
        HttpUtils.getInstance().createService(Api.class)
                .getRuKuList(params)
                .retry(1)
                .compose(RxUtils.handleGlobalError(this))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> adapter.setEmptyView(loadView))
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(listBaseBean -> {
                    refreshLayout.finishRefresh();
                    //更新数据
                    adapter.setNewData(listBaseBean.getData());
                    //设置空view
                    if (adapter.getData().size() == 0) {
                        adapter.setEmptyView(emptyView);
                    }
                }, throwable -> {
                    refreshLayout.finishRefresh();
                    adapter.setEmptyView(errorView);
                });
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
