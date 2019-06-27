package com.hzx.wms.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.hzx.wms.greendao.DaoMaster;
import com.hzx.wms.greendao.DaoSession;
import com.vondear.rxtool.RxTool;

/**
 * Created by linhu on 2019/6/19.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxTool.init(this);
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "hzx.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private static DaoSession daoSession;

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
