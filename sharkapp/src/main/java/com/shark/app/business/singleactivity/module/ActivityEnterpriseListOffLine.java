package com.shark.app.business.singleactivity.module;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hss01248.dialog.StyledDialog;
import com.shark.app.R;
import com.shark.app.business.entity.Entity_Company;
import com.shark.app.business.singleactivity.AbstractActivitySearchList;
import com.shark.app.business.singleactivity.ActivityEnterpriseMesh;
import com.shark.app.business.urlentity.EEnterprise;
import com.wisdomregulation.data.entitybase.DateBase_Entity;
import com.wisdomregulation.help.Demo_DBManager;
import com.wisdomregulation.help.Demo_DbUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25. 离线型
 */

public class ActivityEnterpriseListOffLine extends AbstractActivitySearchList {
    List<DateBase_Entity> showlist=new ArrayList<>();
    int page=1;
    int rows=10;
    int total=0;
    int totalpage=0;
    DateBase_Entity company;
    int intenttype=0;

    public String activityrole;//列表角色 用来标记当前列表 normalenterpriselist : printerenterpriselist

    @Override
    public void onRefresh() {
        getEnterpriseList(new EEnterprise().setPage(page+"").setRows(rows+""));
    }

    @Override
    public void onCreateImp(@Nullable Bundle savedInstanceState) {
        intenttype=getIntent().getIntExtra("intenttype",0);
        company=new Entity_Company();
        company.clear();
//        StyledDialog.buildLoading().show();
        getEnterpriseList(new EEnterprise().setPage(page+"").setRows(rows+""));
    }
    public void getEnterpriseList(EEnterprise eEnterprise){

        StyledDialog.buildLoading().show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List org=Demo_DBManager.build().setNeedcount(true).setLimit(Demo_DbUtil.getLimit(page,rows)).search(company);
                final String totalcount=org.get(0).toString();
                totalpage=Demo_DbUtil.getAllPage(totalcount,rows);
                final List<DateBase_Entity> tmplist= Demo_DbUtil.getSearchResult(org);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int presize=showlist.size();
                        showlist.clear();
                        adapter.notifyItemRangeRemoved(0, presize);
                        if(tmplist!=null&&tmplist.size()>0){
                            showlist.addAll(tmplist);
                        }
                        adapter.notifyItemRangeInserted(0, showlist.size());
                        mRefreshLayout.refreshComplete();
                        patetext.setText(page+"/"+totalpage);
                        totaltext.setText("总计:"+totalcount);
                        StyledDialog.dismissLoading();
                    }
                });
            }
        }).start();
    }
    @Override
    public void clickMultiSearch() {

    }

    @Override
    public MatrixCursor getSuggestionCursor() {
        return null;
    }

    @Override
    public RecyclerView.Adapter getRecycleAdapter() {
        return new EnterPriseAdapter(this);
    }

    @Override
    public void toSearchResult(String search) {
        if("".equals(search)){
            company.clear();
        }else{
            company.clear();
            company.putlogic2value("企业名称","like",search);

        }
        getEnterpriseList(new EEnterprise().setPage(page+"").setRows(rows+""));

    }

    @Override
    public String getSearchHint() {
        return "请输入企业名称";
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public void toNext() {
        if(page<totalpage){
            page++;
//            StyledDialog.buildLoading("载入中").show();
            getEnterpriseList(new EEnterprise().setPage(page+"").setRows(rows+""));
        }else{
            Toast.makeText(this,"已经是最后一页了",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void toPrevious() {
        if(page>1){
            page--;
//            StyledDialog.buildLoading().show();
            getEnterpriseList(new EEnterprise().setPage(page+"").setRows(rows+""));
        }else{
            Toast.makeText(this,"已经是第一页了",Toast.LENGTH_SHORT).show();
        }
    }

    class EnterPriseAdapter extends RecyclerView.Adapter<EnterPriseAdapter.SingelViewHolder>{
        public Activity mactivity;
        public Fragment mfragment;
        public android.support.v4.app.Fragment msupportfragment;

        public EnterPriseAdapter(Fragment mfragment) {
            this.mfragment = mfragment;
        }

        public EnterPriseAdapter(android.support.v4.app.Fragment msupportfragment) {
            this.msupportfragment = msupportfragment;
        }

        public EnterPriseAdapter(Activity mactivity) {
            this.mactivity = mactivity;
        }

        @Override
        public SingelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView=LayoutInflater.from(getBaseContext()).inflate(R.layout.item_enterpriselist,parent,false);
            AutoUtils.autoSize(itemView);
            return new SingelViewHolder(itemView);
        }



        @Override
        public void onBindViewHolder(SingelViewHolder holder, int position) {
            DateBase_Entity enterprise=showlist.get(position);
            holder.qymc.setText(enterprise.getValue("企业名称"));
            holder.fddbr.setText("法定代表人:"+(enterprise.getValue("法定代表人")+"").replace("null",""));
            holder.lxdh.setText("联系电话:"+(enterprise.getValue("联系电话")+"").replace("null",""));
            holder.qyid=enterprise.getId();
            holder.qymcs=enterprise.getValue("企业名称");
            holder.fddbrs=enterprise.getValue("法定代表人");
            holder.dzs=enterprise.getValue("生产经营地址");
            holder.dhs=enterprise.getValue("联系电话");
        }

        @Override
        public int getItemCount() {
            return showlist.size();
        }

        public class SingelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
            public TextView qymc;
            public TextView fddbr;
            public TextView lxdh;
            public String qyid;
            public String qymcs;
            public String fddbrs;
            public String dzs;
            public String dhs;
            public SingelViewHolder(View itemView) {
                super(itemView);
                qymc= (TextView) itemView.findViewById(R.id.qymc);
                fddbr= (TextView) itemView.findViewById(R.id.fddbr);
                lxdh= (TextView) itemView.findViewById(R.id.lxdh);
                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                if(intenttype==0){

                    startActivity(new Intent(getBaseContext(),ActivityEnterpriseMesh.class).putExtra("qyid",qyid));
                }else{
                    Intent intent=new Intent()
                            .putExtra("qyid",qyid)
                            .putExtra("qymc",qymcs)
                            .putExtra("dz",dzs)
                            .putExtra("fddbr",fddbrs)
                            .putExtra("dh",dhs);
                    setResult(1000,intent);
                    finish();
                }
//                //System.out.println("点击项目"+getAdapterPosition());

            }

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        }
    }
}
