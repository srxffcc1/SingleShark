package com.shark.app.business.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.businessframehelp.app.BaseSupportFragment;
import com.leelay.freshlayout.verticalre.VRefreshLayout;
import com.shark.app.R;
import com.shark.app.business.adapter.BianHaoRecycleAdapter;
import com.shark.app.business.entity.Entity_ZhiFaBianHao;
import com.wisdomregulation.data.entitybase.DateBase_Entity;
import com.wisdomregulation.help.Demo_DBManager;
import com.wisdomregulation.help.Demo_DbUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class FragmentHostCheck extends BaseSupportFragment {
    private RecyclerView recycler_view;
    private VRefreshLayout mRefreshLayout;
    List<DateBase_Entity> resultlist=new ArrayList<>();
    BianHaoRecycleAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_zhifabianhaolist,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout();
        initView();
        initListener();


    }
    public void initView(){
        resultlist.clear();
        resultlist.addAll(Demo_DbUtil.getSearchResult(Demo_DBManager.build().search(new Entity_ZhiFaBianHao())));
//        resultlist.add(new Entity_ZhiFaBianHao());
        adapter.notifyDataSetChanged();

    }
    public void initLayout(){
        adapter=new BianHaoRecycleAdapter(this).setList(resultlist);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL,false));
        recycler_view.setAdapter(adapter);
        mRefreshLayout = (VRefreshLayout) findViewById(R.id.vrefresh);
    }
    public void initListener(){
        mRefreshLayout.addOnRefreshListener(new VRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initView();
                        adapter.notifyDataSetChanged();
                        mRefreshLayout.refreshComplete();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==113){
            if(data!=null){
                String jhid=data.getStringExtra("jhid");
                String jhmc=data.getStringExtra("jhmc");
                String jhfzr=data.getStringExtra("jhfzr");
                String jhcy=data.getStringExtra("jhcy");
                String jhwcsj=data.getStringExtra("jhwcsj");
                DateBase_Entity zhifabianhao=new Entity_ZhiFaBianHao();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                Date date=new Date();
                zhifabianhao.put("执法编号",Demo_DbUtil.getZZHead(7).trim()+sdf.format(date));
                zhifabianhao.put("所属计划id",jhid);
                zhifabianhao.put("所属计划名称",jhmc);
                Demo_DBManager.build().save2update(zhifabianhao);
                initView();
            }
        }else{
            initView();
        }
    }
}
