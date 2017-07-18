package com.businessframehelp.utils;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.businessframehelp.R;

/**
 * Created by King6rf on 2017/7/16.
 */

public class BarUtil {
    public static Toolbar initBar(AppCompatActivity appCompatActivity){
        Toolbar toolbar = (Toolbar) appCompatActivity.findViewById(R.id.toolbar);
        if(appCompatActivity.getSupportActionBar()!=null){
            try {
                appCompatActivity.setSupportActionBar(toolbar);
            } catch (Exception e) {
                e.printStackTrace();
            }
            appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }else if(appCompatActivity.getActionBar()!=null){
            appCompatActivity.getActionBar().setHomeButtonEnabled(true); //设置返回键可用
            appCompatActivity.getActionBar().setDisplayHomeAsUpEnabled(true);
        }
// App Logo
//        toolbar.setLogo(R.drawable.ic_launcher);
// Title
//        toolbar.setTitle("My Title");
// Sub Title
//        toolbar.setSubtitle("Sub title");
// Navigation Icon 要設定在 setSupoortActionBar 才有作用
// 否則會出現 back button
//        toolbar.setNavigationIcon(R.drawable.ab_android);
        return toolbar;
    }
}
