package com.shark.app.business.statich;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by King6rf on 2017/8/9. 存放接口的 为了再需要改的时候方便管也利于导出目前使用的接口
 */

public class UrlHome {


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

    /**
     * 获得没有类名点的签注的map请求
     * @param entity
     * @return
     */
    public static Map<String,Object> entity2MapHashClassNoPrefix(Object entity){
        Map<String,Object> result=new HashMap<>();
        String classname = entity.getClass().getSimpleName();
        Field[] declaredFields = entity.getClass().getDeclaredFields();//全反射防止出现字段保护的时候出现看不懂的bug 比如请求无效

        try {
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                if(declaredField.get(entity) == null){
                    result.put(declaredField.getName(),"");
                }else{
                    try {
                        result.put(declaredField.getName(),declaredField.get(entity).toString());
                    } catch (Exception e) {
                        result.put(declaredField.getName(),declaredField.get(entity)+"");
                        e.printStackTrace();
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获得有前缀的map请求
     * @param entity
     * @return
     */
    public static Map<String,String> entity2MapHashClassPrefix(Object entity){
        Map<String,String> result=new HashMap<>();
        String classname = entity.getClass().getSimpleName();
        Field[] declaredFields = entity.getClass().getDeclaredFields();//全反射防止出现字段保护的时候出现看不懂的bug 比如请求无效
        try {
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                if(declaredField.get(entity) == null){
                    result.put(classname+"."+declaredField.getName(),"");
                }else{
                    try {
                        result.put(classname+"."+declaredField.getName(),declaredField.get(entity).toString());
                    } catch (Exception e) {
                        result.put(classname+"."+declaredField.getName(),declaredField.get(entity)+"");
                        e.printStackTrace();
                    } 
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用户方便获得url的方法
     * @param context
     * @param url
     * @return
     */
    public static final String  getUrl(Context context,String url){
        return getIp(context)+url;
    }
    public static final String login="login!loginApps.action"; //
    public static final String enterpriselist="mobileEnterprise/mobileEnterpriseAction!list";//


}
