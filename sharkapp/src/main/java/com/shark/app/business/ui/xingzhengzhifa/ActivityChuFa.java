package com.shark.app.business.ui.xingzhengzhifa;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.businessframehelp.widget.ScrollLinearLayoutManager;
import com.shark.app.R;
import com.shark.app.business.adapter.InsertMurderRecycleAdapter;
import com.shark.app.business.entity.Entity_ChuFa;
import com.shark.app.business.entity.Entity_JianChaXiang;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entitybase.DateBase_Entity;
import com.wisdomregulation.help.Demo_DBManager;
import com.wisdomregulation.help.Demo_DbUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25. 临时模板复制就用
 */

public class ActivityChuFa extends FrameActivity {


    private EditText jiaonafajingshuliang;
    private EditText jiaonafajinzhanghao;
    private String chulijueding;

    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public boolean needActionBar() {
        return false;
    }

    @Override
    public int getMenuid() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_chufa);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        initData();
        initLayout();
        initView();
    }
    public List<DateBase_Entity> showlist;
    private void initView() {
        showlist= Demo_DbUtil.getSearchResult(Demo_DBManager.build().search(new Entity_JianChaXiang()
                .putlogic2value("隐患级别","<>","无隐患")
                .putlogic2value("进行的阶段转化id","=",chulijueding)
                .putlogic2value("关联的执法编号id","=",bianhaoid)));
        if(showlist.size()<1){
            Toast.makeText(getContext(),"暂无违法记录,选择其他来源",Toast.LENGTH_SHORT).show();
            finish();
        }
        RecyclerView recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new ScrollLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recycler_view.setAdapter(new InsertMurderRecycleAdapter(this,showlist).setCanedit(false));
    }
    private String beijianchaqiyetext;
    private String bianhaoid;
    private Base_Entity beanentity;
    public void initData(){

        beijianchaqiyetext = getIntent().getStringExtra("beijianchaqiyetext");

        bianhaoid = getIntent().getStringExtra("bianhaoid");
        chulijueding = getIntent().getStringExtra("chulijueding");
        if(bianhaoid.equals("-1")){
            beanentity=new Base_Entity();
        }else{
            beanentity = Demo_DbUtil.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_ChuFa().put("关联的执法编号id",bianhaoid)));
        }
    }
    private EditText beijianchaqiye;
    private EditText dizhi;
    private EditText fadingdaibiaoren;
    public void initLayout(){
        beijianchaqiye = (EditText) findViewById(R.id.beijianchaqiye);
        beijianchaqiye.setText(beijianchaqiyetext);
        dizhi = (EditText) findViewById(R.id.dizhi);
        dizhi.setText(beanentity.getValue("企业地址"));
        fadingdaibiaoren = (EditText) findViewById(R.id.fadingdaibiaoren);
        fadingdaibiaoren.setText(beanentity.getValue("法定代表人"));
        jiaonafajingshuliang = (EditText) findViewById(R.id.jiaonafajingshuliang);
        jiaonafajingshuliang.setText(beanentity.getValue("缴纳罚金数量"));
        jiaonafajinzhanghao = (EditText) findViewById(R.id.jiaonazhanghao);
        jiaonafajinzhanghao.setText(beanentity.getValue("缴纳罚金账号"));
        if(getIntent().getBooleanExtra("see",false)){
            findViewById(R.id.showfinish).setVisibility(View.GONE);
            beijianchaqiye.setEnabled(false);
                    fadingdaibiaoren.setEnabled(false);
            jiaonafajingshuliang.setEnabled(false);
                    fadingdaibiaoren.setEnabled(false);
            jiaonafajinzhanghao.setEnabled(false);
        }
    }
    public void buttonSubmit(View view){
        Demo_DBManager.build().save2update(new Entity_ChuFa()
                .put("关联的执法编号id",bianhaoid)
                .put("被检查企业名称",beijianchaqiye.getText().toString())
                .put("被检查企业地址",dizhi.getText().toString())
                .put("法定代表人",fadingdaibiaoren.getText().toString())
                .put("缴纳罚金数量",jiaonafajingshuliang.getText().toString())
                .put("缴纳罚金账号",jiaonafajinzhanghao.getText().toString())
        );
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_searchs){
//            //System.out.println("点击了综合查询");
        }
        return super.onOptionsItemSelected(item);
    }

}
