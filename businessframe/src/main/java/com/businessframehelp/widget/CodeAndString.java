package com.businessframehelp.widget;

/**
 * code和中文的包装类
 */
public class CodeAndString {
String code;
String name;
public CodeAndString(String code, String name) {
	super();
	this.code = code;
	this.name = name;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
@Override
public String toString() {
	return code;
}

}
