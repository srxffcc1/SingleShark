package com.shark.app.business.singleactivity.xingzhengzhifa;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.shark.app.R;
import com.shark.app.business.entity.Entity_ZeLingZhengGai;
import com.wisdomregulation.help.Demo_DBManager;

/**
 * Created by Administrator on 2017/5/25. 临时模板复制就用
 */

public class ActivityZeLing extends FrameActivity {


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
        setContentView(R.layout.activity_zeling);
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
    public void initData(){
        bianhaoid = getIntent().getStringExtra("bianhaoid");
    }
    public void initLayout(){
        beijianchaqiye = (EditText) findViewById(R.id.beijianchaqiye);
        dizhi = (EditText) findViewById(R.id.dizhi);
        fadingdaibiaoren = (EditText) findViewById(R.id.fadingdaibiaoren);
        lianxidianhua = (EditText) findViewById(R.id.lianxidianhua);
        jianchachangsuo = (EditText) findViewById(R.id.jianchachangsuo);
        jianchashijian = (EditText) findViewById(R.id.jianchashijian);
    }
    public void buttonSubmit(View view){
        Demo_DBManager.build().save2update(new Entity_ZeLingZhengGai()
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
