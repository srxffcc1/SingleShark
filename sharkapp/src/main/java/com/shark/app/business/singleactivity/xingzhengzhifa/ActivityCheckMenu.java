package com.shark.app.business.singleactivity.xingzhengzhifa;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.flyco.tablayout.SegmentTabLayout;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.shark.app.R;

import java.util.ArrayList;
import java.util.List;

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


    public TextView jihuaaddt;
    public TextView fanganaddt;
    public TextView xianchangaddt;
    public TextView chulicuoshiaddt;
    public TextView zelingaddt;
    public TextView zhenggaifuchaaddt;
    public TextView chufaaddt;

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
        jihuaaddt= (TextView) findViewById(R.id.jihuaaddt);
        jihuaaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityJiHua.class));
            }
        });
        fanganl= (RelativeLayout) findViewById(R.id.fanganl);
        fanganaddt= (TextView) findViewById(R.id.fanganaddt);
        fanganaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityFangAn.class));
            }
        });
        xianchangl= (RelativeLayout) findViewById(R.id.xianchangl);
        xianchangaddt= (TextView) findViewById(R.id.xianchangaddt);
        xianchangaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityXianChangJianCha.class));
            }
        });
        chulicuoshil = (RelativeLayout) findViewById(R.id.chulicuoshil);
        chulicuoshiaddt= (TextView) findViewById(R.id.chulicuoshiaddt);
        chulicuoshiaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityChuLiCuoShi.class));
            }
        });
        zelingl= (RelativeLayout) findViewById(R.id.zelingl);
        zelingaddt= (TextView) findViewById(R.id.zelingaddt);
        zelingaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityZeLing.class));
            }
        });
        zhenggaifuchal= (RelativeLayout) findViewById(R.id.zhenggaifuchal);
        zhenggaifuchaaddt= (TextView) findViewById(R.id.zhenggaifuchaaddt);
        zhenggaifuchaaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startZhengGaiMenuDialog();
            }
        });
        chufal= (RelativeLayout) findViewById(R.id.chufal);
        chufaaddt= (TextView) findViewById(R.id.chufaaddt);
        chufaaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChuFaMenuDialog();
            }
        });
    }
    Dialog zhenggaimenudialog;
    public void startZhengGaiMenuDialog() {
        final List<String> strings = new ArrayList<>();
        strings.add("复查来源:现场处理措施");
        strings.add("复查来源:责令限期整改");
        StyledDialog.init(getContext());
        zhenggaimenudialog=
                StyledDialog.buildBottomItemDialog( strings, "cancle",  new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        startActivity(new Intent(getContext(),ActivityZhengGai.class));
                    }

                    @Override
                    public void onBottomBtnClick() {

                    }
                }).show();
        zhenggaimenudialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                zhenggaimenudialog=null;
            }
        });
    }
    Dialog chufamenudialog;
    public void startChuFaMenuDialog() {
        final List<String> strings = new ArrayList<>();
        strings.add("处罚来源:现场检查");
        strings.add("处罚来源:责令限期整改");
        StyledDialog.init(getContext());
        chufamenudialog=
                StyledDialog.buildBottomItemDialog( strings, "cancle",  new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        startActivity(new Intent(getContext(),ActivityChuFa.class));
                    }

                    @Override
                    public void onBottomBtnClick() {

                    }
                }).show();
        chufamenudialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                chufamenudialog=null;
            }
        });
    }
}
