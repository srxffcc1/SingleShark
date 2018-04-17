package com.shark.app.business.view.xingzhengzhifa;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.businessframehelp.widget.ScrollLinearLayoutManager;
import com.flyco.tablayout.SegmentTabLayout;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.shark.app.R;
import com.shark.app.business.adapter.ChoseCheckRecycleAdapter;
import com.shark.app.business.entity.Entity_ChuFa;
import com.shark.app.business.entity.Entity_JianChaFangAn;
import com.shark.app.business.entity.Entity_JianChaXiang;
import com.shark.app.business.entity.Entity_XianChangChuLiCuoShi;
import com.shark.app.business.entity.Entity_XianChangJianCha;
import com.shark.app.business.entity.Entity_ZeLingZhengGai;
import com.shark.app.business.entity.Entity_ZhengGaiFuCha;
import com.shark.app.business.entity.Entity_ZhiFaBianHao;
import com.shark.app.business.entity.Entity_ZhiFaJiHua;
import com.wisdomregulation.data.entitybase.DateBase_Entity;
import com.wisdomregulation.help.Demo_DBManager;
import com.wisdomregulation.help.Demo_DbUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King6rf on 2017/9/25.
 */

public class ActivityCheckMenu extends FrameActivity{
    private String[] mTitles_3 ;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private SegmentTabLayout mTabLayout_3;
    int testlengtht=17;






    public TextView jihual;
    public TextView fanganl;
    public TextView xianchangl;
    public TextView chulicuoshil;
    public TextView zelingl;
    public TextView zhenggaifuchal;
    public TextView chufal;
    public TextView guidangl;


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



    TextView shanchu1;
    TextView shanchu2;
    TextView shanchu3;
    TextView shanchu4;
    TextView shanchu5;
    TextView shanchu6;
    TextView shanchu7;

    CardView card1;
    CardView card2;
    CardView card3;
    CardView card4;
    CardView card5;
    CardView card6;
    CardView card7;
    private TextView jihuamingcheng;
    private TextView jihualeixing;
    private TextView jihuafuzeren;
    private TextView jihuachengyuan;
    private TextView fanganbeijianchaqiye;
    private TextView fanganzhifarenyuan;
    private TextView fanganjianchafangshi;
    private TextView fanganshenpiren;
    private TextView fanganshenheren;
    private TextView xianchangjianchaqiye;
    private TextView xianchangjianchachangsuo;
    private TextView xianchangjianchashijian;
    private TextView xianchangchulicuoshiqiye;
    private TextView zelingqiye;
    private TextView zhenggaiqiye;
    private TextView chufaqiye;
    private ScrollView scrollviewt;

    private RecyclerView jianchajilurecycleview;
    private RecyclerView jianchajilurecycleview2;
    private DateBase_Entity entityJihua;
    private DateBase_Entity entityFangAn;
    private DateBase_Entity entityJianCha;
    private DateBase_Entity entityChuLi;
    private DateBase_Entity entityZeLing;
    private DateBase_Entity entityZhengGai;
    private DateBase_Entity entityChuFa;
    private LinearLayout linearpart1;
    private LinearLayout linearpart2;
    private LinearLayout linearpart3;
    private LinearLayout linearpart4;
    private LinearLayout linearpart5;
    private LinearLayout linearpart6;
    private LinearLayout linearpart7;
    private RecyclerView chulirecycleview;
    private RecyclerView zelingrecycleview;
    private RecyclerView chufarecycleview;
    private RecyclerView chufarecycleview2;
    private String chufachulijueding;
    String beijianchaqiyetext;
    private TextView fuchayijian;
    private TextView chakan1;
    private TextView chakan2;
    private TextView chakan3;
    private TextView chakan4;
    private TextView chakan5;
    private TextView chakan6;
    private TextView chakan7;

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
    String jihuaid;
    String bianhaoid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkmenu);
        initData();
        initLayout();
        initListener();
        Switch ss;

    }

    @Override
    protected void onResume() {
        super.onResume();
        buildView();
    }

    private void buildView() {
        initAllList();
    }

    private void initAllList() {
        initJihua();
        initFangan();
        initXianChangJianCha();
        initXianChangChuLiCuoShi();
        initZeLingZhengGai();
        initZhengGaiFuCha();
        initChuFa();
    }

    public void initJihua(){
        entityJihua = Demo_DbUtil.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_ZhiFaJiHua().setId(jihuaid)));
        jihuamingcheng.setText(":"+entityJihua.getValue(0));
        jihualeixing.setText(":"+entityJihua.getValue(2));
        jihuafuzeren.setText(":"+entityJihua.getValue(3));
        jihuachengyuan.setText(":"+entityJihua.getValue(4));
        if(entityJihua.getId().equals("-1")){
            card1.setVisibility(View.GONE);
            card1.setTag("no");
            jihuaaddt.setVisibility(View.VISIBLE);
        }else{
            card1.setVisibility(View.VISIBLE);
            jihuaaddt.setVisibility(View.GONE);
        }

    }
    public void initFangan(){
        entityFangAn = Demo_DbUtil.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_JianChaFangAn().putlogic2value("关联的执法编号id","=",bianhaoid)));

        fanganbeijianchaqiye.setText(":"+entityFangAn.getValue(2));
        beijianchaqiyetext=entityFangAn.getValue(2);
        fanganzhifarenyuan.setText(":"+entityFangAn.getValue("行政执法人员"));
        fanganjianchafangshi.setText(":"+entityFangAn.getValue("检查方式"));
        fanganshenpiren.setText(":"+entityFangAn.getValue("审批人"));
        fanganshenheren.setText(":"+entityFangAn.getValue("审核人"));
        if(entityFangAn.getId().equals("-1")){

            fanganl.setEnabled(false);
            card2.setVisibility(View.GONE);
            card2.setTag("no");
            fanganaddt.setVisibility(View.VISIBLE);
        }else{

            fanganl.setEnabled(true);
            card2.setVisibility(View.VISIBLE);
            fanganaddt.setVisibility(View.GONE);
        }
        shanchu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Demo_DBManager.build().delete(entityFangAn);
                buildView();
            }
        });
    }
    public void initXianChangJianCha(){
        if(entityFangAn.getId().equals("-1")){
            linearpart3.setVisibility(View.GONE);
        }else{
            linearpart3.setVisibility(View.VISIBLE);
        }
        entityJianCha = Demo_DbUtil.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_XianChangJianCha().putlogic2value("关联的执法编号id","=",bianhaoid)));

        xianchangjianchaqiye.setText(":"+entityJianCha.getValue("被检查企业名称"));
        xianchangjianchachangsuo.setText(":"+entityJianCha.getValue("检查场所"));
        xianchangjianchashijian.setText(":"+entityJianCha.getValue("检查时间"));
        if(entityJianCha.getId().equals("-1")){
            xianchangl.setEnabled(false);
            card3.setVisibility(View.GONE);
            card3.setTag("no");
            xianchangaddt.setVisibility(View.VISIBLE);
        }else{
            xianchangl.setEnabled(true);
            card3.setVisibility(View.VISIBLE);
            xianchangaddt.setVisibility(View.GONE);
        }
        shanchu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Demo_DBManager.build().delete(entityJianCha);
                buildView();
            }
        });
        initJianChaJiLuList();
    }
    private void initJianChaJiLuList() {
        List<DateBase_Entity> showlist= Demo_DbUtil.getSearchResult(Demo_DBManager.build().search(new Entity_JianChaXiang()
                .putlogic2value("隐患级别","<>","无隐患")
                .putlogic2value("关联的执法编号id","=",bianhaoid)));
        jianchajilurecycleview.setLayoutManager(new ScrollLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        jianchajilurecycleview.setAdapter(new ChoseCheckRecycleAdapter(this,showlist).setCanedit(false));


    }
    private void initChuLiList() {
        List<DateBase_Entity> showlist= Demo_DbUtil.getSearchResult(Demo_DBManager.build().query(Demo_DBManager.lowbuild().justgetSqlUNION(new Entity_JianChaXiang()
                .putlogic2value("隐患级别","<>","无隐患")
                .putlogic2value("进行的阶段转化id","=","现场处理措施")
                .putlogic2value("关联的执法编号id","=",bianhaoid),new Entity_JianChaXiang()
                .putlogic2value("隐患级别","<>","无隐患")
                .putlogic2value("进行的阶段转化id","=","并处")
                .putlogic2value("关联的执法编号id","=",bianhaoid)),new Entity_JianChaXiang()));
        if(showlist.size()<1){
            linearpart4.setVisibility(View.GONE);
        }
        chulirecycleview.setLayoutManager(new ScrollLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        chulirecycleview.setAdapter(new ChoseCheckRecycleAdapter(this,showlist).setCanedit(false));

    }
    private void initZeLingList() {
        List<DateBase_Entity> showlist= Demo_DbUtil.getSearchResult(Demo_DBManager.build().search(new Entity_JianChaXiang()
                .putlogic2value("隐患级别","<>","无隐患")
                .putlogic2value("进行的阶段转化id","=","责令限期整改")
                .putlogic2value("关联的执法编号id","=",bianhaoid)));
        if(showlist.size()<1){

            linearpart5.setVisibility(View.GONE);
        }
        zelingrecycleview.setLayoutManager(new ScrollLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        zelingrecycleview.setAdapter(new ChoseCheckRecycleAdapter(this,showlist).setCanedit(false));

    }
    private void initChuFaList1() {
        List<DateBase_Entity> showlist1= Demo_DbUtil.getSearchResult(Demo_DBManager.build().search(new Entity_JianChaXiang()
                .putlogic2value("隐患级别","<>","无隐患")
                .putlogic2value("进行的阶段转化id","=","并处")
                .putlogic2value("关联的执法编号id","=",bianhaoid)));
        chufarecycleview.setLayoutManager(new ScrollLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        chufarecycleview.setAdapter(new ChoseCheckRecycleAdapter(this,showlist1).setCanedit(false));

        if(!entityZhengGai.getId().equals("-1")){
            List<DateBase_Entity> showlist2= Demo_DbUtil.getSearchResult(Demo_DBManager.build().search(new Entity_JianChaXiang()
                    .putlogic2value("隐患级别","<>","无隐患")
                    .putlogic2value("进行的阶段转化id","=",entityZhengGai.getValue("关联处罚决定书"))
                    .putlogic2value("关联的执法编号id","=",bianhaoid)));
            chufarecycleview2.setLayoutManager(new ScrollLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
            chufarecycleview2.setAdapter(new ChoseCheckRecycleAdapter(this,showlist2).setCanedit(false));
        }


    }


    public void initXianChangChuLiCuoShi(){
        if(entityJianCha.getId().equals("-1")){
            linearpart4.setVisibility(View.GONE);

        }else{
            linearpart4.setVisibility(View.VISIBLE);
        }
        entityChuLi = Demo_DbUtil.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_XianChangChuLiCuoShi().putlogic2value("关联的执法编号id","=",bianhaoid)));

        xianchangchulicuoshiqiye.setText(":"+entityChuLi.getValue("被检查企业名称"));
        if(entityChuLi.getId().equals("-1")){
            chulicuoshil.setEnabled(false);
            card4.setVisibility(View.GONE);
            card4.setTag("no");
            chulicuoshiaddt.setVisibility(View.VISIBLE);
        }else{
            chulicuoshil.setEnabled(true);
            card4.setVisibility(View.VISIBLE);
            chulicuoshiaddt.setVisibility(View.GONE);
        }
        shanchu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Demo_DBManager.build().delete(entityChuLi);
                buildView();
            }
        });
        initChuLiList();
    }



    public void initZeLingZhengGai(){
        if(entityJianCha.getId().equals("-1")){
            linearpart5.setVisibility(View.GONE);
        }else{
            linearpart5.setVisibility(View.VISIBLE);
        }
        entityZeLing = Demo_DbUtil.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_ZeLingZhengGai().putlogic2value("关联的执法编号id","=",bianhaoid)));

        zelingqiye.setText(":"+entityZeLing.getValue("被检查企业名称"));

        if(entityZeLing.getId().equals("-1")){
            zelingl.setEnabled(false);
            card5.setVisibility(View.GONE);
            card5.setTag("no");
            zelingaddt.setVisibility(View.VISIBLE);
        }else{
            zelingl.setEnabled(true);
            card5.setVisibility(View.VISIBLE);
            zelingaddt.setVisibility(View.GONE);
        }
        shanchu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Demo_DBManager.build().delete(entityZeLing);
                buildView();
            }
        });
        initZeLingList();
    }



    public void initZhengGaiFuCha(){
        if(entityChuLi.getId().equals("-1")&&entityZeLing.getId().equals("-1")){
            linearpart6.setVisibility(View.GONE);
        }else{
            linearpart6.setVisibility(View.VISIBLE);
        }
        entityZhengGai = Demo_DbUtil.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_ZhengGaiFuCha().putlogic2value("关联的执法编号id","=",bianhaoid)));

        zhenggaiqiye.setText(":"+entityZhengGai.getValue("被检查企业名称"));
        fuchayijian.setText(entityZhengGai.getValue("复查意见"));
        if(entityZhengGai.getId().equals("-1")){
            zhenggaifuchal.setEnabled(false);
            card6.setVisibility(View.GONE);
            card6.setTag("no");
            zhenggaifuchaaddt.setVisibility(View.VISIBLE);
        }else{
            zhenggaifuchal.setEnabled(true);
            card6.setVisibility(View.VISIBLE);
            zhenggaifuchaaddt.setVisibility(View.GONE);
        }
        shanchu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Demo_DBManager.build().delete(entityZhengGai);
                buildView();
            }
        });
    }

    public void initChuFa(){
        if(entityJianCha.getId().equals("-1")&&entityZhengGai.getId().equals("-1")){
            linearpart7.setVisibility(View.GONE);
        }else{
            linearpart7.setVisibility(View.VISIBLE);
        }
        entityChuFa = Demo_DbUtil.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_ChuFa().putlogic2value("关联的执法编号id","=",bianhaoid)));

        chufaqiye.setText(":"+entityChuFa.getValue("被检查企业名称"));
        if(entityChuFa.getId().equals("-1")){
            card7.setVisibility(View.GONE);
            card7.setTag("no");
            chufaaddt.setVisibility(View.VISIBLE);
            chufal.setEnabled(false);
        }else{
            chufal.setEnabled(true);
            card7.setVisibility(View.VISIBLE);
            chufaaddt.setVisibility(View.GONE);
        }
        shanchu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Demo_DBManager.build().delete(entityChuFa);
                buildView();
            }
        });
        initChuFaList1();
    }



    public void initData(){
        bianhaoid=getIntent().getStringExtra("bianhaoid");
        jihuaid=getIntent().getStringExtra("jihuaid");
        Demo_DBManager.build().search(new Entity_ZhiFaBianHao());
    }
    public void initLayout() {
        scrollviewt = (ScrollView) findViewById(R.id.scrollviewt);
        jihual= (TextView) findViewById(R.id.jihual);
        fanganl= (TextView) findViewById(R.id.fanganl);
        xianchangl= (TextView) findViewById(R.id.xianchangl);
        chulicuoshil = (TextView) findViewById(R.id.chulicuoshil);
        zelingl= (TextView) findViewById(R.id.zelingl);
        zhenggaifuchal= (TextView) findViewById(R.id.zhenggaifuchal);
        chufal= (TextView) findViewById(R.id.chufal);


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


        linearpart1 = (LinearLayout) findViewById(R.id.linearpart1);
        linearpart2 = (LinearLayout) findViewById(R.id.linearpart2);
        linearpart3 = (LinearLayout) findViewById(R.id.linearpart3);
        linearpart4 = (LinearLayout) findViewById(R.id.linearpart4);
        linearpart5 = (LinearLayout) findViewById(R.id.linearpart5);
        linearpart6 = (LinearLayout) findViewById(R.id.linearpart6);
        linearpart7 = (LinearLayout) findViewById(R.id.linearpart7);


        chakan1 = (TextView) findViewById(R.id.chakan1);
        chakan2 = (TextView) findViewById(R.id.chakan2);
        chakan3 = (TextView) findViewById(R.id.chakan3);
        chakan4 = (TextView) findViewById(R.id.chakan4);
        chakan5 = (TextView) findViewById(R.id.chakan5);
        chakan6 = (TextView) findViewById(R.id.chakan6);
        chakan7 = (TextView) findViewById(R.id.chakan7);


        shanchu1= (TextView) findViewById(R.id.shanchu1);
        shanchu2= (TextView) findViewById(R.id.shanchu2);
        shanchu3= (TextView) findViewById(R.id.shanchu3);
        shanchu4= (TextView) findViewById(R.id.shanchu4);
        shanchu5= (TextView) findViewById(R.id.shanchu5);
        shanchu6= (TextView) findViewById(R.id.shanchu6);
        shanchu7= (TextView) findViewById(R.id.shanchu7);

        card1= (CardView) findViewById(R.id.card1);
        card2= (CardView) findViewById(R.id.card2);
        card3= (CardView) findViewById(R.id.card3);
        card4= (CardView) findViewById(R.id.card4);
        card5= (CardView) findViewById(R.id.card5);
        card6= (CardView) findViewById(R.id.card6);
        card7= (CardView) findViewById(R.id.card7);

        jihuamingcheng = (TextView) findViewById(R.id.jihuamingcheng);
        jihualeixing = (TextView) findViewById(R.id.jihualeixing);
        jihuafuzeren = (TextView) findViewById(R.id.jihuafuzeren);
        jihuachengyuan = (TextView) findViewById(R.id.jihuachengyuan);

        fanganbeijianchaqiye = (TextView) findViewById(R.id.fanganbeijianchaqiye);
        fanganzhifarenyuan = (TextView) findViewById(R.id.fanganzhifarenyuan);
        fanganjianchafangshi = (TextView) findViewById(R.id.fanganjianchafangshi);
        fanganshenpiren = (TextView) findViewById(R.id.fanganshenpiren);
        fanganshenheren = (TextView) findViewById(R.id.fanganshenheren);

        xianchangjianchaqiye = (TextView) findViewById(R.id.xianchangjianchaqiye);
        xianchangjianchachangsuo = (TextView) findViewById(R.id.xianchangjianchachangsuo);
        xianchangjianchashijian = (TextView) findViewById(R.id.xianchangjianchashijian);

        xianchangchulicuoshiqiye = (TextView) findViewById(R.id.xianchangchulisuochiqiye);
        zelingqiye = (TextView) findViewById(R.id.zelingqiye);
        zhenggaiqiye = (TextView) findViewById(R.id.zhenggaiqiye);
        fuchayijian = (TextView) findViewById(R.id.fuchayijian);
        chufaqiye = (TextView) findViewById(R.id.chufaqiye);

        jianchajilurecycleview= (RecyclerView) findViewById(R.id.jianchajilurecycleview);
        chulirecycleview = (RecyclerView) findViewById(R.id.chulirecycleview);
        zelingrecycleview = (RecyclerView) findViewById(R.id.zelingrecycleview);
        chufarecycleview = (RecyclerView) findViewById(R.id.chufarecycleview);
        chufarecycleview2 = (RecyclerView) findViewById(R.id.chufarecycleview2);

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
                startActivityForResult(new Intent(getContext(),ActivityJiHua.class)
                        .putExtra("bianhaoid",bianhaoid)
                        .putExtra("beijianchaqiyetext",beijianchaqiyetext),111);
            }
        });

        fanganaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityFangAn.class)
                        .putExtra("bianhaoid",bianhaoid).putExtra("beijianchaqiyetext",beijianchaqiyetext),111);
            }
        });

        xianchangaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityXianChangJianCha.class)
                        .putExtra("bianhaoid",bianhaoid).putExtra("beijianchaqiyetext",beijianchaqiyetext),111);
            }
        });

        chulicuoshiaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityChuLiCuoShi.class)
                        .putExtra("bianhaoid",bianhaoid).putExtra("beijianchaqiyetext",beijianchaqiyetext),111);
            }
        });

        zelingaddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityZeLing.class)
                        .putExtra("bianhaoid",bianhaoid).putExtra("beijianchaqiyetext",beijianchaqiyetext),111);
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

        chakan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityJiHua.class)
                        .putExtra("beijianchaqiyetext",beijianchaqiyetext).putExtra("beanid",entityJihua.getId()).putExtra("see",true),111);
            }
        });
        chakan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityFangAn.class)
                        .putExtra("beijianchaqiyetext",beijianchaqiyetext).putExtra("beanid",entityFangAn.getId()).putExtra("see",true),111);
            }
        });
        chakan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityXianChangJianCha.class)
                        .putExtra("beijianchaqiyetext",beijianchaqiyetext).putExtra("beanid",entityJianCha.getId()).putExtra("see",true),111);
            }
        });
        chakan4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityChuLiCuoShi.class)
                        .putExtra("beijianchaqiyetext",beijianchaqiyetext).putExtra("beanid",entityChuLi.getId()).putExtra("see",true),111);
            }
        });
        chakan5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityZeLing.class)
                        .putExtra("beijianchaqiyetext",beijianchaqiyetext).putExtra("beanid",entityZeLing.getId()).putExtra("see",true),111);
            }
        });
        chakan6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityZhengGai.class)
                        .putExtra("beijianchaqiyetext",beijianchaqiyetext).putExtra("bianhaoid",bianhaoid).putExtra("see",true),111);
            }
        });
        chakan7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityChuFa.class)
                        .putExtra("beijianchaqiyetext",beijianchaqiyetext).putExtra("bianhaoid",bianhaoid).putExtra("see",true),111);
            }
        });
        findViewById(R.id.passcheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityCheckChose.class).putExtra("bianhaoid",bianhaoid),1000);
            }
        });
    }
    Dialog zhenggaimenudialog;
    public void startZhengGaiMenuDialog() {
        final List<String> strings = new ArrayList<>();

        if(!entityChuLi.getId().equals("-1")){
            strings.add("复查来源:现场处理措施");
        }
        if(!entityZeLing.getId().equals("-1")){
            strings.add("复查来源:责令限期整改");
        }

        StyledDialog.init(getContext());
        zhenggaimenudialog=
                StyledDialog.buildBottomItemDialog( strings, "cancle",  new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        if(text.toString().equals("复查来源:现场处理措施")){

                            startActivityForResult(new Intent(getContext(),ActivityZhengGai.class).putExtra("beijianchaqiyetext",beijianchaqiyetext).putExtra("bianhaoid",bianhaoid).putExtra("chulijueding", "现场处理措施"),111);
                        }
                        if(text.toString().equals("复查来源:责令限期整改")){

                            startActivityForResult(new Intent(getContext(),ActivityZhengGai.class).putExtra("beijianchaqiyetext",beijianchaqiyetext).putExtra("bianhaoid",bianhaoid).putExtra("chulijueding", "责令限期整改"),111);
                        }

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
        chufachulijueding = "";
        if(!entityJianCha.getId().equals("-1")){
            strings.add("处罚来源:现场检查");
        }
        if(!entityZhengGai.getId().equals("-1")&&entityZhengGai.getValue("复查结果").equals("不通过")){
            strings.add("处罚来源:整改复查");
            chufachulijueding=entityZhengGai.getValue("关联处罚决定书");
        }


        StyledDialog.init(getContext());
        chufamenudialog=
                StyledDialog.buildBottomItemDialog( strings, "cancle",  new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        if(text.toString().equals("处罚来源:现场检查")){

                            startActivityForResult(new Intent(getContext(),ActivityChuFa.class).putExtra("beijianchaqiyetext",beijianchaqiyetext).putExtra("bianhaoid",bianhaoid).putExtra("chulijueding", "并处"),111);
                        }
                        if(text.toString().equals("处罚来源:整改复查")){
                            startActivityForResult(new Intent(getContext(),ActivityChuFa.class).putExtra("beijianchaqiyetext",beijianchaqiyetext).putExtra("bianhaoid",bianhaoid).putExtra("chulijueding", chufachulijueding),111);
                        }

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
        buildView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                scrollviewt.fullScroll(View.FOCUS_DOWN);
            }
        },300);
    }

}
