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

public class TabFuChaChaFragment extends TabBaseFragment {
    int fuchatype = 0;//用来判断是不是2次复查

    String bianhaoid;//执法编号id 即行政执法id
    private LinearLayout linearpart;
    private TextView zhenggaifuchal;
    private TextView add;
    private CardView card;
    private TextView qiyemingcheng;
    private TextView fuchayijian;
    private TextView fuchaqingkuang;
    private LinearLayout fuchabook;
    private Button bookxcjc;
    private Button bookzlzg;
    private Button bookzgfc;
    private Button bookxccl;
    private TextView chakan;
    private TextView bianji;
    private TextView shanchu;
    private TextView yincang;
    private LinearLayout passl;
    private Button passagainfucha;
    private Button passzip;
    private Button passback;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab4_fucha2, container, false);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initListener() {
        if (getStringValue("show").equals("no")) {
            initAdd();
        }
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
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.chakan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        bookzgfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public String getJianChaQingKuang() {
        String result = "";

        return result;
    }

    @Override
    public void bindDataToView() {
//        if (zgfc == null) {
//            initNotComplete();
//        } else {
//            if (zgfc.getSaveFlag().equals("0")) {
//                initAdd();
//            } else {
//                if (fuchatype == 2) {
//                    zhenggaifuchal.setText("二次复查");
//                    bookxccl.setVisibility(View.VISIBLE);
//                } else {
//                    zhenggaifuchal.setText("复查");
//                    bookxccl.setVisibility(View.GONE);
//                }
//                fuchaqingkuang.setText(getJianChaQingKuang());
//            }
//
//        }

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
        zhenggaifuchal = (TextView) findViewById(R.id.zhenggaifuchal);
        add = (TextView) findViewById(R.id.add);
        card = (CardView) findViewById(R.id.card);
        qiyemingcheng = (TextView) findViewById(R.id.qiyemingcheng);
        fuchayijian = (TextView) findViewById(R.id.fuchayijian);
        fuchaqingkuang = (TextView) findViewById(R.id.fuchaqingkuang);
        fuchabook = (LinearLayout) findViewById(R.id.fuchabook);
        bookxcjc = (Button) findViewById(R.id.bookxcjc);
        bookzlzg = (Button) findViewById(R.id.bookzlzg);
        bookzgfc = (Button) findViewById(R.id.bookzgfc);
        bookxccl = (Button) findViewById(R.id.bookxccl);
        chakan = (TextView) findViewById(R.id.chakan);
        bianji = (TextView) findViewById(R.id.bianji);
        shanchu = (TextView) findViewById(R.id.shanchu);
        yincang = (TextView) findViewById(R.id.yincang);
        passl = (LinearLayout) findViewById(R.id.passl);
        passagainfucha = (Button) findViewById(R.id.passagainfucha);
        passzip = (Button) findViewById(R.id.passzip);
        passback = (Button) findViewById(R.id.passback);
    }
}
