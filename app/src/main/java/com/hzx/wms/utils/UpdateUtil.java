package com.hzx.wms.utils;


import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;

import com.hzx.wms.BuildConfig;
import com.hzx.wms.http.Api;
import com.hzx.wms.http.HttpUtils;
import com.vondear.rxtool.view.RxToast;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by linhu on 2019/6/24.
 */

public class UpdateUtil {
    public static void check(final FragmentActivity activity, final boolean isShowToast) {
        HttpUtils.getInstance().createService(Api.class)
                .getVersion()
                .retry(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listBaseEntity -> {
                    if (Double.parseDouble(listBaseEntity.getData().get(0).getVersion()) <= BuildConfig.VERSION_CODE) {
                        if (isShowToast) {
                            RxToast.success("已是最新版本~");
                        }
                        return;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setCancelable(false);
                    builder.setTitle("发现新版本")
                            .setMessage(String.format("版本号: %s\n更新时间: %s\n更新内容:\n%s",
                                    listBaseEntity.getData().get(0).getVersion(),
                                    listBaseEntity.getData().get(0).getUpdated_at(),
                                    listBaseEntity.getData().get(0).getContent()));
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("立即下载", (dialogInterface, i) -> {
                        BaseTools.openLink(activity,listBaseEntity.getData().get(0).getUrl());
                    });
                    builder.show();
                }, throwable -> {
                });
    }
}


