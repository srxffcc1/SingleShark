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

import com.shark.app.R;
import com.shark.app.business.singleactivity.AbstractActivitySearchList;
import com.shark.app.business.singleactivity.ActivityLawDetail;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ActivityLaw extends AbstractActivitySearchList {


    @Override
    public void onRefresh() {

    }

    @Override
    public void onCreateImp(@Nullable Bundle savedInstanceState) {

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
        return new LawAdapter(this);
    }

    @Override
    public void toSearchResult(String search) {

    }

    @Override
    public String getSearchHint() {
        return "请输入信息";
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public void toNext() {

    }

    @Override
    public void toPrevious() {

    }

    class LawAdapter extends RecyclerView.Adapter<LawAdapter.SingelViewHolder>{
        public Activity mactivity;
        public Fragment mfragment;
        public android.support.v4.app.Fragment msupportfragment;

        public LawAdapter(Fragment mfragment) {
            this.mfragment = mfragment;
        }

        public LawAdapter(android.support.v4.app.Fragment msupportfragment) {
            this.msupportfragment = msupportfragment;
        }

        public LawAdapter(Activity mactivity) {
            this.mactivity = mactivity;
        }

        @Override
        public SingelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView= LayoutInflater.from(getBaseContext()).inflate(R.layout.item_enterpriselist,parent,false);
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
                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),ActivityLawDetail.class));
//                //System.out.println("点击项目"+getAdapterPosition());

            }

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        }
    }
}
