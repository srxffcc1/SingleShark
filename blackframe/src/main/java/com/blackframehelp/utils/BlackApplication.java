package com.blackframehelp.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/5/8.
 */

public class BlackApplication extends Application{//extends PluginApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHelp.instance(this,null,"6fa0de9aa8");
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
