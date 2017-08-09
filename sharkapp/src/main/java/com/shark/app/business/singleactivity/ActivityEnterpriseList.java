package com.shark.app.business.singleactivity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.businessframehelp.staticlib.StaticAppInfo;
import com.shark.app.R;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ActivityEnterpriseList extends FrameActivity {

    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public int getMenuid() {
        return R.menu.enterprisemenu_menu;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterpriselist);

        if (StaticAppInfo.getInstance().getMode()== StaticAppInfo.StaticMode.test) {

        }else{
            if(checkNet()){
//            httpPost();
            }
        }


    }
}
