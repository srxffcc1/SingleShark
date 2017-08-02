package com.businessframehelp.staticlib;

import android.content.Context;

import java.io.File;

/**
 * Created by King6rf on 2017/8/2.
 * sdk工具用来构造一些结构 目录结构
 */

public class StaticSdkTool {
    public static final String GEN="/";
    public static final String TESTPDF="/TestPdf/";
    public static final String ZHIHEAD="/ZhiHead/";
    public static final String ZHICOLLECT="/ZhiCollect/";
    public static final String ZHIEXPORT="/ZhiExport/";
    public static final String ZHIAPK="/ZhiApk/";
    public static final String ZHIDBHOME="/ZhiDbhome/";
    public static void initDir(Context context){
        new File(StaticAppInfo.getInstance().getProjcetDir() +GEN)
                .mkdirs();
        new File(StaticAppInfo.getInstance().getProjcetDir() + TESTPDF)
                .mkdirs();
        new File(StaticAppInfo.getInstance().getProjcetDir()+ ZHIHEAD)
                .mkdirs();
        new File(StaticAppInfo.getInstance().getProjcetDir() + ZHICOLLECT)
                .mkdirs();
        new File(StaticAppInfo.getInstance().getProjcetDir() + ZHIEXPORT)
                .mkdirs();
        new File(StaticAppInfo.getInstance().getProjcetDir() + ZHIAPK)
                .mkdirs();
        new File(StaticAppInfo.getInstance().getProjcetDir() + ZHIDBHOME)
                .mkdirs();
    }


}
