package com.businessframehelp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by King6rf on 2017/8/2.
 * 联网性质的自动补全 建议
 */

abstract public class AbstractAutoCompleteAdapter<T> extends BaseAdapter implements Filterable {
    public Context mcontext;
    public String murl;
    public List<T> showlist = new ArrayList<T>();


    public  AbstractAutoCompleteAdapter(Context mcontext, String murl) {
        this.mcontext = mcontext;
        this.murl = murl;
        this.showlist.clear();
    }
    public Context getContext() {
        return mcontext;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent,showlist.get(position));
    }
    @Override
    public int getCount() {
        return showlist.size();
    }
    @Override
    public Object getItem(int position) {
        return showlist.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence != null) {
                    try {
                        List<T> suggestions = getResponse(charSequence.toString());
                        // Assign the data to the FilterResults
                        filterResults.values = suggestions;
                        filterResults.count = suggestions.size();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                if (results != null && results.count > 0) {
                    showlist.clear();
                    showlist.addAll((Collection<? extends T>) results.values);
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
    abstract public ArrayList<T> parseResponse(String response);
    abstract public View getView(int position, View convertView, ViewGroup parent,T entity);
    abstract public String getResponseNoLine();
    abstract public String getRespinseOnline(String query);
    public ArrayList<T> getResponse(String query) {
        ArrayList<T> displayList = null;
        if (getResponseNoLine() == null){
            String mresponse= getRespinseOnline(query);
            displayList = parseResponse(mresponse);
        } else {
            String mresponse = getResponseNoLine();
            displayList = parseResponse(mresponse);
        }
        return displayList;
    }


}