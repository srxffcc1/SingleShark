package com.businessframehelp.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.businessframehelp.R;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/5/14.
 * 现实能onActivityResult的fragment
 */

public class BaseSupportFragment extends Fragment{
    int mResultCode= Activity.RESULT_OK;
    int mRequestCode;
    Intent mResultData;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
            mRequestCode = bundle.getInt("mRequestCode");

    }
    public <T extends View> T findViewById(@IdRes int id) {
        return (T) getView().findViewById(id);
    }
    public final void setResult(int resultCode) {
        synchronized (this) {
            mResultCode = resultCode;
            mResultData = null;
        }
    }
    public final void setResult(int resultCode, Intent data) {
        synchronized (this) {
            mResultCode = resultCode;
            mResultData = data;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tmpfragment,container,false);
    }

    @Override
    public void onDestroy() {
        try {
            Class clazz = getActivity().getClass();
            Method method = clazz.getDeclaredMethod("onActivityResult",int.class,int.class, Intent.class);
            method.setAccessible(true);
            method.invoke(getActivity(),mRequestCode,mResultCode,mResultData);
        } catch (Exception e) {

        }
        super.onDestroy();
    }
    public final void finish(){
        getActivity().onBackPressed();
    }
}
