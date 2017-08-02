package com.businessframehelp.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by King6rf on 2017/5/25.
 * 用于反射资源
 */

public class ClassUtil {
    private static Map<String,Class> classmap=new HashMap<String,Class>();
    static {

    }
    public ClassUtil init(Class rclass){
        classmap.clear();
        Class[] classchild = rclass.getDeclaredClasses();
        for (int i = 0; i < classchild.length; i++) {
//            System.out.println("测试反射map"+classchild[i].getSimpleName());
            classmap.put(classchild[i].getSimpleName(),classchild[i]);
        }
        return this;
    }
    public static final  ClassUtil instance=new ClassUtil();
    private ClassUtil(){

    }
    public static ClassUtil getInstance(){
        return instance;
    }

    /**
     * resourcestring 格式为 R.xxx.name
     * @param resourcestring
     * @return
     */
    public  long getResourceId(String resourcestring){

        String[] array=resourcestring.split("\\.");
        String classname=array[1];
        String resourcename=array[2];
        try {
//            System.out.println("测试反射出"+ classname);
//            System.out.println("测试反射出"+ resourcename);
            Field field=classmap.get(classname).getField(resourcename);
            return field.getLong(null);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
}
