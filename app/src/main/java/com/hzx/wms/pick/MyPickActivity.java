package com.hzx.wms.pick;

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
import com.hzx.wms.review.CheckOutAdapter;
import com.hzx.wms.utils.RecycleViewDivider;
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
 * @date 2019/5/29
 */

public class MyPickActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    CheckOutAdapter adapter;
    private int mNextRequestPage = 1;
    private static final int LIMIT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pick);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //初始化状态view
        loadView = loadView(recyclerView);
        errorView = errorView(recyclerView);
        emptyView = emptyView(recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(MyPickActivity.this);
        recyclerView.setLayoutManager(manager);
        adapter = new CheckOutAdapter(R.layout.activity_pick_item, null);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);


        adapter.setPreLoadNumber(8);
        adapter.setEnableLoadMore(true);
        errorView.setOnClickListener(view -> getPickTask("1", "100"));
        adapter.setOnItemClickListener((adapter, view, position) -> {
            TaskListBean bean = (TaskListBean) adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("task", bean);
            RxActivityTool.skipActivity(MyPickActivity.this, MyPickDetailsActivity.class, bundle);
        });

    }

    private void getPickTask(String page, String limit) {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("page", page);
        params.put("limit", limit);
        HttpUtils.getInstance().createService(Api.class)
                .getPickTask(params)
                .retry(1)
                .compose(RxUtils.handleGlobalError(this))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> loading.show())
                .doFinally(() -> loading.cancel())
                .observeOn(Schedulers.io())
                .map(listBaseBean -> {
                    RxLogTool.i(Thread.currentThread().getName());
                    int id = Integer.parseInt(RxSPTool.getString(this, "id"));
                    List<TaskListBean> list = new ArrayList<>();
                    for (TaskListBean info : listBaseBean.getData()) {
                        if (info.getScan_user_id() == id) {
                            list.add(info);
                        }
                    }
                    return list;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(listBaseBean -> {
                            adapter.setNewData(listBaseBean);
                            //设置空view
                            if (adapter.getData().size() == 0) {
                                adapter.setEmptyView(emptyView);
                            }
                            //设置加载完成
                            if (listBaseBean.size() < LIMIT) {
                                adapter.loadMoreEnd();
                                return;
                            }
                            mNextRequestPage++;
                            adapter.loadMoreComplete();
                        }
                        , throwable -> {
                        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getPickTask("1", "100");
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
