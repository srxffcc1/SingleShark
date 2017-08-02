package com.businessframehelp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by King6rf on 2017/8/2.
 * 联网性质的自动补全
 */

abstract public class AutoCompleteAdapter<T> extends AbstractAutoCompleteAdapter<T> {


    public AutoCompleteAdapter(Context mcontext) {
        super(mcontext, null);
    }
    abstract public ArrayList<T> parseResponse(String response);
    abstract public View getView(int position, View convertView, ViewGroup parent, T entity);
    abstract public String getResponseNoLine();
    abstract public String getRespinseOnline(String query);
//    @Override//解析返回值
//    public ArrayList<? extends Object> parseResponse(String response) {
//        Log.d("MainActivity", "Response: "+response);
//        ArrayList<WikiItem> models = new ArrayList<WikiItem>();
//        try {
//            JSONArray jsonArray = new JSONArray(response);
//            if(jsonArray!=null){
//                for(int i=0;i<jsonArray.length();i++){
//                    models.add(new WikiItem(jsonArray.getJSONObject(i).optString("yhmc")));
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return models;
//    }
}
