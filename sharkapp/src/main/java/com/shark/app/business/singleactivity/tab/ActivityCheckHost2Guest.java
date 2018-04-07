package com.shark.app.business.singleactivity.tab;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.businessframehelp.utils.BarUtil;
import com.businessframehelp.utils.ViewFindUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.shark.app.R;
import com.shark.app.business.entity.TabEntity;
import com.shark.app.business.fragment.FragmentManagers;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ActivityCheckHost2Guest extends FrameActivity {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"我的主办", "我的协办"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int[] mIconUnselectIds = {
            R.drawable.tab_host_unselect, R.drawable.tab_join_unselect
    };
    private int[] mIconSelectIds = {
            R.drawable.tab_host_select, R.drawable.tab_join_select
    };
    private View mDecorView;
    private CommonTabLayout mTabLayout_3;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_tab);
        BarUtil.initBar(this,"执法任务");
        for (String title : mTitles) {
            mFragments.add(FragmentManagers.getInstance().factory(title));
        }
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mDecorView = getWindow().getDecorView();

        mTabLayout_3 = ViewFindUtils.find(mDecorView, R.id.tl_3);
        mTabLayout_3.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
    }
}
