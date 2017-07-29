package com.businessframehelp.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.businessframehelp.widget.CodeAndString;

import java.util.List;

/**
 * Created by King6rf on 2017/7/26.
 */

public class CanDataChangeFilterableAdapter extends BaseAdapter implements Filterable{
    Activity context;
    List<CodeAndString> mCodeAndStringlist;
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
    public void changeData(){

    }
    @Override
    public Filter getFilter() {
        return null;
    }
    class CanDataChangeFilter extends Filter{
        boolean needfilter=true;

        public CanDataChangeFilter() {

        }

        public CanDataChangeFilter(boolean needfilter) {
            this.needfilter = needfilter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(needfilter){
                if(true){

                }
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();

            }
        }
    }
}
