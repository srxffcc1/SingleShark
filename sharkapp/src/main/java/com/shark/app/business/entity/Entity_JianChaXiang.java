package com.shark.app.business.entity;

import com.wisdomregulation.data.entitybase.DateBase_Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity_JianChaXiang extends DateBase_Entity {
    private List<String> fieldnamelsitchinese=new ArrayList<String>();
    private Map<String,String> fieldvaluemap=new HashMap<String, String>();
    public Entity_JianChaXiang(){
        super.initsuper(fieldnamelsitchinese, fieldvaluemap);
    }
    {
        fieldnamelsitchinese.clear();

        fieldnamelsitchinese.add("关联的执法编号id");//0
        fieldnamelsitchinese.add("关联的检查项模板id");//0
        fieldnamelsitchinese.add("进行的阶段转化id");//0
        fieldnamelsitchinese.add("检查项一级");//1
        fieldnamelsitchinese.add("检查项二级");//2
        fieldnamelsitchinese.add("检查项三级");//2
        fieldnamelsitchinese.add("相关法律");//2
        fieldnamelsitchinese.add("相关依据");//2
        fieldnamelsitchinese.add("检查部位");//2
        fieldnamelsitchinese.add("隐患级别");//2
        fieldnamelsitchinese.add("处理决定");//2
    }
}
