package com.android.other;

import android.app.Application;
import com.android.database.DBManager;
import org.xutils.x;

public class InternetRequest extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        DBManager.initDB(this);
    }
}
