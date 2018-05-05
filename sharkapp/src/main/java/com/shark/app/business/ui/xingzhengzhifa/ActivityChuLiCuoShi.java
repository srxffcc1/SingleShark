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

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.businessframehelp.widget.ScrollLinearLayoutManager;
import com.shark.app.R;
import com.shark.app.business.adapter.InsertDecideRecycleAdapter;
import com.shark.app.business.entity.Entity_JianChaXiang;
import com.shark.app.business.entity.Entity_XianChangChuLiCuoShi;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entitybase.DateBase_Entity;
import com.wisdomregulation.help.Demo_DBManager;
import com.wisdomregulation.help.Demo_DbUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25. 临时模板复制就用
 */

public class ActivityChuLiCuoShi extends FrameActivity {


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
        setContentView(R.layout.activity_i_chulicuoshi);
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
        showlist= Demo_DbUtil.getSearchResult(Demo_DBManager.build().query(Demo_DBManager.lowbuild().justgetSqlUNION(new Entity_JianChaXiang()
                .putlogic2value("隐患级别","<>","无隐患")
                .putlogic2value("进行的阶段转化id","=","现场处理措施")
                .putlogic2value("关联的执法编号id","=",bianhaoid),new Entity_JianChaXiang()
                .putlogic2value("隐患级别","<>","无隐患")
                .putlogic2value("进行的阶段转化id","=","并处")
                .putlogic2value("关联的执法编号id","=",bianhaoid)),new Entity_JianChaXiang()));
        RecyclerView recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new ScrollLinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recycler_view.setAdapter(new InsertDecideRecycleAdapter(this,showlist).setCanedit(false));
    }

    private String bianhaoid;
    private EditText beijianchaqiye;
    private EditText dizhi;
    private EditText fadingdaibiaoren;
    private EditText lianxidianhua;
    private EditText jianchachangsuo;
    private EditText jianchashijian;
    private String beijianchaqiyetext;


    private String beanid;
    private Base_Entity beanentity;
    public void initData(){

        bianhaoid = getIntent().getStringExtra("bianhaoid");
        beijianchaqiyetext = getIntent().getStringExtra("beijianchaqiyetext");
        beanid =getIntent().getStringExtra("beanid")==null?"-1":getIntent().getStringExtra("beanid");
        if(beanid==null||beanid.equals("-1")){
            beanentity=new Base_Entity();
        }else{
            beanentity = Demo_DbUtil.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_XianChangChuLiCuoShi().setId(beanid)));
        }
    }
    public void initLayout(){
        beijianchaqiye = (EditText) findViewById(R.id.beijianchaqiye);
        beijianchaqiye.setText(beijianchaqiyetext);
        dizhi = (EditText) findViewById(R.id.dizhi);
        dizhi.setText(beanentity.getValue("被检查企业地址"));
        fadingdaibiaoren = (EditText) findViewById(R.id.fadingdaibiaoren);
        fadingdaibiaoren.setText(beanentity.getValue("法定代表人"));
        lianxidianhua = (EditText) findViewById(R.id.lianxidianhua);
        lianxidianhua.setText(beanentity.getValue("联系电话"));
        jianchachangsuo = (EditText) findViewById(R.id.jianchachangsuo);
        jianchachangsuo.setText(beanentity.getValue("检查场所"));
        jianchashijian = (EditText) findViewById(R.id.jianchashijian);
        jianchashijian.setText(beanentity.getValue("检查时间"));
        if(getIntent().getBooleanExtra("see",false)){
            findViewById(R.id.showfinish).setVisibility(View.GONE);
            beijianchaqiye.setEnabled(false);
            lianxidianhua.setEnabled(false);
            dizhi.setEnabled(false);
            fadingdaibiaoren.setEnabled(false);
            jianchachangsuo.setEnabled(false);
            jianchashijian.setEnabled(false);
        }
    }
    public void buttonSubmit(View view){
        Demo_DBManager.build().save2update(new Entity_XianChangChuLiCuoShi()
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_searchs){
//            //System.out.println("点击了综合查询");
        }
        return super.onOptionsItemSelected(item);
    }

}
