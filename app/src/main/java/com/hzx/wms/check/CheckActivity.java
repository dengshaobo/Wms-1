package com.hzx.wms.check;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.bean.CheckBean;
import com.hzx.wms.http.Api;
import com.hzx.wms.http.HttpUtils;
import com.hzx.wms.http.RxUtils;
import com.hzx.wms.utils.RecycleViewDivider;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.vondear.rxtool.RxActivityTool;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qinl
 * @date 2019/7/16
 */
public class CheckActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    CheckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new CheckAdapter(R.layout.activity_check_item, null);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            CheckBean bean = adapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putInt("id", bean.getId());
            RxActivityTool.skipActivityForResult(this, CheckDetailsActivity.class, bundle, 1);

        });

        getCheckData();
    }

    private void getCheckData() {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("page", "1");
        params.put("limit", "100");
        params.put("status", "0");
        HttpUtils.getInstance().createService(Api.class)
                .getCheckData(params)
                .retry(1)
                .compose(RxUtils.handleGlobalError(this))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(listBaseBean -> {
                    adapter.setNewData(listBaseBean.getData());
                }, throwable -> {
                });
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
