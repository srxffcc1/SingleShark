package com.businessframehelp.db.ay;


import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 
 * 作用：动态bean
 * 作者： qww
 * 创建时间 ： 2013-7-10
 */
public class DynamicBean {
	
	private Object object = null;

	
	private BeanMap beanMap = null;
/**
 * 
 * Constructor Method
 */
	public DynamicBean() {
		super();
	}

	/**
	 * 
	 * @todo  
	 * @author qww
	 * @created 2013-7-16  下午4:36:06
	 * @update by:
	 */
	public DynamicBean(Map<?, ?> propertyMap) {
		this.object = generateBean(propertyMap);
		this.beanMap = BeanMap.create(this.object);
	}
/**
 * 
 * @todo  
 * @author qww
 * @created 2013-7-16  下午4:36:06
 * @update by:
 */
	public void setValue(String property, Object value) {
		beanMap.put(property, value);
	}

	/**
	 * 
	 * @todo  
	 * @author qww
	 * @created 2013-7-16  下午4:36:06
	 * @update by:
	 */
	public Object getValue(String property) {
		return beanMap.get(property);
	}

	/**
	 * 
	 * @todo  
	 * @author qww
	 * @created 2013-7-16  下午4:36:06
	 * @update by:
	 */
	public Object getObject() {
		return this.object;
	}


	/**
	 * 
	 * @todo  
	 * @author qww
	 * @created 2013-7-16  下午4:36:06
	 * @update by:
	 */
	private Object generateBean(Map<?, ?> propertyMap) {
		BeanGenerator generator = new BeanGenerator();
		Set<?> keySet = propertyMap.keySet();
		for (Iterator<?> i = keySet.iterator(); i.hasNext();) {
			String key = (String) i.next();
			generator.addProperty(key, (Class<?>) propertyMap.get(key));
		}
		return generator.create();
	}
}
