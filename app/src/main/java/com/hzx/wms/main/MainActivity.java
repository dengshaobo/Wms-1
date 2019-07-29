package com.hzx.wms.main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.check.CheckActivity;
import com.hzx.wms.stocktaking.StocktakingActivity;
import com.hzx.wms.login.LoginActivity;
import com.hzx.wms.pick.PickActivity;
import com.hzx.wms.query.QueryActivity;
import com.hzx.wms.repeat.RepeatActivity;
import com.hzx.wms.review.ReviewActivity;
import com.hzx.wms.setting.SettingActivity;
import com.hzx.wms.utils.UpdateUtil;
import com.hzx.wms.warehouse.WarehouseActivity;
import com.hzx.wms.weight.WeightActivity;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxSPTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author qinl
 * @date 2019/7/3
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.card_warehouse)
    CardView cardWarehouse;
    @Bind(R.id.card_pick)
    CardView cardPick;
    @Bind(R.id.card_review)
    CardView cardReview;
    @Bind(R.id.card_check)
    CardView cardCheck;
    @Bind(R.id.card_repeat)
    CardView cardRepeat;
    @Bind(R.id.card_weight)
    CardView cardWeight;
    @Bind(R.id.img_setting)
    ImageView imgSetting;
    @Bind(R.id.card_stocktaking)
    CardView cardStocktaking;
    @Bind(R.id.card_query)
    CardView cardQuery;
    @Bind(R.id.card_replenish)
    CardView cardReplenish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RxActivityTool.addActivity(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        UpdateUtil.check(this, false);
    }


    @OnClick({R.id.card_warehouse, R.id.card_pick, R.id.card_review, R.id.card_check, R.id.card_repeat,
            R.id.card_weight, R.id.img_setting, R.id.card_stocktaking, R.id.card_query, R.id.card_replenish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.card_warehouse:
                RxActivityTool.skipActivity(this, WarehouseActivity.class);
                break;
            case R.id.card_pick:
                RxActivityTool.skipActivity(this, PickActivity.class);
                break;
            case R.id.card_review:
                RxActivityTool.skipActivity(this, ReviewActivity.class);
                break;
            case R.id.card_repeat:
                RxActivityTool.skipActivity(this, RepeatActivity.class);
                break;
            case R.id.card_weight:
                RxActivityTool.skipActivity(this, WeightActivity.class);
                break;
            case R.id.img_setting:
                RxActivityTool.skipActivity(this, SettingActivity.class);
                break;
            case R.id.card_check:
                RxActivityTool.skipActivity(this, CheckActivity.class);
                break;
            case R.id.card_stocktaking:
                RxActivityTool.skipActivity(this, StocktakingActivity.class);
                break;
            case R.id.card_query:
                RxActivityTool.skipActivity(this, QueryActivity.class);
                break;
            case R.id.card_replenish:
                RxToast.warning("补货功能正在完善。。。", 1000);
                break;
            default:
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                showWaringDialog();
            }
        }
    }

    private void showWaringDialog() {
        RxDialogSureCancel dialogSureCancel = new RxDialogSureCancel(this);
        dialogSureCancel.getTitleView().setText("提示");
        dialogSureCancel.getContentView().setText("请开启程序访问文件权限");
        dialogSureCancel.getSureView().setText("添加权限");
        dialogSureCancel.getCancelView().setText("残忍拒绝");
        dialogSureCancel.getSureView().setOnClickListener(view -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 1);
            dialogSureCancel.dismiss();
        });
        dialogSureCancel.getCancelView().setOnClickListener(view -> dialogSureCancel.dismiss());
        dialogSureCancel.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!RxSPTool.getBoolean(this, "isLogin")) {
            RxActivityTool.skipActivityAndFinish(this, LoginActivity.class);
        }
    }
}
