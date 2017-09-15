package com.shark.app.business.singleactivity.module;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shark.app.R;
import com.shark.app.business.utils.SpHome;

/**
 * Created by Administrator on 2017/5/13.
 */

public class ActivitySplash extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.splash);
        if(!"".equals(SpHome.getSpHome().getString("username"))){
            startActivity(new Intent(ActivitySplash.this,ActivityAutoLogin.class));
            ActivitySplash.this.finish();
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(ActivitySplash.this,ActivityLogin.class));
                    ActivitySplash.this.finish();
                }
            },300);
        }

    }
}
