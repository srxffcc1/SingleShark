package com.shark.app.business.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.baoyachi.stepview.VerticalStepView;
import com.shark.app.R;
import com.shark.app.business.resultentity.Check;
import com.shark.app.business.view.xingzhengzhifa.ActivityCheckMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King6rf on 2017/9/25.
 */

public class CheckListExpandAdapter extends BaseExpandableListAdapter {
    public Context mcontext;
    public List<Check> listchek;
    ExpandableListView expandableListView;
    public CheckListExpandAdapter(Context mcontext, ExpandableListView expandableListView,List<Check> listchek) {
        this.mcontext = mcontext;
        this.listchek = listchek;
        this.expandableListView=expandableListView;
    }

    @Override
    public int getGroupCount() {
        return listchek.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listchek.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listchek.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mcontext).inflate(R.layout.checkparent,parent,false);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!expandableListView.isGroupExpanded(groupPosition)){
                    expandableListView.expandGroup(groupPosition,true);
                }else{
                    expandableListView.collapseGroup(groupPosition);
                }
            }
        });
        convertView.findViewById(R.id.passcheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcontext.startActivity(new Intent(mcontext, ActivityCheckMenu.class));
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(mcontext).inflate(R.layout.checkchild,parent,false);
        VerticalStepView mSetpview0= (VerticalStepView) convertView.findViewById(R.id.step_child);
        List<String> list0 = new ArrayList<>();
        //计划----检查方案（领导已审批）----现场检查记录-----现场处理措施、责令整改----整改复查
        list0.add("计划");
        list0.add("检查方案");
        list0.add("现场检查记录");
        list0.add("现场处理措施、责令整改");
        list0.add("整改复查");
        mSetpview0.setStepsViewIndicatorComplectingPosition(0)//设置完成的步数
                .reverseDraw(false)//default is true
                .setStepViewTexts(list0)//总步骤
                .setTextSize(12)
                .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(mcontext, android.R.color.white))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(mcontext, R.color.uncompleted_text_color))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(mcontext, android.R.color.white))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(mcontext, R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(mcontext, R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(mcontext, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(mcontext, R.drawable.attention));//设置StepsViewIndicator AttentionIcon
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}