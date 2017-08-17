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
import android.widget.TextView;

import com.shark.app.R;
import com.shark.app.business.entity.ImageTextMenuEntity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by King6rf on 2017/8/10.
 */

abstract  public class ImageTextClickRecycleAdapter extends RecyclerView.Adapter<ImageTextClickRecycleAdapter.SingelViewHolder>{
    public Activity mactivity;
    public Fragment mfragment;
    private Context mcontext;
    private List<ImageTextMenuEntity> mlist;
    public android.support.v4.app.Fragment msupportfragment;

    public ImageTextClickRecycleAdapter setLongclicksameclick(boolean longclicksameclick) {
        this.longclicksameclick = longclicksameclick;
        return this;
    }

    private boolean longclicksameclick=false;

    public ImageTextClickRecycleAdapter(Fragment mfragment, List<ImageTextMenuEntity> list) {
        this.mfragment = mfragment;
        mcontext=mfragment.getActivity();
        mlist=list;
    }

    public ImageTextClickRecycleAdapter(android.support.v4.app.Fragment msupportfragment, List<ImageTextMenuEntity> list) {
        this.msupportfragment = msupportfragment;
        mcontext=msupportfragment.getActivity();
        mlist=list;
    }

    public ImageTextClickRecycleAdapter(Activity activity, List<ImageTextMenuEntity> list) {
        this.mactivity = activity;
        mcontext=activity;
        mlist=list;
    }
    protected abstract int getImageWidth();
    protected abstract int getImageHeight();
    @Override
    public SingelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(mcontext).inflate(R.layout.item_meshtext,parent,false);
        itemView.setLayoutParams(new LinearLayout.LayoutParams(getImageWidth(), getImageHeight()));
        AutoUtils.autoSize(itemView);
        return new SingelViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(SingelViewHolder holder, int position) {
        holder.autoClickImageView.setImageResource(mlist.get(position).getImageRid());
        holder.textView.setText(mlist.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class SingelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        ImageView autoClickImageView;
        TextView textView;
        public SingelViewHolder(View itemView) {
            super(itemView);
            autoClickImageView= (ImageView) itemView.findViewById(R.id.autoClickImageView);
            autoClickImageView.setOnClickListener(this);
            autoClickImageView.setOnLongClickListener(this);
            textView= (TextView) itemView.findViewById(R.id.meshtext);

        }

        @Override
        public void onClick(View v) {
//                //System.out.println("点击项目"+getAdapterPosition());
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
            if(longclicksameclick){
                onClick(v);
                return true;
            }
            return false;
        }
    }
}