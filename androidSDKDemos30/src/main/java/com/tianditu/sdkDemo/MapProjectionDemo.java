package com.tianditu.sdkDemo;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;

public class MapProjectionDemo extends Activity {
	private MapView mMapView;
	private TabHost mTabHost;
	private Tab1 mTab1;
	private Tab2 mTab2;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapprojectiondemo);

		// 地图视图
		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setLogoPos(MapView.LOGO_LEFT_TOP);
		mMapView.setBuiltInZoomControls(true);

		// Tab视图
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		FrameLayout contentView = mTabHost.getTabContentView();
		LayoutInflater inflater = LayoutInflater.from(this);
		inflater.inflate(R.layout.mapprojectiondemotab1, contentView);
		inflater.inflate(R.layout.mapprojectiondemotab2, contentView);

		TabSpec tab = mTabHost.newTabSpec("tab1");
		tab.setIndicator("屏幕转经纬度");
		tab.setContent(R.id.LinearLayout01);
		mTabHost.addTab(tab);

		tab = mTabHost.newTabSpec("tab2");
		tab.setIndicator("经纬度转屏幕");
		tab.setContent(R.id.LinearLayout02);
		mTabHost.addTab(tab);

		TabWidget tabWidget = mTabHost.getTabWidget();
		int tabHeight = getResources().getDimensionPixelSize(R.dimen.tabtitleheight);
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			tabWidget.getChildAt(i).getLayoutParams().height = tabHeight;
		}
		
		mTab1 = new Tab1(this, contentView.getChildAt(0));
		mTab2 = new Tab2(this, contentView.getChildAt(1));
		mTab1.updateTips();
		mTab2.updateTips();
	}

	static String getScreenPoint(int x, int y) {
		String string = String.format(Locale.getDefault(), "screen x = %d, y = %d", x, y);
		return string;
	}

	static String getMapSize(MapView mapView) {
		int width = mapView.getWidth();
		int height = mapView.getHeight();
		String string = String.format(Locale.getDefault(), "map width = %d, height = %d", width,
				height);
		return string;
	}

	class Tab1 {
		private EditText mEditScreenX;
		private EditText mEditScreenY;
		private Button mBtnTransform;

		private TextView mTextTips;

		public Tab1(Activity activity, View view) {
			mEditScreenX = (EditText) view.findViewById(R.id.editTextScreenX);
			mEditScreenY = (EditText) view.findViewById(R.id.editTextScreenY);

			mBtnTransform = (Button) view.findViewById(R.id.buttonTransform);
			mBtnTransform.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					updateTips();
				}
			});

			mTextTips = (TextView) view.findViewById(R.id.textViewTips);
		}

		/**
		 * 更新提示信息
		 */
		void updateTips() {
			GeoPoint geoPoint = mMapView.getMapCenter();
			int x = mMapView.getWidth() / 2;
			int y = mMapView.getHeight() / 2;
			int lonSpan = mMapView.getLongitudeSpan();
			int latSpan = mMapView.getLatitudeSpan();
			String tips = String.format(Locale.getDefault(), "%s\n%s\n%s\n%s\n%s", //
					MapControllDemo.getGeoPoint(geoPoint), // 经纬度坐标
					MapProjectionDemo.getScreenPoint(x, y), // 屏幕坐标
					MapControllDemo.getSpan(lonSpan, latSpan), // 经度纬度跨度
					MapControllDemo.getZoomLevel(mMapView), // 比例尺
					MapProjectionDemo.getMapSize(mMapView)); // 地图窗口大小
			mTextTips.setText(tips);
		}
	}

	class Tab2 {
		private EditText mEditLon;
		private EditText mEditLat;
		private Button mBtnTransform;

		private TextView mTextTips;

		public Tab2(Activity activity, View view) {

			mEditLon = (EditText) view.findViewById(R.id.editTextLon);
			mEditLat = (EditText) view.findViewById(R.id.editTextLat);
			mBtnTransform = (Button) view.findViewById(R.id.buttonTransform);
			mBtnTransform.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					updateTips();
				}
			});

			mTextTips = (TextView) view.findViewById(R.id.textViewTips);
		}

		/**
		 * 更新提示信息
		 */
		void updateTips() {
			GeoPoint geoPoint = mMapView.getMapCenter();
			int x = mMapView.getWidth() / 2;
			int y = mMapView.getHeight() / 2;
			int lonSpan = mMapView.getLongitudeSpan();
			int latSpan = mMapView.getLatitudeSpan();
			String tips = String.format(Locale.getDefault(), "%s\n%s\n%s\n%s\n%s", // 
					MapControllDemo.getGeoPoint(geoPoint), // 经纬度坐标
					MapProjectionDemo.getScreenPoint(x, y), // 屏幕坐标
					MapControllDemo.getSpan(lonSpan, latSpan), // 经度纬度跨度
					MapControllDemo.getZoomLevel(mMapView), // 比例尺
					MapProjectionDemo.getMapSize(mMapView)); // 地图窗口大小

			mTextTips.setText(tips);
		}
	}

}
