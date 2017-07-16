package com.businessframehelp.utils;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.businessframehelp.R;

/**
 * Created by King6rf on 2017/7/16.
 */

public class BarUtil {
    public static void initBar(AppCompatActivity appCompatActivity){
        Toolbar toolbar = (Toolbar) appCompatActivity.findViewById(R.id.toolbar);

// App Logo
//        toolbar.setLogo(R.drawable.ic_launcher);
// Title
//        toolbar.setTitle("My Title");
// Sub Title
//        toolbar.setSubtitle("Sub title");
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);



// Navigation Icon 要設定在 setSupoortActionBar 才有作用
// 否則會出現 back button
//        toolbar.setNavigationIcon(R.drawable.ab_android);
    }
}
