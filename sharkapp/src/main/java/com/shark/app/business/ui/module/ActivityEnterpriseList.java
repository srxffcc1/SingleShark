package com.shark.app.business.ui.module;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shark.app.R;
import com.shark.app.business.ui.AbstractActivitySearchList;
import com.shark.app.business.resultentity.Enterprise;
import com.shark.app.business.ui.ActivityEnterpriseMesh;
import com.shark.app.business.statich.UrlHome;
import com.shark.app.business.urlentity.EEnterprise;
import com.shark.app.business.utils.DefaultHttpCallBack;
import com.zhy.autolayout.utils.AutoUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ActivityEnterpriseList extends AbstractActivitySearchList {
    List<Enterprise> showlist=new ArrayList<>();
    int page=1;
    int rows=10;
    int total=0;
    int totalpage=0;

    public String activityrole;//列表角色 用来标记当前列表 normalenterpriselist : printerenterpriselist

    @Override
    public void onRefresh() {
        getEnterpriseList(new EEnterprise().setPage(page+"").setRows(rows+""));
    }

    @Override
    public void onCreateImp(@Nullable Bundle savedInstanceState) {

//        StyledDialog.buildLoading().show();
        getEnterpriseList(new EEnterprise().setPage(page+"").setRows(rows+""));
    }
    public void getEnterpriseList(EEnterprise eEnterprise){
        httpPost(UrlHome.getUrl(this, UrlHome.enterpriselist), UrlHome.entity2MapHashClassNoPrefix(eEnterprise), new DefaultHttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                try {
                    JSONObject jsonObject=new JSONObject(t);
                    total=jsonObject.getInt("total");
                    totalpage = (total % rows == 0) ? total / rows : total
                            / rows + 1;
                    String array=jsonObject.get("rows").toString();
                    Type type = new TypeToken<ArrayList<Enterprise>>() {}.getType();
                    Gson gson=new Gson();
                    List<Enterprise> tmplist=gson.fromJson(array,type);

                    if(tmplist!=null&&tmplist.size()>0){
                        showlist.clear();
                        adapter.notifyItemRangeRemoved(0, tmplist.size());
                    }
                    showlist.addAll(tmplist);
                    patetext.setText(page+"/"+totalpage);
                    totaltext.setText("总计:"+total);
                    adapter.notifyItemRangeChanged(0, showlist.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                StyledDialog.dismissLoading();
                mRefreshLayout.refreshComplete();

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }


        });
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
            Enterprise enterprise=showlist.get(position);
            holder.qymc.setText(enterprise.getQymc());
            holder.fddbr.setText("法定代表人:"+(enterprise.getFddbr()+"").replace("null",""));
            holder.lxdh.setText("联系电话:"+(enterprise.getLxdh()+"").replace("null",""));
            holder.qyid=showlist.get(position).getId();
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
            public SingelViewHolder(View itemView) {
                super(itemView);
                qymc= (TextView) itemView.findViewById(R.id.qymc);
                fddbr= (TextView) itemView.findViewById(R.id.fddbr);
                lxdh= (TextView) itemView.findViewById(R.id.lxdh);
                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),ActivityEnterpriseMesh.class).putExtra("qyid",qyid));
//                //System.out.println("点击项目"+getAdapterPosition());

            }

            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        }
    }
}
