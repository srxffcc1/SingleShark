package com.businessframehelp.db.ay;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * 
 * 作用：操作数据库 作 qww 创建时间 2013-7-4
 */
public class DBConn extends SQLiteOpenHelper {
	public static boolean dataLock=true;
	private final static int DATABASEVERSION = 1;
	private final static String DATABASEIMAGEINFONAME = "tabledata.db";

	public final static String MENUTABLE = "menutable";
	private static Context mContext;

	/**
	 * 
	 * Constructor Method
	 * 
	 * @param context
	 */
	private DBConn(Context context) {
		super(context, DATABASEIMAGEINFONAME, null, DATABASEVERSION);
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "create table tablefields (tableid varchar(255), isdelete varchar(255),fieldsorder interger,filedsName varchar(255),isClientBasic varchar(255),title varchar(255), metadata varchar(255),type varchar(255),canNull varchar(255), isBasic varchar(255), createTime varchar(255),foreignKeys varchar(255),primaryKey varchar(255),relation varchar(255),controlledFields varchar(255))";
		db.execSQL(sql);
		sql = "create table orginfo (id varchar(255), name varchar(255),parent varchar(255),pinyin varchar(255), type varchar(255),ord int,typeOrd int)";
		db.execSQL(sql);
		sql = "create table slaveinfo (tableId varchar(255),slaveId varchar(255),slaveName varchar(255))";
		db.execSQL(sql);
		sql = "create table workflowcurrent (workName varchar(255),filedId varchar(255),filedCn varchar(255))";
		db.execSQL(sql);
		sql = "create table workflowhandled (workName varchar(255),filedId varchar(255),filedCn varchar(255))";
		db.execSQL(sql);
		sql = "create table workflowhistory (workName varchar(255),filedId varchar(255),filedCn varchar(255))";
		db.execSQL(sql);
		sql = "create table workflowmonitoring (workName varchar(255),filedId varchar(255),filedCn varchar(255))";
		db.execSQL(sql);
		sql = "create table worknodeinfo(id varchar(255), instendtId varchar(255),workname varchar(255), workcn varchar(255), nodename varchar(255), nodecn varchar(255),flag varchar(255))";
		db.execSQL(sql);
		sql = "create table nodetableinfo(id varchar(255),wid varchar(255),tablename varchar(255),tablecn varchar(255),type varchar(255))";
		db.execSQL(sql);
		sql = "create table tableinfo (wid varchar(255),tablename varchar(255),fieldsname varchar(255),access varchar(255))";
		db.execSQL(sql);
		sql = "create table worknextnode (wid varchar(255),name varchar(255),assignedTo varchar(255),label varchar(255))";
		db.execSQL(sql);
		sql = "create table libversion (menu varchar(255),time varchar(255))";
		db.execSQL(sql);
		db.execSQL("create table "
				+ MENUTABLE
				+ " (id varchar(255),e_name varchar(255),name varchar(255),type varchar(255),url varchar(255),icon_url varchar(255),parent_id varchar(255),ord varchar(255))");
		// relatrion info表单关联的信息
		db.execSQL("create table relationFiledsInfo (tableName varchar(255),groupNo varchar(255),filedName varchar(255),indexNo int,targetField varchar(255))");
		// 流程标签存储
		db.execSQL("create table workflowlable (workflowname varchar(255),lablename varchar(255),lablecn varchar(255))");
		db.execSQL("create table infolable (tablename varchar(255),lablename varchar(255),lablecn varchar(255),lableadd varchar(255))");
		// 数据中心表结构的版本
		db.execSQL("create table datafiledsversion (tableName varchar(255),lastUpdateTime varchar(255))");
		// tablefields isdelete.

		// tianwei
		sql = "create table infolastresponse (tableId varchar(255),lableId varchar(255),time varchar(255))";
		db.execSQL(sql);

		// tianwei
		sql = "create table flowRefreshId (workflowname varchar(255),lablename varchar(255),instanceid varchar(255))";
		db.execSQL(sql);
		sql = "create table flowlastresponse (workflowname varchar(255),lablename varchar(255),time varchar(255),count varchar(255))";
		db.execSQL(sql);

		sql = "create table menuversion (menu varchar(255),time varchar(255))";
		db.execSQL(sql);
		sql = "create table orgrelation (ancestor varchar(255),descendant varchar(255))";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// 贾卫星 2014.1.9 数据库版本跟新机制修改 （start）
		if (oldVersion != newVersion) {
			try {
				Cursor cursor = db.rawQuery(
						"select name from sqlite_master where type = 'table'",
						null);
				while (cursor.moveToNext()) {
					String tablename = cursor.getString(cursor
							.getColumnIndex("name"));
					if (!tablename.equals("android_metadata")) {
						String dropsql = "drop table " + tablename;
						db.execSQL(dropsql);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sql = "create table tablefields (tableid varchar(255), isdelete varchar(255),fieldsorder interger,filedsName varchar(255),isClientBasic varchar(255),title varchar(255), metadata varchar(255),type varchar(255),canNull varchar(255), isBasic varchar(255), createTime varchar(255),foreignKeys varchar(255),primaryKey varchar(255),relation varchar(255),controlledFields varchar(255))";
			db.execSQL(sql);
			sql = "create table orginfo (id varchar(255),pinyin varchar(255), name varchar(255),parent varchar(255), type varchar(255),ord int,typeOrd int)";
			db.execSQL(sql);
			sql = "create table slaveinfo (tableId varchar(255),slaveId varchar(255),slaveName varchar(255))";
			db.execSQL(sql);
			sql = "create table workflowcurrent (workName varchar(255),filedId varchar(255),filedCn varchar(255))";
			db.execSQL(sql);
			sql = "create table workflowhandled (workName varchar(255),filedId varchar(255),filedCn varchar(255))";
			db.execSQL(sql);
			sql = "create table workflowhistory (workName varchar(255),filedId varchar(255),filedCn varchar(255))";
			db.execSQL(sql);
			sql = "create table workflowmonitoring (workName varchar(255),filedId varchar(255),filedCn varchar(255))";
			db.execSQL(sql);
			sql = "create table worknodeinfo(id varchar(255), instendtId varchar(255),workname varchar(255), workcn varchar(255), nodename varchar(255), nodecn varchar(255),flag varchar(255))";
			db.execSQL(sql);
			sql = "create table nodetableinfo(id varchar(255),wid varchar(255),tablename varchar(255),tablecn varchar(255),type varchar(255))";
			db.execSQL(sql);
			sql = "create table tableinfo (wid varchar(255),tablename varchar(255),fieldsname varchar(255),access varchar(255))";
			db.execSQL(sql);
			sql = "create table worknextnode (wid varchar(255),name varchar(255),assignedTo varchar(255),label varchar(255))";
			db.execSQL(sql);
			sql = "create table libversion (menu varchar(255),time varchar(255))";
			db.execSQL(sql);
			db.execSQL("create table "
					+ MENUTABLE
					+ " (id varchar(255),e_name varchar(255),name varchar(255),type varchar(255),url varchar(255),icon_url varchar(255),parent_id varchar(255),ord varchar(255))");
			// relatrion info表单关联的信息
			db.execSQL("create table relationFiledsInfo (tableName varchar(255),groupNo varchar(255),filedName varchar(255),indexNo int,targetField varchar(255))");
			// 流程标签存储
			db.execSQL("create table workflowlable (workflowname varchar(255),lablename varchar(255),lablecn varchar(255))");
			db.execSQL("create table infolable (tablename varchar(255),lablename varchar(255),lablecn varchar(255),lableadd varchar(255))");
			// 数据中心表结构的版本
			db.execSQL("create table datafiledsversion (tableName varchar(255),lastUpdateTime varchar(255))");

			// tianwei
			sql = "create table infolastresponse (tableId varchar(255),lableId varchar(255),time varchar(255))";
			db.execSQL(sql);

			// tianwei
			sql = "create table flowRefreshId (workflowname varchar(255),lablename varchar(255),instanceid varchar(255))";
			db.execSQL(sql);
			sql = "create table flowlastresponse (workflowname varchar(255),lablename varchar(255),time varchar(255),count varchar(255))";
			db.execSQL(sql);
			sql = "create table menuversion (menu varchar(255),time varchar(255))";
			db.execSQL(sql);
			sql = "create table orgrelation (ancestor varchar(255),descendant varchar(255))";
			db.execSQL(sql);

		}
		// 贾卫星 2014.1.9 数据库版本跟新机制修改 （end）
	}

	/**
	 * 
	 * @todo 执行SQL语句
	 * @param sql
	 * @author qww
	 * @created 2013-7-16 下午3:57:11
	 * @update by:
	 */
	public void add(String sql) throws Exception {
		DbConnHolder.useflag = 1;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(sql);
		DbConnHolder.useflag = 0;
		// Object o = new Object();
	}

	/**
	 * 
	 * @todo 执行SQL返回cursor
	 * @param sql
	 * @return Cursor
	 * @author qww
	 * @created 2013-7-16 下午3:57:41
	 * @update by:
	 */

	public Cursor getList(String sql) throws Exception {
		if (DbConnHolder.useflag == 1) {
			int k = 0;
			while (DbConnHolder.useflag == 0) {
				k++;
				if (k == 100) {

					//Log.d("ay", "-----2----无对象返回");
					return null;
				}
			}
		}
		DbConnHolder.useflag = 1;
		SQLiteDatabase db = getWritableDatabase();
		//Log.d("--zy--","SQL:   "+sql);
		Cursor cursor = db.rawQuery(sql, null);
		DbConnHolder.useflag = 0;
		return cursor;
	}

	/**
	 * \
	 * 
	 * @todo 执行事务
	 * @param sql
	 *            【】
	 * @return Boolean
	 * @author qww
	 * @created 2013-7-13 下午1:57:00
	 * @update by:
	 */
	public Boolean executAffairs(String[] sql) {
		DbConnHolder.useflag = 1;
		boolean flag = true;
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.beginTransaction();
			for (int i = 0; i < sql.length; i++) {
				db.execSQL(sql[i]);
			}
			db.setTransactionSuccessful();
			db.endTransaction();
		} catch (Exception e) {
			flag = false;
		}
		DbConnHolder.useflag = 0;
		return flag;

	}

	/**
	 * 
	 * @todo 将list传递过来每个对象封装为sql，执行事务。 使用注意:重写toString方法
	 * @param list
	 * 
	 * @author qww
	 * @created 2013-7-13 下午5:17:03
	 * @update by:
	 */

	public Boolean executAffairsList(List<?> list) {
		DbConnHolder.useflag = 1;
		boolean flag = true;
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.beginTransaction();
			for (int i = 0; i < list.size(); i++) {
				db.execSQL(list.get(i).toString());
			}
			db.setTransactionSuccessful();
			db.endTransaction();
			// db.close();
			// db = null;
		} catch (Exception e) {
			flag = false;
		}
		DbConnHolder.useflag = 0;
		return flag;

	}

	/**
	 * 
	 * 作用：db单例 作者： qww 创建时间 ： 2013-11-28
	 */
	private static class DbConnHolder {
		static DBConn instance = null;
		private static Context context;
		static int useflag = 0; // 单例对象所用状态 0未所用 1在所用
	}

	/**
	 * 
	 * @todo 单例获得实例对象
	 * @return @param context
	 * @author qww
	 * @created 2013-11-28 下午4:41:23
	 * @update by:
	 */
	public static DBConn getInstance(Context context) {
		if (DbConnHolder.useflag == 1) {
			//Log.d("ay", "--------DB对象正在使用中.....");
			int k = 0;
			while (DbConnHolder.useflag == 1) { // 进入循环等待对象使用结束
				k++;
				if (k == 100) {
					//Log.d("ay", "-----1----无对象返回");
					DbConnHolder.context = context;
					DbConnHolder.instance = new DBConn(DbConnHolder.context);
					return DbConnHolder.instance;
				}
			}
		}
		if (DbConnHolder.context == null) {
			DbConnHolder.context = context;
			DbConnHolder.instance = new DBConn(DbConnHolder.context);
		}
		if (DbConnHolder.instance == null) {
			DbConnHolder.context = context;
			DbConnHolder.instance = new DBConn(DbConnHolder.context);
		}
		return DbConnHolder.instance;
	}
}
