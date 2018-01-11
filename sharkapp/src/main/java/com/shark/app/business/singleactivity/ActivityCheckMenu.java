package com.shark.app.business.singleactivity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.flyco.tablayout.SegmentTabLayout;
import com.shark.app.R;

import java.util.ArrayList;

/**
 * Created by King6rf on 2017/9/25.
 */

public class ActivityCheckMenu extends FrameActivity {
    private String[] mTitles_3 ;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private SegmentTabLayout mTabLayout_3;
    int testlengtht=17;
    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public int getMenuid() {
        return -1;
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public boolean needActionBar() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkmenu);
    }
}
