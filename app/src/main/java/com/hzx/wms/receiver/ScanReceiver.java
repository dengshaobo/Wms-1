package com.hzx.wms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.vondear.rxtool.RxLogTool;


/**
 * @author qinl
 * @package qinl.com.hzxoderquery.receiver
 * @date 2019/4/16 15:09
 * @fileName ScanReceiver
 * @describe TODO
 */

public class ScanReceiver extends BroadcastReceiver {
    public static final String HONEYWELL_ACTION = "com.honeywell.decode.intent.action.EDIT_DATA";
    public static final String RES_ACTION = "android.intent.action.SCANRESULT";
    public static final String YOUBOXUN_ACTION = "com.android.server.scannerservice.broadcast";
    public static final String NEW_LAND = "nlscan.action.SCANNER_RESULT";
    ScanListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (HONEYWELL_ACTION.equals(intent.getAction())) {
            String message = intent.getStringExtra("data");
            listener.onNext(message);
            RxLogTool.e("条码：" + message);
        } else if (RES_ACTION.equals(intent.getAction())) {
            String message = intent.getStringExtra("value");
            listener.onNext(message);
        } else if (YOUBOXUN_ACTION.equals(intent.getAction())) {
            String message = intent.getStringExtra("scannerdata");
            listener.onNext(message);
        } else if (NEW_LAND.equals(intent.getAction())) {
            String message = intent.getStringExtra("SCAN_BARCODE1");
            listener.onNext(message);
        }
    }

    public interface ScanListener {
        void onNext(String msg);
    }

    public void setScanListener(ScanListener listener) {
        this.listener = listener;
    }
}

