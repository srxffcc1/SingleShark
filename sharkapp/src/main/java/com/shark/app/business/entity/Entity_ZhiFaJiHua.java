package com.shark.app.business.entity;

import com.wisdomregulation.data.entitybase.DateBase_Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity_ZhiFaJiHua extends DateBase_Entity {
    private List<String> fieldnamelsitchinese=new ArrayList<String>();
    private Map<String,String> fieldvaluemap=new HashMap<String, String>();
    public Entity_ZhiFaJiHua(){
        super.initsuper(fieldnamelsitchinese, fieldvaluemap);
    }
    {
        fieldnamelsitchinese.clear();
        fieldnamelsitchinese.add("计划名称");//0
        fieldnamelsitchinese.add("计划来源");//1
        fieldnamelsitchinese.add("计划类型");//1
        fieldnamelsitchinese.add("计划负责人");//1
        fieldnamelsitchinese.add("计划成员");//1
        fieldnamelsitchinese.add("计划完成时间");//2
    }
}
