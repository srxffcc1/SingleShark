package com.shark.app.business.ui.xingzhengzhifa;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.shark.app.R;
import com.shark.app.business.entity.Entity_ZhiFaJiHua;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.help.Demo_DBManager;
import com.wisdomregulation.help.Demo_DbUtil;

/**
 * Created by Administrator on 2017/5/25. 临时模板复制就用
 */

public class ActivityJiHua extends FrameActivity {


    private TextView jihuamingcheng;
    private TextView jihualaiyuan;
    private TextView jihualeixing;
    private TextView jihuafuzeren;
    private TextView jihuachengyuan;
    private TextView jihuawanchengshijian;
    private String beanid;
    private Base_Entity beanentity;

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
        setContentView(R.layout.activity_jihua);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        initData();
        initLayout();
    }

    private void initData() {
        if(getIntent().getBooleanExtra("see",false)){
            findViewById(R.id.showfinish).setVisibility(View.GONE);
        }
        beanid = getIntent().getStringExtra("beanid")==null?"-1":getIntent().getStringExtra("beanid");
        if(beanid==null||beanid.equals("-1")){
            beanentity=new Base_Entity();
        }else{
            beanentity = Demo_DbUtil.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_ZhiFaJiHua().setId(beanid)));
        }


    }

    private void initLayout() {
        jihuamingcheng = (TextView) findViewById(R.id.jihuamingcheng);
        jihuamingcheng.setText(beanentity.getValue("计划名称"));
        jihualaiyuan = (TextView) findViewById(R.id.jihualaiyuan);
        jihualaiyuan.setText(beanentity.getValue("计划来源"));
        jihualeixing = (TextView) findViewById(R.id.jihualeixing);
        jihualeixing.setText(beanentity.getValue("计划类型"));
        jihuafuzeren = (TextView) findViewById(R.id.jihuafuzeren);
        jihuafuzeren.setText(beanentity.getValue("计划负责人"));
        jihuachengyuan = (TextView) findViewById(R.id.jihuachengyuan);
        jihuachengyuan.setText(beanentity.getValue("计划成员"));
        jihuawanchengshijian = (TextView) findViewById(R.id.jihuawanchengshijian);
        jihuawanchengshijian.setText(beanentity.getValue("计划完成时间"));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_searchs){
//            //System.out.println("点击了综合查询");
        }
        return super.onOptionsItemSelected(item);
    }

}
