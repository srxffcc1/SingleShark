package com.shark.app.business.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shark.app.R;
import com.shark.app.business.singleactivity.ActivityCheckMenu;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by King6rf on 2017/8/10.
 * 执法编号的list
 */

public class BianHaoRecycleAdapter extends RecyclerView.Adapter<BianHaoRecycleAdapter.SingelViewHolder>{
    public Activity mactivity;
    public Fragment mfragment;
    public android.support.v4.app.Fragment msupportfragment;
    Context mcontext;

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

    @Override
    public SingelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(mcontext).inflate(R.layout.item_zhifabianhaolist,parent,false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcontext.startActivity(new Intent(mcontext, ActivityCheckMenu.class));
            }
        });
        AutoUtils.autoSize(itemView);
        return new SingelViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(SingelViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class SingelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        public SingelViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
