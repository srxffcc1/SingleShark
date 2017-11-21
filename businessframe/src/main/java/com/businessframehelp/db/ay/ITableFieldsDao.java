package com.businessframehelp.db.ay;

import java.util.List;
import java.util.Map;

/**
 * 
 * 作用：数据记录表结构相关操作。 作者： qww 创建时间 ： 2013-7-9
 */
public interface ITableFieldsDao {
	/**
	 * 
	 * @todo 新建表，如果表存在则新建。 将表信息写入到tableFileds表中 （后期处理，判断是否更新）
	 * @param tableId 表ID。json为服务器返回的JSON字符串
	 * @return true为成功 flase 失败
	 * @author qww
	 * @created 2013-7-9 上午11:19:49
	 * @update by:
	 */
	// boolean create(List<TableFields> list);
	boolean add(String tableId, String json);

	/**
	 * 
	 * @todo 设置版本时间
	 * @param tableName
	 * @param time
	 * @return @return
	 * @author qww
	 * @created 2013-11-29 下午3:25:45
	 * @update by:
	 */
	boolean setVersion(String tableName, String time);

	/**
	 * 
	 * @todo 判断本地存储的版本时间 true为正确
	 * @param tableName
	 * @param time
	 * @return
	 * @author qww
	 * @created 2013-11-29 上午9:48:40
	 * @update by:
	 */
	boolean checkVersion(String tableName, String time);

	/**
	 * 
	 * @todo relation数据解析存储
	 * @param tableId
	 *            表名ID
	 * @param json
	 * @return
	 * @author qww
	 * @created 2013-8-27 上午11:49:32
	 * @update by:
	 */
	boolean addRelation(String tableId, String json);

	/**
	 * 
	 * @todo 得到字段的relation相关。
	 * @param tableName
	 * @param fieldsName
	 * @param groupNo
	 * @return 返回map. 有2个值：up为index比当前小的，是需要给服务器传值的字段
	 *         clean为index比当前大的。是需要清空数据的字段
	 * @author qww
	 * @created 2013-8-27 下午2:57:23
	 * @update by:
	 */
	Map<String, List<String>> getRelation(String tableName, String fieldsName,
                                          String groupNo);

	/**
	 * 
	 * @todo 删除表,并删除表字段信息
	 * @param tableName表名
	 * @return true为成功 flase 失败
	 * @author qww
	 * @created 2013-7-9 上午11:23:50
	 * @update by:
	 */
	boolean delTable(String tableName);

	/**
	 * 
	 * @todo 返回这张表的所有字段信息
	 * @param tableId表名
	 * @return List<TableFields>
	 * @author qww
	 * @created 2013-7-9 上午11:28:26
	 * @update by:
	 */
	List<TableFields> getTableFileds(String tableName);

	/**
	 * 
	 * @todo 返回这张表的部分字段信息
	 * @param tableName
	 *            表名 where 为过滤条件
	 * @return List<TableFields>
	 * @author qww
	 * @created 2013-7-9 上午11:28:26
	 * @update by:
	 */
	List<TableFields> getTableFileds(String tableName, String where);

	/**
	 * 
	 * @todo 将从表信息保存到本地
	 * @param tableName
	 * @param json
	 * @return Boolean
	 * @author qww
	 * @created 2013-7-17 下午5:43:37
	 * @update by:
	 */
	Boolean saveSlave(String tableName, String json);

	/**
	 * 
	 * @todo 将本地从表信息取出
	 * @param tableName
	 * @return map 英文-中文 对应
	 * @author qww
	 * @created 2013-7-17 下午5:43:37
	 * @update by:
	 */
	Map<String, String> getSlave(String rableName);
}
