package com.shark.app.business.entity;

import com.wisdomregulation.data.entitybase.DateBase_Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity_ZhiFaBianHao extends DateBase_Entity {
    private List<String> fieldnamelsitchinese=new ArrayList<String>();
    private Map<String,String> fieldvaluemap=new HashMap<String, String>();
    public Entity_ZhiFaBianHao(){
        super.initsuper(fieldnamelsitchinese, fieldvaluemap);
    }
    {
        fieldnamelsitchinese.clear();
        fieldnamelsitchinese.add("执法编号");//0
        fieldnamelsitchinese.add("所属计划id");//1
        fieldnamelsitchinese.add("所属计划名称");//1
        fieldnamelsitchinese.add("企业名称");//2
        fieldnamelsitchinese.add("检查时间");//3
    }
}
