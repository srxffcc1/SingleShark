package com.shark.app.business.db;

import android.content.Context;
import android.content.Intent;


import com.businessframehelp.db.ay.DBConn;
import com.businessframehelp.db.ay.ITableDao;
import com.businessframehelp.db.ay.ITableDaoImp;
import com.businessframehelp.db.ay.ITableFieldsDao;
import com.businessframehelp.db.ay.ITableFieldsDaoImp;
import com.businessframehelp.utils.HttpUrlConnectUtil;
import com.shark.app.business.statich.UrlHome;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.http.HttpConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BuildKu {

    private Context context;

    public BuildKu(Context context) {
        this.context = context;
    }

    public void bulid() {

        ITableFieldsDao mTableFields = new ITableFieldsDaoImp(context);
        ITableDao mTableRecords = new ITableDaoImp(context);
        String mTablename = "ZhiFaYiJuK";

        while (DBConn.dataLock) {
            DBConn.dataLock = false;
            String urlTable = "js/json/zfyjk_gg.json";
            String resultTable = null;
            try {
                resultTable = HttpUrlConnectUtil.doGet(UrlHome.getUrl(context, urlTable), null, HttpConfig.sCookie);
            } catch (Exception e) {
                e.printStackTrace();
            }

            boolean flag = mTableFields.add(mTablename, resultTable);// 本地保存表单结构

            DBConn db = DBConn.getInstance(context);

            String orderUrl = "js/json/zfyjk_order.json";
            String orderInfo = null;
            try {
                orderInfo = HttpUrlConnectUtil.doGet(UrlHome.getUrl(context, orderUrl), null, HttpConfig.sCookie);
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<String> list = new ArrayList<String>();
            Iterator<String> it = null;
            if (orderInfo == null || orderInfo.equals("null")
                    || orderInfo.equals("")) {
                String sql = "update tablefields set fieldsorder=" + 0
                        + " where tableid = '" + mTablename + "'";
                list.add(sql);
                db.executAffairsList(list);
            } else {
                try {
                    JSONObject jo = new JSONObject(orderInfo);
                    it = jo.keys();
                    while (it.hasNext()) {
                        String filedsName = it.next();
                        String order = jo.getString(filedsName);
                        String sql = "update tablefields set fieldsorder="
                                + Integer.parseInt(order)
                                + " where tableid = '" + mTablename
                                + "' and filedsName='" + filedsName + "'";
                        list.add(sql);
                        db.executAffairsList(list);
                    }
                } catch (Exception e) {
                }
            }
            it = null;
            list = null;
            break;
        }
        DBConn.dataLock = true;
        // 先清空数据
        mTableRecords.delAll(mTablename);
        // 获取数据。

        int count = 0;
        int size = 10;
        int cishu = 1;

        aa:
        {
            while (true) {
                while (DBConn.dataLock) {
                    DBConn.dataLock = false;
                    int start = (cishu - 1) * size;
                    int end = cishu * size;
                    String urlRecord = "zhiFaYiJuKu/zhiFaYiJuKuAction!list2?type=all" + "&startIndex=" + start + "&endIndex=" + end;
                    String resultRecord = null;
                    try {
                        resultRecord = HttpUrlConnectUtil.doGet(UrlHome.getUrl(context, urlRecord), null, HttpConfig.sCookie);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    boolean ceshi = mTableRecords.saveWHPList(mTablename,
                            resultRecord, "all");
                    if (count == 0) {
                        try {
                            JSONObject jo = new JSONObject(resultRecord);
                            count = jo.getInt("count");
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    Intent intent = new Intent("updateprgrg");
                    intent.putExtra("type", 0);
                    intent.putExtra("current", cishu);
                    intent.putExtra("all", count % size == 0 ? count / size
                            : count / size + 1);
                    System.out.println("SRX:数据库初始化"+cishu+":"+(count % size == 0 ? count / size
                            : count / size + 1));
                    context.sendBroadcast(intent);
                    if (count - cishu * size <= 0) {
                        break aa;
                    } else {
                        if (ceshi) {
                            cishu++;
                        }
                        break;
                    }
                }
                DBConn.dataLock = true;
            }
        }
        DBConn.dataLock = true;


    }

    Thread mDataThread = new Thread() {
        public void run() {

        }
    };

}
