package com.shark.app.business.ui.xingzhengzhifa;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.shark.app.R;

public abstract class TabBaseFragment extends Fragment {
    public void setVisibility(int visibility){
        getView().setVisibility(visibility);
    }
    public void setContentVisibility(boolean bool){
        if(bool){
            getView().findViewById(R.id.add).setVisibility(View.GONE);
            getView().findViewById(R.id.card).setVisibility(View.VISIBLE);
        }else{
            getView().findViewById(R.id.add).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.card).setVisibility(View.GONE);
        }
    }
    public void initAdd(){//说明开始新增
        getView().setVisibility(View.VISIBLE);
        setContentVisibility(false);
    }
    public void initNotComplete(){//说明这个东西还不存在
        getView().setVisibility(View.GONE);
        setContentVisibility(false);
    }
    public void showcard(){
        getView().findViewById(R.id.card).setVisibility(View.VISIBLE);
    }
    public void hidecard(){
        getView().findViewById(R.id.card).setVisibility(View.GONE);

    }
    public abstract void initData();
    public abstract void initListener();
    public abstract void bindDataToView();
    public View findViewById(int rid){
        return getView().findViewById(rid);
    }
    public TabBaseFragment setArguments(String key, String value){
        Bundle bundle=new Bundle();
        bundle.putString(key,value);
        setArguments(bundle);
        return this;
    }
    public String getStringValue(String key){

        Bundle bundle=getArguments();
        if(bundle!=null){
            return bundle.getString(key,"");
        }
        return "";
    }

}
