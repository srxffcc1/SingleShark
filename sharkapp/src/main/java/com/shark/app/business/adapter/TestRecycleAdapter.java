package com.shark.app.business.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shark.app.R;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by King6rf on 2017/8/10.
 */

@Deprecated //用来测试的adapter
public class TestRecycleAdapter extends RecyclerView.Adapter<TestRecycleAdapter.SingelViewHolder>{
    public Activity mactivity;
    public Fragment mfragment;
    public android.support.v4.app.Fragment msupportfragment;
    Context mcontext;

    public TestRecycleAdapter(Fragment mfragment) {
        this.mfragment = mfragment;
        mcontext=mfragment.getActivity();
    }

    public TestRecycleAdapter(android.support.v4.app.Fragment msupportfragment) {
        this.msupportfragment = msupportfragment;
        mcontext=msupportfragment.getActivity();
    }

    public TestRecycleAdapter(Activity mactivity) {
        this.mactivity = mactivity;
        mcontext=mactivity;
    }

    @Override
    public SingelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(mcontext).inflate(R.layout.item_enterpriselist,parent,false);
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
//                System.out.println("点击项目"+getAdapterPosition());

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
