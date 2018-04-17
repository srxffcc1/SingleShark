package com.shark.app.business.view.module;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.shark.app.R;
import com.shark.app.business.view.home.smp.TabMainActivity;
import com.shark.app.business.statich.UrlHome;
import com.shark.app.business.urlentity.ELogin;
import com.shark.app.business.utils.SpHome;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpConfig;

/**
 * Created by Administrator on 2017/5/13.
 */

public class ActivityAutoLogin extends FrameActivity {
    boolean islogin=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_autologin);
        ProgressWheel progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        progressWheel.spin();
        toLogin();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(!islogin){
//                    Toast.makeText(getContext(),"自动登录失败尝试手动登录",Toast.LENGTH_SHORT).show();
//                    finish();
//                    startActivity(new Intent(getContext(),ActivityLogin.class));
//                }
//
//            }
//        },3000);

    }

    @Override
    public boolean needActionBar() {
        return false;
    }

    public void toLogin(){
        if(!SpHome.needlogin){
            startActivity(new Intent(getContext(),TabMainActivity.class));
            finish();
        }else {
            HttpConfig.sCookie = "";
            Toast.makeText(getContext(), "正在登录", Toast.LENGTH_SHORT).show();
            ELogin eLogin = new ELogin(SpHome.getSpHome().getString("username"), SpHome.getSpHome().getString("password"));
            httpGet(UrlHome.getUrl(this, UrlHome.login), UrlHome.entity2MapHashClassNoPrefix(eLogin), new HttpCallBack() {
                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    try {
                        JSONObject jsonObject = new JSONObject(t);
                        if (jsonObject.getBoolean("status")) {
                            String sessionid = jsonObject.getString("sessionId");
                            HttpConfig.sCookie = sessionid;
                            islogin = true;
                            startActivity(new Intent(getContext(), TabMainActivity.class));
                            finish();
                        } else {
                            SpHome.getSpHome().remove("username", "password");
                            Toast.makeText(getContext(), "自动登录失败尝试手动登录", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), ActivityLogin.class));
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
                    super.onFailure(errorNo, strMsg);
                    finish();
                    startActivity(new Intent(getContext(), ActivityLogin.class));
                }

                @Override
                public void onCookieTimeOut() {

                }
            });
        }

    }

    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public int getMenuid() {
        return -1;
    }

    @Override
    public void handleMessage(Message msg) {

    }
}
