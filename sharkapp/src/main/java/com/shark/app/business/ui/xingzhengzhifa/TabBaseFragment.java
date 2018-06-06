package com.shark.app.business.ui.xingzhengzhifa;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.shark.app.R;

public abstract class TabBaseFragment extends Fragment {
    public int nowfragmentstatus=-1;
    Bundle bundle=new Bundle();
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
        nowfragmentstatus=1;
//        nowfragmentstatus=0;
        getView().setVisibility(View.VISIBLE);
        ((TextView)(getView().findViewById(R.id.add))).setText("新增");
        setContentVisibility(false);
    }
    public void initNotComplete(){//说明这个东西还不存在
        nowfragmentstatus=-1;
        getView().setVisibility(View.GONE);
        setContentVisibility(false);
    }

    public int getNowfragmentstatus() {
        return nowfragmentstatus;
    }

    public void setNowfragmentstatus(int nowfragmentstatus) {
        this.nowfragmentstatus = nowfragmentstatus;
    }

    public void initComplete(){//说明这个东西已经完整
        nowfragmentstatus=1;
        getView().findViewById(R.id.add).setVisibility(View.GONE);
        getView().findViewById(R.id.card).setVisibility(View.VISIBLE);
        if(getStringValue("pass").equals("1")){
            getView().findViewById(R.id.add).setVisibility(View.VISIBLE);
            ((TextView)(getView().findViewById(R.id.add))).setText("修改");
            getView().findViewById(R.id.passl).setVisibility(View.VISIBLE);
        }else{
            getView().findViewById(R.id.passl).setVisibility(View.GONE);
        }
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
    public TabBaseFragment setArguments(String key,String value){

        bundle.putString(key,value);
        setArguments(bundle);
        return this;
    }
    public TabBaseFragment setArguments(String key,int value){
        bundle.putInt(key,value);
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

    public Context getBaseContext() {
        return super.getContext();
    }
    public  void runOnUiThread(Runnable action) {
        getActivity().runOnUiThread(action);
    }
}
