package com.chenkovegor.gameofthrones.utils;

import android.app.Application;
import android.content.Context;

import com.chenkovegor.gameofthrones.data.storage.models.DaoMaster;
import com.chenkovegor.gameofthrones.data.storage.models.DaoSession;

import org.greenrobot.greendao.database.Database;

public class App extends Application{

    private static Context sContext;
    private static DaoSession sDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "gameofthrones-db");
        Database db = helper.getWritableDb();
        sDaoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession() {
        return sDaoSession;
    }


    public static Context getContext() {
        return sContext;
    }
}
