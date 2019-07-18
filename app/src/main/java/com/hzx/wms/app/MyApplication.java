package com.hzx.wms.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.hzx.wms.greendao.DaoMaster;
import com.hzx.wms.greendao.DaoSession;
import com.tencent.tinker.entry.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;
import com.vondear.rxtool.RxTool;


/**
 * @author qinl
 * @date 2019/7/3
 */
public class MyApplication extends Application {
    private ApplicationLike tinkerApplicationLike;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        // 我们可以从这里获得Tinker加载过程的信息
        tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

        // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
        TinkerPatch.init(tinkerApplicationLike)
                .reflectPatchLibrary()
                .setPatchRollbackOnScreenOff(true)
                .setPatchRestartOnSrceenOff(true);

        // 每隔3个小时去访问后台时候有更新,通过handler实现轮训的效果
        TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();

        RxTool.init(this);
        context = getApplicationContext();
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "hzx.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static Context getContext() {
        return context;
    }

    private static DaoSession daoSession;

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
