package com.wisdomregulation.help;

import android.util.Log;

import com.wisdomregulation.data.entitybase.DateBase_Entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by King6rf on 2018/4/7.
 */

public class Demo_DbUtil {
    public static DateBase_Entity getEntityType(DateBase_Entity entity) {
        DateBase_Entity result = null;
        try {
            result = entity.getClass().newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            Log.v("SqliteError", e.toString());
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            Log.v("SqliteError", e.toString());
        }
        return result;
    }

    public static String getRealTime(String longstr) {
        String result = "";
        try {
            if (longstr.equals("")) {
                return "";
            }
            Date date = new Date(Long.parseLong(longstr));

            result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = "";
        }
        return result;

    }

    /**
     * 对不可变得数组进行包装 实现可以增加
     *
     * @param stringorg
     * @param target
     * @return
     */
    public static String[] arrayaddarray(String[] stringorg, String target) {
        String[] result = null;
        if (stringorg == null) {
            result = new String[]{target};

        } else {
            result = new String[stringorg.length + 1];
            for (int i = 0; i < stringorg.length; i++) {
                result[i] = stringorg[i];
            }
            result[result.length - 1] = target;
        }
        return result;
    }

    /**
     * 对不可变得数组进行包装 实现可以增加
     *
     * @param stringorg
     * @param target
     * @return
     */
    public static String[] arrayaddarray(String[] stringorg, String[] target) {
        String[] result = null;
        if (stringorg == null) {
            if (target != null) {
                result = target;
            } else {

            }


        } else {
            result = new String[stringorg.length + target.length];
            for (int i = 0; i < stringorg.length; i++) {
                result[i] = stringorg[i];
            }
            for (int i = stringorg.length; i < result.length; i++) {
                result[i] = target[i - stringorg.length];
            }

        }
        return result;
    }

    /**
     * 获得16位的uuid
     *
     * @return
     */
    public static String get16Uuid() {
        String leastbits = UUID.randomUUID().getLeastSignificantBits() + "";
        return leastbits;
    }

    /**
     * 获得32位的uuid
     *
     * @return
     */
    public static String get32Uuid() {
        String leastbits = UUID.randomUUID().toString().replaceAll("-", "");
        return leastbits;
    }

    /**
     * 转义特殊字符
     *
     * @param keyword
     * @return
     */
    public static String washString(String keyword) {
        if (keyword != null && !keyword.equals("")) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]",
                    "?", "^", "{", "}", "|"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    /**
     * db的帮助类直接获得查询结果集
     *
     * @param org
     * @return
     */
    public static List<DateBase_Entity> getSearchResult(List org) {
        List<DateBase_Entity> result = new ArrayList<DateBase_Entity>();
        if (org != null && org.size() > 1) {
            result = (List<DateBase_Entity>) org.get(1);
        } else {
        }
        return result;

    }

    /**
     * 只获得一个结果
     *
     * @param org
     * @return
     */
    public static DateBase_Entity getSearchResultOnlyOne(List org) {
        DateBase_Entity result = null;
        List org1 = getSearchResult(org);
        if (org1 != null && org1.size() > 0) {
            result = getSearchResult(org).get(0);
        } else {
            result = new DateBase_Entity().setId("-1");//用来证明数据不存在
        }

        return result;

    }
    public static String getLimit(int page,int rows) {
        int limit1 = (page - 1) * rows;
        int limit2 = rows;
        return limit1 + "," + limit2;
    }
    public static int getAllPage(String totalString,int rows) {
        int total=Integer.parseInt( totalString);
        int tmp = total % rows;
        if (tmp == 0) {
            return total / rows;
        } else {
            return (int) (total / rows) + 1;
        }

    }
    public static int getAllPage(int total,int rows) {
        int tmp = total % rows;
        if (tmp == 0) {
            return total / rows;
        } else {
            return (int) (total / rows) + 1;
        }

    }
}
