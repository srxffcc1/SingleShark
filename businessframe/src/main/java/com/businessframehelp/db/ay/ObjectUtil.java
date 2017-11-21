package com.businessframehelp.db.ay;

import android.database.Cursor;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * 作用：工具类 作者： qww 创建时间 ： 2013-7-12
 */

public class ObjectUtil {
	/**
	 * 
	 * @todo Cursor转换为List
	 * @return list
	 * @param cursor
	 * @author qww
	 * @created 2013-7-10 ����8:30:28
	 * @update by:
	 */
	public static List<Object> cursorTwoList(Cursor c) {
		List<Object> list = new ArrayList<Object>();
		String[] columnNames = c.getColumnNames();

		HashMap<String, String> propertyMap = new HashMap<String, String>();

		while (c.moveToNext()) {
			for (int i = 0; i < columnNames.length; i++) {
				propertyMap.put(columnNames[i], "java.lang.String");
			}
			DynamicBean bean = new DynamicBean(propertyMap);
			for (int i = 0; i < columnNames.length; i++) {
				bean.setValue(columnNames[i],
						c.getString(c.getColumnIndex(columnNames[i])));
			}
			Object object = bean.getObject();
			list.add(object);
			bean = null;
			object = null;
			propertyMap.clear();
		}
		return list;
	}

	/**
	 * 
	 * @todo将cursor封装为map放入到list
	 * @param c
	 * @return list
	 * @author qww
	 * @created 2013-7-12 下午3:42:29
	 * @update by:
	 */
	public static List<HashMap<String, String>> cursorTwoListMap(Cursor c) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String[] columnNames = c.getColumnNames();
		HashMap<String, String> propertyMap = new HashMap<String, String>();
		while (c.moveToNext()) {
			for (int i = 0; i < columnNames.length; i++) {
				propertyMap.put(columnNames[i],
						c.getString(c.getColumnIndex(columnNames[i])));
					}
			list.add(propertyMap);
			propertyMap = new HashMap<String, String>();
			// propertyMap.clear();
		}
		return list;
	}

	/**
	 * 
	 * @todo cursor转换为map
	 * @param c
	 * @return map
	 * @author qww
	 * @created 2013-7-12 下午3:48:44
	 * @update by:
	 */
	public static Map<String, String> cursorTwoMap(Cursor c) {
		String[] columnNames = c.getColumnNames();
		Map<String, String> propertyMap = new HashMap<String, String>();
		while (c.moveToNext()) {
			for (int i = 0; i < columnNames.length; i++) {
				propertyMap.put(columnNames[i],
						c.getString(c.getColumnIndex(columnNames[i])));
			}
		}
		return propertyMap;
	}

	/**
	 * 
	 * @todo Cursor 转换为Obj
	 * @param c
	 * @return Object
	 * @author qww
	 * @created 2013-7-12 下午3:47:39
	 * @update by:
	 */
	public static Object cursorTwoObj(Cursor c) {

		String[] columnNames = c.getColumnNames();

		HashMap<String, String> propertyMap = new HashMap<String, String>();
		Object object = null;
		while (c.moveToNext()) {
			for (int i = 0; i < columnNames.length; i++) {
				propertyMap.put(columnNames[i], "java.lang.String");
			}
			DynamicBean bean = new DynamicBean(propertyMap);
			for (int i = 0; i < columnNames.length; i++) {
				bean.setValue(columnNames[i],
						c.getString(c.getColumnIndex(columnNames[i])));
			}
			object = bean.getObject();
			bean = null;
			propertyMap.clear();
		}
		return object;
	}

	/**
	 * 
	 * @todo json 转化为list field
	 * @param tableId
	 * @param json
	 * @return LIST
	 * @author qww
	 * @created 2013-7-12 下午3:47:55
	 * @update by:
	 */
	public static List<TableFields> jsonTwolist(String tableId, String json)
			throws Exception {
		AyJSONObject jo = new AyJSONObject(json.replaceAll(":null", ":\"\""));
		Iterator<?> it = jo.keys();
		List<TableFields> list = new ArrayList<TableFields>();
		while (it.hasNext()) {
			TableFields tf = new TableFields();
			String itemKey = (String) it.next().toString();
			// JSONArray defultvalues = new JSONArray();
			AyJSONObject item = jo.getJSONObject(itemKey);
			tf.setTableid(tableId);
			tf.setTitle(item.getString("title"));
			tf.setType(item.getString("type"));
			tf.setIsClientBasic(item.getString("is_client_basic"));
			tf.setFiledsName(item.getString("id"));
			tf.setCreateTime(item.getString("create_time"));
			tf.setIsBasic(item.getString("is_basic"));
			tf.setCanNull("0");
			tf.setIsDelete("0");
			tf.setRelation("");
			try {
				//JSONArray fk = item.getJSONArray("foreignKeys");			
//				JSONArray ja = new JSONArray(item.getString("foreignKeys")
//						.replace("\\\"", "\"").replace("[\"", "[")
//						.replace("\"]", "]"));
//				JSONObject ob = ja.getJSONObject(0);
				tf.setForeignKeys("");
				tf.setType("relspinner");
			} catch (Exception e) {
				Log.d("ay", e.toString());
				tf.setForeignKeys("");
			}

			tf.setPrimaryKey("0");
			Object metadata = item.get("metadata");
			tf.setMetadata(metadata.toString());
			Object controlledFields = item.get("controlledFields");
			tf.setControlledFields(controlledFields.toString()
					.replace("\\\"", "\"").replace("[\"", "[")
					.replace("\"]", "]").replace("}\",\"{", "},{"));
			list.add(tf);
		/*	tf = null;*/
		}
		return list;

	}

	/**
	 * 
	 * @todo JSON工具类
	 * @param ja
	 * @return String
	 * @author qww
	 * @created 2013-7-16 下午4:01:17
	 * @update by:
	 */
	public static String jXdefultvalues(JSONArray ja) {

		StringBuffer value = new StringBuffer();
		int len = ja.length();
		for (int i = 0; i < len; i++) {
			try {
				value.append(ja.get(i).toString() + ",");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return value.substring(0, value.length() - 1);
	}

	/**
	 * 
	 * @todo 从表数据生成sql
	 * @param tableName
	 * @param json
	 * @return list
	 * @author qww
	 * @created 2013-7-18 上午9:48:32
	 * @update by:
	 */
	public static List<String> slaveToSql(String tableName, String json) {
		List<String> list = new ArrayList<String>();

		try {
			JSONObject jsono = new JSONObject(json);
			JSONObject jo = jsono.getJSONObject("detail");
			Iterator<?> it = jo.keys();
			while (it.hasNext()) {
				String key = it.next().toString();
				String name = jo.getString(key);
				String sql = "insert into slaveinfo (tableId,slaveId,slaveName) values('"
						+ tableName + "','" + key + "','" + name + "')";
				list.add(sql);
			}
		} catch (JSONException e) {
			Log.d("ay", e.toString());
			return null;
		}

		return list;
	}
}
