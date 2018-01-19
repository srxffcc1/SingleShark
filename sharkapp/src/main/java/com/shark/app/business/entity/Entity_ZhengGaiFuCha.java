package com.shark.app.business.entity;

import com.wisdomregulation.data.entitybase.DateBase_Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity_ZhengGaiFuCha extends DateBase_Entity {
    private List<String> fieldnamelsitchinese=new ArrayList<String>();
    private Map<String,String> fieldvaluemap=new HashMap<String, String>();
    public Entity_ZhengGaiFuCha(){
        super.initsuper(fieldnamelsitchinese, fieldvaluemap);
    }
    {
        fieldnamelsitchinese.clear();
        fieldnamelsitchinese.add("被检查企业id");//0
        fieldnamelsitchinese.add("复查意见");//1
        fieldnamelsitchinese.add("关联处罚决定书类型id");//2
        fieldnamelsitchinese.add("关联处罚决定书id");//2
        fieldnamelsitchinese.add("检查人");//2
        fieldnamelsitchinese.add("检查时间");//2
    }
}
