package com.shark.app.business.ui;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.google.gson.Gson;
import com.leelay.freshlayout.verticalre.VRefreshLayout;
import com.shark.app.R;
import com.shark.app.business.resultentity.Enterprise;
import com.shark.app.business.statich.UrlHome;

import org.kymjs.kjframe.http.HttpCallBack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ActivityEnterpriseBaseDetail extends FrameActivity {


    private String qyid;

    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public int getMenuid() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qyid = getIntent().getStringExtra("qyid");
        setContentView(R.layout.include_recyclerefresh);
        VRefreshLayout vRefreshLayout= (VRefreshLayout) findViewById(R.id.vrefresh);
        getData();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_searchs){
//            //System.out.println("点击了综合查询");
        }
        return super.onOptionsItemSelected(item);
    }
    public void getData(){
        Map<String,Object> map=new HashMap<>();
        map.put("id",qyid);
        httpGet(UrlHome.getUrl(this, "enterprise/enterpriseAction!getById"), map, new HttpCallBack() {
            @Override
            public void onCookieTimeOut() {

            }

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                Enterprise bean=new Gson().fromJson(t,Enterprise.class);
                System.out.println("over");
            }
        });
    }

}
