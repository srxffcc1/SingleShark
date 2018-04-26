package com.businessframehelp.utils;

import android.content.Context;

import com.businessframehelp.staticlib.StaticAppInfo;
import com.businessframehelp.staticlib.StaticSdkTool;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by King6rf on 2018/4/26. 初始化某些文件到本地
 */

public class FileSdk {
    public Context context;
    public static final FileSdk instance=new FileSdk();
    private FileSdk(){

    }

    public static FileSdk getInstance(){
        return instance;
    }
    public void dispose(Context context){
        this.context=context;
        initTest();
    }
    public void initTest(){//把所有test文件写入test目录
        try {
            String[] alllist = context.getAssets().list("");
            for (int i = 0; i < alllist.length; i++) {
                if (alllist[i].contains("test")) {
                    copyAssetToPath(alllist[i], StaticAppInfo.getInstance().getProjcetDir()+ StaticSdkTool.TEST+"/"+alllist[i]);
                    System.gc();// gc
                    System.runFinalization();// gc
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void copyAssetToPath( String instring,String outstring) throws IOException {
        InputStream is;
        FileOutputStream fos;
        is = context.getAssets().open(
                instring);
        fos = new FileOutputStream(outstring+"/"+instring);
        long total = is.available();
        int copyedSize = 0;
//            int needsize=total>512*1024*1024?4096:1024;
        byte[] buffer = new byte[1024];
        long byteCount = 0;
        int len = 0;
        long sum = 0;
        while (sum < total) {
            len = is.read(buffer);
            fos.write(buffer, 0, len);
            sum += len;
        }
        is.close();
        fos.flush();// 刷新缓冲区
        fos.close();
    }
}
