package com.amap.navi.demo.activity;

import android.os.Bundle;

import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.view.TrafficButtonView;
import com.shark.resoureclone.R;


public class CustomTrafficButtonViewActivity extends BaseActivity {


    private TrafficButtonView mTrafficButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_traffic_button_view);
        mTrafficButtonView = (TrafficButtonView) findViewById(R.id.myTrafficButtonView);
        mAMapNaviView = (com.amap.api.navi.AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);

        //设置布局完全不可见
        com.amap.api.navi.AMapNaviViewOptions options = mAMapNaviView.getViewOptions();
        options.setLayoutVisible(false);
        mAMapNaviView.setViewOptions(options);

        mAMapNaviView.setLazyTrafficButtonView(mTrafficButtonView);
    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);

    }

    @Override
    public void onCalculateRouteSuccess(int[] ids) {
        super.onCalculateRouteSuccess(ids);
        mAMapNavi.startNavi(NaviType.EMULATOR);
    }
}
