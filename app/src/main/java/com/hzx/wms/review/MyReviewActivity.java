package com.hzx.wms.review;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.bean.TaskListBean;
import com.hzx.wms.http.Api;
import com.hzx.wms.http.HttpUtils;
import com.hzx.wms.http.RxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;

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
public class MyReviewActivity extends BaseActivity {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private static final String NUM = "1";
    CheckOutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        //初始化状态view
        loadView = loadView(recyclerView);
        errorView = errorView(recyclerView);
        emptyView = emptyView(recyclerView);
        //初始化列表
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new CheckOutAdapter(R.layout.activity_pick_item, null);
        recyclerView.setAdapter(adapter);
        adapter.setPreLoadNumber(8);
        adapter.setEnableLoadMore(true);

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            TaskListBean taskListBean = adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(taskListBean.getId()));
            bundle.putString("out_code", taskListBean.getOut_code());
            RxActivityTool.skipActivityForResult(MyReviewActivity.this, MyReviewMailNoActivity.class, bundle, 1);
        });
        errorView.setOnClickListener(v -> getData("1", "100"));
        refreshLayout.setOnRefreshListener(refreshLayout1 -> getData("1", "100"));
        getData("1", "100");
    }

    private void getData(String page, String limit) {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("page", page);
        params.put("limit", limit);
        HttpUtils.getInstance().createService(Api.class)
                .getCheckoutList(params)
                .compose(RxUtils.handleGlobalError(this))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (NUM.equals(page)) {
                        loading.show();
                        adapter.setEmptyView(loadView);
                    }
                })
                .observeOn(Schedulers.io())
                .map(listBaseBean -> {
                    RxLogTool.i(Thread.currentThread().getName());
                    int id = Integer.parseInt(RxSPTool.getString(this, "id"));
                    List<TaskListBean> list = new ArrayList<>();
                    for (TaskListBean info : listBaseBean.getData()) {
                        if (info.getCheck_user_id() == id && info.getOrder_status() != 3) {
                            list.add(info);
                        }
                    }
                    return list;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> loading.cancel())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(taskListBeans -> {
                    refreshLayout.finishRefresh();
                    adapter.setNewData(taskListBeans);
                    //设置空view
                    if (adapter.getData().size() == 0) {
                        adapter.setEmptyView(emptyView);
                    }
                }, throwable -> {
                    refreshLayout.finishRefresh(false);
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
        if (requestCode == 1) {
            getData("1", "100");
        }
    }

}


