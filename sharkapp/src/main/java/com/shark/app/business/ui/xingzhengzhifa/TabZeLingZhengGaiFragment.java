package com.shark.app.business.ui.xingzhengzhifa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shark.app.R;


import java.util.ArrayList;
import java.util.List;

public class TabZeLingZhengGaiFragment extends TabBaseFragment {
    private LinearLayout linearpart;
    private TextView xianchangl;
    private TextView add;
    private CardView card;
    private TextView chufaqiye;
    private TextView weifaxingwei;
    private LinearLayout jianchabook;
    private Button bookxcjc;
    private Button bookzlzg;
    private Button bookxccl;
    private Button bookdccf;
    private TextView chakan;
    private TextView bianji;
    private TextView shanchu;
    private TextView yincang;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3_zelingzhenggai, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        initListener();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        linearpart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showcard();
            }
        });
        yincang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidecard();
            }
        });
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.chakan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        findViewById(R.id.bookxcjc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        findViewById(R.id.bookxccl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        bookxcjc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        bookzlzg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        bookxccl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        bookdccf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void bindDataToView() {


    }

    public String getJianChaQingKuang() {
        String result = "";

        return result;
    }

    private void initView() {

        linearpart = (LinearLayout) findViewById(R.id.linearpart);
        xianchangl = (TextView) findViewById(R.id.xianchangl);
        add = (TextView) findViewById(R.id.add);
        card = (CardView) findViewById(R.id.card);
        chufaqiye = (TextView) findViewById(R.id.chufaqiye);
        weifaxingwei = (TextView) findViewById(R.id.weifaxingwei);
        jianchabook = (LinearLayout) findViewById(R.id.jianchabook);
        bookxcjc = (Button) findViewById(R.id.bookxcjc);
        bookzlzg = (Button) findViewById(R.id.bookzlzg);
        bookxccl = (Button) findViewById(R.id.bookxccl);
        bookdccf = (Button) findViewById(R.id.bookdccf);
        chakan = (TextView) findViewById(R.id.chakan);
        bianji = (TextView) findViewById(R.id.bianji);
        shanchu = (TextView) findViewById(R.id.shanchu);
        yincang = (TextView) findViewById(R.id.yincang);
    }
}
