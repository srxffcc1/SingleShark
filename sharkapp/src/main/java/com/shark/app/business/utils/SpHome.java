package com.shark.app.business.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by King6rf on 2017/9/13.处理sp
 */

public class SpHome {
    public static boolean needlogin=false;
    private Context mcontext;
    public static final SpHome SP_HOME=new SpHome();
    public static SpHome getSpHome(){
        return SP_HOME;
    }

    public SpHome setMcontext(Context mcontext) {
        this.mcontext = mcontext;
        return this;
    }
    public void clearAll(){
        SharedPreferences sharedPreferences=mcontext.getSharedPreferences("config",Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
    public void remove(String... keys){
        SharedPreferences sharedPreferences=mcontext.getSharedPreferences("config",Context.MODE_PRIVATE);
        for (String key : keys) {
            sharedPreferences.edit().remove(key);
        }
        sharedPreferences.edit().commit();
    }
    public String getString(String key){
        SharedPreferences sharedPreferences=mcontext.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
    public int getInt(String key){
        SharedPreferences sharedPreferences=mcontext.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,0);
    }
    public boolean getBool(String key){
        SharedPreferences sharedPreferences=mcontext.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }
    public void put(String key,String value){
        SharedPreferences sharedPreferences=mcontext.getSharedPreferences("config",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key,value).commit();
    }
    public void put(String key,int value){
        SharedPreferences sharedPreferences=mcontext.getSharedPreferences("config",Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key,value).commit();
    }
    public void put(String key,boolean value){
        SharedPreferences sharedPreferences=mcontext.getSharedPreferences("config",Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key,value).commit();
    }
}
