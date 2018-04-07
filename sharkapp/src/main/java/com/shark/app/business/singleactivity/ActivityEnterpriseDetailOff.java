package com.shark.app.business.singleactivity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.hss01248.dialog.StyledDialog;
import com.shark.app.R;
import com.shark.app.business.entity.Entity_Company;
import com.wisdomregulation.data.entitybase.DateBase_Entity;
import com.wisdomregulation.help.Demo_DBManager;
import com.wisdomregulation.help.Demo_DbUtil;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ActivityEnterpriseDetailOff extends FrameActivity {

    DateBase_Entity result;
    private String qyid;
    DateBase_Entity search=new Entity_Company();
    private RecyclerView recycler_view;

    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public int getMenuid() {
        return -1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qyid = getIntent().getStringExtra("qyid");
        search.setId(qyid);
        setContentView(R.layout.include_recyclerefresh);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//        getData();
        initView();
    }
    public void initView(){
        StyledDialog.buildLoading().show();
        final EnterPriseDetailAdapter adapter=new EnterPriseDetailAdapter(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                result=Demo_DbUtil.getSearchResultOnlyOne(Demo_DBManager.build().search(search));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recycler_view.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        StyledDialog.dismissLoading();
                    }
                });
            }
        }).start();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_searchs){
//            //System.out.println("点击了综合查询");
        }
        return super.onOptionsItemSelected(item);
    }
    public void getData(){
//        Map<String,Object> map=new HashMap<>();
//        map.put("id",qyid);
//        httpGet(UrlHome.getUrl(this, "enterprise/enterpriseAction!getById"), map, new HttpCallBack() {
//            @Override
//            public void onCookieTimeOut() {
//
//            }
//
//            @Override
//            public void onSuccess(String t) {
//                super.onSuccess(t);
//                Enterprise bean=new Gson().fromJson(t,Enterprise.class);
//                System.out.println("over");
//            }
//        });
    }
    class EnterPriseDetailAdapter extends RecyclerView.Adapter<EnterPriseDetailAdapter.SingelViewHolder>{
        public Activity mactivity;
        public Fragment mfragment;
        public android.support.v4.app.Fragment msupportfragment;

        public EnterPriseDetailAdapter(Fragment mfragment) {
            this.mfragment = mfragment;
        }

        public EnterPriseDetailAdapter(android.support.v4.app.Fragment msupportfragment) {
            this.msupportfragment = msupportfragment;
        }

        public EnterPriseDetailAdapter(Activity mactivity) {
            this.mactivity = mactivity;
        }

        @Override
        public SingelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView= LayoutInflater.from(getBaseContext()).inflate(R.layout.item_enterprisedetail,parent,false);
            AutoUtils.autoSize(itemView);
            return new SingelViewHolder(itemView);
        }



        @Override
        public void onBindViewHolder(SingelViewHolder holder, int position) {
            holder.title.setText(result.getFieldChinese(position)+":");
            holder.content.setText(result.getValue(position));

        }

        @Override
        public int getItemCount() {
            return result.size();
        }

        public class SingelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
            public TextView title;
            public TextView content;

            public SingelViewHolder(View itemView) {
                super(itemView);
                title= (TextView) itemView.findViewById(R.id.title);
                content= (TextView) itemView.findViewById(R.id.content);
                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
//                //System.out.println("点击项目"+getAdapterPosition());

            }

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        }
    }
}
