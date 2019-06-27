package com.hzx.wms.review;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;
import com.vondear.rxtool.RxTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ReviewActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    CheckOutAdapter adapter;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.text_mine)
    TextView textMine;
    private View loadView;
    private View errorView;
    private View emptyView;
    private int mNextRequestPage = 1;
    private static final int LIMIT = 10;
    private static final String NUM = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
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

        //item子控件点击事件
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            TaskListBean taskListBean = (TaskListBean) adapter.getData().get(position);
            TaskDistributionBean bean = new TaskDistributionBean();
            bean.setIds(String.valueOf(taskListBean.getId()));
            bean.setUser_id(RxSPTool.getString(ReviewActivity.this, "id"));
            RxDialogSureCancel sureCancel = new RxDialogSureCancel(this);
            sureCancel.getContentView().setText("是否领取这个复核任务");
            sureCancel.getSureView().setOnClickListener(v -> {
                distributionCheckTask(bean);
                adapter.remove(position);
                sureCancel.cancel();
            });
            sureCancel.getCancelView().setOnClickListener(v -> sureCancel.cancel());
            sureCancel.show();
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        getData("1", "100");
    }

    private void getData(String page, String limit) {
        RxTool.delayToDo(200, () -> {
            HashMap<String, String> params = new HashMap<>(2);
            params.put("page", page);
            params.put("limit", limit);
            HttpUtils.getInstance().createService(Api.class)
                    .getCheckoutList(params)
                    .compose(RxUtils.handleGlobalError(this))
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable -> adapter.setEmptyView(loadView))
                    .observeOn(Schedulers.io())
                    .map(listBaseBean -> {
                        RxLogTool.i("2:" + Thread.currentThread().getName());
                        int id = Integer.parseInt(RxSPTool.getString(this, "id"));
                        List<TaskListBean> list = new ArrayList<>();
                        for (TaskListBean info : listBaseBean.getData()) {
                            if (info.getCheck_user_id() != id && info.getCheck_user_id() == 0) {
                                list.add(info);
                            }
                        }
                        return list;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                    .subscribe(taskListBeans -> {
                        RxLogTool.i("3:" + Thread.currentThread().getName());
                        adapter.setNewData(taskListBeans);
                        //设置空view
                        if (adapter.getData().size() == 0) {
                            adapter.setEmptyView(emptyView);
                        }
                    }, throwable -> adapter.setEmptyView(errorView));
        });
    }


    /**
     * 领取任务
     *
     * @param bean
     */
    private void distributionCheckTask(TaskDistributionBean bean) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), GsonUtils.GsonString(bean));
        HttpUtils.getInstance().createService(Api.class)
                .distributionCheckTask(requestBody)
                .compose(RxUtils.handleGlobalError(ReviewActivity.this))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> loading.show())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> loading.cancel())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ReviewActivity.this)))
                .subscribe(baseBean -> RxToast.success(baseBean.getMsg()), throwable -> {
                });
    }

    @OnClick({R.id.img_back, R.id.text_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.text_mine:
                RxActivityTool.skipActivity(this, MyReviewActivity.class);
                break;
            default:
        }
    }


}
