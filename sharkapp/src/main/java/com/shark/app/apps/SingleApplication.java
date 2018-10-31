package com.shark.app.apps;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.businessframehelp.app.BusinessApplication;
import com.businessframehelp.service.GeTIntentService;
import com.businessframehelp.service.GeTPushService;
import com.businessframehelp.staticlib.StaticAppInfo;
import com.businessframehelp.utils.ClassUtil;
import com.hss01248.dialog.StyledDialog;
import com.igexin.sdk.PushManager;
import com.shark.app.R;
import com.shark.app.business.utils.SpHome;
import com.wisdomregulation.help.Demo_DBManager;

import org.kymjs.kjframe.http.HttpConfig;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2017/5/9.
 */

public class SingleApplication extends BusinessApplication {
    @Override
    public void onCreate() {
        super.onCreate();
//        //System.out.println("打开应用");

        new Thread(new Runnable() {
            @Override
            public void run() {
                ClassUtil.getInstance().init(R.class);//R辅助类
                StaticAppInfo.getInstance().init(getApplicationContext(), StaticAppInfo.StaticMode.test);//app信息辅助类
                PushManager.getInstance().initialize(getApplicationContext().getApplicationContext(), GeTPushService.class);//push
                // 注册 intentService 后 PushDemoReceiver 无效, sdk 会使用 DemoIntentService 传递数据,
                // AndroidManifest 对应保留一个即可(如果注册 DemoIntentService, 可以去掉 PushDemoReceiver, 如果注册了
                // IntentService, 必须在 AndroidManifest 中声明)
                PushManager.getInstance().registerPushIntentService(getApplicationContext(), GeTIntentService.class);
                SpHome.getSpHome().setMcontext(getApplicationContext());//初始化sp
                HttpConfig.needPHPSESSID=false;
                HttpConfig.TIMEOUT=2000;
                StyledDialog.init(getApplicationContext());
                Demo_DBManager.build().init(getApplicationContext(), getApplicationContext().getFilesDir().getAbsolutePath(),"releaseIII.db");//注册初始化离线数据库
                RongIM.init(getApplicationContext());
            }
        }).start();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
