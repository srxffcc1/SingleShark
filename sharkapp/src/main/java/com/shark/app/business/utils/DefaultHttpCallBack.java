package com.shark.app.business.utils;

import android.os.Looper;

import com.businessframehelp.app.FrameActivityManager;
import com.shark.app.business.ui.module.ActivityLogin;

import org.kymjs.kjframe.http.HttpCallBack;

/**
 * Created by King6rf on 2018/3/9. 默认实现
 */

public abstract class DefaultHttpCallBack extends HttpCallBack {
    public void onCookieTimeOut() {
        Looper.prepare();
        FrameActivityManager.instance().showToast("登录异常");
        FrameActivityManager.instance().finishAllActivity();
        FrameActivityManager.instance().startActivity(ActivityLogin.class);
        Looper.loop();



    }
}
