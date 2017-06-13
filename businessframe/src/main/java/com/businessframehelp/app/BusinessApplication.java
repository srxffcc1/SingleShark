package com.businessframehelp.app;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.blackframehelp.utils.BlackApplication;
import com.kymjs.common.SystemTool;
import com.morgoo.helper.Log;

/**
 * Created by Administrator on 2017/5/8.
 */

public class BusinessApplication extends BlackApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.sDebug= SystemTool.isApkDebugable(this);
        this.registerActivityLifecycleCallbacks(new BusinessActivityLifecycleCallbacks());


    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
    class BusinessActivityLifecycleCallbacks implements ActivityLifecycleCallbacks{

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            FrameActivityManager.instance().addActivity(activity);
            Log.v("ActivityLifecycleCallbacks", activity.getClass().getSimpleName()+":"+"onActivityCreated");

        }

        @Override
        public void onActivityStarted(Activity activity) {
            Log.v("ActivityLifecycleCallbacks", activity.getClass().getSimpleName()+":"+"onActivityStarted");

        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.v("ActivityLifecycleCallbacks", activity.getClass().getSimpleName()+":"+"onActivityResumed");
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.v("ActivityLifecycleCallbacks", activity.getClass().getSimpleName()+":"+"onActivityPaused");

        }

        @Override
        public void onActivityStopped(Activity activity) {
            Log.v("ActivityLifecycleCallbacks", activity.getClass().getSimpleName()+":"+"onActivityStopped");

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.v("ActivityLifecycleCallbacks", activity.getClass().getSimpleName()+":"+"onActivitySaveInstanceState");

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            FrameActivityManager.instance().finishActivity(activity);
            Log.v("ActivityLifecycleCallbacks", activity.getClass().getSimpleName()+":"+"onActivityDestroyed");
        }
    }
}
