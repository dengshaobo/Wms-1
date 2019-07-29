package com.hzx.wms.pick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.app.Constants;
import com.hzx.wms.bean.TaskListBean;
import com.hzx.wms.http.Api;
import com.hzx.wms.http.HttpUtils;
import com.hzx.wms.http.RxUtils;
import com.hzx.wms.utils.EditSearchAction;
import com.hzx.wms.utils.RecycleViewDivider;
import com.hzx.wms.utils.SoundPlayUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialogEditSureCancel;
import com.vondear.rxui.view.dialog.RxDialogSure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qinl
 * @package qinl.com.hzxoderquery.activity.task
 * @date 2019/5/29 14:24
 * @fileName MyPickTaskDetailsActivity
 * @describe TODO
 */

public class MyPickDetailsActivity extends BaseActivity {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    MyPickTaskDetailsListAdapter adapter;
    TaskListBean bean;
    @Bind(R.id.edt)
    EditText edt;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.text_post)
    TextView textPost;

    RxDialogEditSureCancel dialogEditSureCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pick_details);
        ButterKnife.bind(this);
        initView();
        SoundPlayUtils.init(this);
    }

    private void initView() {
        //软键盘回车搜索
        EditSearchAction action = new EditSearchAction();
        action.searchAction(this, edt);
        action.setListener(this::intentNext);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        adapter = new MyPickTaskDetailsListAdapter(R.layout.activity_my_pick_details_item, null);
        adapter.setPreLoadNumber(8);
        adapter.setEnableLoadMore(true);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            TaskListBean.NumListBean list = (TaskListBean.NumListBean) adapter.getData().get(position);
            showNumDialog(list, position);
        });
        getTaskDetails();
    }


    private void showNumDialog(TaskListBean.NumListBean list, int position) {
        dialogEditSureCancel = new RxDialogEditSureCancel(this);
        dialogEditSureCancel.getTitleView().setText(String.format(Locale.CHINA, "输入%s已捡数量(应捡:%d)", list.getName(), list.getNum()));
        dialogEditSureCancel.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        dialogEditSureCancel.getSureView().setOnClickListener(v -> {
            String num = dialogEditSureCancel.getEditText().getText().toString();
            if ("".equals(num)) {
                RxToast.warning("请输入数量");
                return;
            }
            list.setPick_num(Integer.parseInt(num));
            if (list.getNum() == Integer.parseInt(num)) {
                adapter.remove(position);
            } else {
                adapter.notifyItemChanged(position);
            }
            if (adapter.getData().size() == 0) {
                post();
            }
            dialogEditSureCancel.cancel();
        });
        dialogEditSureCancel.getCancelView().setOnClickListener(v -> dialogEditSureCancel.cancel());
        dialogEditSureCancel.show();
    }

    @Override
    public void intentNext(String message) {
        if (message == null) {
            SoundPlayUtils.play(8);
            return;
        }
        if (message.length() < Constants.WAREHOUSE_LENGTH) {
            RxToast.warning("请扫描或输入正确的条码");
            return;
        }
        if (dialogEditSureCancel != null) {
            dialogEditSureCancel.dismiss();
        }
        for (TaskListBean.NumListBean info : adapter.getData()) {
            if (message.equals(info.getBar_code())) {
                showNumDialog(info, adapter.getData().indexOf(info));
            }
        }
    }

    private void getTaskDetails() {
        Observable
                .create((ObservableOnSubscribe<List<TaskListBean.NumListBean>>) emitter -> {
                    Intent intent = getIntent();
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        bean = (TaskListBean) bundle.getSerializable("task");
                    }
                    Map<String, TaskListBean.NumListBean> map = new HashMap<>();
                    for (TaskListBean.NumListBean info : bean.getNum_list()) {
                        String key = info.getBar_code();
                        if (!map.containsKey(key)) {
                            map.put(key, info);
                        } else {
                            TaskListBean.NumListBean numListBean = map.get(key);
                            numListBean.setNum(numListBean.getNum() + info.getNum());
                            if (!numListBean.getWare_location().equals(info.getWare_location())) {
                                numListBean.setWare_location(numListBean.getWare_location() + "," + info.getWare_location());
                            }
                        }
                    }

                    List<TaskListBean.NumListBean> listBeans = new ArrayList<>();
                    Collection<TaskListBean.NumListBean> connection = map.values();
                    listBeans.addAll(connection);
                    emitter.onNext(listBeans);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(numListBean -> adapter.addData(numListBean));
    }

    private void post() {
        StringBuilder builder = new StringBuilder();
        for (TaskListBean.NumListBean info : adapter.getData()) {
            if (info.getNum() != info.getPick_num()) {
                builder.append(info.getBar_code()).append(",");
            }
        }
        if (builder.length() > 0) {
            RxDialogSure dialogSure = new RxDialogSure(this);
            dialogSure.getTitleView().setText("提示");
            dialogSure.getContentView().setText(String.format("%s有差异", builder.toString()));
            dialogSure.getSureView().setOnClickListener(v -> dialogSure.cancel());
            dialogSure.show();
            return;
        }

        HttpUtils.getInstance().createService(Api.class)
                .postPick(bean.getId())
                .retry(1)
                .compose(RxUtils.handleGlobalError(this))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> loading.cancel())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(baseBean -> {
                    RxToast.success(baseBean.getMsg());
                    finish();
                }, throwable -> {

                });
    }


    @OnClick({R.id.img_back, R.id.text_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.text_post:
                vib();
                post();
                break;
            default:
        }
    }
}
