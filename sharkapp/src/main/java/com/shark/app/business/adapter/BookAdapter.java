package com.shark.app.business.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wisdomregulation.data.entitybase.Base_Entity;

/**
 * Created by King6rf on 2017/7/18.
 */

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private Base_Entity base_entity;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Button box=null;


    }

    @Override
    public int getItemCount() {
        return base_entity.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class NormalViewHolder extends RecyclerView.ViewHolder{

        public NormalViewHolder(View itemView) {
            super(itemView);
        }
    }
    class CheckViewHolder extends RecyclerView.ViewHolder{

        public CheckViewHolder(View itemView) {
            super(itemView);
        }
    }
}
