package com.shark.app.business.singleactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;

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
    public RelativeLayout jihual;
    public RelativeLayout fanganl;
    public RelativeLayout xianchangl;
    public RelativeLayout chulicuoshil;
    public RelativeLayout zelingl;
    public RelativeLayout zhenggaifuchal;
    public RelativeLayout chufal;
    public RelativeLayout guidangl;

    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public int getMenuid() {
        return R.menu.place_menu;
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
        jihual= (RelativeLayout) findViewById(R.id.jihual);
        jihual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityJiHua.class));
            }
        });
        fanganl= (RelativeLayout) findViewById(R.id.fanganl);
        fanganl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityFangAn.class));
            }
        });
        xianchangl= (RelativeLayout) findViewById(R.id.xianchangl);
        xianchangl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityXianChangJianCha.class));
            }
        });
        chulicuoshil = (RelativeLayout) findViewById(R.id.chulicuoshil);
        chulicuoshil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityChuLiCuoShi.class));
            }
        });
        zelingl= (RelativeLayout) findViewById(R.id.zelingl);
        zelingl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityZeLing.class));
            }
        });
        zhenggaifuchal= (RelativeLayout) findViewById(R.id.zhenggaifuchal);
        zhenggaifuchal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityZhengGaiMenu.class));
            }
        });
        chufal= (RelativeLayout) findViewById(R.id.chufal);
        chufal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityChuFaMenu.class));
            }
        });
    }
}
