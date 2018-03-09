package com.shark.app.business.singleactivity.xingzhengzhifa;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.shark.app.R;
import com.shark.app.business.adapter.ChoseCheckRecycleAdapter;
import com.shark.app.business.entity.Entity_JianChaXiang;
import com.wisdomregulation.data.entitybase.DateBase_Entity;
import com.wisdomregulation.help.Demo_DBManager;

import java.util.List;

/**
 * Created by King6rf on 2018/1/25.
 */

public class ActivityCheckChose extends FrameActivity {
    public List<DateBase_Entity> showlist;
    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public int getMenuid() {
        return 0;
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
        setContentView(R.layout.activity_chosecheck);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        String bianhaoid=getIntent().getStringExtra("bianhaoid");
        String fanganid=getIntent().getStringExtra("fanganid");
        showlist= Demo_DBManager.getSearchResult(Demo_DBManager.build().search(new Entity_JianChaXiang()
                .putlogic2value("隐患级别","<>","无隐患")
                .putlogic2value("关联的执法编号id","=",bianhaoid)));
        if(showlist.size()<1){
            setResult(1001);
            finish();
        }
        RecyclerView recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recycler_view.setAdapter(new ChoseCheckRecycleAdapter(this,showlist));
    }

    @Override
    public void buttonCancel(View view) {
        finish();
    }

    @Override
    public void buttonSubmit(View view) {
        setResult(1001);
        finish();
    }
}
