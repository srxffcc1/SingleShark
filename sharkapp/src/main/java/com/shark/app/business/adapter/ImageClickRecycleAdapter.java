package com.shark.app.business.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shark.app.R;
import com.shark.app.business.entity.ImageMenuEntity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by King6rf on 2017/8/10.
 */

abstract  public class ImageClickRecycleAdapter extends RecyclerView.Adapter<ImageClickRecycleAdapter.SingelViewHolder>{
    public Activity mactivity;
    public Fragment mfragment;
    private Context mcontext;
    private List<ImageMenuEntity> mlist;
    public android.support.v4.app.Fragment msupportfragment;

    public ImageClickRecycleAdapter(Fragment mfragment,List<ImageMenuEntity> list) {
        this.mfragment = mfragment;
        mcontext=mfragment.getActivity();
        mlist=list;
    }

    public ImageClickRecycleAdapter(android.support.v4.app.Fragment msupportfragment,List<ImageMenuEntity> list) {
        this.msupportfragment = msupportfragment;
        mcontext=msupportfragment.getActivity();
        mlist=list;
    }

    public ImageClickRecycleAdapter(Activity activity,List<ImageMenuEntity> list) {
        this.mactivity = activity;
        mcontext=activity;
        mlist=list;
    }
    protected abstract int getImageWidth();
    @Override
    public SingelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(mcontext).inflate(R.layout.item_mesh,parent,false);
        itemView.setLayoutParams(new LinearLayout.LayoutParams(getImageWidth(), ViewGroup.LayoutParams.MATCH_PARENT));
        AutoUtils.autoSize(itemView);
        return new SingelViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(SingelViewHolder holder, int position) {
        holder.autoClickImageView.setImageResource(mlist.get(position).getImageRid());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class SingelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        ImageView autoClickImageView;
        public SingelViewHolder(View itemView) {
            super(itemView);
            autoClickImageView= (ImageView) itemView.findViewById(R.id.autoClickImageView);
            autoClickImageView.setOnClickListener(this);
            autoClickImageView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
//                System.out.println("点击项目"+getAdapterPosition());
            Class desclass=mlist.get(getAdapterPosition()).getClickpassclass();
            if(desclass!=null){
                if(mactivity!=null){
                    mactivity.startActivity(new Intent(mcontext,desclass));
                }else if(mfragment!=null){
                    mfragment.startActivity(new Intent(mcontext,desclass));
                }else{
                    msupportfragment.startActivity(new Intent(mcontext,desclass));
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}