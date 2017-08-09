package com.shark.app.business.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;


@SuppressLint("ValidFragment")
public class FragmentManager{
    public static final FragmentManager instance=new FragmentManager();
    private FragmentManager(){

    }
    public static FragmentManager getInstance(){
        return instance;
    }
    public Fragment factory(String tag){
        Fragment fragment=null;
        switch (tag){
            case "首页":
                fragment=new FragmentMain();
                break;
            case "消息":
                fragment=new FragmentSetting();
                break;
            default:
                fragment=new FragmentError();
                break;
        }
        return fragment;
    }
}