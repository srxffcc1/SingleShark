package com.businessframehelp.db;

import android.database.Cursor;

/**
 * Created by Administrator on 2017/5/14.
 */

public class SharkDb {

    public void insert2update(IDao dao) {
        final Boolean[] isexist = new Boolean[1];
        DataBaseManager.instance().open().query(dao.getTable(), dao.getColumns(), dao.geIdSelection(), new String[]{dao.getId()}, null, null, null, null, new IDataBaseListener() {
            @Override
            public void canvasWithCursor(Cursor cursor) {
                if (cursor == null) {
                    isexist[0] = false;
                } else {
                    isexist[0] = true;
                }
            }
        }).close();
        if (isexist[0]) {
            DataBaseManager.instance().open().update(dao.getTable(), dao.getValues(), dao.getWhereClause(), dao.getSelectionArgs());
        } else {
            DataBaseManager.instance().open().insert(dao.getTable(), dao.getNullColumnHack(), dao.getValues());
        }
    }
    public int query(IDao dao){
        final int[] counts=new int[1];
        if(dao.needCount()){
            DataBaseManager.instance().open().query(dao.getTable(), new String[]{"COUNT(*)"}, dao.getSelection(), dao.getSelectionArgs(), dao.getGroupBy(), dao.getHaving(), dao.getOrderBy(), dao.getLimit(), new IDataBaseListener() {
                @Override
                public void canvasWithCursor(Cursor cursor) {
                    counts[0]=cursor.getInt(0);
                }
            });
        }else{
            DataBaseManager.instance().open().query(dao.getTable(),dao.getColumns(),dao.getSelection(),dao.getSelectionArgs(),dao.getGroupBy(),dao.getHaving(),dao.getOrderBy(),dao.getLimit(),dao.getListener());
            counts[0]=0;
        }
        return counts[0];
    }
    public int rawQuery(IDao dao){
        final int[] counts=new int[1];
        if(dao.needCount()){
            DataBaseManager.instance().open().rawQuery(dao.getSqlCount(), null, new IDataBaseListener() {
                @Override
                public void canvasWithCursor(Cursor cursor) {
                    counts[0]=cursor.getInt(0);
                }
            });
        }else{
            DataBaseManager.instance().open().rawQuery(dao.getSql(),dao.getSelectionArgs(),dao.getListener());
            counts[0]=0;
        }
        return counts[0];
    }
}
