package com.hzx.wms.app;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.hzx.wms.R;
import com.hzx.wms.receiver.ScanReceiver;
import com.hzx.wms.utils.SystemBarTintManager;
import com.vondear.rxtool.RxBarTool;
import com.vondear.rxui.view.dialog.RxDialogLoading;

import java.util.ArrayList;
import java.util.List;

import static com.vondear.rxtool.RxBarTool.getStatusBarHeight;


/**
 * @author qinl
 * @time 2018/7/26 10:58
 * @methodParameters
 * @describe
 */

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    IntentFilter mIntentFilter;
    ScanReceiver receiver;
    public static final String SCN_CUST_ACTION_SCODE = "com.android.server.scannerservice.broadcast";
    public static final String RES_ACTION = "android.intent.action.SCANRESULT";
    public static final String HONEYWELL_ACTION = "com.honeywell.decode.intent.action.EDIT_DATA";
    public static final String NEW_LAND = "nlscan.action.SCANNER_RESULT";
    private MyApplication application;
    public RxDialogLoading loading;

    public View loadView;
    public View errorView;
    public View emptyView;
    private static final String TAG_FAKE_STATUS_BAR_VIEW = "statusBarView";
    private static final String TAG_MARGIN_ADDED = "marginAdded";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            RxBarTool.setStatusBarColor(this, R.color.colorPrimary);
        }

        if (application == null) {
            application = (MyApplication) getApplication();
        }
        loading = new RxDialogLoading(this);
        loading.setLoadingText("正在加载请稍后。。。");
    }


    @Override
    public void onResume() {
        super.onResume();
        //接收不同设备发送的广播
        receiver = new ScanReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(HONEYWELL_ACTION);
        mIntentFilter.addAction(RES_ACTION);
        mIntentFilter.addAction(SCN_CUST_ACTION_SCODE);
        mIntentFilter.addAction(NEW_LAND);
        registerReceiver(receiver, mIntentFilter);
        receiver.setScanListener(this::intentNext);
    }

    public void intentNext(String message) {

    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }


    /**
     * 正在加载view
     *
     * @param view view
     * @return view_network_error
     */
    protected View loadView(View view) {
        return getLayoutInflater().inflate(R.layout.view_loading, (ViewGroup) view.getParent(), false);
    }


    /**
     * 正在加载view
     *
     * @param view view
     * @return view_network_error
     */
    protected View emptyView(View view) {
        return getLayoutInflater().inflate(R.layout.view_empty, (ViewGroup) view.getParent(), false);
    }

    /**
     * 错误view
     *
     * @param view view
     * @return view_network_error
     */
    protected View errorView(View view) {
        return getLayoutInflater().inflate(R.layout.view_network_error, (ViewGroup) view.getParent(), false);
    }

    /**
     * 隐藏键盘
     *
     * @param view
     */
    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
