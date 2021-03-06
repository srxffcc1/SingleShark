package com.businessframehelp.adapter;

import android.app.Activity;
import android.widget.BaseAdapter;

import com.businessframehelp.widget.CodeAndString;

import java.util.List;

/**
 * 带全部的spinner
 */
abstract public class AbstratAutoSpinnerAdapter extends BaseAdapter {
	Activity context;
	List<CodeAndString> mCodeAndStringlist;
	
	public AbstratAutoSpinnerAdapter(Activity context,
                                     List<CodeAndString> mCodeAndStringlist) {
		super();
		this.context = context;
		this.mCodeAndStringlist = mCodeAndStringlist;
		this.mCodeAndStringlist.add(0, new CodeAndString("", "全部"));
	}

//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return mCodeAndStringlist.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return mCodeAndStringlist.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		convertView=LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent,false);
//		((TextView)convertView.findViewById(android.R.id.text1)).setText(mCodeAndStringlist.get(position).getName());
//		// TODO Auto-generated method stub
//		return convertView;
//	}
//
//	@Override
//	public View getDropDownView(int position, View convertView, ViewGroup parent) {
//		convertView=LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent,false);
//		((TextView)convertView.findViewById(android.R.id.text1)).setText(mCodeAndStringlist.get(position).getName());
//
//		return convertView;
//	}
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		if(mCodeAndStringlist.size()>0){
			if(mCodeAndStringlist.get(0).toString().equals("")){
				
			}else{
				this.mCodeAndStringlist.add(0, new CodeAndString("", "全部"));
			}
		}else{
			this.mCodeAndStringlist.add(0, new CodeAndString("", "全部"));
		}
		super.notifyDataSetChanged();
		
	}

}
