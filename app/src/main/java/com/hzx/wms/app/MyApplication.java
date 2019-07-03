package com.hzx.wms.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hzx.wms.greendao.DaoMaster;
import com.hzx.wms.greendao.DaoSession;
import com.vondear.rxtool.RxSPTool;
import com.vondear.rxtool.RxTool;

/**
 * Created by linhu on 2019/6/19.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        RxTool.init(this);
        context=getApplicationContext();
        initGreenDao();

        String URL = RxSPTool.getString(context, "BaseUrl");
        if (URL.isEmpty()) {
            RxSPTool.putString(this, "BaseUrl", "http://192.168.0.162:8001/api/");
        }
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
