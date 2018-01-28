package com.shark.app.business.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.shark.app.R;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entitybase.DateBase_Entity;
import com.wisdomregulation.help.Demo_DBManager;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by King6rf on 2017/8/10.
 */

public class InsertDecideRecycleAdapter extends RecyclerView.Adapter<InsertDecideRecycleAdapter.SingelViewHolder>{
    public Activity mactivity;
    public Fragment mfragment;
    public android.support.v4.app.Fragment msupportfragment;
    Context mcontext;
    public List<DateBase_Entity> showlist;

    public InsertDecideRecycleAdapter(Fragment mfragment) {
        this.mfragment = mfragment;
        mcontext=mfragment.getActivity();
    }

    public InsertDecideRecycleAdapter(android.support.v4.app.Fragment msupportfragment) {
        this.msupportfragment = msupportfragment;
        mcontext=msupportfragment.getActivity();
    }

    public InsertDecideRecycleAdapter(Activity mactivity, List<DateBase_Entity> showlist) {
        this.mactivity = mactivity;
        this.showlist=showlist;
        mcontext=mactivity;
    }

    @Override
    public SingelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(mcontext).inflate(R.layout.item_activity_insert,parent,false);
        AutoUtils.autoSize(itemView);
        return new SingelViewHolder(itemView);
    }

    public InsertDecideRecycleAdapter setCanedit(boolean canedit) {
        this.canedit = canedit;
        return this;
    }

    boolean canedit=true;



    @Override
    public void onBindViewHolder(SingelViewHolder holder, int position) {
        final Base_Entity entity=showlist.get(position);
        if(canedit){
            holder.optionvalue.setVisibility(View.GONE);
        }else{
            holder.optionvalue.setVisibility(View.VISIBLE);
        }
        holder.optionvalue.setText(entity.getValue("隐患级别"));
        holder.optionname.setText(entity.getValue("检查项三级"));
        holder.chulijueding.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Demo_DBManager.build().save2update(entity.put("处理决定",s.toString()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return showlist.size();
    }

    public class SingelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        public TextView optionname;
        public TextView optionvalue;
        public EditText chulijueding;
        public SingelViewHolder(View itemView) {
            super(itemView);
            optionname= (TextView) itemView.findViewById(R.id.optionname);
            optionvalue= (TextView) itemView.findViewById(R.id.optionvalue);
            chulijueding= (EditText) itemView.findViewById(R.id.chulijueding);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
    public static interface FinishTransfer{
        public void back();
    }
}
