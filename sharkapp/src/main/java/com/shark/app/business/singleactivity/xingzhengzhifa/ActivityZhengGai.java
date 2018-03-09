package com.shark.app.business.singleactivity.xingzhengzhifa;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.shark.app.R;
import com.shark.app.business.entity.Entity_ZhengGaiFuCha;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.help.Demo_DBManager;

/**
 * Created by Administrator on 2017/5/25. 临时模板复制就用
 */

public class ActivityZhengGai extends FrameActivity {


    private EditText beijianchaqiye;
    private EditText fuchayijian;
    private EditText chufajuedingshu;
    private EditText jiancharen;
    private String chulijueding;
    private RadioGroup yinhuangroup;
    private String fuchajieguo;

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
        setContentView(R.layout.activity_zhenggai);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        initData();
        initLayout();

    }
    private String beijianchaqiyetext;
    private String bianhaoid;
    private Base_Entity beanentity;
    public void initData(){
        if(getIntent().getBooleanExtra("see",false)){
            findViewById(R.id.showfinish).setVisibility(View.GONE);
        }
        beijianchaqiyetext = getIntent().getStringExtra("beijianchaqiyetext");
        bianhaoid = getIntent().getStringExtra("bianhaoid");
        chulijueding = getIntent().getStringExtra("chulijueding");
        if(bianhaoid.equals("-1")){
            beanentity=new Base_Entity();
        }else{
            beanentity = Demo_DBManager.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_ZhengGaiFuCha().put("关联的执法编号id",bianhaoid)));
        }
    }
    public void initLayout(){
        beijianchaqiye = (EditText) findViewById(R.id.beijianchaqiye);
        beijianchaqiye.setText(beijianchaqiyetext);
        fuchayijian = (EditText) findViewById(R.id.fuchayijian);
        fuchayijian.setText(beanentity.getValue("复查意见"));
        chufajuedingshu = (EditText) findViewById(R.id.chufajuedingwenshu);
        chufajuedingshu.setText(beanentity.getValue("关联处罚决定书"));
        jiancharen = (EditText) findViewById(R.id.jiancharen);
        jiancharen.setText(beanentity.getValue("检查人"));
        yinhuangroup = (RadioGroup) findViewById(R.id.yinhuangroup);
        yinhuangroup.check(beanentity.getValue("复查结果").equals("通过")?R.id.yinhuanlevel1:R.id.yinhuanlevel2);
        fuchajieguo = "通过";
        yinhuangroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==R.id.yinhuanlevel1){
                    fuchajieguo="通过";
                }else{
                    fuchajieguo="不通过";
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_searchs){
//            //System.out.println("点击了综合查询");
        }
        return super.onOptionsItemSelected(item);
    }
    public void buttonSubmit(View view){
        Demo_DBManager.build().save2update(new Entity_ZhengGaiFuCha()
                .put("关联的执法编号id",bianhaoid)
                .put("复查意见",fuchayijian.getText().toString())
                .put("被检查企业名称",beijianchaqiye.getText().toString())
                .put("关联处罚决定书",chulijueding)
                .put("检查人",jiancharen.getText().toString())
                .put("复查结果",fuchajieguo)

        );
        finish();
    }
}
