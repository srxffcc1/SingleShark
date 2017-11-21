package com.businessframehelp.db.ay;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 作用：数据记录操作。 作者： qww 创建时间 ： 2013-7-9
 */

public interface ITableDao {
	/**
	 * 
	 * @todo 得到某个表的所有记录
	 * @param tableName表名
	 * @return list
	 * @author qww
	 * @created 2013-7-9 下午1:37:28
	 * @update by:
	 */
	List<?> getShujuList(String tableName, String where);
	
	/**
	 * 
	 * @todo 得到某个表的所有记录
	 * @param tableName表名
	 * @return list
	 * @author qww
	 * @created 2013-7-9 下午1:37:28
	 * @update by:
	 */
	List<?> getShujuListForPage(String tableName, String where);

	/**
	 * 
	 * @todo 得到某个表的所有记录
	 * @param tableName表名
	 * @return list
	 * @author qww
	 * @created 2013-7-9 下午1:37:28
	 * @update by:
	 */
	List<?> getSlaveShujuList(String tableName, String where);

	/**
	 * 
	 * @todo 得到某个表的过滤记录
	 * @param tableName表名
	 *            ，where过滤条件
	 * @return list
	 * @author qww
	 * @created 2013-7-9 下午1:37:28
	 * @update by:
	 */
	List<?> getList(String tableName, String where);

	/**
	 * 
	 * @todo 得到某个表的单条记录
	 * @param tableName表名
	 *            ，ObjectId记录ID
	 * @return Object
	 * @author qww
	 * @created 2013-7-9 下午1:37:28
	 * @update by:
	 */
	Object getObject(String tableName, String objectId);

	/**
	 * 
	 * @todo 保存记录
	 * @param tableName
	 *            ,json
	 * @return boolean
	 * @author qww
	 * @created 2013-7-9 下午1:40:30
	 * @update by:
	 */
	Boolean saveObject(String tableName, String json);

	/**
	 * 
	 * @todo 保存记录
	 * @param tableName
	 *            ,Map
	 * @return boolean
	 * @author qww
	 * @created 2013-7-9 下午1:40:30
	 * @update by:
	 */
	Boolean saveObject(String tableName, Map<String, String> map);

	/**
	 * 
	 * @todo 保存多条记录
	 * @param tableName
	 *            ,json
	 * @return boolean
	 * @author qww
	 * @created 2013-7-9 下午1:40:30
	 * @update by:
	 */
	Boolean saveList(String tableName, String json, String lablename);

	/**
	 * 
	 * @todo 保存多条记录
	 * @param tableName
	 *            ,json
	 * @return boolean
	 * @author qww
	 * @created 2013-7-9 下午1:40:30
	 * @update by:
	 */
	Boolean saveWHPList(String tableName, String json, String lablename);

	/**
	 * 
	 * @todo 保存多条记录
	 * @param tableName
	 *            ,json
	 * @return boolean
	 * @author qww
	 * @created 2013-7-9 下午1:40:30
	 * @update by:
	 */
	Boolean saveListShuaxin(String tableName, String json, String lablename);

	/**
	 * 
	 * @todo 删除记录
	 * @param tableName
	 * @return boolean
	 * @author qww
	 * @created 2013-7-9 下午1:40:30
	 * @update by:
	 */
	Boolean delObject(String tableName, String tableId);

	/**
	 * 
	 * @todo 编辑记录
	 * @param tableName
	 *            map id
	 * @return boolean
	 * @author qww
	 * @created 2013-7-9 下午1:40:30
	 * @update by:
	 */
	Boolean editObject(String tableName, String tableid, Map<String, String> map);

	/**
	 * 
	 * @todo 编辑记录
	 * @param tableName
	 *            json id
	 * @return boolean
	 * @author qww
	 * @created 2013-7-9 下午1:40:30
	 * @update by:
	 */
	Boolean editObject(String tableName, String tableid, String json);

	/**
	 * 
	 * @todo
	 * @param workflowName
	 * @param name
	 * @param cn
	 * @return
	 * @author JiaWeiXing
	 * @created 2013-12-17 上午11:12:32
	 * @update by:
	 */
	Boolean saveInfoLable(String tablename, ArrayList<String> lablename,
                          ArrayList<String> lablenamecn, HashMap<String, Boolean> mIsAdds);

	/**
	 * 
	 * @todo
	 * @param workflowName
	 * @return
	 * @author JiaWeiXing
	 * @created 2013-12-17 上午11:42:38
	 * @update by:
	 */
	Map<String, String> getInfoLable(String tableame);

	/**
	 * 
	 * @todo
	 * @return @param tableName
	 * @author tianwei1201
	 * @created 2014-1-16 下午1:56:05
	 * @update by:
	 */
	String getFormTime(String tableId, String lableId);

	Boolean delAll(String tableName);

	Boolean delAll(String tableName, String where);

	Boolean saveListNodelete(String tableName, String json, String lablename);

	List<HashMap<String, String>> getShujuList(String tableName, String lable,
                                               String where);
	
	int getCount(String tablename);
	
	JSONObject getYinHuanXinXiUpdataJson(String id);

	String getMaxVersion(String table);

}
