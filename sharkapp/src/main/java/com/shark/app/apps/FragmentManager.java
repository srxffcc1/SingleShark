package com.shark.app.apps;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

import com.shark.app.fragment.FragmentError;
import com.shark.app.fragment.FragmentMain;
import com.shark.app.fragment.FragmentSetting;


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