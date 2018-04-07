package com.shark.app.business.singleactivity.xingzhengzhifa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.shark.app.R;
import com.shark.app.business.entity.Entity_JianChaFangAn;
import com.shark.app.business.singleactivity.module.ActivityEnterpriseListOffLine;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.help.Demo_DBManager;
import com.wisdomregulation.help.Demo_DbUtil;

/**
 * Created by Administrator on 2017/5/25. 临时模板复制就用
 */

public class ActivityFangAn extends FrameActivity {


    private EditText beijianchaqiye;
    private EditText dizhi;
    private EditText fadingdaibiaoren;
    private EditText lianxidianhua;
    private EditText jianchachangsuo;
    private EditText jianchashijian;
    private String bianhaoid;
    TextView isselectchecktext;
    private TextView selectcompany;

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
        setContentView(R.layout.activity_fangan);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        initData();
        initLayout();

    }
    public void initLayout(){

        beijianchaqiye = (EditText) findViewById(R.id.beijianchaqiye);
        beijianchaqiye.setText(beanentity.getValue("被检查企业名称"));
        dizhi = (EditText) findViewById(R.id.dizhi);
        dizhi.setText(beanentity.getValue("被检查企业地址"));
        fadingdaibiaoren = (EditText) findViewById(R.id.fadingdaibiaoren);
        fadingdaibiaoren.setText(beanentity.getValue("法定代表人"));
        lianxidianhua= (EditText) findViewById(R.id.lianxidianhua);
        lianxidianhua.setText(beanentity.getValue("联系电话"));
        jianchachangsuo = (EditText) findViewById(R.id.jianchachangsuo);
        jianchachangsuo.setText(beanentity.getValue("检查场所"));
        jianchashijian = (EditText) findViewById(R.id.jianchashijian);
        jianchashijian.setText(beanentity.getValue("检查时间"));
        isselectchecktext= (TextView) findViewById(R.id.isselectchecktext);
        isselectchecktext.setText(beanentity.getValue("检查内容").replace(",","\n"));
        findViewById(R.id.selectcheckoption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),ActivityCheckOptionSelect.class),1000);
            }
        });
        if(getIntent().getBooleanExtra("see",false)){
            findViewById(R.id.showfinish).setVisibility(View.GONE);
            findViewById(R.id.selectcheckoption).setVisibility(View.GONE);
            findViewById(R.id.selectcompany).setVisibility(View.GONE);
            beijianchaqiye.setEnabled(false);
            lianxidianhua.setEnabled(false);
            dizhi.setEnabled(false);
            fadingdaibiaoren.setEnabled(false);
            jianchachangsuo.setEnabled(false);
            jianchashijian.setEnabled(false);
        }
    }
    private String beanid;
    private Base_Entity beanentity;
    public void initData(){
        if(getIntent().getBooleanExtra("see",false)){
            findViewById(R.id.showfinish).setVisibility(View.GONE);
        }
        bianhaoid = getIntent().getStringExtra("bianhaoid");
        beanid = getIntent().getStringExtra("beanid")==null?"-1":getIntent().getStringExtra("beanid");
        if(beanid==null||beanid.equals("-1")){
            beanentity=new Base_Entity();
        }else{
            beanentity = Demo_DbUtil.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_JianChaFangAn().setId(beanid)));
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_searchs){
//            //System.out.println("点击了综合查询");
        }
        return super.onOptionsItemSelected(item);
    }
    public void buttonSubmit(View view){
        if(TextUtils.isEmpty(isselectchecktext.getText().toString())||TextUtils.isEmpty(beijianchaqiye.getText().toString())){
            Toast.makeText(this,"企业和检查项不能为空",Toast.LENGTH_LONG).show();
        }else{
            Demo_DBManager.build().save2update(new Entity_JianChaFangAn()
                    .put("关联的执法编号id",bianhaoid)
                    .put("被检查企业名称",beijianchaqiye.getText().toString())
                    .put("被检查企业地址",dizhi.getText().toString())
                    .put("法定代表人",fadingdaibiaoren.getText().toString())
                    .put("联系电话",lianxidianhua.getText().toString())
                    .put("检查场所",jianchachangsuo.getText().toString())
                    .put("检查时间",jianchashijian.getText().toString())
                    .put("行政执法人员","王建国")
                    .put("检查方式","政府抽查")
                    .put("检查内容",isselectchecktext.getText().toString().replace("\n",","))
            );
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==117){
            if(data!=null){
                String qymc=data.getStringExtra("qymc");
                String dz=data.getStringExtra("dz");
                String fddbr=data.getStringExtra("fddbr");
                String dh=data.getStringExtra("dh");
                beijianchaqiye.setText(qymc);
                dizhi.setText(dz);
                fadingdaibiaoren.setText(fddbr);
                lianxidianhua.setText(dh);
            }
        }else{
            try {
                isselectchecktext.setText(data.getStringExtra("result").replace(",","\n"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void selectcompany(View view){
        startActivityForResult(new Intent(getContext(),ActivityEnterpriseListOffLine.class).putExtra("intenttype",1),117);
    }
}
