package com.shark.app.business.singleactivity.library;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.shark.app.R;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ActivityZfyj extends FrameActivity {


    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public int getMenuid() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_searchs){
            System.out.println("点击了综合查询");
        }
        return super.onOptionsItemSelected(item);
    }

}
