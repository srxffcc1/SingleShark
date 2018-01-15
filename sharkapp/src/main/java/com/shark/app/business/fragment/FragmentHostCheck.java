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

/**
 * Created by Administrator on 2017/5/25.
 */

public class FragmentHostCheck extends BaseSupportFragment {
    private RecyclerView recycler_view;
    private VRefreshLayout mRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_zhifabianhaolist,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL,false));
        recycler_view.setAdapter(new BianHaoRecycleAdapter(this));
        mRefreshLayout = (VRefreshLayout) findViewById(R.id.vrefresh);
        mRefreshLayout.addOnRefreshListener(new VRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.refreshComplete();
                    }
                }, 2000);
            }
        });
    }
}
