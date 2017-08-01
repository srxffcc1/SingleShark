package com.blackframehelp.utils;

import android.app.Application;
import android.content.Context;

import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.stub.VASettings;

/**
 * Created by Administrator on 2017/5/8.
 */

public class BlackApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHelp.instance(this,null,"6fa0de9aa8");
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        VASettings.ENABLE_IO_REDIRECT = true;
        VASettings.ENABLE_INNER_SHORTCUT = false;
        try {
            VirtualCore.get().startup(base);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
