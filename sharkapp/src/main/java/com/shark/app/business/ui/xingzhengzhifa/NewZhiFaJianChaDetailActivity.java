package com.shark.app.business.ui.xingzhengzhifa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.shark.app.R;


public class NewZhiFaJianChaDetailActivity extends AppCompatActivity {
    private FrameLayout xianchangjianchaf;
    private FrameLayout fucha1f;
    private FrameLayout fucha2f;
    private FrameLayout bingchulianf;
    private FrameLayout fucha2lianf;
    private FrameLayout zelingzhenggaif;
    private ScrollView scrollviewt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_xingzhengzhifa);
        initView();
        initData();
        bindDataToView();
        initListener();

    }

    private void initListener() {
        scrollviewt.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollviewt.post(new Runnable() {
                    public void run() {
                            scrollviewt.fullScroll(View.FOCUS_DOWN);


                    }
                });
            }
        });
    }

    private void bindDataToView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TabBaseFragment xianchang = new TabXianChangJianChaFragment();
        TabBaseFragment zelingzhenggai = new TabZeLingZhengGaiFragment();
        TabBaseFragment fucha = new TabFuChaChaFragment();
        TabBaseFragment fucha2 = new TabFuChaChaFragment();
        fragmentTransaction
                .replace(R.id.xianchangjianchaf, xianchang)
                .replace(R.id.zelingzhenggaif, zelingzhenggai)
                .replace(R.id.fucha1f, fucha)
                .replace(R.id.fucha2f, fucha2).commitAllowingStateLoss();
    }

    private void initData() {

    }

    private void initView() {
        xianchangjianchaf = (FrameLayout) findViewById(R.id.xianchangjianchaf);
        fucha1f = (FrameLayout) findViewById(R.id.fucha1f);
        fucha2f = (FrameLayout) findViewById(R.id.fucha2f);
        bingchulianf = (FrameLayout) findViewById(R.id.bingchulianf);
        fucha2lianf = (FrameLayout) findViewById(R.id.fucha2lianf);
        zelingzhenggaif = (FrameLayout) findViewById(R.id.zelingzhenggaif);
        scrollviewt = (ScrollView) findViewById(R.id.scrollviewt);
    }
}
