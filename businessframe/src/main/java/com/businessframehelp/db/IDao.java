package com.businessframehelp.db;

import android.content.ContentValues;

/**
 * Created by Administrator on 2017/5/14.
 */

public interface IDao {
    boolean needCount();
    String getId();
    String geIdSelection();
    String getTable();
    String getNullColumnHack();
    ContentValues getValues();
    String getWhereClause();
    String[] getWhereArgs();
    String getSql();
    String getSqlCount();
    String[] getColumns();
    String getSelection();
    String[] getSelectionArgs();
    String getGroupBy();
    String getHaving();
    String getOrderBy();
    String getLimit();
    IDataBaseListener getListener();
}
