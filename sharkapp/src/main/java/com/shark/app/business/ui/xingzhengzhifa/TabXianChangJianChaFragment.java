package com.shark.app.business.ui.xingzhengzhifa;

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

public class TabXianChangJianChaFragment extends TabBaseFragment {
    private LinearLayout linearpart;
    private TextView xianchangl;
    private TextView add;
    private CardView card;
    private TextView xianchangjianchaqiye;
    private TextView xianchangjianchachangsuo;
    private TextView xianchangjianchashijian;
    private TextView jianchajilu;
    private LinearLayout jianchabook;
    private Button bookxcjc;
    private Button bookzlzg;
    private Button bookxccl;
    private Button bookdccf;
    private TextView chakan;
    private TextView bianji;
    private TextView shanchu;
    private TextView yincang;
    private LinearLayout passl;
    private Button passlian;
    private Button passzeling;
    private Button passbingchu;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3_xianchangjiancha, container, false);

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

        return null;
    }

    private void initView() {
        if (getStringValue("showType").equals("0")) {
            initNotComplete();
        } else if (getStringValue("showType").equals("1")) {
            initAdd();
        } else if (getStringValue("showType").equals("2")) {
            initComplete();
        } else {
            initNotComplete();
        }
        linearpart = (LinearLayout) findViewById(R.id.linearpart);
        xianchangl = (TextView) findViewById(R.id.xianchangl);
        add = (TextView) findViewById(R.id.add);
        card = (CardView) findViewById(R.id.card);
        xianchangjianchaqiye = (TextView) findViewById(R.id.xianchangjianchaqiye);
        xianchangjianchachangsuo = (TextView) findViewById(R.id.xianchangjianchachangsuo);
        xianchangjianchashijian = (TextView) findViewById(R.id.xianchangjianchashijian);
        jianchajilu = (TextView) findViewById(R.id.jianchajilu);
        jianchabook = (LinearLayout) findViewById(R.id.jianchabook);
        bookxcjc = (Button) findViewById(R.id.bookxcjc);
        bookzlzg = (Button) findViewById(R.id.bookzlzg);
        bookxccl = (Button) findViewById(R.id.bookxccl);
        bookdccf = (Button) findViewById(R.id.bookdccf);
        chakan = (TextView) findViewById(R.id.chakan);
        bianji = (TextView) findViewById(R.id.bianji);
        shanchu = (TextView) findViewById(R.id.shanchu);
        yincang = (TextView) findViewById(R.id.yincang);
        passl = (LinearLayout) findViewById(R.id.passl);
        passlian = (Button) findViewById(R.id.passlian);
        passzeling = (Button) findViewById(R.id.passzeling);
        passbingchu = (Button) findViewById(R.id.passbingchu);
    }
}
