package com.shark.app.business.singleactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.shark.app.R;

/**
 * Created by Administrator on 2017/5/13.
 */

public class ActivityAutoLogin extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_autologin);
        ProgressWheel progressWheel= (ProgressWheel) findViewById(R.id.progress_wheel);
        progressWheel.spin();

    }
}
