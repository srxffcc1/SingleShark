package com.shark.app.business.statich;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by King6rf on 2017/8/9.
 */

public class UrlHome {
    public static final String enterpriselist="";

    public static String getIp(Context context){
        final SharedPreferences SP = context.getSharedPreferences("IP",
                Context.MODE_PRIVATE);
        return SP.getString("ip","http://58.213.148.41:8805/jnzhaj/");

    }
    public static boolean setIp(Context context,String ip){
        final SharedPreferences SP = context.getSharedPreferences("IP",
                Context.MODE_PRIVATE);
        if(ip==null||"".equals(ip)){
            return false;
        }
        String check =  "^(?:https?://)?[\\w]{1,}(?:\\.?[\\w]{1,})+[\\w-_/?&=#%:]*$";
        if(!ip.matches(check)){
            return false;
        }
        SP.edit().putString("IP",ip).commit();
        return true;

    }
}
