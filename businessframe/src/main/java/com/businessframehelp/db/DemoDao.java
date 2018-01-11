package com.businessframehelp.db;

import android.content.ContentValues;

import com.wisdomregulation.data.entitybase.Base_Entity;

/**
 * Created by King6rf on 2018/1/8.
 */

public class DemoDao implements IDao {

    public DemoDao(Base_Entity base_entity){

    }

    /**
     * 解析对象
     * @return
     */
    public DemoDao parese(){
        return this;
    }


    @Override
    public boolean needCount() {
        return false;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String geIdSelection() {
        return null;
    }

    @Override
    public String getTable() {
        return null;
    }

    @Override
    public String getNullColumnHack() {
        return null;
    }

    @Override
    public ContentValues getValues() {
        return null;
    }

    @Override
    public String getWhereClause() {
        return null;
    }

    @Override
    public String[] getWhereArgs() {
        return new String[0];
    }

    @Override
    public String getSql() {
        return null;
    }

    @Override
    public String getSqlCount() {
        return null;
    }

    @Override
    public String[] getColumns() {
        return new String[0];
    }

    @Override
    public String getSelection() {
        return null;
    }

    @Override
    public String[] getSelectionArgs() {
        return new String[0];
    }

    @Override
    public String getGroupBy() {
        return null;
    }

    @Override
    public String getHaving() {
        return null;
    }

    @Override
    public String getOrderBy() {
        return null;
    }

    @Override
    public String getLimit() {
        return null;
    }

    @Override
    public IDataBaseListener getListener() {
        return null;
    }
}
