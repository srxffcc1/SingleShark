package com.shark.app.business.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shark.app.R;
import com.shark.app.business.view.xingzhengzhifa.ActivityCheckMenu;
import com.wisdomregulation.data.entitybase.DateBase_Entity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by King6rf on 2017/8/10.
 * 执法编号的list
 */

public class BianHaoRecycleAdapter extends RecyclerView.Adapter<BianHaoRecycleAdapter.SingelViewHolder>{
    public Activity mactivity;
    public Fragment mfragment;
    public android.support.v4.app.Fragment msupportfragment;
    Context mcontext;
    List<DateBase_Entity> resultlist;
    public BianHaoRecycleAdapter(Fragment mfragment) {
        this.mfragment = mfragment;
        mcontext=mfragment.getActivity();
    }

    public BianHaoRecycleAdapter(android.support.v4.app.Fragment msupportfragment) {
        this.msupportfragment = msupportfragment;
        mcontext=msupportfragment.getActivity();
    }

    public BianHaoRecycleAdapter(Activity mactivity) {
        this.mactivity = mactivity;
        mcontext=mactivity;
    }
    public BianHaoRecycleAdapter setList(List<DateBase_Entity> resultlist){
        this.resultlist=resultlist;
        return this;

    }
    @Override
    public SingelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(mcontext).inflate(R.layout.item_zhifabianhaolist,parent,false);
        AutoUtils.autoSize(itemView);
        return new SingelViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(SingelViewHolder holder, int position) {
        final DateBase_Entity entity=resultlist.get(position);
        holder.bianhaotext.setText("执法编号："+entity.getValue("执法编号"));
        holder.suoshujihuatext.setText("所属计划："+entity.getValue("所属计划名称"));
        holder.qiyemingchengtext.setText("企业名称："+entity.getValue("企业名称"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((Activity)mcontext).startActivityForResult(new Intent(mcontext, ActivityCheckMenu.class).putExtra("bianhaoid",entity.getId()).putExtra("jihuaid",entity.getValue("所属计划id")),121);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultlist.size();
    }

    public class SingelViewHolder extends RecyclerView.ViewHolder {
        public TextView bianhaotext;
        public TextView suoshujihuatext;
        public TextView qiyemingchengtext;
        public SingelViewHolder(View itemView) {
            super(itemView);
            bianhaotext= (TextView) itemView.findViewById(R.id.bianhaotext);
            suoshujihuatext= (TextView) itemView.findViewById(R.id.suoshujihuatext);
            qiyemingchengtext= (TextView) itemView.findViewById(R.id.qiyemingchengtext);
        }

    }
}
