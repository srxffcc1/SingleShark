package com.shark.app.apps;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.businessframehelp.app.BusinessApplication;
import com.businessframehelp.utils.ClassUtil;
import com.shark.app.R;

/**
 * Created by Administrator on 2017/5/9.
 */

public class SingleApplication extends BusinessApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("打开应用");
        ClassUtil.getInstance().init(R.class);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
