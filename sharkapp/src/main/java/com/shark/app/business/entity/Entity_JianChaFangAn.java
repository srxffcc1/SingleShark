package com.shark.app.business.entity;

import com.wisdomregulation.data.entitybase.DateBase_Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity_JianChaFangAn extends DateBase_Entity {
    private List<String> fieldnamelsitchinese=new ArrayList<String>();
    private Map<String,String> fieldvaluemap=new HashMap<String, String>();
    public Entity_JianChaFangAn(){
        super.initsuper(fieldnamelsitchinese, fieldvaluemap);
    }
    {
        fieldnamelsitchinese.clear();
        fieldnamelsitchinese.add("关联的执法编号id");//0
        fieldnamelsitchinese.add("被检查企业id");//1
        fieldnamelsitchinese.add("被检查企业名称");//2
        fieldnamelsitchinese.add("被检查企业地址");//2
        fieldnamelsitchinese.add("法定代表人");//2
        fieldnamelsitchinese.add("联系电话");//2
        fieldnamelsitchinese.add("检查场所");//2
        fieldnamelsitchinese.add("检查时间");//2
        fieldnamelsitchinese.add("行政执法人员");//2
        fieldnamelsitchinese.add("检查方式");//2
        fieldnamelsitchinese.add("审批人");//2
        fieldnamelsitchinese.add("审核人");//2
        fieldnamelsitchinese.add("已经选检查项id");//2
    }
}
