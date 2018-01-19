package com.shark.app.business.entity;

import com.wisdomregulation.data.entitybase.DateBase_Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity_ChuFa extends DateBase_Entity {
    private List<String> fieldnamelsitchinese=new ArrayList<String>();
    private Map<String,String> fieldvaluemap=new HashMap<String, String>();
    public Entity_ChuFa(){
        super.initsuper(fieldnamelsitchinese, fieldvaluemap);
    }
    {
        fieldnamelsitchinese.clear();
        fieldnamelsitchinese.add("被检查企业id");//0
        fieldnamelsitchinese.add("企业地址");//1
        fieldnamelsitchinese.add("缴纳罚金数量");//2
        fieldnamelsitchinese.add("缴纳罚金账号");//2
    }
}
