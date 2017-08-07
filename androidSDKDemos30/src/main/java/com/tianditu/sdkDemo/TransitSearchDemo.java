package com.tianditu.sdkDemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.TBusRoute;
import com.tianditu.android.maps.TBusRoute.OnTransitResultListener;
import com.tianditu.android.maps.TTransitLine;
import com.tianditu.android.maps.TTransitResult;
import com.tianditu.android.maps.TTransitSegmentInfo;
import com.tianditu.android.maps.TTransitSegmentLine;
import com.tianditu.android.maps.overlay.MarkerOverlay;
import com.tianditu.android.maps.overlayutil.TransitRouteOverlay;
import com.tianditu.android.maps.renderoption.DrawableOption;

/*
 * 主要用来测试SDK代码
 * */
public class TransitSearchDemo extends Activity implements
		OnTransitResultListener {
	private MapView mMapView = null;
	public static Context mCon = null;
	public static TBusRoute busRoute = null;
	public boolean mIsStart = false;
	public GeoPoint mStart = null;
	public GeoPoint mEnd = null;
	private MarkerOverlay mStartMarker = null;
	private MarkerOverlay mEndMarker = null;
	private int mCount = 0;
	private TransitRouteOverlay mTrOverlay = null;
	private Button mSearch;
	private Button mBtnList;
	private ListView mList = null;
	private TextView mTextTitle;
	private TextView mTextStart;
	private TextView mTextEnd;
	private TextView mTextContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCon = this;
		setContentView(R.layout.transitsearch);
		mMapView = (MapView) this.findViewById(R.id.mapview);
		mList = (ListView) findViewById(R.id.list);
		mSearch = (Button) this.findViewById(R.id.bus_route);
		mBtnList = (Button) this.findViewById(R.id.bus_list);
		mTextTitle = (TextView) this.findViewById(R.id.bus_title);
		mTextStart = (TextView) this.findViewById(R.id.bus_start);
		mTextEnd = (TextView) this.findViewById(R.id.bus_end);
		mTextContent = (TextView) this.findViewById(R.id.bus_content);
		mMapView.addOverlay(new MyOverlay(this));
		mSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                mCount = 0;
                mIsStart = true;
                if (mTrOverlay != null) {
                    mMapView.removeOverlay(mTrOverlay);
                    mTrOverlay = null;
                }
                if (mStartMarker != null) {
                    mMapView.removeOverlay(mStartMarker);
                    mStartMarker = null;
                }
                if (mEndMarker != null) {
                    mMapView.removeOverlay(mEndMarker);
                    mEndMarker = null;
                }
                mList.setVisibility(View.GONE);
                mTextStart.setVisibility(View.GONE);
                mTextEnd.setVisibility(View.GONE);
                mTextContent.setVisibility(View.GONE);
                mTextTitle.setText("点击地图选择起始点");
            }
        });

        mBtnList.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mList.setVisibility(View.VISIBLE);
            }
        });
    }

    public class MyOverlay extends Overlay {
        private Drawable mDrawableStart = null; //开始点图标
        private Drawable mDrawableEnd = null; //终点图标

        public MyOverlay(Context con) {
            mDrawableStart = (Drawable) con.getResources().getDrawable(
                    R.drawable.icon_route_start);

            mDrawableEnd = (Drawable) con.getResources().getDrawable(
                    R.drawable.icon_route_end);
        }

        @Override
        public boolean onTap(GeoPoint point, MapView mapView) {
            if (mIsStart == false) {
                return false;
            }
            if (mCount == 0) {
                mStart = point;
				DrawableOption option = new DrawableOption();
				option.setAnchor(1.6f, 1.2f);
				
				mStartMarker = new MarkerOverlay();
				mStartMarker.setOption(option);
				mStartMarker.setPosition(point);
				mStartMarker.setIcon(mDrawableStart);
                mMapView.addOverlay(mStartMarker);
                mTextTitle.setText("点击地图选择终点");
                mTextStart.setVisibility(View.VISIBLE);
                mTextStart.setText("起点坐标 :" + point.getLatitudeE6() + ","
                        + point.getLongitudeE6());
            } else if (mCount == 1) {
				mEnd = point;
				DrawableOption option = new DrawableOption();
				option.setAnchor(1.6f, 1.2f);

				mEndMarker = new MarkerOverlay();
				mEndMarker.setOption(option);
				mEndMarker.setPosition(point);
				mEndMarker.setIcon(mDrawableEnd);
				mMapView.addOverlay(mEndMarker);
				mCount = 0;
                busRoute = new TBusRoute(TransitSearchDemo.this);
                mIsStart = false;
                busRoute.startRoute(mStart, mEnd, TBusRoute.BUS_TYPE_FASTEST);
                mTextEnd.setVisibility(View.VISIBLE);
                mTextEnd.setText("起点坐标 :" + point.getLatitudeE6() + ","
                        + point.getLongitudeE6());
                mTextTitle.setText("再次点击规划按钮，重新进行公交规划");
            }
            mCount++;
            mapView.postInvalidate();
            return true;
        }

    }

	@Override
	public void onTransitResult(TTransitResult result, int error) {
		if (result == null || result.getTransitLines().size() == 0) {
			Toast.makeText(mCon, "未找到结果", Toast.LENGTH_LONG).show();
			return;
		}
		initListData(result);
		if (mStartMarker != null) {
			mMapView.removeOverlay(mStartMarker);
			mStartMarker = null;
		}
		if (mEndMarker != null) {
			mMapView.removeOverlay(mEndMarker);
			mEndMarker = null;
		}
		this.mTrOverlay = new TransitRouteOverlay(mCon,mMapView.getResources().getDrawable(R.drawable.icon_bus_station));
		this.mTrOverlay.setData(result);
		this.mTrOverlay.setLineWidth(8);
		mMapView.addOverlay(mTrOverlay);
	}

    public void initListData(TTransitResult result) {
        ArrayList<String> data = new ArrayList<String>();
        data.add("是否有地铁：" + result.hasSubWay());
        for (int i = 0; i < result.getTransitLines().size(); i++) {
            TTransitLine line = result.getTransitLines().get(i);
            data.add("");
            data.add("方案" + (i + 1));
            data.add("线路名称：" + line.getName());
            data.add("线路里程：" + line.getLength() + "米");
            data.add("线路用时：" + line.getCostTime() + "分");
            ArrayList<TTransitSegmentInfo> infos = line.getSegmentInfo();
            for (int j = 0; j < infos.size(); j++) {
                TTransitSegmentInfo info = infos.get(j);
                data.add("");
                data.add("路段" + (j + 1));
                data.add("路段起点：" + info.getStart().getName());
                data.add("路段终点：" + info.getEnd().getName());
                String str = "" + info.getType();
                int type = info.getType();
                if (type == 1) {
                    str += "(步行)";
                } else if (type == 2) {
                    str += "(公交)";
                } else if (type == 3) {
                    str += "(地铁)";
                } else {
                    str += "(站内换乘)";
                }
                data.add("路段类型：" + str);
                for (int k = 0; k < info.getSegmentLine().size(); k++) {
                    TTransitSegmentLine segmentLine = info.getSegmentLine()
                            .get(k);
                    data.add("路段方案" + (k + 1) + "名称：" + segmentLine.getName());
                    data.add("路段方案" + (k + 1) + "方向："
                            + segmentLine.getDirection());
                    data.add("路段方案" + (k + 1) + "id：" + segmentLine.getId());
                    data.add("路段方案" + (k + 1) + "经过站数："
                            + segmentLine.getStationCount());
                    data.add("路段方案" + (k + 1) + "里程：" + segmentLine.getLength()
                            + "米");
                }
            }
        }
        mList.setAdapter((ListAdapter) new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, data));
        mList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                    int position, long arg3) {
                mList.setVisibility(View.GONE);
            }
        });
    }
}
