package com.shark.app.business.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
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

public class ChoseCheckRecycleAdapter extends RecyclerView.Adapter<ChoseCheckRecycleAdapter.SingelViewHolder>{
    public Activity mactivity;
    public Fragment mfragment;
    public android.support.v4.app.Fragment msupportfragment;
    Context mcontext;
    public List<DateBase_Entity> showlist;

    public ChoseCheckRecycleAdapter(Fragment mfragment) {
        this.mfragment = mfragment;
        mcontext=mfragment.getActivity();
    }

    public ChoseCheckRecycleAdapter(android.support.v4.app.Fragment msupportfragment) {
        this.msupportfragment = msupportfragment;
        mcontext=msupportfragment.getActivity();
    }

    public ChoseCheckRecycleAdapter(Activity mactivity,List<DateBase_Entity> showlist) {
        this.mactivity = mactivity;
        this.showlist=showlist;
        mcontext=mactivity;
    }

    @Override
    public SingelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(mcontext).inflate(R.layout.item_activity_check_spinner,parent,false);
        AutoUtils.autoSize(itemView);
        return new SingelViewHolder(itemView);
    }

    public ChoseCheckRecycleAdapter setCanedit(boolean canedit) {
        this.canedit = canedit;
        return this;
    }

    boolean canedit=true;



    @Override
    public void onBindViewHolder(SingelViewHolder holder, int position) {
        final Base_Entity entity=showlist.get(position);
        if(canedit){
            holder.optionspinner.setVisibility(View.VISIBLE);
            holder.optionvalue.setVisibility(View.GONE);
        }else{
            holder.optionspinner.setVisibility(View.GONE);
            holder.optionvalue.setVisibility(View.VISIBLE);
        }
        holder.optionvalue.setText(entity.getValue("隐患级别"));
        holder.optionname.setText(entity.getValue("检查项三级"));
        if(entity.getValue("进行的阶段转化id").equals("现场处理措施")){
            holder.optionspinner.setSelection(0);
        }
        if(entity.getValue("进行的阶段转化id").equals("责令限期整改")){
            holder.optionspinner.setSelection(1);

        }
        if(entity.getValue("进行的阶段转化id").equals("并处")){
            holder.optionspinner.setSelection(2);

        }
        holder.optionspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int positionchild, long id) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String jueding="";
                        if(positionchild==0){
                            jueding="现场处理措施";
                        }
                        if(positionchild==1){

                            jueding="责令限期整改";
                        }
                        if(positionchild==2){

                            jueding="并处";
                        }
                        Demo_DBManager.build().save2update(entity.put("进行的阶段转化id",jueding));
//                        FinishTransfer backactivity= (FinishTransfer) mactivity;
//                        if(backactivity!=null){
//                            backactivity.back();
//                        }
//                        FinishTransfer backfragment= (FinishTransfer) mfragment;
//                        if(backfragment!=null){
//                            backfragment.back();
//                        }
                    }
                }).start();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        public Spinner optionspinner;
        public SingelViewHolder(View itemView) {
            super(itemView);
            optionname= (TextView) itemView.findViewById(R.id.optionname);
            optionvalue= (TextView) itemView.findViewById(R.id.optionvalue);
            optionspinner= (Spinner) itemView.findViewById(R.id.optionspinner);

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
