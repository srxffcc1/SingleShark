package com.businessframehelp.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/5/13.
 */

public class DataBaseManager {
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private static final DataBaseManager instance = new DataBaseManager();
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper helper;
    private DataBaseManager() {
    }
    public static DataBaseManager instance() {
        return instance;
    }
    public  DataBaseManager init(SQLiteOpenHelper helper) {
        this.helper=helper;
        return this;
    }
    public DataBaseManager open(){
        openimp();
        return this;
    }
    public DataBaseManager close(){
        closeimp();
        return this;
    }
    public DataBaseManager beginTransaction(){
        mDatabase.beginTransaction();
        return this;
    }
    public DataBaseManager setTransactionSuccessful(){
        mDatabase.setTransactionSuccessful();
        return this;
    }
    public DataBaseManager endTransaction(){
        mDatabase.endTransaction();
        return this;
    }
    public DataBaseManager insert(String table, String nullColumnHack, ContentValues values){
        mDatabase.insert(table,nullColumnHack,values);
        return this;
    }
    public DataBaseManager delete(String table, String whereClause, String[] whereArgs){
        mDatabase.delete(table,whereClause,whereArgs);
        return this;
    }
    public DataBaseManager update(String table, ContentValues values, String whereClause, String[] whereArgs){
        mDatabase.update(table,values,whereClause,whereArgs);
        return this;
    }
    public DataBaseManager query(String table, String[] columns, String selection,
                                 String[] selectionArgs, String groupBy, String having,
                                 String orderBy, String limit,IDataBaseListener listener){
        Cursor cursor=mDatabase.query(table, columns, selection, selectionArgs, groupBy,
                having, orderBy, limit);
        if(listener!=null){
            listener.canvasWithCursor(cursor);
        }
        if(cursor!=null){
            cursor.close();
        }
        return this;
    }
    public DataBaseManager rawQuery(String sql, String[] selectionArgs,IDataBaseListener listener){
        Cursor cursor=mDatabase.rawQuery(sql,selectionArgs);
        if(listener!=null){
            listener.canvasWithCursor(cursor);
        }
        if(cursor!=null){
            cursor.close();
        }
        return this;
    }
    public DataBaseManager execSQL(String sql, Object[] bindArgs){
        mDatabase.execSQL(sql,bindArgs);
        return this;
    }

    private synchronized SQLiteDatabase openimp(){
        if (mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDatabase = helper.getWritableDatabase();
        }
        return mDatabase;
    }
    private synchronized void closeimp(){
        if (mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            mDatabase.close();
        }
    }

}
