package com.hzx.wms.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.http.Api;
import com.hzx.wms.http.HttpUtils;
import com.hzx.wms.http.RxUtils;
import com.hzx.wms.main.MainActivity;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxSPTool;
import com.vondear.rxtool.view.RxToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qinl
 * @date 2019/7/3
 */

public class LoginActivity extends BaseActivity {

    @Bind(R.id.edt_name)
    EditText edtName;
    @Bind(R.id.edt_pwd)
    EditText edtPwd;
    @Bind(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String userName = edtName.getText().toString();
        String userPwd = edtPwd.getText().toString();
        if (!"".equals(userName) && !"".equals(userPwd)) {
            onLogin(userName, userPwd);
        } else {
            RxToast.warning("用户名或密码不能为空");
        }
    }

    private void onLogin(final String name, final String pwd) {
        HttpUtils.getInstance().createService(Api.class)
                .login(name, pwd)
                .compose(RxUtils.handleGlobalError(this))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> loading.show())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(loginBeanBaseBean -> {
                    String token = loginBeanBaseBean.getData().getToken();
                    RxSPTool.putString(this, "token", token);
                    return HttpUtils.getInstance().createService(Api.class)
                            .getId();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> loading.dismiss())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(whoBeanBaseBean -> {
                    //保存用户id、名称
                    RxSPTool.putString(this, "id", whoBeanBaseBean.getData().getUser().getId());
                    RxSPTool.putString(this, "name", whoBeanBaseBean.getData().getUser().getName());
                    RxSPTool.putBoolean(this, "isLogin", true);
                    RxActivityTool.skipActivityAndFinish(this, MainActivity.class);
                }, throwable -> {
                });
    }
}
