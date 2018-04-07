package com.shark.app.business.fragment;

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

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class FragmentHostCheck extends BaseSupportFragment {
    private RecyclerView recycler_view;
    private VRefreshLayout mRefreshLayout;
    List<DateBase_Entity> resultlist;
    BianHaoRecycleAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_zhifabianhaolist,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initLayout();
        initListener();


    }
    public void initData(){
        resultlist= Demo_DbUtil.getSearchResult(Demo_DBManager.build().search(new Entity_ZhiFaBianHao()));

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
                        initData();
                        adapter.notifyDataSetChanged();
                        mRefreshLayout.refreshComplete();
                    }
                }, 2000);
            }
        });
    }
}
