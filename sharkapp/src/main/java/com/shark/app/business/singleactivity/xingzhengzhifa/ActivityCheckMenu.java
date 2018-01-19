package com.shark.app.business.singleactivity.xingzhengzhifa;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.flyco.tablayout.SegmentTabLayout;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.shark.app.R;
import com.shark.app.business.entity.Entity_ZhiFaBianHao;
import com.wisdomregulation.help.Demo_DBManager;

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


    TextView yincang1;
    TextView yincang2;
    TextView yincang3;
    TextView yincang4;
    TextView yincang5;
    TextView yincang6;
    TextView yincang7;

    CardView card1;
    CardView card2;
    CardView card3;
    CardView card4;
    CardView card5;
    CardView card6;
    CardView card7;

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
        initData();
        initLayout();
        initListener();

    }
    public void initData(){
        Demo_DBManager.build().search(new Entity_ZhiFaBianHao());
    }
    public void initLayout() {
        jihual= (RelativeLayout) findViewById(R.id.jihual);
        fanganl= (RelativeLayout) findViewById(R.id.fanganl);
        xianchangl= (RelativeLayout) findViewById(R.id.xianchangl);
        chulicuoshil = (RelativeLayout) findViewById(R.id.chulicuoshil);
        zelingl= (RelativeLayout) findViewById(R.id.zelingl);
        zhenggaifuchal= (RelativeLayout) findViewById(R.id.zhenggaifuchal);
        chufal= (RelativeLayout) findViewById(R.id.chufal);


        jihuaaddt= (TextView) findViewById(R.id.jihuaaddt);
        fanganaddt= (TextView) findViewById(R.id.fanganaddt);
        xianchangaddt= (TextView) findViewById(R.id.xianchangaddt);
        chulicuoshiaddt= (TextView) findViewById(R.id.chulicuoshiaddt);
        zhenggaifuchaaddt= (TextView) findViewById(R.id.zhenggaifuchaaddt);
        zelingaddt= (TextView) findViewById(R.id.zelingaddt);
        chufaaddt= (TextView) findViewById(R.id.chufaaddt);

        yincang1= (TextView) findViewById(R.id.yincang1);
        yincang2= (TextView) findViewById(R.id.yincang2);
        yincang3= (TextView) findViewById(R.id.yincang3);
        yincang4= (TextView) findViewById(R.id.yincang4);
        yincang5= (TextView) findViewById(R.id.yincang5);
        yincang6= (TextView) findViewById(R.id.yincang6);
        yincang7= (TextView) findViewById(R.id.yincang7);

        card1= (CardView) findViewById(R.id.card1);
        card2= (CardView) findViewById(R.id.card2);
        card3= (CardView) findViewById(R.id.card3);
        card4= (CardView) findViewById(R.id.card4);
        card5= (CardView) findViewById(R.id.card5);
        card6= (CardView) findViewById(R.id.card6);
        card7= (CardView) findViewById(R.id.card7);
    }
    public void initListener() {
        yincang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card1.getVisibility()==View.VISIBLE){
                    card1.setVisibility(View.GONE);
                }else{
                    card1.setVisibility(View.VISIBLE);
                }
            }
        });
        jihual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card1.getVisibility()==View.VISIBLE){
                    card1.setVisibility(View.GONE);
                }else{
                    card1.setVisibility(View.VISIBLE);
                }
            }
        });
        yincang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card2.getVisibility()==View.VISIBLE){
                    card2.setVisibility(View.GONE);
                }else{
                    card2.setVisibility(View.VISIBLE);
                }
            }
        });
        fanganl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card2.getVisibility()==View.VISIBLE){
                    card2.setVisibility(View.GONE);
                }else{
                    card2.setVisibility(View.VISIBLE);
                }
            }
        });
        yincang3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card3.getVisibility()==View.VISIBLE){
                    card3.setVisibility(View.GONE);
                }else{
                    card3.setVisibility(View.VISIBLE);
                }
            }
        });

        xianchangl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card3.getVisibility()==View.VISIBLE){
                    card3.setVisibility(View.GONE);
                }else{
                    card3.setVisibility(View.VISIBLE);
                }
            }
        });
        yincang4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card4.getVisibility()==View.VISIBLE){
                    card4.setVisibility(View.GONE);
                }else{
                    card4.setVisibility(View.VISIBLE);
                }
            }
        });
        chulicuoshil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card4.getVisibility()==View.VISIBLE){
                    card4.setVisibility(View.GONE);
                }else{
                    card4.setVisibility(View.VISIBLE);
                }
            }
        });
        yincang4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card4.getVisibility()==View.VISIBLE){
                    card4.setVisibility(View.GONE);
                }else{
                    card4.setVisibility(View.VISIBLE);
                }
            }
        });
        yincang5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card5.getVisibility()==View.VISIBLE){
                    card5.setVisibility(View.GONE);
                }else{
                    card5.setVisibility(View.VISIBLE);
                }
            }
        });
        zelingl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card5.getVisibility()==View.VISIBLE){
                    card5.setVisibility(View.GONE);
                }else{
                    card5.setVisibility(View.VISIBLE);
                }
            }
        });
        yincang6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card6.getVisibility()==View.VISIBLE){
                    card6.setVisibility(View.GONE);
                }else{
                    card6.setVisibility(View.VISIBLE);
                }
            }
        });
        zhenggaifuchal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card6.getVisibility()==View.VISIBLE){
                    card6.setVisibility(View.GONE);
                }else{
                    card6.setVisibility(View.VISIBLE);
                }
            }
        });
        yincang7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card7.getVisibility()==View.VISIBLE){
                    card7.setVisibility(View.GONE);
                }else{
                    card7.setVisibility(View.VISIBLE);
                }
            }
        });
        chufal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(card7.getVisibility()==View.VISIBLE){
                    card7.setVisibility(View.GONE);
                }else{
                    card7.setVisibility(View.VISIBLE);
                }
            }
        });
        jihuaaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityJiHua.class));
            }
        });

        fanganaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityFangAn.class));
            }
        });

        xianchangaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityXianChangJianCha.class));
            }
        });

        chulicuoshiaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityChuLiCuoShi.class));
            }
        });

        zelingaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),ActivityZeLing.class));
            }
        });

        zhenggaifuchaaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startZhengGaiMenuDialog();
            }
        });

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
