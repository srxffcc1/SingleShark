package com.shark.app.business.singleactivity.xingzhengzhifa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.shark.app.R;
import com.shark.app.business.entity.Entity_JianChaFangAn;
import com.shark.app.business.entity.Entity_XianChangJianCha;
import com.shark.app.business.fragment.JianChaXiangCardFragment;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entitybase.DateBase_Entity;
import com.wisdomregulation.help.Demo_DBManager;
import com.wisdomregulation.help.Demo_DbUtil;

import java.util.ArrayList;

/**
 * Created by King6rf on 2017/9/25.
 */

public class ActivityXianChangJianCha extends FrameActivity {
    private String[] mTitles_3 ;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private SegmentTabLayout mTabLayout_3;
    private String laizifangancheck;
    private String beijianchaqiyetext;


    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public int getMenuid() {
        return R.menu.finish_menu;
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public boolean needActionBar() {
        return false;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xianchangjiancha);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        initData();
        initLayout();
    }
    private String bianhaoid;
    private EditText beijianchaqiye;
    private EditText dizhi;
    private EditText fadingdaibiaoren;
    private EditText lianxidianhua;
    private EditText jianchachangsuo;
    private EditText jianchashijian;
    private String beanid;
    private Base_Entity beanentity;
    public void initData(){
        if(getIntent().getBooleanExtra("see",false)){
            findViewById(R.id.showfinish).setVisibility(View.GONE);
            findViewById(R.id.fromfangan).setVisibility(View.GONE);
        }
        bianhaoid = getIntent().getStringExtra("bianhaoid");
        beijianchaqiyetext = getIntent().getStringExtra("beijianchaqiyetext");
        DateBase_Entity fanganentity= Demo_DbUtil.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_JianChaFangAn().putlogic2value("关联的执法编号id","=",bianhaoid)));
        laizifangancheck = fanganentity.getValue("检查内容");
        beanid = getIntent().getStringExtra("beanid")==null?"-1":getIntent().getStringExtra("beanid");
        if(beanid==null||beanid.equals("-1")){
            beanentity=new Base_Entity();
        }else{
            beanentity = Demo_DbUtil.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_XianChangJianCha().setId(beanid)));
        }
    }
    public void initLayout(){
        mTabLayout_3= (SegmentTabLayout) findViewById(R.id.tl_3);
        beijianchaqiye = (EditText) findViewById(R.id.beijianchaqiye);
        beijianchaqiye.setText(beanentity.getValue("被检查企业名称"));
        beijianchaqiye.setText(beijianchaqiyetext);
        dizhi = (EditText) findViewById(R.id.dizhi);
        dizhi.setText(beanentity.getValue("被检查企业地址"));
        fadingdaibiaoren = (EditText) findViewById(R.id.fadingdaibiaoren);
        fadingdaibiaoren.setText(beanentity.getValue("法定代表人"));
        lianxidianhua = (EditText) findViewById(R.id.lianxidianhua);
        lianxidianhua.setText(beanentity.getValue("联系电话"));
        jianchachangsuo = (EditText) findViewById(R.id.jianchachangsuo);
        jianchachangsuo.setText("检查场所");
        jianchashijian = (EditText) findViewById(R.id.jianchashijian);
        jianchashijian.setText(beanentity.getValue("检查时间"));
        String[] laizifangancheckarray=laizifangancheck.split(",");
        mTitles_3=new String[laizifangancheckarray.length];
        for (int i = 0; i < mTitles_3.length; i++) {
            mTitles_3[i]="检查项 " +(i+1);
            mFragments.add(JianChaXiangCardFragment.getInstance(laizifangancheckarray[i],bianhaoid,getIntent().getBooleanExtra("see",false)));
        }

        final ViewPager vp_3 = (ViewPager) findViewById(R.id.vp_2);
        vp_3.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        String[] data=new String[laizifangancheckarray.length];
        for (int i = 0; i <data.length ; i++) {
            data[i]=(i+1)+"";
        }


        mTabLayout_3.setTabData(data);
        mTabLayout_3.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp_3.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        vp_3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_3.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_3.setCurrentItem(0);
    }
    public void buttonSubmit(View view){

        startActivityForResult(new Intent(getContext(),ActivityCheckChose.class).putExtra("bianhaoid",bianhaoid),1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1001){
            Demo_DBManager.build().save2update(new Entity_XianChangJianCha()
                    .put("关联的执法编号id",bianhaoid)
                    .put("被检查企业名称",beijianchaqiye.getText().toString())
                    .put("被检查企业地址",dizhi.getText().toString())
                    .put("法定代表人",fadingdaibiaoren.getText().toString())
                    .put("联系电话",lianxidianhua.getText().toString())
                    .put("检查场所",jianchachangsuo.getText().toString())
                    .put("检查时间",jianchashijian.getText().toString())
            );
            finish();
        }

    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles_3[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
