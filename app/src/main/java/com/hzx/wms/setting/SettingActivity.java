package com.hzx.wms.setting;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.app.MyApplication;
import com.hzx.wms.login.LoginActivity;
import com.hzx.wms.utils.UpdateUtil;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxSPTool;
import com.vondear.rxtool.RxTool;
import com.vondear.rxui.view.dialog.RxDialogEditSureCancel;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    static final String mUpdateUrl = "http://139.224.57.189:8017/wms/version/get_version";
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.text_update)
    TextView textUpdate;
    @Bind(R.id.text_login_out)
    TextView textLoginOut;
    @Bind(R.id.text_url)
    TextView textUrl;
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        RxActivityTool.addActivity(this);
        URL = RxSPTool.getString(MyApplication.getContext(), "BaseUrl");
        if (!URL.isEmpty()) {
            textUrl.setText(String.format("服务器地址:%s", URL));
        }

    }


    @OnClick({R.id.img_back, R.id.text_update, R.id.text_login_out, R.id.text_url})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.text_update:
                UpdateUtil.check(this, false);
                break;
            case R.id.text_login_out:
                RxSPTool.putString(this, "id", "");
                RxSPTool.putString(this, "name", "");
                RxSPTool.putBoolean(this, "isLogin", false);
                RxSPTool.putString(RxTool.getContext(), "token", "");
                RxActivityTool.skipActivityAndFinishAll(this, LoginActivity.class);
                break;
            case R.id.text_url:
                RxDialogEditSureCancel editSureCancel = new RxDialogEditSureCancel(this);
                editSureCancel.getTitleView().setText("编辑服务器地址");
                editSureCancel.getEditText().setText(URL);
                editSureCancel.getSureView().setOnClickListener(view1 -> {
                    RxSPTool.putString(this, "BaseUrl", editSureCancel.getEditText().getText().toString());
                    editSureCancel.dismiss();
                    RxDialogSureCancel rxDialogSure = new RxDialogSureCancel(this);
                    rxDialogSure.getTitleView().setText("更改");
                    rxDialogSure.getContentView().setText("配置重启生效");
                    rxDialogSure.setSureListener(view2 -> {
                        rxDialogSure.dismiss();
                        reStartApp();
                    });
                    rxDialogSure.getCancelView().setOnClickListener(view2 -> {
                        RxSPTool.putString(this, "BaseUrl", URL);
                        rxDialogSure.dismiss();
                    });
                    rxDialogSure.show();

                });
                editSureCancel.getCancelView().setOnClickListener(view1 -> editSureCancel.dismiss());
                editSureCancel.show();
                break;

            default:
        }
    }

    public static void reStartApp() {
        Intent intent = MyApplication.getContext().getPackageManager().getLaunchIntentForPackage(MyApplication.getContext().getPackageName());
        PendingIntent restartIntent = PendingIntent.getActivity(MyApplication.getContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager) MyApplication.getContext().getSystemService(Context.ALARM_SERVICE);
        // 1秒钟后重启应用
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);
        RxActivityTool.AppExit(MyApplication.getContext());
    }

}
