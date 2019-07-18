package com.hzx.wms.query;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.app.Constants;

import com.hzx.wms.http.Api;
import com.hzx.wms.http.HttpUtils;
import com.hzx.wms.http.RxUtils;
import com.hzx.wms.utils.EditSearchAction;
import com.hzx.wms.utils.SoundPlayUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qinl
 * @package com.hzx.wms.query
 * @date 2019/7/8 15:26
 * @fileName QueryActivity
 * @describe TODO
 */

public class QueryActivity extends BaseActivity {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.edt_warehouse_warehouseNum)
    EditText edtWarehouseWarehouseNum;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    QueryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        EditSearchAction searchAction = new EditSearchAction();
        searchAction.searchAction(this, edtWarehouseWarehouseNum);
        searchAction.setListener(this::intentNext);

        //初始化状态view
        loadView = loadView(recyclerView);
        errorView = errorView(recyclerView);
        emptyView = emptyView(recyclerView);
        errorView.setOnClickListener(v -> getData(edtWarehouseWarehouseNum.getText().toString()));
        LinearLayoutManager manager = new LinearLayoutManager(QueryActivity.this);
        recyclerView.setLayoutManager(manager);
        adapter = new QueryAdapter(R.layout.activity_stock_item, null);
        // recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void intentNext(String message) {
        if (message == null) {
            SoundPlayUtils.play(8);
            return;
        }
        getData(message);
    }

    private void getData(String message) {
        adapter.setNewData(null);
        HashMap<String, String> params = new HashMap<>(3);
        params.put("page", "1");
        params.put("limit", "100");
        if (message.length() > Constants.WAREHOUSE_LENGTH) {
            params.put("bar_code", message);
        } else {
            params.put("ware_location", message);
        }
        HttpUtils.getInstance().createService(Api.class)
                .getData(params)
                .retry(1)
                .compose(RxUtils.handleGlobalError(this))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> loading.show())
                .doFinally(() -> loading.cancel())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(listBaseBean -> {
                    adapter.setNewData(listBaseBean.getData());
                    if (adapter.getData().size() == 0) {
                        adapter.setEmptyView(emptyView);
                    }
                }, throwable -> adapter.setEmptyView(errorView));
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
