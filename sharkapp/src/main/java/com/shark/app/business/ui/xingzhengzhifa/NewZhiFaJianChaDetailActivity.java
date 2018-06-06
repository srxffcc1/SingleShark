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
import com.shark.app.business.entity.Entity_ZhiFaBianHao;
import com.wisdomregulation.data.entitybase.DateBase_Entity;
import com.wisdomregulation.help.Demo_DBManager;
import com.wisdomregulation.help.Demo_DbUtil;


public class NewZhiFaJianChaDetailActivity extends AppCompatActivity {
    private FrameLayout xianchangjianchaf;
    private FrameLayout fucha1f;
    private FrameLayout fucha2f;
    private FrameLayout bingchulianf;
    private FrameLayout fucha2lianf;
    private FrameLayout zelingzhenggaif;
    private ScrollView scrollviewt;
    private DateBase_Entity entity_ZhiFaBianHao;
    private String bianhaoid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_xingzhengzhifa);
        initView();
        initData();
//        bindDataToView();
        initListener();

    }

    private void initListener() {
//        scrollviewt.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                scrollviewt.post(new Runnable() {
//                    public void run() {
//                            scrollviewt.fullScroll(View.FOCUS_DOWN);
//
//
//                    }
//                });
//            }
//        });
    }

    private void bindDataToView() {

        String flag=entity_ZhiFaBianHao.getValue("当前阶段");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        TabBaseFragment xianchang = new TabXianChangJianChaFragment();
        TabBaseFragment zelingzhenggai = new TabZeLingZhengGaiFragment();
        TabBaseFragment fucha = new TabFuChaChaFragment();
        TabBaseFragment fucha2 = new TabFuChaChaFragment();
        System.out.println("此刻状态"+flag);
        if ("0".equals(flag)) {
            xianchang.setArguments("showType", "2").setArguments("bianhaoid",bianhaoid);
            zelingzhenggai.setArguments("showType", "2").setArguments("bianhaoid",bianhaoid);
            fucha.setArguments("showType", "2").setArguments("fuchaType","1").setArguments("bianhaoid",bianhaoid);
            if(entity_ZhiFaBianHao.getValue("是否二次整改").equals("1")){
                fucha2.setArguments("showType", "1").setArguments("fuchaType","2").setArguments("bianhaoid",bianhaoid);
            }
        } else if ("1".equals(flag)) {
            xianchang.setArguments("showType", "2").setArguments("bianhaoid",bianhaoid).setArguments("pass","1");
//            zelingzhenggai.setArguments("showType", "1").setArguments("bianhaoid",bianhaoid);

        } else if ("2".equals(flag)) {
            xianchang.setArguments("showType", "2").setArguments("bianhaoid",bianhaoid);
            zelingzhenggai.setArguments("showType", "1").setArguments("bianhaoid",bianhaoid).setArguments("pass","1");

        } else if ("3".equals(flag)) {
        } else if ("4".equals(flag)) {
            xianchang.setArguments("showType", "2").setArguments("bianhaoid",bianhaoid);
            zelingzhenggai.setArguments("showType", "2").setArguments("bianhaoid",bianhaoid);
            fucha.setArguments("showType", "1").setArguments("fuchaType","1").setArguments("bianhaoid",bianhaoid).setArguments("pass","1");

        } else if ("5".equals(flag)) {
            xianchang.setArguments("showType", "2").setArguments("bianhaoid",bianhaoid);
            zelingzhenggai.setArguments("showType", "2").setArguments("bianhaoid",bianhaoid);
            fucha.setArguments("showType", "2").setArguments("fuchaType","1").setArguments("bianhaoid",bianhaoid);
            if(entity_ZhiFaBianHao.getValue("是否二次整改").equals("1")){
                fucha2.setArguments("showType", "1").setArguments("fuchaType","2").setArguments("bianhaoid",bianhaoid).setArguments("pass","1");
            }


        } else if ("6".equals(flag)) {
            xianchang.setArguments("showType", "2").setArguments("bianhaoid",bianhaoid);
            zelingzhenggai.setArguments("showType", "2").setArguments("bianhaoid",bianhaoid);
            fucha.setArguments("showType", "2").setArguments("fuchaType","1").setArguments("bianhaoid",bianhaoid);
            if(entity_ZhiFaBianHao.getValue("是否二次整改").equals("1")){
                fucha2.setArguments("showType", "2").setArguments("fuchaType","2").setArguments("bianhaoid",bianhaoid);
            }
        }else{
            xianchang.setArguments("showType", "2").setArguments("bianhaoid",bianhaoid).setArguments("link","1");
            zelingzhenggai.setArguments("showType", "2").setArguments("bianhaoid",bianhaoid).setArguments("link","1");
            fucha.setArguments("showType", "2").setArguments("fuchaType","1").setArguments("bianhaoid",bianhaoid).setArguments("link","1");
            if(entity_ZhiFaBianHao.getValue("是否二次整改").equals("1")){
                fucha2.setArguments("showType", "2").setArguments("fuchaType","2").setArguments("bianhaoid",bianhaoid).setArguments("link","1");
            }
        }
        fragmentTransaction
                .replace(R.id.xianchangjianchaf, xianchang)
                .replace(R.id.zelingzhenggaif, zelingzhenggai)
                .replace(R.id.fucha1f, fucha)
                .replace(R.id.fucha2f, fucha2).commitAllowingStateLoss();
    }

    private void initData() {
        bianhaoid=getIntent().getStringExtra("bianhaoid");
        entity_ZhiFaBianHao= Demo_DbUtil.getSearchResultOnlyOne(Demo_DBManager.build().search(new Entity_ZhiFaBianHao().setId(bianhaoid)));
        bindDataToView();

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
