package com.hzx.wms.repeat;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.app.Constants;
import com.hzx.wms.app.MyApplication;
import com.hzx.wms.bean.RepeatBean;
import com.hzx.wms.greendao.DaoSession;
import com.hzx.wms.http.Api;
import com.hzx.wms.http.HttpUtils;
import com.hzx.wms.http.RxUtils;
import com.hzx.wms.utils.EditSearchAction;
import com.hzx.wms.utils.SoundPlayUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.vondear.rxtool.view.RxToast;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qinl
 * @date 2019/7/3
 */
public class RepeatActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.checkbox)
    CheckBox checkbox;
    @Bind(R.id.text_barcode)
    TextView textBarcode;
    @Bind(R.id.text_check)
    TextView textCheck;
    @Bind(R.id.text_scan_num)
    TextView textScanNum;
    @Bind(R.id.text_checked)
    TextView textChecked;
    @Bind(R.id.text_post)
    TextView textPost;
    @Bind(R.id.edit_num)
    EditText editNum;
    @Bind(R.id.text_status)
    TextView textStatus;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);
        ButterKnife.bind(this);
        SoundPlayUtils.init(this);
        daoSession = MyApplication.getDaoSession();

        //软键盘回车搜索
        EditSearchAction action = new EditSearchAction();
        action.searchAction(this, editNum);
        action.setListener(this::intentNext);

        SoundPlayUtils.init(this);
    }

    @Override
    public void intentNext(String message) {
        if (message == null) {
            SoundPlayUtils.play(8);
            return;
        }
        if (message.length() < Constants.WAREHOUSE_LENGTH) {
            RxToast.warning("请扫描或输入正确的条码");
            SoundPlayUtils.play(5);
            return;
        }
        textBarcode.setText(String.format("物流单号：%s", message));
        List<RepeatBean> list = daoSession.queryRaw(RepeatBean.class, " where barcode = ?", message);
        if (list.size() > 0) {
            SoundPlayUtils.play(4);
            return;
        }

        getIntercept(message);
    }


    private void getIntercept(String barcode) {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("logistics_no", barcode);
        params.put("page", "1");
        params.put("limit", "1");
        HttpUtils.getInstance().createService(Api.class)
                .getIntercept(params)
                .retry(1)
                .compose(RxUtils.handleGlobalError(this))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> loading.show())
                .doFinally(() -> loading.cancel())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(listBaseBean -> {
                    String msg = "无状态";
                    int status = 0;
                    if (listBaseBean.getData().size() != 0) {
                        if(listBaseBean.getData().get(0).getNow_status()!=null){
                            msg = listBaseBean.getData().get(0).getNow_status().getDeclare_status_msg();
                            status = listBaseBean.getData().get(0).getNow_status().getDeclare_status_id();
                        }
                    }
                    textStatus.setText(String.format("拦截状态：%s", msg));
                    if (checkbox.isChecked()) {
                        if (status == 800) {
                            SoundPlayUtils.play(1);
                            daoSession.insert(new RepeatBean(barcode));
                        }
                    } else {
                        daoSession.insert(new RepeatBean(barcode));
                    }
                    textScanNum.setText(String.format(Locale.CHINA, "扫描次数：%d", daoSession.loadAll(RepeatBean.class).size()));
                }, throwable -> {

                });
    }


    @OnClick({R.id.img_back, R.id.text_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                daoSession.deleteAll(RepeatBean.class);
                finish();
                break;
            case R.id.text_post:
                RxToast.warning("正在完善");
                break;
            default:
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            daoSession.deleteAll(RepeatBean.class);
        }
        return super.onKeyDown(keyCode, event);
    }
}
