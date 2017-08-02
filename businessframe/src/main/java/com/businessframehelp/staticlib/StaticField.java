package com.businessframehelp.staticlib;

/**
 * app静态字段记录类
 * Created by King6rf on 2017/5/10.
 */
@Deprecated
public class StaticField {
    private static String syncurl="login!sjdsjtb";//线上交互地址头   login!sjdsjtb?json参数json
    private static String bigplanurl="enforcementPlan/enforcementPlanAction!listAll";//bigplan表
    private static String smallplanurl="enforcementArea/enforcementAreaAction!listAll";//plan表
    private static String checkurl="enforcement/enforcementAction!listAll";//check表
    private static String checkoptionurl="yinHuanXinXi/yinHuanXinXiAction!listAll";//checkoption表
    private static String lawurl="xingZhengZhiFa/xingZhengZhiFaAction!listAll";//law表
    private static String companyurl="enterprise/enterpriseAction!listAll";//enterprise表 参数createTime不要一次写两个参数updateTime   无参数就是请求所有 没有参数就是所有 分开请求 第一次请求create 得到的数据直接插入 第二次请求 uodate的 得到的数据 对元数据进行一次判断性质的删除 再插入
    private static String saveproducebase="criterion/criKnowlbaseAction!listAll";//参数同上问题criterion  安全生产标准库
    private static String dangerousbase="tbMsds/tbMsdsAction!listAll";//参数同上问题?tbMsds   危险品化学特征库
    private static String lawdependencebase="zhiFaYiJuKu/zhiFaYiJuKuAction!listAll";//参数同上问题?zhiFaYiJuKu  执法依据库
    private static String casespecialbase="accidentCase/accKnowlbaseAction!listAll";//参数同上问题?accidentCase 典型案例
    private static String savelawbase="law/lawKnowlbaseAction!listAll";//参数同上问题?law   安全生产标注库
    private static String checkonlineurl="login!loginApp.action";//login  username= password= checkType=gov; 登陆参数
    private static String loginurl="login!loginApps.action";//login  username= password= checkType=gov; 登陆参数
    private static String upload="uploadphone/upload";//上传文件 传入id 和 文件内容

    private static  final  StaticField STATIC_FIELD=new StaticField();
    private StaticField(){

    }
    public static StaticField getInstance(){
        return  STATIC_FIELD;
    }
}
