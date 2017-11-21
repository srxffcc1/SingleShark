package com.businessframehelp.db.ay;

/**
 * 
 * 作用：表结构BEAN 作者： qww 创建时间 ： 2013-7-16
 */
public class TableFields {

	public String tableid;
	public String filedsName; // 英文
	public String title;// 中文
	public String metadata;
	public String canNull;
	public String isBasic;
	public String createTime;
	public String type;
	public String foreignKeys;
	public String primaryKey;
	public String relation;// 对其他字段的关系
	public String controlledFields; // 控制其他字段显示
	public String isDelete;// 是否已删除 0表示未删除，1表示此字段已删除
	public String isClientBasic;
	public String fieldsorder;

	/**
	 * 
	 * @todo
	 * @return @return
	 * @author qww
	 * @created 2013-12-2 上午11:21:14
	 * @update by:
	 */
	public String getIsDelete() {
		return isDelete;
	}

	/**
	 * 
	 * @todo
	 * @author qww
	 * @created 2013-12-2 上午11:21:17
	 * @update by:
	 */
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * 
	 * @todo
	 * @return @return
	 * @author qww
	 * @created 2013-10-16 下午7:48:27
	 * @update by:
	 */
	public String getIsClientBasic() {
		return isClientBasic;
	}

	/**
	 * 
	 * @todo
	 * @author qww
	 * @created 2013-10-16 下午7:48:31
	 * @update by:
	 */
	public void setIsClientBasic(String isClientBasic) {
		this.isClientBasic = isClientBasic;
	}

	/**
	 * 
	 * @todo
	 * @return @return
	 * @author qww
	 * @created 2013-7-23 下午1:28:28
	 * @update by:
	 */
	public String getRelation() {
		return relation;
	}

	/**
	 * 
	 * @todo
	 * @param relation
	 * @author qww
	 * @created 2013-7-23 下午1:28:31
	 * @update by:
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}

	/**
	 * 
	 * @todo
	 * @return @return
	 * @author qww
	 * @created 2013-8-1 下午3:12:06
	 * @update by:
	 */
	public String getControlledFields() {
		return controlledFields;
	}

	/**
	 * 
	 * @todo
	 * @param controlledFields
	 * @author qww
	 * @created 2013-8-1 下午3:12:36
	 * @update by:
	 */
	public void setControlledFields(String controlledFields) {
		this.controlledFields = controlledFields;
	}

	/**
	 * 
	 * @todo
	 * @return @return
	 * @author qww
	 * @created 2013-7-16 下午4:42:33
	 * @update by:
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public String getTableid() {
		return tableid;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public String getFiledsName() {
		return filedsName;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public void setFiledsName(String filedsName) {
		this.filedsName = filedsName;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public String getMetadata() {
		return metadata;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public String getCanNull() {
		return canNull;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public void setCanNull(String canNull) {
		this.canNull = canNull;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public String getIsBasic() {
		return isBasic;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public void setIsBasic(String isBasic) {
		this.isBasic = isBasic;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public String getForeignKeys() {
		return foreignKeys;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public void setForeignKeys(String foreignKeys) {
		this.foreignKeys = foreignKeys;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public String getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * 
	 * @todo
	 * @param type
	 * @author qww
	 * @created 2013-7-16 下午4:42:42
	 * @update by:
	 */
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * 
	 * @todo
	 * @return
	 * @author JiaWeiXing
	 * @created 2013-12-24 下午2:53:19
	 * @update by:
	 */
	public String getFieldsorder() {
		return fieldsorder;
	}

	/**
	 * 
	 * @todo
	 * @param fieldsorder
	 * @author JiaWeiXing
	 * @created 2013-12-24 下午2:53:26
	 * @update by:
	 */
	public void setFieldsorder(String fieldsorder) {
		this.fieldsorder = fieldsorder;
	}

}
