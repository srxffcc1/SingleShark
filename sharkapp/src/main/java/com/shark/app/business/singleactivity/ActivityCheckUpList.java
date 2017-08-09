package com.shark.app.business.singleactivity;

import android.os.Message;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.shark.app.R;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ActivityCheckUpList extends FrameActivity {
    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public int getMenuid() {
        return R.menu.enterprisemenu_menu;
    }

    @Override
    public void handleMessage(Message msg) {

    }
}
