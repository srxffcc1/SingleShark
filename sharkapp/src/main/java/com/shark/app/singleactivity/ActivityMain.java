package com.shark.app.singleactivity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.businessframehelp.utils.ViewFindUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.shark.app.R;
import com.shark.app.apps.FragmentManager;
import com.shark.app.entity.TabEntity;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/18.
 */

 public class ActivityMain extends FrameActivity {
   private ArrayList<Fragment> mFragments = new ArrayList<>();
   private String[] mTitles = {"首页", "消息"};
   private int[] mIconUnselectIds = {
           R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect
           };
   private int[] mIconSelectIds = {
           R.mipmap.tab_home_select, R.mipmap.tab_speech_select
           };
   private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
   private View mDecorView;
   private CommonTabLayout mTabLayout_3;
    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override

    public void handleMessage(Message msg) {

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new SlidingRootNavBuilder(this)
                .setBackgroundRid(R.drawable.img_frame_background)
                .withMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.activity_leftmenu)
                .inject();
        for (String title : mTitles) {
            mFragments.add(FragmentManager.getInstance().factory(title));
        }


        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mDecorView = getWindow().getDecorView();

        mTabLayout_3 = ViewFindUtils.find(mDecorView, R.id.tl_3);

        mTabLayout_3.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
    }
    public void syncBackGround(){

    }
    public void sync(){

    }
    public void showMenu(){

    }
    public void addPlugin(){

    }
    public void update(){

    }
    public void logout(){

    }

}