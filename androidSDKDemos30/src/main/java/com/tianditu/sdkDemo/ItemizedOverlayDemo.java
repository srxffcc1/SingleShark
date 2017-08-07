package com.tianditu.sdkDemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.ItemizedOverlay;
import com.tianditu.android.maps.ItemizedOverlay.OnFocusChangeListener;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MapView.LayoutParams;
import com.tianditu.android.maps.MyLocationOverlay;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.OverlayItem;

/*
 * 主要用来测试SDK代码
 * */
public class ItemizedOverlayDemo extends Activity implements
        OnFocusChangeListener {
    public static View mPopView = null;
    public static TextView mText = null;
    public static MapView mMapView = null;
    private TabHost mTabHost = null;
    private static Tab1 mTab1 = null;
    private static Tab2 mTab2 = null;
    private OverItemT mOverlay = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemizedoverlaydemo);
        
        // 地图视图
        mMapView = (MapView) findViewById(R.id.itemized_mapview);
        mMapView.setLogoPos(MapView.LOGO_RIGHT_BOTTOM);
        mMapView.setBuiltInZoomControls(true);

        // Tab视图
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        FrameLayout contentView = mTabHost.getTabContentView();
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.itemizedoverlaydemotab1, contentView);
        inflater.inflate(R.layout.itemizedoverlaydemotab2, contentView);

        TabSpec tab = mTabHost.newTabSpec("tab1");
        tab.setIndicator("焦点&操作");
        tab.setContent(R.id.LinearLayout01);
        mTabHost.addTab(tab);

        tab = mTabHost.newTabSpec("tab2");
        tab.setIndicator("视图范围");
        tab.setContent(R.id.LinearLayout02);
        mTabHost.addTab(tab);

        TabWidget tabWidget = mTabHost.getTabWidget();
        int tabHeight = getResources().getDimensionPixelSize(
                R.dimen.tabtitleheight);
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            tabWidget.getChildAt(i).getLayoutParams().height = tabHeight;
        }

        mTab1 = new Tab1(contentView.getChildAt(0));
        mTab2 = new Tab2(contentView.getChildAt(1));

        // 添加我的位置和覆盖物集合
        List<Overlay> list = mMapView.getOverlays();
        MyLocationOverlay myLocation = new MyLocationOverlay(this, mMapView);
        myLocation.enableMyLocation();
        list.add(myLocation);
        Drawable marker = getResources().getDrawable(R.drawable.poi_xml);
        mOverlay = new OverItemT(marker);
        mOverlay.setOnFocusChangeListener(this);
        list.add(mOverlay);

        // 创建弹出框view
        mPopView = super.getLayoutInflater().inflate(R.layout.popview, null);
        mText = (TextView) mPopView.findViewById(R.id.text);
        mMapView.addView(mPopView, new MapView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, null,
                MapView.LayoutParams.TOP_LEFT));
        mPopView.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            System.exit(0);
        return super.onKeyUp(keyCode, event);
    }

	class Tab1 {
		public CheckBox mShowFocus = null;
		public Button mBtnUp = null;
		public Button mBtnDown = null;
		public TextView mTvFocus = null;
		public TextView mTvTips = null;

        public Tab1(View view) {
            mShowFocus = (CheckBox) view.findViewById(R.id.showFocus);
            mBtnUp = (Button) view.findViewById(R.id.buttonup);
            mBtnDown = (Button) view.findViewById(R.id.buttondown);
            mTvFocus = (TextView) view.findViewById(R.id.textViewFocus);
            mTvTips = (TextView) view.findViewById(R.id.textViewTips);

            mBtnUp.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int index = mOverlay.getFocusID();
					index--;
					if (index < 0){
						mTvTips.setText("已经是第一个了");
						index = 0;
					}
					mOverlay.setFocusID(index);
					mOverlay.onTap(index);
					mMapView.postInvalidate();
				}
			});

            mBtnDown.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int index = mOverlay.getFocusID();
					index++;
					if (index >= mOverlay.size() - 1){
						mTvTips.setText("已经是第最后一个了");
						index = mOverlay.size() - 1;
					}
					mOverlay.setFocusID(index);
					mOverlay.onTap(index);
					mMapView.postInvalidate();
				}
			});

            mShowFocus
                    .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                boolean isChecked) {
                            mOverlay.showFocusAlways(isChecked);
                        }
                    });
        }

        public void updateFocus(String focus) {
            mTvFocus.setText(focus);
        }

        public void updateTips(String tips) {
            mTvTips.setText(tips);
        }

    }

    class Tab2 {

        public TextView mTvPoint;
        public TextView mTvSpan;
        public Button mBtnSet;
        
        public int lonSpan;
        public int latSpan;
        public GeoPoint mPoint;
        

        public Tab2(View view) {
            
            mTvPoint = (TextView) view.findViewById(R.id.textViewpoint);
            mTvSpan = (TextView) view.findViewById(R.id.textViewspan);
            mBtnSet = (Button) view.findViewById(R.id.buttonset);
            
            mBtnSet.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    if(mPoint == null){
                        mTvPoint.setText("lon:xxx.xxxxx,lat:xx.xxxxx");
                        mTvSpan.setText("lonSpan:x.xxxxx,latSpan:x.xxxxx");
                        return;
                    }
                    mTvPoint.setText(MapControllDemo.getGeoPoint(mPoint));
                    mTvSpan.setText("lonSpan:"+lonSpan+",latSpan:"+latSpan);
                }
            });
        }
        
        public void updateData(int lon,int lat,GeoPoint point) {
            lonSpan = lon;
            latSpan = lat;
            mPoint = point;
        }
    }

	static class OverItemT extends ItemizedOverlay<OverlayItem> implements
			Overlay.Snappable {
		private List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
		double[][] points = new double[][] { { 39.90923, 116.397428 },
				{ 39.9022, 116.3922 }, { 39.917723, 116.3722 },
				{ 39.891826, 116.366157 }, { 39.885768, 116.394996 },
				{ 39.929485, 116.421089 }, { 39.888664, 116.424522 },
				{ 39.910263, 116.353454 }, { 39.911315, 116.416282 } };
		int size = points.length;

		public OverItemT(Drawable marker) {
			super(boundCenterBottom(marker));
			for (int i = 0; i < size; i++) {
				OverlayItem item = new OverlayItem(
						new GeoPoint((int) (points[i][0] * 1E6),
								(int) (points[i][1] * 1E6)), "P" + i, "point"
								+ i);
				item.setMarker(marker);
				mGeoList.add(item);
			}
			populate(); // createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法

        }

        @Override
        protected OverlayItem createItem(int i) {
            return mGeoList.get(i);
        }

        @Override
        public int size() {
            return mGeoList.size();
        }

		// 处理当点击事件
		@Override
		protected boolean onTap(int i) {
			if (i == -1)
				return false;
			GeoPoint pt = mGeoList.get(i).getPoint();
			ItemizedOverlayDemo.mMapView.updateViewLayout(
					ItemizedOverlayDemo.mPopView, new MapView.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, pt,
							MapView.LayoutParams.BOTTOM_CENTER));
			ItemizedOverlayDemo.mPopView.setVisibility(View.VISIBLE);
			ItemizedOverlayDemo.mText.setText(mGeoList.get(i).getTitle());
			return true;
		}

        @Override
        public boolean onTouchEvent(MotionEvent event, MapView mapView) {
            String str = "";
            switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                str = "ACTION_DOWN" + event.getX() + "," + event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                str = "ACTION_MOVE" + event.getX() + "," + event.getY();
                break;
            case MotionEvent.ACTION_UP:
                str = "ACTION_UP" + event.getX() + "," + event.getY();
                break;
            default:
                break;
            }
            mTab1.updateTips(str);
            return super.onTouchEvent(event, mapView);
        }

        @Override
        public boolean onKeyUp(int keyCode, KeyEvent event, MapView mapView) {
            mTab1.updateTips("onKeyUp");
            return super.onKeyUp(keyCode, event, mapView);
        }

        @Override
        public boolean onTap(GeoPoint p, MapView mapView) {
            ItemizedOverlayDemo.mPopView.setVisibility(View.GONE);
            mTab1.updateTips(MapControllDemo.getGeoPoint(p));
            return super.onTap(p, mapView);
        }

    }

	@Override
	public void onFocusChanged(ItemizedOverlay<?> overlay, OverlayItem newFocus) {
		if (newFocus == null) {
			mTab1.updateFocus("焦点变化");
			mTab2.updateData(0, 0, null);
			return;
		}
		mTab1.updateFocus(MapControllDemo.getGeoPoint(newFocus.getPoint()));
		mTab2.updateData(overlay.getLonSpanE6(), overlay.getLatSpanE6(),
				newFocus.getPoint());
	}
}
