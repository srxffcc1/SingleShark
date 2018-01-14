package com.shark.app.business.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shark.app.R;


@SuppressLint("ValidFragment")
public class JianChaXiangCardFragment extends Fragment {
    private String mTitle;
    public static JianChaXiangCardFragment getInstance(String title) {
        JianChaXiangCardFragment sf = new JianChaXiangCardFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_simple_card, null);
        return v;
    }

}
