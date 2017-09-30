package com.shark.app.business.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.businessframehelp.app.BaseSupportFragment;
import com.leelay.freshlayout.verticalre.VRefreshLayout;
import com.shark.app.R;
import com.shark.app.business.adapter.CheckListExpandAdapter;
import com.shark.app.business.resultentity.Check;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class FragmentHostCheck extends BaseSupportFragment {
    private ExpandableListView expand_view;
    private VRefreshLayout mRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.include_expandfresh,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expand_view= (ExpandableListView) view.findViewById(R.id.expand_view);
        List<Check> checkList=new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            checkList.add(new Check());
        }
        expand_view.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;//用来屏蔽这个事件
            }
        });
        expand_view.setGroupIndicator(null);
        expand_view.setAdapter(new CheckListExpandAdapter(getActivity(),expand_view,checkList));
    }
}
