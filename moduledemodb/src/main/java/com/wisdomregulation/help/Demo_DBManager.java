package com.wisdomregulation.help;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.wisdomregulation.data.entitybase.DateBase_Entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class Demo_DBManager {
    public static final int anjiandbversion = 1;
    public Context mcontext;
    private static Demo_DBManager dbhelper;
    public String limit = null;// 分页
    public String dbdir;//初始化db地址名字
    public boolean needcount = false;// 需返回全部页数
    public boolean isregex = false;// 是否模糊匹配 2016.6.18 修改为实体自己决定  如果设置也行 默认为false
    public boolean isfull = true;//   是否结果完整 行完整行 为了缩小返回集 * 还是只有 id，name,xxx
    public String logic = "";//or and
    public String order = "";//排序
    public SQLiteDatabase sqldb;
    public String databasename = "";//初始化db名字
    public int syncint = 0;
    public Demo_DBManager setDatabasename(String databasename) {
        this.databasename = databasename;
        return this;
    }



    private Demo_DBManager() {

    }

    public Demo_DBManager setDbdir(String dbdir) {
        this.dbdir = dbdir;
        return this;
    }

    private Demo_DBManager setContext(Context context) {
        this.mcontext = context;
        return this;
    }

    /**
     * 是否需要知道总数据数 为了分页
     *
     * @param needcount
     * @return
     */
    public Demo_DBManager setNeedcount(boolean needcount) {
        this.needcount = needcount;
        return this;
    }

    /**
     * 设置排序
     *
     * @param order
     * @return
     */
    public Demo_DBManager setOrder(String order) {
        this.order = order;
        return this;
    }


    /**
     * 是否结果字段完整 是否结果完整 行完整行 为了缩小返回集 * 还是只有 id，name,xxx
     *
     * @param isfull
     * @return
     */
    public Demo_DBManager setIsfull(boolean isfull) {
        this.isfull = isfull;
        return this;
    }

    /**
     * 是否模糊查询
     *
     * @param isregex
     * @return
     */
    public Demo_DBManager setIsregex(boolean isregex) {
        this.isregex = isregex;
        return this;
    }

    /**
     * 设置 or and
     *
     * @param logic2
     * @return
     */
    public Demo_DBManager setLogic(String logic2) {
        this.logic = logic2;
        return this;
    }

    public void init() {
        needcount = false;
        isregex = false;
        isfull = true;
        limit = "0,9999999";// 可能会存在bug
        logic = "and";
        order = "";
    }

    /**
     * 初始化
     *
     * @return
     */
    public static Demo_DBManager create() {
        Demo_DBManager result = null;
        if (dbhelper != null) {
            result = dbhelper;
        } else {
            result = new Demo_DBManager();
        }
        dbhelper.init();
        return result;

    }

    public static Demo_DBManager createS() {
        return dbhelper;
    }

    public void clear() {
        if (sqldb != null) {
            sqldb.close();
            sqldb.releaseReference();
            sqldb = null;
        }
    }

    public static Demo_DBManager release() {
        dbhelper.clear();
        return dbhelper;
    }

    /**
     * 单例数据库操作类初始化
     *
     * @param context
     * @param nowdatabasename
     * @return
     */
    public static Demo_DBManager create(Context context, String nowdatabasename) {
        if (dbhelper == null) {
            dbhelper = new Demo_DBManager();
            dbhelper.clear();
        } else {
            dbhelper.clear();
        }
        dbhelper.databasename = nowdatabasename;
        return dbhelper;
    }

    /**
     * 设置查询limit
     *
     * @param limit
     * @return
     */
    public Demo_DBManager setLimit(String limit) {
        this.limit = limit;
        return this;
    }

    /**
     * 获得db操作对象
     *
     * @param
     * @return
     */
    public SQLiteDatabase getDataBase() {

        return getDataBaseIn();


    }

    /**
     * 获得大于或者小于时间点之后的需要更新的数据
     *
     * @param time
     * @param logic
     * @return
     */
    public List<String> getSyncJson(String time, String logic) {
        List<String> resultList = new ArrayList<String>();
//		String key="client";
//		String ordertmp=order;
//		String selection = "datastate like ?";
//		String[] selectionArgs={"%" + key+ "%"};
//		if(time!=null&&!time.equals(""))
//		{
//			selection="datastate like ? and updatadate "+logic+" ?";
//			selectionArgs=new String[]{"%" + key+ "%",time};
//		}
//		SQLiteDatabase db=getDataBaseIn();
//		List<String> datanamelist=getAllTableName();
//		for (int i = 0; i < datanamelist.size(); i++) {
//			boolean needpring=false;
//			if(datanamelist.get(i).equalsIgnoreCase("Entity_Plan")||datanamelist.get(i).equalsIgnoreCase("Entity_Check")||datanamelist.get(i).equalsIgnoreCase("Entity_CheckDetail")){
//				needpring=true;
//			}
//			if(needpring){
//				Cursor cursor=db.query(datanamelist.get(i), null, selection, selectionArgs, null, null,
//						ordertmp.equals("")?null:ordertmp, null);
//
//				String[] cloumnname=null;
//				if(cursor!=null){
//					if(cursor.getCount()!=0){
//						while(cursor.moveToNext()){
//
//							String result=new String();
//							List<String> uptable= Util_Sync.getclientlist(cursor.getString(cursor.getColumnIndex("datastate")));
//							for (int j = 0; j < uptable.size(); j++) {
//							}
//							result="{";
//
//							String tabletmpname=Util_Sync.client2serverTableName(cursor.getString(cursor.getColumnIndex("tableid")));
//							result=""+result+""
//									+"'id':'"+cursor.getString(cursor.getColumnIndex("id"))+"',"
//									+ "'hashid':'"+cursor.getString(cursor.getColumnIndex("hashid"))+"',"
//									+ "'datastate':'client',"
//									+ "'CREATE_TIME':'"+getRealTime(cursor.getString(cursor.getColumnIndex("createdatadate")))+"',"
//									+ "'UPDATE_TIME':'"+getRealTime(cursor.getString(cursor.getColumnIndex("updatadate")))+"',"
//									+ "'createdatadate':'"+getRealTime(cursor.getString(cursor.getColumnIndex("createdatadate")))+"',"
//									+ "'tableid':'"+tabletmpname+"',"
//									+ "'isadd':'"+cursor.getString(cursor.getColumnIndex("isadd"))+"',"
//									+ "'CREATED':'"+cursor.getString(cursor.getColumnIndex("created"))+"',"
//									;
//							if(uptable.size()>0){
//								for (int j = 0; j < uptable.size(); j++) {
//								if(cursor.getString(j)!=null&&!cursor.getString(j).equals(" ")){
//								result=result+"'"+Util_Sync.client2serverField(tabletmpname, uptable.get(j))+"':'"+Util_Sync.client2serverField(tabletmpname,cursor.getString(cursor.getColumnIndex(uptable.get(j))))+"',";
//								}
//							}
//							}else{
//								for (int j = 0; j < cursor.getColumnCount(); j++) {
//								if(cursor.getString(j)!=null&&!cursor.getString(j).equals(" ")){
//									if(!cursor.getColumnName(j).equals("id")&&!cursor.getColumnName(j).equals("hashid")&&!cursor.getColumnName(j).equals("datastate")&&!cursor.getColumnName(j).equals("datadate")&&!cursor.getColumnName(j).equals("tableid")&&!cursor.getColumnName(j).equals("isadd")){
//										result=result+"'"+Util_Sync.client2serverField(tabletmpname, cursor.getColumnName(j))+"':'"+Util_Sync.client2serverField(tabletmpname,cursor.getString(j))+"',";
//									}
//
//								}
//							}
//							}
//							result=result.substring(0, result.length()-1);
//							result=result+"}";
//							resultList.add(result);
//						}
//					}
//
//				}
//				cursor.close();
//			}
//
//
//		}
//		close(db);
        return resultList;
    }

    /**
     * 获得数据文件名
     *
     * @param databasepath
     * @return
     */
    public static String getFileName(String databasepath) {
        String[] datapatharray = databasepath.split(File.separator);

        return datapatharray[datapatharray.length - 1].trim();
    }

    /**
     * 获得所有数据表名
     *
     * @return
     */
    public List<String> getAllTableName() {
        List<String> list = new ArrayList<String>();
        String dbpath = new File(dbdir + "/ZhiDbhome" + "/" + databasename).getAbsolutePath();
        //System.out.println("可能出错的数据库path:"+dbpath);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbpath,
                null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        Cursor cursor = db.rawQuery("select name from sqlite_master where type='table' order by name asc", null);
        while (cursor.moveToNext()) {
            String tablename = cursor.getString(0);
            if (!tablename.startsWith("android") && !tablename.startsWith("sqlite_master")) {
                list.add(tablename);
            }

        }
        db.releaseReference();
        db = null;
        return list;
    }

    /**
     * 将文件转化为可操作的db对象
     *
     * @param
     * @return
     */
    @SuppressLint("InlinedApi")
    public SQLiteDatabase getDataBaseIn() {
        SQLiteDatabase db;
        if (isDataBaseExists(dbdir + "/" + databasename)) {
            if (sqldb != null) {
                List<String> datanamelist = getAllTableName();
                if (datanamelist.size() < 1) {
                    sqldb.releaseReference();
                    sqldb = null;

                    String datapathfilename = getFileName(dbdir + "/" + databasename);
                    try {
                        sqldb.releaseReference();
                        sqldb = null;
                        SharedPreferences sp = mcontext.getSharedPreferences("releaseconfig", Context.MODE_PRIVATE);
                        sp.edit().putBoolean("dberror", true).commit();

                        InputStream is = mcontext.getAssets().open("" + databasename + "");
                        FileOutputStream fos = new FileOutputStream(dbdir + "/" + databasename);
                        byte[] buffer = new byte[1024];
                        int byteCount = 0;
                        while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                            // buffer字节
                            fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                        }
                        fos.flush();// 刷新缓冲区
                        is.close();
                        fos.close();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    db = SQLiteDatabase.openDatabase(new File(dbdir + "/" + databasename).getAbsolutePath(),
                            null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);

                    sqldb = db;
                } else {
                    db = sqldb;
                }

            } else {
                List<String> datanamelist = getAllTableName();
                if (datanamelist.size() < 1) {
                    sqldb.releaseReference();
                    sqldb = null;

                    String datapathfilename = getFileName(dbdir + "/" + databasename);
                    try {
                        InputStream is = mcontext.getAssets().open("" + databasename + "");
                        FileOutputStream fos = new FileOutputStream(dbdir + "/" + databasename);
                        byte[] buffer = new byte[1024];
                        int byteCount = 0;
                        while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                            // buffer字节
                            fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                        }
                        fos.flush();// 刷新缓冲区
                        is.close();
                        fos.close();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    db = SQLiteDatabase.openDatabase(new File(dbdir + "/" + databasename).getAbsolutePath(),
                            null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
                    sqldb = db;
                } else {
                    db = SQLiteDatabase.openDatabase(new File(dbdir + "/" + databasename).getAbsolutePath(),
                            null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
                    sqldb = db;
                }


            }

            db.enableWriteAheadLogging();
        } else {
            String datapathfilename = getFileName(dbdir + "/" + databasename);
            try {
                InputStream is = mcontext.getAssets().open("" + databasename + "");
                FileOutputStream fos = new FileOutputStream(dbdir + "/" + databasename);
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                    // buffer字节
                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                }
                fos.flush();// 刷新缓冲区
                is.close();
                fos.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            db = SQLiteDatabase.openDatabase(new File(dbdir + "/" + databasename).getAbsolutePath(),
                    null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
            sqldb = db;
        }
//		List<String> datanamelist=getAllTableName();
//		if(datanamelist.size()<1){
//			sqldb.releaseReference();
//			sqldb=null;
//
//			String datapathfilename = getFileName(dbdir+"/"+databasename);
//			try {
//				InputStream is = context.getAssets().open(""+databasename+"");
//				FileOutputStream fos = new FileOutputStream(dbdir+"/"+databasename);
//				byte[] buffer = new byte[1024];
//				int byteCount = 0;
//				while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
//																// buffer字节
//					fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
//				}
//				fos.flush();// 刷新缓冲区
//				is.close();
//				fos.close();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			db = SQLiteDatabase.openDatabase(new File(dbdir+"/"+databasename).getAbsolutePath(),
//					null,SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
//			sqldb=db;
//		}else{
//
//		}
        return db;
    }

    /**
     * 关闭db对象
     *
     * @param db
     */
    private void close(SQLiteDatabase db) {
//		db.releaseReference();
//		db.close();
//		db=null;
    }

    /**
     * 判断db文件是否存在
     *
     * @param databasepath
     * @return
     */
    public static boolean isDataBaseExists(String databasepath) {
        File isDataBaseExists = new File(databasepath);
        return isDataBaseExists.exists();
    }

    /**
     * 保存或者更新数据
     *
     * @param object
     * @return
     */
    public int save2update(Object object) {
        DateBase_Entity entity = (DateBase_Entity) object;
        SQLiteDatabase database = getDataBase();
        try {


            String tablename = getTableName(entity);
            ContentValues values = new ContentValues();
            List<String> uptablename = new ArrayList<String>();
            for (int i = 0; i < entity.size(); i++) {
                String sqlstring1 = entity.getField(i);
                String sqlstring2 = entity.getValue(i);
                if (sqlstring2 != null && !sqlstring2.equals(" ")) {
                    values.put(sqlstring1, sqlstring2);
                    uptablename.add(sqlstring1);
                    Log.v("Save", sqlstring1 + "," + sqlstring2 + "");
                }


            }
            if (entity.getId() == null || entity.getId().equals("")) {
                values.put("id", get16Uuid());
            } else {
                values.put("id", entity.getId());
            }
            Log.v("Save", "ID:" + values.get("id"));
            values.put("status", "1");
            values.put("hashid", get32Uuid());
//			values.put("datastate", Util_Sync.addclientstring(uptablename, "client"));
            values.put("createdatadate", (entity.getCreatedatadate().equals("") ? new Date().getTime() + "" : entity.getCreatedatadate()));
            values.put("updatadate", (entity.getUpDatadate().equals("") ? new Date().getTime() + "" : entity.getUpDatadate()));
            values.put("tableid", tablename);
            values.put("isadd", "1");
//			values.put("created", Static_InfoApp.create().getAccountId());
            String id = entity.getId();
            String isthis = "0";
            isthis = ((List<Object>) searchOnId(entity.setId(values.get("id").toString())).get(1)).size() + "";
            //System.out.println("判断要不要update"+isthis);
            long result;
            if (isthis.equals("0")) {
                Log.v("Save", "ID:" + values.get("id"));
                result = database.insert(tablename, null, values);
            } else {
                Log.v("Update", "ID:" + values.get("id"));
                result = update(object);
            }
            close(database);
            return (int) result;
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("SqliteError", e.toString());
        } finally {
            close(database);
        }
        return 0;

    }

    /**
     * 保存数据
     *
     * @param object
     * @return
     */
//	public  int save(Object object) {
//		try {
//			DateBase_Entity entity=(DateBase_Entity)object;
//			SQLiteDatabase database = getDataBase(entity);
//			String tablename = getTableName(entity);
//			ContentValues values = new ContentValues();
//			for (int i = 0; i < entity.size(); i++) {
//				String sqlstring1 = entity.getField(i);
//				String sqlstring2 = entity.getValue(i);
//				values.put(sqlstring1, sqlstring2);
//				com.wisdomregulation.utils.Log.v("Save", sqlstring2);
//
//			}
//			if(entity.getId()==null||entity.getId().equals("")){
//				values.put("id", get16Uuid());
//			}else{
//				values.put("id", entity.getId());
//			}
//			com.wisdomregulation.utils.Log.v("Save", "ID:"+values.get("id"));
//			values.put("hashid", get32Uuid());
//			values.put("datastate", "client");
//			values.put("datadate", new Date().getTime() + "");
//			values.put("tableid", tablename);
//			values.put("isadd", "1");
//
//			String id=entity.getId();
//			String isthis="0";
//			isthis=((List<Object>)this.searchOnId(entity.setId(values.get("id").toString())).get(1)).size()+"";
//			long result;
//			if(isthis.equals("0")){
//				result= database.insert(tablename, null, values);
//			}else{
//				result=0;
//			}
//			close(database);
//			return (int) result;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			com.wisdomregulation.utils.Log.v("SqliteError",e.toString());
//		}
//		return 0;
//	}

//	public  int delete(Object object) {
//		DateBase_Entity entity=(DateBase_Entity)object;
//		String[] whereArgs = { entity.getId(), entity.getHashid() };
//		SQLiteDatabase database = getDataBase(entity);
//		String tablename = getTableName(entity);
//		long result = database.delete(tablename, "id = ? and hashid = ?",
//				whereArgs);
//		close(database);
//		return (int) result;
//	}
    public int truncate(Object object) {
        try {
            //System.out.println("清空数据库内容");
            DateBase_Entity entity = (DateBase_Entity) object;
            SQLiteDatabase database = getDataBase();
            String tablename = getTableName(entity);
            long result = database.delete(tablename, null,
                    null);
            close(database);
            return (int) result;
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("SqliteError", e.toString());
        }
        return 0;

    }

    /**
     * 删除
     *
     * @param object
     * @return
     */

    public int delete(Object object) {
//		try {
//			DateBase_Entity entity=(DateBase_Entity)object;
//			String where="";
//			String id="";
//			id=entity.getId();
//			String[] whereArgs = null;
//			ContentValues values = new ContentValues();
//			if(!entity.getId().equals("")){
//				where=where+" id = ?";
//				whereArgs=arrayaddarray(whereArgs, entity.getId());
//			}
//			values.put("status","0");
////			for (int i = 0; i < entity.size(); i++) {
////				if(!entity.getValue(i).equals(" ")){
////					where=where+" "+entity.getField(i)+" = ? and";
////					whereArgs=arrayaddarray(whereArgs, entity.getValue(i));
////				}
////
////			}
////			if(where.length()>3){
////				where=where.substring(0, where.length()-3);
////			}
////			for (int i = 0; i < whereArgs.length; i++) {
////				com.wisdomregulation.utils.Log.v("DeleteTag", whereArgs[i]);
////			}
//			Log.v("DeleteTag", where);
//			SQLiteDatabase database = getDataBase();
//			String tablename = getTableName(entity);
//			if(!id.equals("")){
//				DateBase_Entity entityadd=new Entity_DeleteHistory().put(0, tablename).put(1, id);
//				save2update(entityadd);
//
//			}
//			database.update(tablename, values, "id = ? ", whereArgs);
////			long result = database.delete(tablename, where,
////					whereArgs);
//			close(database);
//			return (int) 1;
//		} catch (Exception e) {
//			e.printStackTrace();
//			Log.v("SqliteError",e.toString());
//		}
        return 0;
    }
//	public  int deleteSimple(Object object) {
//		DateBase_Entity entity=(DateBase_Entity)object;
//		String deletestring="";
//		String deletekey="";
//		for (int i = 0; i < entity.size(); i++) {
//					if(entity.getValue(i)!=null&&!entity.equals("")){
//						deletestring=entity.getValue(i);
//						deletekey=entity.getField(i);
//					}
//		}
//		if(!entity.getId().equals(" ")){
//			deletestring=entity.getId();
//			deletekey="id";
//		}
//		String[] whereArgs = {deletestring};
//
//		SQLiteDatabase database = getDataBase(entity);
//		String tablename = getTableName(entity);
//		long result = database.delete(tablename, ""+deletekey+" = ?",
//				whereArgs);
//		close(database);
//		return (int) result;
//	}


    /**
     * 更新数据
     *
     * @param object
     * @return
     */
    public int update(Object object) {
        try {
            Log.v("DBUpdate", "OK");
            DateBase_Entity entity = (DateBase_Entity) object;
            DateBase_Entity entity2 = getSearchResultOnlyOne(searchOnId(object));
            String[] whereArgs = {entity.getId()};
            SQLiteDatabase database = getDataBase();
            String tablename = getTableName(entity);
            ContentValues values = new ContentValues();
            List<String> uptablename = new ArrayList<String>();
            Log.v("DBUpdate", "OK" + entity.size());
            for (int i = 0; i < entity.size(); i++) {
                Log.v("Update", "OK");
                String sqlstring1 = entity.getField(i);
                String sqlstring2 = entity.getValue(i);
                if (sqlstring2 != null && !sqlstring2.equals(" ")) {
                    if (!entity2.getValue(i).equals(sqlstring2)) {

                        values.put(sqlstring1, sqlstring2);
                        uptablename.add(sqlstring1);
                        Log.v("UpdateName", "Update:" + sqlstring1);
                        Log.v("UpdateValue", "Update:" + sqlstring2);
                    }
                }
            }
            values.put("updatadate", (entity.getUpDatadate().equals("") ? new Date().getTime() + "" : entity.getUpDatadate()));
            uptablename.add("updatadate");
//			values.put("datastate", Util_Sync.addclientstring(uptablename, entity2.getDatastate()));


            long result = database.update(tablename, values,
                    "id = ? ", whereArgs);
            close(database);
            //System.out.println("updatehelp:"+result);
            return (int) result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.v("UpdateSqliteError", e.toString());
        }
        return 0;
    }

    /**
     * 以对象的id在特定表搜索数据
     *
     * @param object
     * @return
     */
    private List<Object> searchOnId(Object object) {
        try {
            Log.v("Cur", "751:searchOnId");
            DateBase_Entity entity = (DateBase_Entity) object;
            SQLiteDatabase database = getDataBase();
            String tablename = getTableName(entity);
            List resultlist = new ArrayList();
            resultlist.clear();
            List<DateBase_Entity> entitylist = new ArrayList<DateBase_Entity>();
            ContentValues values = new ContentValues();
            Cursor allcur = null;
            Cursor cur = null;
            String[] searchcolumn = null;
            String selection = "id = ?";
            String ordertmp = order;
            String[] selectionArgs = {entity.getId()};
            Log.v("searchOnId", tablename);
            Log.v("searchOnId", selection);
            Log.v("searchOnId", selectionArgs[0]);
            cur = database.query(tablename, null, selection, selectionArgs, null, null,
                    ordertmp.equals("") ? null : ordertmp, null);

            if (cur != null) {
                Log.v("Cur", "772:nonull");
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//					Log.v("Cur", "774:curhashvalue");
                    DateBase_Entity tmpentity = getEntityType(entity);

                    if (isfull) {
//						Log.v("Cur", "size"+":"+tmpentity.size()+"");
                        for (int i = 0; i < tmpentity.size(); i++) {

                            int colu = cur.getColumnIndex(tmpentity.getField(i));
                            tmpentity.put(i,
                                    cur.getString(colu));

                        }
                    }
                    tmpentity.setId(cur.getString(cur.getColumnIndex("id")));
                    tmpentity.setHashid(cur.getString(cur.getColumnIndex("hashid")));
                    tmpentity.setDatastate(cur.getString(cur.getColumnIndex("datastate")));
                    tmpentity.setUpDatadate(cur.getString(cur.getColumnIndex("updatadate")));
                    tmpentity.setTableid(cur.getString(cur.getColumnIndex("tableid")));
                    tmpentity.setCreated(cur.getString(cur.getColumnIndex("created")));
                    entitylist.add(tmpentity);
                }
                if (allcur != null) {
                    resultlist.add(allcur.getCount() + "");
                } else {
                    resultlist.add(0 + "");
                }
                Log.v("Cur", "799:result" + ":" + entitylist.size() + "");
                resultlist.add(entitylist);
                if (allcur != null) {
                    allcur.close();
                }
                if (cur != null) {
                    cur.close();
                }
                Log.v("Cur", "resultlist" + ":" + resultlist.size() + "");
                close(database);
                return resultlist;
            } else {
                close(database);
                return null;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.v("SqliteError", e.toString());
        }
        return null;

    }

    /**
     * 获得一个sql语句
     *
     * @param
     * @return
     */

    public void execute(String sql) {
        SQLiteDatabase database = getDataBase();
        if (sql.equals("")) {

        } else {
            if (sql.matches(".*drop.*")) {

            } else {
                database.execSQL(sql);
            }

        }

        close(database);
    }

    public void executebeginTransaction(String sql) {
        if (sql.equals("")) {
            syncint++;
            //System.out.println("东西为空");
        } else {
            //System.out.println("进行事务");
            if (sql.matches("(.*)delete(.*)")) {
                //System.out.println("操作删除");
            } else {
                //System.out.println("操作普通");
            }
//			Log.v("Sql", sql);
            sqldb.execSQL(sql);
            syncint++;

        }
        //System.out.println("完成了数据量:"+syncint);

    }

    public void beginTransaction() {
        syncint = 0;
//		System.gc();// gc
//		System.runFinalization();// gc
        getDataBaseIn();
//		sqldb.beginTransaction();
    }

    public void execute(List<String> sqllist) {
        for (int i = 0; i < sqllist.size(); i++) {
            String sql = sqllist.get(i);
            if (sql.equals("")) {

            } else {
                sqldb.execSQL(sql);
                sql = null;
            }
        }
        sqllist.clear();
        sqllist = null;
    }

    public void endbeginTransaction() {
        syncint = 0;
//
//		sqldb.setTransactionSuccessful();
//		sqldb.endTransaction();
        //System.out.println("确认关闭事务");
    }

    /**
     * 差集
     *
     * @param object
     * @return
     */
    public String justgetSqlExcept(Object... object) {
        String sqls = "";
        for (int i = 0; i < object.length; i++) {
            String sqlObject = justgetSql(object[i]);
            //System.out.println("查询分"+i+":"+sqlObject);
            sqls = sqls + sqlObject + " EXCEPT ";
        }
        if (sqls.length() > 10) {
            sqls = sqls.substring(0, sqls.length() - " EXCEPT ".length());
        }
        System.out.println("查询总:" + sqls);
        return "" + sqls + "";
    }

    /**
     * 交集
     *
     * @param object
     * @return
     */
    public String justgetSqlINTERSECT(Object... object) {
        String sqls = "";
        for (int i = 0; i < object.length; i++) {
            String sqlObject = justgetSql(object[i]);
            //System.out.println("查询分"+i+":"+sqlObject);
            sqls = sqls + sqlObject + " INTERSECT ";
        }
        if (sqls.length() > 10) {
            sqls = sqls.substring(0, sqls.length() - " INTERSECT ".length());
        }
        //System.out.println("查询总:"+sqls);
        return "" + sqls + "";
    }

    /**
     * 并集
     *
     * @param object
     * @return
     */
    public String justgetSqlUNION(Object... object) {
        String sqls = "";
        for (int i = 0; i < object.length; i++) {
            String sqlObject = justgetSql(object[i]);
            //System.out.println("查询分"+i+":"+sqlObject);
            sqls = sqls + sqlObject + " UNION ";
        }
        if (sqls.length() > 10) {
            sqls = sqls.substring(0, sqls.length() - " UNION ".length());
        }
        Log.v("Cur", "921:" + sqls);
        return "" + sqls + "";
    }

    public String justgetSql(Object object) {
        String sqls = "";
        if (object.getClass().getSimpleName().equals(String.class.getSimpleName())) {
            sqls = (String) object;
        } else {
            List resultlist = new ArrayList();
            if (object != null) {
                DateBase_Entity entity = (DateBase_Entity) object;
                SQLiteDatabase database = getDataBase();
                String tablename = getTableName(entity);

                resultlist.clear();
                List<DateBase_Entity> entitylist = new ArrayList<DateBase_Entity>();
                ContentValues values = new ContentValues();
                Cursor allcur = null;
                Cursor cur = null;
                String[] searchcolumn = null;
                String selection = "";
                String[] selectionArgs = null;
                if (entity.getId() != null && !entity.getId().equals("")) {
                    selection = selection + entity.getDbFieldSql("id", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("id"));

                }
                if (entity.getHashid() != null && !entity.getHashid().equals("")) {
                    selection = selection + entity.getDbFieldSql("hashid", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("hashid"));

                }
                if (entity.getDatastate() != null && !entity.getDatastate().equals("")) {
                    selection = selection + entity.getDbFieldSql("datastate", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("datastate"));

                }
                if (entity.getUpDatadate() != null && !entity.getUpDatadate().equals("")) {
                    selection = selection + entity.getDbFieldSql("updatadate", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("updatadate"));

                }
                if (entity.getTableid() != null && !entity.getTableid().equals("")) {
                    selection = selection + entity.getDbFieldSql("tableid", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("tableid"));

                }
                if (entity.getCreated() != null && !entity.getCreated().equals("")) {
                    selection = selection + entity.getDbFieldSql("created", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("created"));

                }
                if (selection != null && selection.length() > 0 && !logic.equals("or")) {
                    selection = selection + " status <> 0 " + logic;
                }
                for (int i = 0; i < entity.size(); i++) {
                    String columnname = entity.getField(i);
                    String columnvalue = entity.getValue(i);
                    if (!columnvalue.equals("") && !columnvalue.equals(" ")) {
                        selection = selection + entity.getDbFieldSql(i, logic);
                        selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue(i));
                    }

                }
                if (selection != null && selection.length() > 0) {
                    selection = selection.substring(0, selection.length() - logic.length());
                }

                Log.v("Cur", "selection:" + selection);
                if (selectionArgs != null && selectionArgs.length > 0) {
                    for (int i = 0; i < selectionArgs.length; i++) {
                        Log.v("Cur", "selectionArg:" + selectionArgs[i]);
                    }
                }

                sqls = SQLiteQueryBuilder.buildQueryString(false, tablename, null, selection, null, null, null, null);
                if (selectionArgs != null) {
                    for (int i = 0; i < selectionArgs.length; i++) {
                        sqls = sqls.replaceFirst("\\?", "'" + washString(selectionArgs[i]) + "'");
                    }
                } else {

                }

            }
        }
        Log.v("Cur", "1006:" + sqls);
        return sqls;
    }


    /**
     * 最后一个传入一个结果实体 实际是交集特有的query
     *
     * @param object
     * @return
     */
    public List<Object> searchFromResult(Object... object) {
        Log.v("Cur", "1007:Int");
        List resultlist = new ArrayList();
        String limittmp = limit;
        //System.out.println("我的Limit："+limittmp);
        try {
            if (object != null) {

                SQLiteDatabase database = getDataBase();
                DateBase_Entity entity = (DateBase_Entity) object[object.length - 1];
                resultlist.clear();
                List<DateBase_Entity> entitylist = new ArrayList<DateBase_Entity>();
                Cursor allcur = null;
                Cursor cur = null;
                String sqlparent = justgetSqlINTERSECT(object) + " limit " + limittmp + "";
                //System.out.println("查询1:"+sqlparent);
                cur = database.rawQuery(sqlparent, null);
                if (needcount) {
                    Log.v("Cur", "1034:needcount");
                    String sqlparent2 = justgetSqlINTERSECT(object);
                    //System.out.println("查询2:"+sqlparent2);
                    allcur = database.rawQuery(sqlparent2, null);
                }

                if (cur != null) {
                    Log.v("Cur", "1041:nonull");
                    for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//						Log.v("Cur", "1043:curhashvalue");
                        DateBase_Entity tmpentity = getEntityType(entity);
                        if (isfull) {
//							Log.v("Cur", "size"+":"+tmpentity.size()+"");
                            for (int i = 0; i < tmpentity.size(); i++) {
//								Log.v("Cur", "coloumname:"+tmpentity.getField(i));
                                int colu = cur.getColumnIndex(tmpentity.getField(i));
//								Log.v("Cur", "coloumvalue:"+cur.getString(colu));
                                try {
                                    tmpentity.put(i,
                                            cur.getString(colu));
                                } catch (Exception e) {
                                    Toast.makeText(mcontext, "数据库和应用部分不匹配\n建议升级", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }
                        tmpentity.setId(cur.getString(cur.getColumnIndex("id")));
                        tmpentity.setHashid(cur.getString(cur.getColumnIndex("hashid")));
                        tmpentity.setDatastate(cur.getString(cur.getColumnIndex("datastate")));
                        tmpentity.setCreatedatadate(cur.getString(cur.getColumnIndex("createdatadate")));
                        tmpentity.setUpDatadate(cur.getString(cur.getColumnIndex("updatadate")));
                        tmpentity.setTableid(cur.getString(cur.getColumnIndex("tableid")));
                        tmpentity.setCreated(cur.getString(cur.getColumnIndex("created")));
                        entitylist.add(tmpentity);
                    }
                    if (allcur != null) {
                        resultlist.add(allcur.getCount() + "");
                    } else {
                        resultlist.add(0 + "");
                    }
                    Log.v("Cur", "1075:result" + ":" + entitylist.size() + "");
                    resultlist.add(entitylist);
                    if (allcur != null) {
                        allcur.close();
                    }
                    if (cur != null) {
                        cur.close();
                    }
                    Log.v("Cur", "1083:resultlist" + ":" + resultlist.size() + "");
                    close(database);
//					return resultlist;
                } else {
                    close(database);
//					return resultlist;
                }
            } else {
//				return resultlist;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.v("SqliteError", "1097:" + e.toString());
        }
        if (resultlist.size() == 0) {
            resultlist.add(0 + "");
            resultlist.add(null);
        }
        return resultlist;

    }


    /**
     * 进行结果查找 可以是用并集
     *
     * @param sql
     * @param object 结果实体 只是用于确认类型
     * @return
     */
    public List<Object> query(String sql, Object object) {
        Log.v("Cur", "query");
        List resultlist = new ArrayList();
        DateBase_Entity entity = (DateBase_Entity) object;
        List<DateBase_Entity> entitylist = new ArrayList<DateBase_Entity>();
        SQLiteDatabase database = getDataBase();
        String limittmp = limit;
        Cursor allcur = null;
        Cursor cur = null;
        sql = sql + " INTERSECT " + "SELECT * from " + object.getClass().getSimpleName() + " WHERE " + object.getClass().getSimpleName() + ".status <> 0";
        Log.v("Cur", "1123:" + sql);
        cur = database.rawQuery(sql + " limit " + limittmp, null);
        if (needcount) {
            Log.v("Cur", "needcount");
            allcur = database.rawQuery(sql, null);
        }
        if (cur != null) {
            Log.v("Cur", "nonull");
            for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//				Log.v("Cur", "curhashvalue");
                DateBase_Entity tmpentity = getEntityType(entity);
                if (isfull) {
//					Log.v("Cur", "size"+":"+tmpentity.size()+"");
                    for (int i = 0; i < tmpentity.size(); i++) {
//						Log.v("Cur", "coloumname:"+tmpentity.getField(i));
                        int colu = cur.getColumnIndex(tmpentity.getField(i));
                        if (colu == -1) {

                        } else {
                            try {
                                tmpentity.put(i, cur.getString(colu));
                            } catch (Exception e) {
                                Looper.prepare();
                                Toast.makeText(mcontext, "数据库和应用部分不匹配\n建议升级", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }

                        }
//						Log.v("Cur", "coloumvalue:"+cur.getString(colu));


                    }
                    tmpentity.setId(cur.getString(cur.getColumnIndex("id")));
                    tmpentity.setHashid(cur.getString(cur.getColumnIndex("hashid")));
                    tmpentity.setDatastate(cur.getString(cur.getColumnIndex("datastate")));
                    tmpentity.setCreatedatadate(cur.getString(cur.getColumnIndex("createdatadate")));
                    tmpentity.setUpDatadate(cur.getString(cur.getColumnIndex("updatadate")));
                    tmpentity.setTableid(cur.getString(cur.getColumnIndex("tableid")));
                    tmpentity.setCreated(cur.getString(cur.getColumnIndex("created")));
                    entitylist.add(tmpentity);
                }

            }
            if (allcur != null) {
                resultlist.add(allcur.getCount() + "");
            } else {
                resultlist.add(0 + "");
            }
            Log.v("Cur", "result" + ":" + entitylist.size() + "");
            resultlist.add(entitylist);
            if (allcur != null) {
                allcur.close();
            }
            if (cur != null) {
                cur.close();
            }
            Log.v("Cur", "resultlist" + ":" + resultlist.size() + "");
            close(database);
//			return resultlist;
        } else {
            close(database);
//			return resultlist;
        }
        return resultlist;
    }

    public List<Object> searchRandom(Object object) {
        Log.v("Cur", "limit=" + (limit == null ? "null" : limit));
        Log.v("Cur", "search");
        Log.v("Cur", "Int");
        List resultlist = new ArrayList();
        String limittmp = limit;
        String ordertmp = "random()";
        SQLiteDatabase database = getDataBase();
        try {
            if (object != null) {
                DateBase_Entity entity = (DateBase_Entity) object;
                if (isregex) {
                    entity.putlogicAll("like");
                }

                String tablename = getTableName(entity);

                resultlist.clear();
                List<DateBase_Entity> entitylist = new ArrayList<DateBase_Entity>();
                ContentValues values = new ContentValues();
                Cursor allcur = null;
                Cursor cur = null;
                String[] searchcolumn = null;
                String selection = "";
                String[] selectionArgs = null;
                if (entity.getId() != null && !entity.getId().equals("")) {
                    selection = selection + entity.getDbFieldSql("id", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("id"));

                }
                if (entity.getHashid() != null && !entity.getHashid().equals("")) {
                    selection = selection + entity.getDbFieldSql("hashid", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("hashid"));

                }
                if (entity.getDatastate() != null && !entity.getDatastate().equals("")) {
                    selection = selection + entity.getDbFieldSql("datastate", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("datastate"));

                }
                if (entity.getUpDatadate() != null && !entity.getUpDatadate().equals("")) {
                    selection = selection + entity.getDbFieldSql("updatadate", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("updatadate"));

                }
                if (entity.getTableid() != null && !entity.getTableid().equals("")) {
                    selection = selection + entity.getDbFieldSql("tableid", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("tableid"));

                }
                if (entity.getCreated() != null && !entity.getCreated().equals("")) {
                    selection = selection + entity.getDbFieldSql("created", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("created"));

                }
                for (int i = 0; i < entity.size(); i++) {
                    String columnname = entity.getField(i);
                    String columnvalue = entity.getValue(i);
                    if (!columnvalue.equals("") && !columnvalue.equals(" ")) {
                        selection = selection + entity.getDbFieldSql(i, logic);
                        selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue(i));


                    }

                }
                if (selection != null && selection.length() > 0 && !logic.equals("or")) {
                    selection = selection + " status <> 0 " + logic;
                }

                if (selection != null && selection.length() > 0) {
                    selection = selection.substring(0, selection.length() - logic.length());
                }

                Log.v("Cur", "selection:" + selection);
                if (selectionArgs != null && selectionArgs.length > 0) {
                    for (int i = 0; i < selectionArgs.length; i++) {
                        Log.v("Cur", "selectionArg:" + selectionArgs[i]);
                    }
                }


                if (needcount) {
//					if(selection==null||selection.equals("")||selection.equals(" ")){
//						resultlist.arrayaddarray(0 + "");
//						return resultlist;
//					}
                    Log.v("Cur", "needcount");
                    allcur = database.query(tablename, new String[]{"COUNT(*)"}, selection, selectionArgs, null, null, ordertmp.equals("") ? null : ordertmp, null);
                }
                Log.v("Cur", "table:" + tablename);
                Log.v("Cur", "limit:" + limittmp);
                Log.v("Order", object.getClass().getSimpleName() + ":" + ordertmp);
//				if(selection==null||selection.equals("")||selection.equals(" ")){
//					resultlist.arrayaddarray(0 + "");
//					return resultlist;
//				}
                cur = database.query(tablename, searchcolumn, selection, selectionArgs, null, null,
                        ordertmp.equals("") ? null : ordertmp, limittmp);

                if (cur != null) {
                    Log.v("Cur", "nonull");
                    for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//						Log.v("Cur", "curhashvalue");
                        DateBase_Entity tmpentity = getEntityType(entity);
                        if (isfull) {
//							Log.v("Cur", "size"+":"+tmpentity.size()+"");
                            for (int i = 0; i < tmpentity.size(); i++) {
//								Log.v("Cur", "coloumname:"+tmpentity.getField(i));
                                int colu = cur.getColumnIndex(tmpentity.getField(i));

                                try {
                                    tmpentity.put(i,
                                            cur.getString(colu));
//
                                } catch (Exception e) {
                                    Log.v("Cur", "colonname:" + tmpentity.getField(i));
                                    e.printStackTrace();

                                }

                            }
                        }
                        tmpentity.setId(cur.getString(cur.getColumnIndex("id")));
                        tmpentity.setHashid(cur.getString(cur.getColumnIndex("hashid")));
                        tmpentity.setDatastate(cur.getString(cur.getColumnIndex("datastate")));
                        tmpentity.setCreatedatadate(cur.getString(cur.getColumnIndex("createdatadate")));
                        tmpentity.setUpDatadate(cur.getString(cur.getColumnIndex("updatadate")));
                        tmpentity.setTableid(cur.getString(cur.getColumnIndex("tableid")));
                        tmpentity.setTableid(cur.getString(cur.getColumnIndex("created")));
                        entitylist.add(tmpentity);
                    }
                    if (allcur != null) {
                        allcur.moveToFirst();
                        resultlist.add(allcur.getInt(0) + "");
                    } else {
                        resultlist.add(0 + "");
                    }
                    Log.v("Cur", "1326:result" + ":" + entitylist.size() + "");
                    resultlist.add(entitylist);
                    if (allcur != null) {
                        allcur.close();
                    }
                    if (cur != null) {
                        cur.close();
                    }
                    Log.v("Cur", "1334:resultlist" + ":" + resultlist.size() + "");
                    close(database);
//					return resultlist;
                } else {
                    close(database);
//					return resultlist;
                }
            } else {
//				return resultlist;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(database);
        }
        return resultlist;

    }

    /**
     * 数据库中查找对应条件的数据
     *
     * @param object
     * @return
     */

    public List<Object> search(Object object) {
        Log.v("Cur", "limit=" + (limit == null ? "null" : limit));
        Log.v("Cur", "search");
        Log.v("Cur", "Int");
        List resultlist = new ArrayList();
        String limittmp = limit;
        String ordertmp = order;
        SQLiteDatabase database = getDataBase();
        try {
            if (object != null) {
                DateBase_Entity entity = (DateBase_Entity) object;
                if (isregex) {
                    entity.putlogicAll("like");
                }

                String tablename = getTableName(entity);

                resultlist.clear();
                List<DateBase_Entity> entitylist = new ArrayList<DateBase_Entity>();
                ContentValues values = new ContentValues();
                Cursor allcur = null;
                Cursor cur = null;
                String[] searchcolumn = null;
                String selection = "";
                String[] selectionArgs = null;
                if (entity.getId() != null && !entity.getId().equals("")) {
                    selection = selection + entity.getDbFieldSql("id", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("id"));

                }
                if (entity.getHashid() != null && !entity.getHashid().equals("")) {
                    selection = selection + entity.getDbFieldSql("hashid", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("hashid"));

                }
                if (entity.getDatastate() != null && !entity.getDatastate().equals("")) {
                    selection = selection + entity.getDbFieldSql("datastate", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("datastate"));

                }
                if (entity.getUpDatadate() != null && !entity.getUpDatadate().equals("")) {
                    selection = selection + entity.getDbFieldSql("updatadate", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("updatadate"));

                }
                if (entity.getTableid() != null && !entity.getTableid().equals("")) {
                    selection = selection + entity.getDbFieldSql("tableid", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("tableid"));

                }
                if (entity.getCreated() != null && !entity.getCreated().equals("")) {
                    selection = selection + entity.getDbFieldSql("created", logic);
                    selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue("created"));

                }
                for (int i = 0; i < entity.size(); i++) {
                    String columnname = entity.getField(i);
                    String columnvalue = entity.getValue(i);
                    if (!columnvalue.equals("") && !columnvalue.equals(" ")) {
                        selection = selection + entity.getDbFieldSql(i, logic);
                        selectionArgs = arrayaddarray(selectionArgs, entity.getDbValue(i));


                    }

                }
                if (selection != null && selection.length() > 0 && !logic.equals("or")) {
                    selection = selection + " status <> 0 " + logic;
                }

                if (selection != null && selection.length() > 0) {
                    selection = selection.substring(0, selection.length() - logic.length());
                }

                Log.v("Cur", "selection:" + selection);
                if (selectionArgs != null && selectionArgs.length > 0) {
                    for (int i = 0; i < selectionArgs.length; i++) {
                        Log.v("Cur", "selectionArg:" + selectionArgs[i]);
                    }
                }


                if (needcount) {
//					if(selection==null||selection.equals("")||selection.equals(" ")){
//						resultlist.arrayaddarray(0 + "");
//						return resultlist;
//					}
                    Log.v("Cur", "needcount");
                    allcur = database.query(tablename, new String[]{"COUNT(*)"}, selection, selectionArgs, null, null,
                            ordertmp.equals("") ? null : ordertmp, null);
                }
                Log.v("Cur", "table:" + tablename);
                Log.v("Cur", "limit:" + limittmp);
                Log.v("Order", object.getClass().getSimpleName() + ":" + ordertmp);
//				if(selection==null||selection.equals("")||selection.equals(" ")){
//					resultlist.arrayaddarray(0 + "");
//					return resultlist;
//				}
                cur = database.query(tablename, searchcolumn, selection, selectionArgs, null, null,
                        ordertmp.equals("") ? null : ordertmp, limittmp);

                if (cur != null) {
                    Log.v("Cur", "nonull");
                    for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//						Log.v("Cur", "curhashvalue");
                        DateBase_Entity tmpentity = getEntityType(entity);
                        if (isfull) {
//							Log.v("Cur", "size"+":"+tmpentity.size()+"");
                            for (int i = 0; i < tmpentity.size(); i++) {
//								Log.v("Cur", "coloumname:"+tmpentity.getField(i));
                                int colu = cur.getColumnIndex(tmpentity.getField(i));

                                try {
                                    tmpentity.put(i,
                                            cur.getString(colu));
//
                                } catch (Exception e) {
                                    Log.v("Cur", "colonname:" + tmpentity.getField(i));
                                    e.printStackTrace();

                                }

                            }
                        }
                        tmpentity.setId(cur.getString(cur.getColumnIndex("id")));
                        tmpentity.setHashid(cur.getString(cur.getColumnIndex("hashid")));
                        tmpentity.setDatastate(cur.getString(cur.getColumnIndex("datastate")));
                        tmpentity.setCreatedatadate(cur.getString(cur.getColumnIndex("createdatadate")));
                        tmpentity.setUpDatadate(cur.getString(cur.getColumnIndex("updatadate")));
                        tmpentity.setTableid(cur.getString(cur.getColumnIndex("tableid")));
                        tmpentity.setTableid(cur.getString(cur.getColumnIndex("created")));
                        entitylist.add(tmpentity);
                    }
                    if (allcur != null) {
                        allcur.moveToFirst();
                        resultlist.add(allcur.getInt(0) + "");
                    } else {
                        resultlist.add(0 + "");
                    }
                    Log.v("Cur", "1326:result" + ":" + entitylist.size() + "");
                    resultlist.add(entitylist);
                    if (allcur != null) {
                        allcur.close();
                    }
                    if (cur != null) {
                        cur.close();
                    }
                    Log.v("Cur", "1334:resultlist" + ":" + resultlist.size() + "");
                    close(database);
//					return resultlist;
                } else {
                    close(database);
//					return resultlist;
                }
            } else {
//				return resultlist;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(database);
        }
        return resultlist;

    }
//	/**
//	 * 增加查村数据回调方法
//	 * @param object
//	 * @param callback
//	 * @return
//	 */
//	public   List<Object> search(Object object,CallBack callback) {
//		List resultlist = new ArrayList();
//		resultlist=search(object);
//		if(callback!=null){
//			callback.back(resultlist);
//		}
//		return resultlist;
//
//	}

    /**
     * 按对象获得表名
     *
     * @param entity
     * @return
     */
    public static String getTableName(DateBase_Entity entity) {
        String tablename = "";
        if (entity instanceof DateBase_Entity) {
            tablename = entity.getClass().getSimpleName();
        }
        tablename = tablename.toLowerCase();
        if (tablename.matches(".*android.*")) {
            //System.out.println("SRX出错 写入了android");
        }
        return tablename;
    }

    /**
     * 测试可用
     */
    public void attach() {
        SQLiteDatabase database = SQLiteDatabase.openDatabase(new File(dbdir + "/" + "" + databasename + "").getAbsolutePath(), null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING
        );
        database.execSQL("attach database '" + dbdir + "/" + "dep.db" + "' as 'source2'");
        Cursor curosr = database.rawQuery("select sql from source2.sqlite_master where name = 'tb_law_info'", null);
        String createsql = "";
        if (curosr.moveToNext()) {
            createsql = curosr.getString(0);
        }
//		database.execSQL(createsql);
//		database.execSQL("insert into tb_law_info select * from source2.tb_law_info");
        database.execSQL("create table tb_law_info as select * from source2.tb_law_info");//优化语句
        //System.out.println("接触成功finish");
    }

    /**
     * debug更新数据库办法 会拷贝表结构 清空特定数据表 用于测试阶段
     */
    public void updataDatabaseDebug() {
        //System.out.println("debug升级开始");
        List<String> createsql = new ArrayList<String>();
        List<String> whattable = new ArrayList<String>();
        List<String> tablenewsql = new ArrayList<String>();
        SQLiteDatabase database = SQLiteDatabase.openDatabase(new File(dbdir + "/" + "tmp" + databasename + "").getAbsolutePath(), null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING
        );
        Cursor cursor = database.query("sqlite_master", new String[]{"sql"}, null, null, null, null, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {


                String sqltmp = cursor.getString(0);
                if (sqltmp.matches("(.*)entity_plan(.*)") || sqltmp.matches("(.*)entity_check(.*)") || sqltmp.matches("(.*)entity_checkdetail(.*)")) {
                    createsql.add(sqltmp);
                    int start = sqltmp.indexOf("(");
                    int end = sqltmp.indexOf(")");
                    String tablehead = sqltmp.substring(0, start).replaceAll("CREATE TABLE ", "");
                    whattable.add(tablehead);
                    String table = sqltmp.substring(start + 1, end).replaceAll(" DEFAULT ' '", "");
                    tablenewsql.add(table);
                } else {

                }

            }

        }
        database.close();
        database.releaseReference();
        database = null;
        SQLiteDatabase database2 = SQLiteDatabase.openDatabase(new File(dbdir + "/" + "" + databasename + "").getAbsolutePath(), null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        for (int i = 0; i < whattable.size(); i++) {
            database2.execSQL("ALTER TABLE " + whattable.get(i) + " RENAME TO " + "tmp_" + whattable.get(i) + "");
            database2.execSQL(createsql.get(i));
            Cursor cursor2 = database2.rawQuery("select count(*) from sqlite_master where type='table' and name='" + whattable.get(i).trim() + "'", null);
            boolean result = false;
            if (cursor2.moveToNext()) {
                int count = cursor2.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
            if (result) {
//				database2.execSQL("INSERT INTO "+whattable.get(i)+"("+tablenewsql.get(i)+") SELECT  "+tablenewsql.get(i)+" FROM "+"tmp_"+whattable.get(i)+"");//debug时重新安装就清空数据

            }
            database2.execSQL("DROP TABLE " + "tmp_" + whattable.get(i) + ";");


        }
        database2.close();
        database2.releaseReference();
        database2 = null;
        new File(dbdir + "/" + "tmp" + databasename + "").renameTo(new File(dbdir + "/" + "delete" + databasename + ""));
        new File(dbdir + "/" + "delete" + databasename + "").delete();
        //System.out.println("debug升级完成");
    }

    /**
     * 数据库升级办法 拷贝文件为tmp 复制数据到新的数据库结构中
     */
    public void updataDatabase() {
        updataDatabase(anjiandbversion);
    }

    public void updataDatabaseSpecial() {
        updataDatabaseSpecialDetail();
    }

    public void updataDatabaseSpecialDetail() {
//		SQLiteDatabase database = getDataBase();
//		database.delete("Entity_Company", "qiyemingcheng like ?", new String[]{"%测试%"});
    }

    public void updataDatabase(int dbversion) {
        //System.out.println("升级开始");
        switch (dbversion) {
            case 1:
                upall();
                break;
            case 2:
                updataDatabaseSpecialDetail();
                break;
            default:
                break;
        }


    }

    private void upall() {
        List<String> createsql = new ArrayList<String>();
        List<String> whattable = new ArrayList<String>();
        List<String> tablenewsql = new ArrayList<String>();
        if (sqldb != null) {
            sqldb.releaseReference();
            sqldb = null;
        }
        boolean es = isDataBaseExists(new File(dbdir + "/" + "tmp" + databasename + "").getAbsolutePath());
        if (es) {

        } else {
            try {
                InputStream is = mcontext.getAssets().open("" + databasename + "");
                FileOutputStream fos = new FileOutputStream(dbdir + "/" + "tmp" + databasename);
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                    // buffer字节
                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                }
                fos.flush();// 刷新缓冲区
                is.close();
                fos.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        SQLiteDatabase database = SQLiteDatabase.openDatabase(new File(dbdir + "/" + "tmp" + databasename + "").getAbsolutePath(), null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        Cursor cursor = database.query("sqlite_master", new String[]{"sql"}, null, null, null, null, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {


                String sqltmp = cursor.getString(0);
                if (sqltmp.matches("(.*)android_metadata(.*)") || sqltmp.matches("(.*)sqlite_metadata(.*)")) {

                } else {
                    createsql.add(sqltmp);
                    int start = sqltmp.indexOf("(");
                    int end = sqltmp.indexOf(")");
                    String tablehead = sqltmp.substring(0, start).replaceAll("CREATE TABLE ", "");
                    whattable.add(tablehead);
                    String table = sqltmp.substring(start + 1, end).replaceAll(" DEFAULT ' '", "");
                    tablenewsql.add(table);
                }

            }

        }
        database.close();
        database.releaseReference();
        database = null;
        SQLiteDatabase database2 = SQLiteDatabase.openDatabase(new File(dbdir + "/" + "" + databasename + "").getAbsolutePath(), null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        for (int i = 0; i < whattable.size(); i++) {
            database2.execSQL("ALTER TABLE " + whattable.get(i) + " RENAME TO " + "tmp_" + whattable.get(i) + "");
            database2.execSQL(createsql.get(i));
            Cursor cursor2 = database2.rawQuery("select count(*) from sqlite_master where type='table' and name='" + whattable.get(i).trim() + "'", null);
            boolean result = false;
            if (cursor2.moveToNext()) {
                int count = cursor2.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
            if (result) {
                database2.execSQL("INSERT INTO " + whattable.get(i) + "(" + tablenewsql.get(i) + ") SELECT  " + tablenewsql.get(i) + " FROM " + "tmp_" + whattable.get(i) + "");

            }
            database2.execSQL("DROP TABLE " + "tmp_" + whattable.get(i) + ";");


        }
        database2.close();
        database2.releaseReference();
        database2 = null;
        new File(dbdir + "/" + "tmp" + databasename + "").renameTo(new File(dbdir + "/" + "delete" + databasename + ""));
        new File(dbdir + "/" + "delete" + databasename + "").delete();
        //System.out.println("升级完成");
    }

    /**
     * 获得对象类型
     *
     * @param entity
     * @return
     */
    public DateBase_Entity getEntityType(DateBase_Entity entity) {
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
            result = new DateBase_Entity();
        }

        return result;

    }
}
