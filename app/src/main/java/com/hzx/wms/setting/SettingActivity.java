package com.hzx.wms.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.login.LoginActivity;
import com.hzx.wms.utils.UpdateUtil;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxSPTool;
import com.vondear.rxtool.RxTool;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author  qinl
 * @date  2019/7/25
*/
public class SettingActivity extends BaseActivity {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.text_update)
    TextView textUpdate;
    @Bind(R.id.text_login_out)
    TextView textLoginOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        RxActivityTool.addActivity(this);
    }


    @OnClick({R.id.img_back, R.id.text_update, R.id.text_login_out})
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
            default:
        }
    }
}
