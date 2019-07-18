package com.hzx.wms.pick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.bean.TaskDistributionBean;
import com.hzx.wms.bean.TaskListBean;
import com.hzx.wms.http.Api;
import com.hzx.wms.http.HttpUtils;
import com.hzx.wms.http.RxUtils;
import com.hzx.wms.utils.GsonUtils;
import com.hzx.wms.utils.RecycleViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxSPTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author qinl
 * @date 2019/7/3
 */
public class PickActivity extends BaseActivity {
    TaskListAdapter adapter;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.text_mine)
    TextView textMine;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        loadView = loadView(recyclerView);
        errorView = errorView(recyclerView);
        emptyView = emptyView(recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new TaskListAdapter(R.layout.activity_pick_item, null);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.setPreLoadNumber(8);
        adapter.setEnableLoadMore(true);

        //item子控件点击事件
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            TaskListBean taskListBean = (TaskListBean) adapter.getData().get(position);
            TaskDistributionBean bean = new TaskDistributionBean();
            bean.setIds(String.valueOf(taskListBean.getId()));
            bean.setUser_id(RxSPTool.getString(this, "id"));
            RxDialogSureCancel sureCancel = new RxDialogSureCancel(this);
            sureCancel.getContentView().setText("是否领取这个出库任务");
            sureCancel.getSureView().setOnClickListener(v -> {
                distributionTask(bean);
                adapter.remove(position);
                if (adapter.getData().size() == 0) {
                    adapter.setEmptyView(emptyView);
                }
                sureCancel.cancel();
            });
            sureCancel.getCancelView().setOnClickListener(v -> sureCancel.cancel());
            sureCancel.show();
        });

        errorView.setOnClickListener(v -> getTaskList("1", "100"));

        refreshLayout.setOnRefreshListener(refreshLayout -> getTaskList("1", "100"));

        getTaskList("1", "100");
    }


    /**
     * 领取任务
     *
     * @param bean
     */
    private void distributionTask(TaskDistributionBean bean) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), GsonUtils.GsonString(bean));
        HttpUtils.getInstance().createService(Api.class)
                .distributionTask(requestBody)
                .compose(RxUtils.handleGlobalError(PickActivity.this))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> loading.show())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> loading.cancel())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(PickActivity.this)))
                .subscribe(baseBean -> RxToast.success(baseBean.getMsg()), throwable -> {
                });
    }


    /**
     * 获取任务列表
     *
     * @param page
     * @param limit
     */
    private void getTaskList(String page, String limit) {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("page", page);
        params.put("limit", limit);
        HttpUtils.getInstance().createService(Api.class)
                .getTaskList(params)
                .retry(1)
                .compose(RxUtils.handleGlobalError(this))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> adapter.setEmptyView(loadView))
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(listBaseBean -> {
                    refreshLayout.finishRefresh();
                    adapter.setNewData(listBaseBean.getData());
                    //设置空view
                    if (adapter.getData().size() == 0) {
                        adapter.setEmptyView(emptyView);
                    }
                    adapter.loadMoreComplete();
                }, throwable -> {
                    refreshLayout.finishRefresh();
                    adapter.setEmptyView(errorView);
                });
    }

    @OnClick({R.id.img_back, R.id.text_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.text_mine:
                RxActivityTool.skipActivityForResult(this, MyPickActivity.class, 1);
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            getTaskList("1", "100");
        }
    }
}
