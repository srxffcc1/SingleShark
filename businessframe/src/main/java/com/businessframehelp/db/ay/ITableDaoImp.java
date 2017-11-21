package com.businessframehelp.db.ay;

import android.content.Context;
import android.database.Cursor;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * 作用：表数据操作接口实现 作者： qww 创建时间 ： 2013-7-16
 */

public class ITableDaoImp implements ITableDao {
	private Context context;

	/**
	 * 
	 * Constructor Method
	 * 
	 * @param context
	 */
	public ITableDaoImp(Context context) {
		this.context = context;

	}

	@Override
	public List<HashMap<String, String>> getShujuList(String tableName,
			String where) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		DBConn db = DBConn.getInstance(context);
		String sql = "select * from `" + tableName + "` where lablename='"
				+ where + "'";
		Cursor cs = null;
		try {
			cs = db.getList(sql);
		} catch (Exception e) {
			if (cs != null) {
				cs.close();
			}

			return null;
		}
		list = ObjectUtil.cursorTwoListMap(cs);
		if (cs != null) {
			cs.close();
		}

		db.close();
		db = null;
		cs.close();
		cs = null;
		return list;
	}

	@Override
	public List<HashMap<String, String>> getShujuListForPage(String tableName,
			String where) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		DBConn db = DBConn.getInstance(context);
		String sql = "select * from `" + tableName + "` " + where;

		Cursor cs = null;
		try {
			cs = db.getList(sql);
		} catch (Exception e) {
			if (cs != null) {
				cs.close();
			}

			return null;
		}
		list = ObjectUtil.cursorTwoListMap(cs);
		if (cs != null) {
			cs.close();
		}

		db.close();
		db = null;
		cs.close();
		cs = null;
		return list;
	}

	@Override
	public List<HashMap<String, String>> getShujuList(String tableName,
			String lable, String where) {

		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		DBConn db = DBConn.getInstance(context);
		String sql = "select * from `" + tableName + "` where lablename='"
				+ lable + "' " + where;
		Cursor cs = null;
		try {
			cs = db.getList(sql);
		} catch (Exception e) {
			if (cs != null) {
				cs.close();
			}

			return null;
		}
		list = ObjectUtil.cursorTwoListMap(cs);
		if (cs != null) {
			cs.close();
		}

		db.close();
		db = null;
		cs.close();
		cs = null;
		return list;
	}

	@Override
	public List<HashMap<String, String>> getSlaveShujuList(String tableName,
			String where) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		DBConn db = DBConn.getInstance(context);
		String sql = "select * from `" + tableName + "`" + where;
		Cursor cs = null;
		try {
			cs = db.getList(sql);
		} catch (Exception e) {
			if (cs != null) {
				cs.close();
			}

			return null;
		}
		list = ObjectUtil.cursorTwoListMap(cs);
		if (cs != null) {
			cs.close();
		}

		// //db.close();
		return list;
	}

	@Override
	public List<?> getList(String tableName, String where) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		DBConn db = DBConn.getInstance(context);
		String sql = "select * from `" + tableName + "` where " + where;
		Cursor cs = null;
		try {
			cs = db.getList(sql);
		} catch (Exception e) {
			if (cs != null) {
				cs.close();
			}

			return null;
		}
		list = ObjectUtil.cursorTwoListMap(cs);
		if (cs != null) {
			cs.close();
		}

		return list;
	}

	@Override
	public Object getObject(String tableName, String objectId) {
		DBConn db = DBConn.getInstance(context);
		String sql = "select * from `" + tableName + "` where id='" + objectId
				+ "'";
		Cursor cs = null;
		try {
			cs = db.getList(sql);
		} catch (Exception e) {
			if (cs != null) {
				cs.close();
			}

			return null;
		}
		Object o = ObjectUtil.cursorTwoMap(cs);
		if (cs != null) {
			cs.close();
		}

		return o;
	}

	@Override
	public Boolean delObject(String tableName, String objectId) {
		Boolean flag = true;
		String[] tableId = objectId.split(",");
		DBConn db = DBConn.getInstance(context);
		try {
			for (int i = 0; i < tableId.length; i++) {
				String sql = "delete  from `" + tableName + "` where id='"
						+ tableId[i] + "'";
				db.add(sql);
			}
		} catch (Exception e) {
			flag = false;

		}
		// db.close();
		return flag;
	}

	@Override
	public Boolean delAll(String tableName) {
		Boolean flag = true;
		DBConn db = DBConn.getInstance(context);
		try {
			String sql = "delete  from `" + tableName + "`";
			db.add(sql);
		} catch (Exception e) {
			flag = false;

		}
		// db.close();
		return flag;
	}

	@Override
	public Boolean delAll(String tableName, String where) {
		Boolean flag = true;
		DBConn db = DBConn.getInstance(context);
		try {
			String sql = "delete  from `" + tableName + "` where " + where;
			db.add(sql);
		} catch (Exception e) {
			flag = false;

		}
		// db.close();
		return flag;
	}

	@Override
	public Boolean saveObject(String tableName, Map<String, String> map) {
		boolean flag = true;
		DBConn db = DBConn.getInstance(context);
		try {
			Set<String> set = map.keySet();
			StringBuffer fields = new StringBuffer();
			StringBuffer values = new StringBuffer();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String key = it.next().toString();
				fields.append(key + ",");
				values.append("'" + map.get(key).toString() + "',");
			}
			String sql = "insert into `" + tableName + "` ("
					+ fields.toString() + ") values (" + values.toString()
					+ ")";
			set = null;
			it = null;
			db.add(sql);
		} catch (Exception e) {
			flag = false;
		}
		// db.close();
		return flag;
	}

	@Override
	public Boolean saveObject(String tableName, String json) {
		boolean flag = true;
		DBConn db = DBConn.getInstance(context);
		StringBuffer fileds = new StringBuffer();
		StringBuffer values = new StringBuffer();
		try {
			JSONObject jo = new JSONObject(json.replaceAll(":null", ":\"\""));
			Iterator<?> it = jo.keys();
			while (it.hasNext()) {
				String itemkey = (String) it.next().toString();
				fileds.append("`" + itemkey + "`,");
				values.append("'" + jo.getString(itemkey) + "',");
			}
			String sql = "insert into `" + tableName + "` ('tableinfoId',"
					+ fileds.substring(0, fileds.length() - 1)
					+ ") values ('0',"
					+ values.substring(0, values.length() - 1) + ")";

			db.add(sql);
			sql = null;
		} catch (Exception e) {
			flag = false;
		}
		// db.close();
		return flag;
	}

	/**
	 * 
	 * @todo
	 * @return @param tableId
	 * @author tianwei1201
	 * @created 2014-1-16 上午10:38:55
	 * @update by:
	 */
	public boolean saveInfo(String tableId, String lableId, String time) {

		Boolean flag = true;
		DBConn db = DBConn.getInstance(context);
		try {
			List<String> list = new ArrayList<String>();
			list.add("delete from infolastresponse where tableId='" + tableId
					+ "' and lableId ='" + lableId + "'");
			list.add("insert into infolastresponse values('" + tableId + "','"
					+ lableId + "','" + time + "')");
			return db.executAffairsList(list);
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
		}
		return flag;
	}

	@Override
	public Boolean saveList(String tableName, String json, String lablename) {
		boolean flag = true;
		DBConn db = DBConn.getInstance(context);
		try {
			if (lablename == null) {
				db.add("delete from `" + tableName + "` where tableinfoId='0'");
			} else {
				db.add("delete from `" + tableName
						+ "` where tableinfoId='0' and lablename='" + lablename
						+ "'");
			}
		} catch (Exception e) {
			//Log.d("ay", e.toString());
		}

		String time = "";

		try {
			AyJSONObject jsonRecordsum = new AyJSONObject(json.replaceAll(
					":null", ":\"\""));
			time = jsonRecordsum.getString("time");
			AyJSONObject jo = jsonRecordsum.getJSONObject("records");

			Iterator<?> it = jo.keys();
			while (it.hasNext()) {
				String itemkey = (String) it.next().toString();
				AyJSONObject o = jo.getJSONObject(itemkey);
				Iterator<?> itr = o.keys();
				StringBuffer fileds = new StringBuffer();
				StringBuffer values = new StringBuffer();
				while (itr.hasNext()) {

					String itemKey = (String) itr.next().toString();
					//Log.d("Legend", "@@@@@@@@@@itemKey===" + itemKey);
					fileds.append("`" + itemKey + "`,");
					try {
						Object item = o.getJSONArray(itemKey);
						if (item instanceof JSONArray) {
							values.append("'"
									+ sqliteEscape(ObjectUtil
											.jXdefultvalues((JSONArray) item))
									+ "',");
						}
					} catch (Exception e) {
						values.append("'" + sqliteEscape(o.getString(itemKey))
								+ "',");
					}
				}
				String sql = "insert into `" + tableName
						+ "` (`tableinfoId`,`lablename`,"
						+ fileds.substring(0, fileds.length() - 1)
						+ ") values ('0','" + lablename + "',"
						+ values.substring(0, values.length() - 1) + ")";

				sql = sql.toString().replaceAll("/", "");
				db.add(sql);
				sql = null;
				fileds = null;
				values = null;
			}

			// db.close();
		} catch (Exception e) {
			flag = false;
			// db.close();
		}

		if (lablename != null) {
			saveInfo(tableName, lablename, time);
		}

		return flag;
	}

	@Override
	public Boolean saveListNodelete(String tableName, String json,
			String lablename) {// 添加数据,并不删除以前添加的

		boolean flag = true;
		DBConn db = DBConn.getInstance(context);

		String time = "";

		try {
			AyJSONObject jsonRecordsum = new AyJSONObject(json.replaceAll(
					":null", ":\"\""));
			time = jsonRecordsum.getString("time");
			AyJSONObject jo = jsonRecordsum.getJSONObject("records");

			Iterator<?> it = jo.keys();
			while (it.hasNext()) {
				String itemkey = (String) it.next().toString();
				AyJSONObject o = jo.getJSONObject(itemkey);
				Iterator<?> itr = o.keys();
				StringBuffer fileds = new StringBuffer();
				StringBuffer values = new StringBuffer();
				while (itr.hasNext()) {

					String itemKey = (String) itr.next().toString();
					fileds.append("`" + itemKey + "`,");
					try {
						Object item = o.getJSONArray(itemKey);
						if (item instanceof JSONArray) {
							values.append("'"
									+ sqliteEscape(ObjectUtil
											.jXdefultvalues((JSONArray) item))
									+ "',");
						}
					} catch (Exception e) {
						values.append("'" + sqliteEscape(o.getString(itemKey))
								+ "',");
					}
				}
				String sql = "insert into `" + tableName
						+ "` (`tableinfoId`,`lablename`,"
						+ fileds.substring(0, fileds.length() - 1)
						+ ") values ('0','" + lablename + "',"
						+ values.substring(0, values.length() - 1) + ")";

				db.add(sql);

				//Log.d("tw", "$$$$$$$$$$$$sql==="+sql);
				sql = null;
				fileds = null;
				values = null;
			}

			// db.close();
		} catch (Exception e) {
			flag = false;
			// db.close();
			//Log.d("tw", "%%%%%%%%%");
		}

		if (lablename != null) {
			saveInfo(tableName, lablename, time);
		}

		return flag;
	}

	@Override
	public Boolean saveWHPList(String tableName, String json, String lablename) {
		boolean flag = true;
		DBConn db = DBConn.getInstance(context);
		String time = "";

		try {
			AyJSONObject jsonRecordsum = new AyJSONObject(json.replaceAll(
					":null", ":\"\""));
			time = jsonRecordsum.getString("time");
			AyJSONObject jo = jsonRecordsum.getJSONObject("records");

			Iterator<?> it = jo.keys();
			while (it.hasNext()) {
				String itemkey = (String) it.next().toString();
				AyJSONObject o = jo.getJSONObject(itemkey);
				Iterator<?> itr = o.keys();
				StringBuffer fileds = new StringBuffer();
				StringBuffer values = new StringBuffer();
				while (itr.hasNext()) {

					String itemKey = (String) itr.next().toString();
					fileds.append("`" + itemKey + "`,");
					try {
						Object item = o.getJSONArray(itemKey);
						if (item instanceof JSONArray) {
							values.append("'"
									+ sqliteEscape(ObjectUtil
											.jXdefultvalues((JSONArray) item))
									+ "',");
						}
					} catch (Exception e) {
						values.append("'" + sqliteEscape(o.getString(itemKey))
								+ "',");
					}
				}
				String sql = "insert into `" + tableName
						+ "` (`tableinfoId`,`lablename`,"
						+ fileds.substring(0, fileds.length() - 1)
						+ ") values ('0','" + lablename + "',"
						+ values.substring(0, values.length() - 1) + ")";
				//Log.d("wang","SQL======================"+sql);
				db.add(sql);
				sql = null;
				fileds = null;
				values = null;
			}

			// db.close();
		} catch (Exception e) {
			//Log.e("wang","e======================="+e.toString());
			flag = false;
			// db.close();
		}

		if (lablename != null) {
			saveInfo(tableName, lablename, time);
		}

		return flag;
	}

	public static String sqliteEscape(String keyWord) {
		keyWord = keyWord.replace("/", "//");
		keyWord = keyWord.replace("'", "''");
		keyWord = keyWord.replace("[", "/[");
		keyWord = keyWord.replace("]", "/]");
		keyWord = keyWord.replace("%", "/%");
		keyWord = keyWord.replace("&", "/&");
		keyWord = keyWord.replace("_", "/_");
		keyWord = keyWord.replace("(", "/(");
		keyWord = keyWord.replace(")", "/)");
		return keyWord;
	}

	@Override
	public Boolean saveListShuaxin(String tableName, String json,
			String lablename) {
		boolean flag = true;
		DBConn db = DBConn.getInstance(context);
		try {
			AyJSONObject jsonRecordsum = new AyJSONObject(json.replaceAll(
					":null", ":\"\""));
			AyJSONObject jo = jsonRecordsum.getJSONObject("records");
			Iterator<?> it = jo.keys();
			while (it.hasNext()) {
				String itemkey = (String) it.next().toString();
				AyJSONObject o = jo.getJSONObject(itemkey);
				Iterator<?> itr = o.keys();
				StringBuffer fileds = new StringBuffer();
				StringBuffer values = new StringBuffer();
				while (itr.hasNext()) {

					String itemKey = (String) itr.next().toString();
					fileds.append("`" + itemKey + "`,");
					try {
						Object item = o.getJSONArray(itemKey);
						if (item instanceof JSONArray) {
							values.append("'"
									+ ObjectUtil
											.jXdefultvalues((JSONArray) item)
									+ "',");
						}
					} catch (Exception e) {
						values.append("'" + o.getString(itemKey) + "',");
					}
				}
				String sql = "insert into `" + tableName
						+ "` (`tableinfoId`,`lablename`,"
						+ fileds.substring(0, fileds.length() - 1)
						+ ") values ('0','" + lablename + "',"
						+ values.substring(0, values.length() - 1) + ")";

				db.add(sql);
				sql = null;
				fileds = null;
				values = null;
			}

			// db.close();
		} catch (Exception e) {
			flag = false;
			// db.close();
		}

		return flag;
	}

	@Override
	public Boolean editObject(String tableName, String tableid,
			Map<String, String> map) {
		boolean flag = true;
		DBConn db = DBConn.getInstance(context);
		try {
			Set<String> set = map.keySet();
			StringBuffer fields = new StringBuffer();
			StringBuffer values = new StringBuffer();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String key = it.next().toString();
				fields.append(key + ",");
				values.append("'" + map.get(key).toString() + "',");
			}
			String ins = "insert into `" + tableName + "` ("
					+ fields.toString() + ") values (" + values.toString()
					+ ")";
			set = null;
			it = null;
			String delOld = "delete from `" + tableName + "` where id = '"
					+ tableid + "'";
			String[] sql = { ins, delOld };
			db.executAffairs(sql);
		} catch (Exception e) {
			flag = false;
		}
		// db.close();
		return flag;
	}

	@Override
	public Boolean editObject(String tableName, String tableid, String json) {
		boolean flag = true;
		DBConn db = DBConn.getInstance(context);
		StringBuffer values = new StringBuffer();
		try {
			JSONObject jo = new JSONObject(json.replaceAll(":null", ":\"\""));
			Iterator<?> it = jo.keys();
			while (it.hasNext()) {
				String itemkey = (String) it.next().toString();
				values.append("`" + itemkey + "`='" + jo.getString(itemkey)
						+ "',");
			}
			String sql = "update `" + tableName + "` set "
					+ values.substring(0, values.length() - 1) + " where id='"
					+ tableid + "'";

			db.add(sql);
			sql = null;
		} catch (Exception e) {
			flag = false;
		}
		// db.close();
		return flag;
	}

	@Override
	public Boolean saveInfoLable(String tablename, ArrayList<String> lablename,
			ArrayList<String> lablenamecn, HashMap<String, Boolean> mIsAdds) {
		ArrayList<String> list = new ArrayList<String>();
		DBConn db = DBConn.getInstance(context);
		String delsql = "delete from infolable where tablename='" + tablename
				+ "'";
		try {
			db.add(delsql);
		} catch (Exception e) {

		}
		for (int i = 0; i < lablename.size(); i++) {
			String lableadd = "true";
			if (mIsAdds.get(lablename.get(i)) == null) {
				lableadd = "false";
			}

			String sql = "insert into infolable values('" + tablename + "','"
					+ lablename.get(i) + "','" + lablenamecn.get(i) + "','"
					+ lableadd + "')";
			list.add(sql);
		}

		return db.executAffairsList(list);
	}

	@Override
	public Map<String, String> getInfoLable(String tablename) {
		String sql = "select * from infolable where tablename='" + tablename
				+ "'";
		DBConn db = DBConn.getInstance(context);
		Cursor cs = null;
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			cs = db.getList(sql);
			while (cs.moveToNext()) {
				map.put(cs.getString(cs.getColumnIndex("lablecn")),
						cs.getString(cs.getColumnIndex("lablename")) + ","
								+ cs.getString(cs.getColumnIndex("lableadd")));
			}
		} catch (Exception e) {
			if (cs != null)
				cs.close();
			// db.close();
			e.printStackTrace();
		}
		if (cs != null)
			cs.close();
		// db.close();
		if (map.size() == 0) {
			return null;
		} else {
			return map;
		}
	}

	// tianwei
	@Override
	public String getFormTime(String tableId, String lableId) {
		// TODO Auto-generated method stub
		String time = "";
		String sql = "select time from infolastresponse where tableId = '"
				+ tableId + "'and lableId = '" + lableId + "'";
		DBConn db = DBConn.getInstance(context);
		Cursor cs = null;
		try {
			cs = db.getList(sql);
			while (cs.moveToNext()) {
				time = cs.getString(cs.getColumnIndex("time"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			if (cs != null) {
				cs.close();
			}
		}
		if (cs != null) {
			cs.close();
		}
		if (time.equals("")) {
			time = "0";
		}
		return time;
	}
	
	
	// wang
	@Override
	public String getMaxVersion(String table) {
		// TODO Auto-generated method stub
		String time = "";
		String sql = "select max(version) as version from "+table;
		DBConn db = DBConn.getInstance(context);
		Cursor cs = null;
		try {
			cs = db.getList(sql);
			while (cs.moveToNext()) {
				time = cs.getString(cs.getColumnIndex("version"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			if (cs != null) {
				cs.close();
			}
		}
		if (cs != null) {
			cs.close();
		}
		if (time.equals("")) {
			time = "0";
		}
		return time;
	}
	

	@Override
	public int getCount(String tablename) {
		// TODO Auto-generated method stub
		int count = 0;
		String sql = "select count(*) from `" + tablename + "`";
		DBConn db = DBConn.getInstance(context);
		Cursor cs = null;
		Map<String, String> map = new LinkedHashMap<String, String>();
		try {
			cs = db.getList(sql);
			while (cs.moveToNext()) {
				count = cs.getInt(cs.getColumnIndex("count(*)"));
			}
		} catch (Exception e) {
			if (cs != null)
				cs.close();
			// db.close();
			e.printStackTrace();
		}
		if (cs != null)
			cs.close();
		// db.close();
		//Log.d("wang","**************sql=============="+sql);
		//Log.d("wang","**************count=============="+count);
		return count;
	}

	@Override
	public JSONObject getYinHuanXinXiUpdataJson(String id) {
		// TODO Auto-generated method stub
		JSONObject tempJsonRemote = new JSONObject();
		String sql = "select * from `YinHuanXin` where id='" + id + "'";
		DBConn db = DBConn.getInstance(context);
		Cursor cs = null;
		Map<String, String> map = new LinkedHashMap<String, String>();

		ITableFieldsDao mTableFields = new ITableFieldsDaoImp(context);
		List<TableFields> filedList = mTableFields.getTableFileds("YinHuanXin",
				"type != 'system' ");
		try {
			cs = db.getList(sql);
			while (cs.moveToNext()) {
				for (int i = 0; i < filedList.size(); i++) {
					String title = filedList.get(i).filedsName;
					if (title.equals("ShiFouWanC")) {
						tempJsonRemote.put(title, "是");
					} else {
						tempJsonRemote.put(title,
								cs.getString(cs.getColumnIndex(title)));
					}
				}
			}
		} catch (Exception e) {
			if (cs != null)
				cs.close();
			e.printStackTrace();
		}
		if (cs != null)
			cs.close();

		return tempJsonRemote;
	}

}
