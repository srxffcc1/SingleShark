package com.shark.app.business.singleactivity.xingzhengzhifa;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.flyco.tablayout.SegmentTabLayout;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.shark.app.R;
import com.shark.app.business.entity.Entity_ChuFa;
import com.shark.app.business.entity.Entity_JianChaFangAn;
import com.shark.app.business.entity.Entity_XianChangChuLiCuoShi;
import com.shark.app.business.entity.Entity_XianChangJianCha;
import com.shark.app.business.entity.Entity_ZeLingZhengGai;
import com.shark.app.business.entity.Entity_ZhengGaiFuCha;
import com.shark.app.business.entity.Entity_ZhiFaBianHao;
import com.shark.app.business.entity.Entity_ZhiFaJiHua;
import com.wisdomregulation.data.entitybase.DateBase_Entity;
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
    private EditText jihuamingcheng;
    private EditText jihualeixing;
    private EditText jihuafuzeren;
    private EditText jihuachengyuan;
    private EditText fanganbeijianchaqiye;
    private EditText fanganzhifarenyuan;
    private EditText fanganjianchafangshi;
    private EditText fanganshenpiren;
    private EditText fanganshenheren;
    private EditText xianchangjianchaqiye;
    private EditText xianchangjianchachangsuo;
    private EditText xianchangjianchashijian;
    private EditText xianchangchulicuoshiqiye;
    private EditText zelingqiye;
    private EditText zhenggaiqiye;
    private EditText chufaqiye;
    private ScrollView scrollviewt;

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
    String jihuaid;
    String bianhaoid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkmenu);
        initData();
        initLayout();
        initListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initJihua();
        initFangan();
        initXianChangJianCha();
        initXianChangChuLiCuoShi();
        initZeLingZhengGai();
        initZhengGaiFuCha();
        initChuFa();
    }

    public void initJihua(){
        DateBase_Entity entitytmp=Demo_DBManager.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_ZhiFaJiHua().setId(jihuaid)));
        jihuamingcheng.setText(entitytmp.getValue(0));
        jihualeixing.setText(entitytmp.getValue(2));
        jihuafuzeren.setText(entitytmp.getValue(3));
        jihuachengyuan.setText(entitytmp.getValue(4));
        if(entitytmp.getId().equals("-1")){
            card1.setVisibility(View.GONE);
            card1.setTag("no");
        }else{
            card1.setVisibility(View.VISIBLE);
            jihuaaddt.setVisibility(View.GONE);
        }

    }
    public void initFangan(){
        DateBase_Entity entitytmp=Demo_DBManager.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_JianChaFangAn().putlogic2value("关联的执法编号id","=",bianhaoid)));

        fanganbeijianchaqiye.setText(entitytmp.getValue(2));
        fanganzhifarenyuan.setText(entitytmp.getValue("行政执法人员"));
        fanganjianchafangshi.setText(entitytmp.getValue("检查方式"));
        fanganshenpiren.setText(entitytmp.getValue("审批人"));
        fanganshenheren.setText(entitytmp.getValue("审核人"));
        if(entitytmp.getId().equals("-1")){
            card2.setVisibility(View.GONE);
            card2.setTag("no");
        }else{
            card2.setVisibility(View.VISIBLE);
            fanganaddt.setVisibility(View.GONE);
        }
    }
    public void initXianChangJianCha(){
        DateBase_Entity entitytmp=Demo_DBManager.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_XianChangJianCha().putlogic2value("关联的执法编号id","=",bianhaoid)));

        xianchangjianchaqiye.setText(entitytmp.getValue("被检查企业名称"));
        xianchangjianchachangsuo.setText(entitytmp.getValue("检查场所"));
        xianchangjianchashijian.setText(entitytmp.getValue("检查时间"));
        if(entitytmp.getId().equals("-1")){
            card3.setVisibility(View.GONE);
            card3.setTag("no");
        }else{
            card3.setVisibility(View.VISIBLE);
            xianchangaddt.setVisibility(View.GONE);
        }
    }
    public void initXianChangChuLiCuoShi(){
        DateBase_Entity entitytmp=Demo_DBManager.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_XianChangChuLiCuoShi().putlogic2value("关联的执法编号id","=",bianhaoid)));

        xianchangchulicuoshiqiye.setText(entitytmp.getValue("被检查企业名称"));
        if(entitytmp.getId().equals("-1")){
            card4.setVisibility(View.GONE);
            card4.setTag("no");
        }else{
            card4.setVisibility(View.VISIBLE);
            chulicuoshiaddt.setVisibility(View.GONE);
        }
    }

    public void initZeLingZhengGai(){
        DateBase_Entity entitytmp=Demo_DBManager.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_ZeLingZhengGai().putlogic2value("关联的执法编号id","=",bianhaoid)));

        zelingqiye.setText(entitytmp.getValue("被检查企业名称"));
        if(entitytmp.getId().equals("-1")){
            card5.setVisibility(View.GONE);
            card5.setTag("no");
        }else{
            card5.setVisibility(View.VISIBLE);
            zelingaddt.setVisibility(View.GONE);
        }
    }
    public void initZhengGaiFuCha(){
        DateBase_Entity entitytmp=Demo_DBManager.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_ZhengGaiFuCha().putlogic2value("关联的执法编号id","=",bianhaoid)));

        zhenggaiqiye.setText(entitytmp.getValue("被检查企业名称"));
        if(entitytmp.getId().equals("-1")){
            card6.setVisibility(View.GONE);
            card6.setTag("no");
        }else{
            card6.setVisibility(View.VISIBLE);
            zhenggaifuchaaddt.setVisibility(View.GONE);
        }
    }

    public void initChuFa(){
        DateBase_Entity entitytmp=Demo_DBManager.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_ChuFa().putlogic2value("关联的执法编号id","=",bianhaoid)));

        chufaqiye.setText(entitytmp.getValue("被检查企业名称"));
        if(entitytmp.getId().equals("-1")){
            card7.setVisibility(View.GONE);
            card7.setTag("no");
        }else{
            card7.setVisibility(View.VISIBLE);
            chufaaddt.setVisibility(View.GONE);
        }
    }
    public void initData(){
        bianhaoid=getIntent().getStringExtra("bianhaoid");
        jihuaid=getIntent().getStringExtra("jihuaid");
        Demo_DBManager.build().search(new Entity_ZhiFaBianHao());
    }
    public void initLayout() {
        scrollviewt = (ScrollView) findViewById(R.id.scrollviewt);
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

        jihuamingcheng = (EditText) findViewById(R.id.jihuamingcheng);
        jihualeixing = (EditText) findViewById(R.id.jihualeixing);
        jihuafuzeren = (EditText) findViewById(R.id.jihuafuzeren);
        jihuachengyuan = (EditText) findViewById(R.id.jihuachengyuan);

        fanganbeijianchaqiye = (EditText) findViewById(R.id.fanganbeijianchaqiye);
        fanganzhifarenyuan = (EditText) findViewById(R.id.fanganzhifarenyuan);
        fanganjianchafangshi = (EditText) findViewById(R.id.fanganjianchafangshi);
        fanganshenpiren = (EditText) findViewById(R.id.fanganshenpiren);
        fanganshenheren = (EditText) findViewById(R.id.fanganshenheren);

        xianchangjianchaqiye = (EditText) findViewById(R.id.xianchangjianchaqiye);
        xianchangjianchachangsuo = (EditText) findViewById(R.id.xianchangjianchachangsuo);
        xianchangjianchashijian = (EditText) findViewById(R.id.xianchangjianchashijian);

        xianchangchulicuoshiqiye = (EditText) findViewById(R.id.xianchangchulisuochiqiye);
        zelingqiye = (EditText) findViewById(R.id.zelingqiye);
        zhenggaiqiye = (EditText) findViewById(R.id.zhenggaiqiye);
        chufaqiye = (EditText) findViewById(R.id.chufaqiye);

    }
    int nowtag=0;
    public void initListener() {
        scrollviewt.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollviewt.post(new Runnable() {
                    public void run() {
                        if(nowtag>2){
                            scrollviewt.fullScroll(View.FOCUS_DOWN);
                        }

                    }
                });
            }
        });
        yincang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowtag=0;
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
                nowtag=0;
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
                nowtag=1;
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
                nowtag=1;
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
                nowtag=2;
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
                nowtag=2;
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
                nowtag=3;
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
                nowtag=3;
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
                nowtag=4;
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
                nowtag=4;
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
                nowtag=5;
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
                nowtag=5;
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
                nowtag=6;
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
                nowtag=6;
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
                startActivityForResult(new Intent(getContext(),ActivityJiHua.class).putExtra("bianhaoid",bianhaoid),111);
            }
        });

        fanganaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityFangAn.class).putExtra("bianhaoid",bianhaoid),111);
            }
        });

        xianchangaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityXianChangJianCha.class).putExtra("bianhaoid",bianhaoid),111);
            }
        });

        chulicuoshiaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityChuLiCuoShi.class).putExtra("bianhaoid",bianhaoid),111);
            }
        });

        zelingaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityZeLing.class).putExtra("bianhaoid",bianhaoid),111);
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
                        startActivityForResult(new Intent(getContext(),ActivityZhengGai.class).putExtra("bianhaoid",bianhaoid),111);
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
                        startActivityForResult(new Intent(getContext(),ActivityChuFa.class).putExtra("bianhaoid",bianhaoid),111);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initJihua();
        initFangan();
        initXianChangJianCha();
        initXianChangChuLiCuoShi();
        initZeLingZhengGai();
        initZhengGaiFuCha();
        initChuFa();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                scrollviewt.fullScroll(View.FOCUS_DOWN);
            }
        },300);
    }
}
