package com.businessframehelp.db.ay;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * 作用：ITableFieldsService实现类 作者： qww 创建时间 ： 2013-7-10
 */

public class ITableFieldsDaoImp implements ITableFieldsDao {
	private Context context;
	DBConn db = DBConn.getInstance(context);;

	/**
	 * 
	 * Constructor Method
	 * 
	 * @param context
	 */
	public ITableFieldsDaoImp(Context context) {
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @todo 将表结构封装为对象生成SQL
	 * @param o
	 * @return Boolean
	 * @author qww
	 * @created 2013-7-16 下午4:39:02
	 * @update by:
	 */
	public String saveObject(Object o) {
		String cloms = "";
		String values = "";
		String sql = "";
		try {
			Field[] f = o.getClass().getFields();
			for (int i = 0; i < f.length; i++) {
				if (i == f.length - 1) {
					cloms += "`" + f[i].getName() + "`";
					values += "'" + f[i].get(o).toString() + "'";
				} else {
					cloms += "`" + f[i].getName() + "`,";
					values += "'" + f[i].get(o).toString() + "',";
				}
			}
			// cloms = cloms + "`fieldsorder`";
			// values = values + "'999'";
			sql = "insert into tablefields  (" + cloms + ") values (" + values
					+ ")";
			//Log.d("testing", "sql@@@@@@@======="+sql);
		} catch (Exception e) {
			//Log.d("tw", e.toString());
		}
		return sql;
	}

	@Override
	public boolean add(String tableId, String json) {
		// delTable(tableId);
		Boolean flag = false;
		List<String> oldlist = getTableFiledsName(tableId);
		List<String> addlist = new ArrayList<String>();
		List<TableFields> list = new ArrayList<TableFields>();
		List<String> sqlList = new ArrayList<String>();
		List<String> delList = new ArrayList<String>();
		try {

			list = ObjectUtil.jsonTwolist(tableId, json);

			if (list == null || list.size() == 0) {
				return false;
			}
			// 判断是否有旧数据，如果没有旧数据，直接插入，并建表
			if (oldlist == null || oldlist.size() == 0) {
				for (TableFields tf : list) {
					tf.setFieldsorder("999");
					sqlList.add(saveObject(tf));
				}
				String temp = getCreateSql(tableId, list);
				//Log.d("testing", "%%%%temp = "+ temp );
				sqlList.add(temp);
				flag = db.executAffairsList(sqlList);
			} else {
				// 如果有旧数据
				for (TableFields tf : list) {
					String name = tf.getFiledsName();
					// 判断旧的里面有没有，如果没有，说明是新添加的。 记录下来，更改表结构
					if (!oldlist.contains(name)) {
						addlist.add(name);
					}
				}
				// 判断旧的数据在新的数据中，哪个不存在，如果不存在说明这个字段被删除。
				for (String str : oldlist) {
					Boolean mark = true;
					for (TableFields tf : list) {
						if (tf.getFiledsName().equals(str)) {
							mark = false;
							break;
						}
					}
					if (mark) {
						delList.add(str);
					}
				}
			
				if (delList.size() > 0) {
					// 将删除的字段设置为已删除。
					for (String str : delList) {
						alterTableDropColumn(tableId, list, oldlist, addlist);
					}
				} else {
					// 更新表
					for (String name : addlist) {
						sqlList.add("alter table `" + tableId + "` add `"
								+ name + "` varchar(255)");
					}
				}
				// 删除掉新数据中也存在的数据。 防止某个字段具体信息改变
				sqlList.add("delete from tablefields where tableid = '"
						+ tableId + "'");
				// 更新结构数据
				for (TableFields tf : list) {
					tf.setFieldsorder("999");
					sqlList.add(saveObject(tf));
				}

				flag = db.executAffairsList(sqlList);
			}

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 
	 * @todo 删除表中的字段
	 * @return
	 * @author qww
	 * @created 2013-12-3 下午1:22:52
	 * @update by:
	 */
	private void alterTableDropColumn(String tableName, List<TableFields> list,
			List<String> oldlist, List<String> addlist) {
		String createsql = getCreateSql(tableName, list);
		oldlist.removeAll(addlist);
		StringBuffer columnsSeperated = new StringBuffer();

		for (String string : oldlist) {
			columnsSeperated.append("'" + string + "',");
		}
		String columns = columnsSeperated.substring(0,
				columnsSeperated.length() - 1);
		try {
			db.add("ALTER TABLE " + tableName + " RENAME TO " + tableName
					+ "_old;");
			db.add(createsql);
			db.add("INSERT INTO " + tableName + "(" + columns + ") SELECT "
					+ columns + " FROM " + tableName + "_old;");
			db.add("DROP TABLE " + tableName + "_old;");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @todo 生成建表语句
	 * @param tableId
	 * @return @param list
	 * @author qww
	 * @created 2013-7-16 下午4:39:47
	 * @update by:
	 */
	private String getCreateSql(String tableId, List<TableFields> list) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();

		sb.append("create table if not exists `" + tableId + "` (");
		for (TableFields tf : list) {
			sb.append("`" + tf.getFiledsName() + "` varchar(255),");
		}
		sb.append("right_modify varchar(255),right_delete varchar(255),"
				+ "rawrole varchar(255),localflag varchar(255),"
				+ "map varchar(255),tableinfoId varchar(255),lablename varchar(255)");
		sb.append(")");
		Log.d("ay", "create sql:"+sb.toString());
		return sb.toString();
	}

	@Override
	public boolean delTable(String tableName) {
		Boolean falg = true;
		try {
			/*
			 * String[] sql = { "drop table if exists " + tableName,
			 * "delete from tablefields where tableid = '" + tableName + "'" };
			 */
			String[] sql = { "delete from tablefields where tableid = '"
					+ tableName + "'" };
			falg = db.executAffairs(sql);
		} catch (Exception e) {
			falg = false;
		}
		// db.close();
		return falg;
	}

	/**
	 * 
	 * @todo //获得字段名字集合
	 * @return @param tableName
	 * @author qww
	 * @created 2013-11-29 上午11:08:44
	 * @update by:
	 */
	public List<String> getTableFiledsName(String tableName) {
		List<String> list = new ArrayList<String>();

		List<TableFields> listfield = getTableFileds(tableName);
		if (listfield == null) {
			return list;
		}
		for (TableFields tableFields : listfield) {
			list.add(tableFields.getFiledsName());
		}
		return list;
	}

	@Override
	public List<TableFields> getTableFileds(String tableName) {
		List<TableFields> list = new ArrayList<TableFields>();
		String sql = "select * from tablefields where tableid='" + tableName
				+ "' and isdelete='0' order by fieldsorder asc";
		Cursor cs = null;
		try {
			cs = db.getList(sql);
			while (cs.moveToNext()) {
				TableFields m = new TableFields();
				m.setCreateTime(cs.getString(cs.getColumnIndex("createTime")));
				m.setFiledsName(cs.getString(cs.getColumnIndex("filedsName")));
				m.setIsBasic(cs.getString(cs.getColumnIndex("isBasic")));
				m.setCanNull(cs.getString(cs.getColumnIndex("canNull")));
				m.setType(cs.getString(cs.getColumnIndex("type")));
				m.setMetadata(cs.getString(cs.getColumnIndex("metadata")));
				m.setTableid(cs.getString(cs.getColumnIndex("tableid")));
				m.setTitle(cs.getString(cs.getColumnIndex("title")));
				m.setPrimaryKey(cs.getString(cs.getColumnIndex("primaryKey")));
				m.setForeignKeys(cs.getString(cs.getColumnIndex("foreignKeys")));
				m.setRelation(cs.getString(cs.getColumnIndex("relation")));
				m.setControlledFields(cs.getString(cs
						.getColumnIndex("controlledFields")));
				m.setIsClientBasic(cs.getString(cs
						.getColumnIndex("isClientBasic")));

				list.add(m);
				m = null;
			}
		} catch (Exception e) {
			if(cs != null){
				cs.close();
			}
		
			return null;
		}
		if(cs != null){
			cs.close();
		}
	
		return list;

	}

	@Override
	public List<TableFields> getTableFileds(String tableName, String where) {
		List<TableFields> list = new ArrayList<TableFields>();
		String sql = "select * from tablefields where tableid='" + tableName
				+ "' and isdelete='0' and " + where
				+ "order by fieldsorder asc";
		Cursor cs = null;

		try {
			cs = db.getList(sql);
			while (cs.moveToNext()) {
				TableFields m = new TableFields();
				m.setCreateTime(cs.getString(cs.getColumnIndex("createTime")));
				m.setFiledsName(cs.getString(cs.getColumnIndex("filedsName")));
				m.setIsBasic(cs.getString(cs.getColumnIndex("isBasic")));
				m.setIsClientBasic(cs.getString(cs
						.getColumnIndex("isClientBasic")));
				m.setCanNull(cs.getString(cs.getColumnIndex("canNull")));
				m.setMetadata(cs.getString(cs.getColumnIndex("metadata")));
				m.setTableid(cs.getString(cs.getColumnIndex("tableid")));
				m.setTitle(cs.getString(cs.getColumnIndex("title")));
				m.setType(cs.getString(cs.getColumnIndex("type")));
				m.setPrimaryKey(cs.getString(cs.getColumnIndex("primaryKey")));
				m.setForeignKeys(cs.getString(cs.getColumnIndex("foreignKeys")));
				m.setRelation(cs.getString(cs.getColumnIndex("relation")));
				m.setControlledFields(cs.getString(cs
						.getColumnIndex("controlledFields")));
				m.setFieldsorder(cs.getString(cs.getColumnIndex("fieldsorder")));
				list.add(m);
				m = null;
			}
		} catch (Exception e) {
			if(cs != null){
				cs.close();
			}
		
			return null;
		}
		if(cs != null){
			cs.close();
		}
	
		return list;
	}

	@Override
	public Boolean saveSlave(String tableName, String json) {
		Boolean flag = true;
		List<String> list;
		list = ObjectUtil.slaveToSql(tableName, json);
		if (list == null) {
			flag = false;
		} else {
			flag = db.executAffairsList(list);
		}
		// db.close();
		return flag;
	}

	@Override
	public Map<String, String> getSlave(String tableName) {

		String sql = "select * from slaveinfo where tableId='" + tableName
				+ "'";
		Map<String, String> map = new HashMap<String, String>();
		Cursor cs = null;
		try {
			cs = db.getList(sql);
		} catch (Exception e) {
			// db.close();
			return null;
		}
		while (cs.moveToNext()) {
			map.put(cs.getString(cs.getColumnIndex("slaveId")),
					cs.getString(cs.getColumnIndex("slaveName")));
		}
		if(cs != null){
			cs.close();
		}
	
		return map;

	}

	@Override
	public boolean addRelation(String tableId, String json) {
		Boolean flag = false;
		try {

			JSONArray ja = new JSONArray(json);
			List<String> list = new ArrayList<String>();
			String s = "delete from relationFiledsInfo where tableName='"+tableId+"'";
			list.add(s);
			int len = ja.length();
			for (int i = 0; i < len; i++) {
				JSONObject o = ja.getJSONObject(i);
				JSONObject jo = o.getJSONObject("relation");

				Iterator<?> it = jo.keys();
				while (it.hasNext()) {
					String fieldName = it.next().toString();
					JSONObject jobj = jo.getJSONObject(fieldName);
					int index = jobj.getInt("index");
					String targetFeild = jobj.getString("targetField");
					String sql = "insert into relationFiledsInfo values('"
							+ tableId + "','" + i + "','" + fieldName + "',"
							+ index + ",'" + targetFeild + "')";
					String upSql = "update tablefields set relation ='" + i
							+ "' , type = 'relspinner' where tableid='"
							+ tableId + "' and filedsName='" + fieldName + "'";
					list.add(sql);
					list.add(upSql);
				}

			}
			flag = db.executAffairsList(list);
		} catch (JSONException e) {

			e.printStackTrace();
		}
		// db.close();
		return flag;
	}

	@Override
	public Map<String, List<String>> getRelation(String tableName,
			String fieldsName, String groupNo) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> up = new ArrayList<String>();
		List<String> clean = new ArrayList<String>();

		String sql = "select indexNo from relationFiledsInfo where groupNo='"
				+ groupNo + "' and tableName='" + tableName
				+ "' and filedName='" + fieldsName + "'";
		Cursor cs = null;
		int indexNo = 0;
		try {
			cs = db.getList(sql);
			while (cs.moveToNext()) {
				indexNo = cs.getInt(cs.getColumnIndex("indexNo"));
			}
			sql = "select filedName from relationFiledsInfo where groupNo='"
					+ groupNo + "' and tableName='" + tableName
					+ "' and indexNo<" + indexNo;
			if (cs != null) {
				cs.close();
			}
			cs = db.getList(sql);
			while (cs.moveToNext()) {
				String filed = cs.getString(cs.getColumnIndex("filedName"));
				up.add(filed);
			}
			sql = "select filedName from relationFiledsInfo where groupNo='"
					+ groupNo + "' and tableName='" + tableName
					+ "' and indexNo>" + indexNo;
			if (cs != null) {
				cs.close();
			}
			cs = db.getList(sql);
			while (cs.moveToNext()) {
				String filed = cs.getString(cs.getColumnIndex("filedName"));
				clean.add(filed);
			}
			map.put("up", up);
			map.put("clean", clean);
		} catch (Exception e) {
			e.printStackTrace();
			if (cs != null) {
				cs.close();
			}
			// db.close();
			return null;
		}
		if (cs != null) {
			cs.close();
		}
		// db.close();
		return map;
	}

	@Override
	public boolean checkVersion(String tableName, String time) {
		// TODO Auto-generated method stub
		Boolean flag = false;
		String sql = "select lastUpdateTime from datafiledsversion where tableName='"
				+ tableName + "'";
		Log.d("ay", "sql:" + sql);
		Cursor cs = null;
		String localtime = "";
		try {
			cs = db.getList(sql);
			while (cs.moveToNext()) {
				localtime = cs.getString(cs.getColumnIndex("lastUpdateTime"));
			}
			if (time.equals(localtime)) {
				flag = true;
			}

		} catch (Exception e) {
		}
		if (cs != null) {
			cs.close();
		}

		return flag;
	}

	@Override
	public boolean setVersion(String tableName, String time) {
		List<String> list = new ArrayList<String>();
		list.add("delete from datafiledsversion where tableName='" + tableName
				+ "'");
		list.add("insert into datafiledsversion values('" + tableName + "','"
				+ time + "')");
		return db.executAffairsList(list);
	}

}
