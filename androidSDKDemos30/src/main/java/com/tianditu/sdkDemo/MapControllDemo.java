package com.tianditu.sdkDemo;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MapView.OnMapParamChangeListener;
import com.tianditu.android.maps.MapView.OnScaleChangeListener;
import com.tianditu.android.maps.overlay.MarkerOverlay;
import com.tianditu.android.maps.overlay.PolygonOverlay;
import com.tianditu.android.maps.renderoption.DrawableOption;
import com.tianditu.android.maps.renderoption.PlaneOption;

public class MapControllDemo extends Activity {
	private MapView mMapView;
	private TabHost mTabHost;
	private Tab1 mTab1;
	private Tab2 mTab2;
	private Tab3 mTab3;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapcontrolldemo);

		// 地图视图
		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setLogoPos(MapView.LOGO_LEFT_TOP);
		mMapView.setBuiltInZoomControls(true);
		mMapView.setScaleChangeListener(new OnScaleChangeListener() {

			@Override
			public void onScaleChange() {
				mTab1.updateTips();
				mTab2.updateTips();
				mTab3.updateTips();
			}
			
		});
		
		mMapView.setParamChangeListener(new OnMapParamChangeListener() {

			@Override
			public void onMapParamChange(int iwitchchange) {
				mTab1.updateTips();
				mTab2.updateTips();
				mTab3.updateTips();
			}
			
		});
		
		// Tab视图
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		FrameLayout contentView = mTabHost.getTabContentView();
		LayoutInflater inflater = LayoutInflater.from(this);
		inflater.inflate(R.layout.mapcontrolldemotab1, contentView);
		inflater.inflate(R.layout.mapcontrolldemotab2, contentView);
		inflater.inflate(R.layout.mapcontrolldemotab3, contentView);

		TabSpec tab = mTabHost.newTabSpec("tab1");
		tab.setIndicator("坐标");
		tab.setContent(R.id.LinearLayout01);
		mTabHost.addTab(tab);

		tab = mTabHost.newTabSpec("tab2");
		tab.setIndicator("地图范围");
		tab.setContent(R.id.LinearLayout02);
		mTabHost.addTab(tab);

		tab = mTabHost.newTabSpec("tab3");
		tab.setIndicator("地图视角");
		tab.setContent(R.id.LinearLayout03);
		mTabHost.addTab(tab);

		TabWidget tabWidget = mTabHost.getTabWidget();
		int tabHeight = getResources().getDimensionPixelSize(R.dimen.tabtitleheight);
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			tabWidget.getChildAt(i).getLayoutParams().height = tabHeight;
		}

		mTab1 = new Tab1(this, contentView.getChildAt(0));
		mTab2 = new Tab2(this, contentView.getChildAt(1));
		mTab3 = new Tab3(this, contentView.getChildAt(2));
		mTab1.updateTips();
		mTab2.updateTips();
		mTab3.updateTips();
	}

	static String getGeoPoint(GeoPoint geoPoint) {
		double dLon = ((double) geoPoint.getLongitudeE6()) / 1E6;
		double dLat = ((double) geoPoint.getLatitudeE6()) / 1E6;
		String string = String.format(Locale.getDefault(), "lon = %.6f, lat = %.6f", dLon, dLat);
		return string;
	}

	static String getZoomLevel(MapView mapView) {
		int level = mapView.getZoomLevel();
		int min = mapView.getMinZoomLevel();
		int max = mapView.getMaxZoomLevel();
		String string = String.format(Locale.getDefault(), "level = %d(%d-%d)", level, min, max);
		return string;
	}
	
	static String getSpan(int lonSpan, int latSpan) {
		double dLonSpan = ((double) lonSpan) / 1E6;
		double dLatSpan = ((double) latSpan) / 1E6;
		String string = String.format(Locale.getDefault(), "lonSpan = %.6f, latSpan = %.6f",
				dLonSpan, dLatSpan);
		return string;
	}

	static double getDouble(String value) {
		try {
			Double d = Double.valueOf(value);
			return d;
		} catch (NumberFormatException e) {
		}
		return 0;
	}
	
	/**
	 * Tab1
	 * 坐标
	 */
	class Tab1 {
		private EditText mEditLon;
		private EditText mEditLat;
		private Button mBtnSetCenter;

		private TextView mTextTips;

		private MarkerOverlay mMarker;

		public Tab1(Activity activity, View view) {
			mEditLon = (EditText) view.findViewById(R.id.editTextLon);
			mEditLat = (EditText) view.findViewById(R.id.editTextLat);
			
			mBtnSetCenter = (Button) view.findViewById(R.id.buttonCenter);
			mBtnSetCenter.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					double dLon = MapControllDemo.getDouble(mEditLon.getText().toString());
					double dLat = MapControllDemo.getDouble(mEditLat.getText().toString());
					GeoPoint geoPoint = new GeoPoint((int) (dLat * 1E6), (int) (dLon * 1E6));
					mMarker.setPosition(geoPoint);
					mMapView.getController().setCenter(geoPoint);
					updateTips();
				}
			});
			
			mTextTips = (TextView) view.findViewById(R.id.textViewTips);
			
			mEditLon.setText(String.format(Locale.getDefault(), "%.6f", 116.4));
			mEditLat.setText(String.format(Locale.getDefault(), "%.6f", 39.9));

			DrawableOption option = new DrawableOption();
			option.setAnchor(0.5f, 1.0f);
			
			mMarker = new MarkerOverlay();
			mMarker.setOption(option);
			mMarker.setIcon(activity.getResources().getDrawable(R.drawable.poiresult));
			mMapView.addOverlay(mMarker);
		}
		
		/**
		 * 更新提示信息
		 */
		void updateTips() {
			GeoPoint center = mMapView.getMapCenter();
			String tips = String.format(Locale.getDefault(), "%s\n%s", //
					MapControllDemo.getGeoPoint(center), // 经纬度坐标
					MapControllDemo.getZoomLevel(mMapView)); // 比例尺
			mTextTips.setText(tips);
		}
	}

	/**
	 * Tab2
	 * 地图范围
	 */
	class Tab2 {
		private EditText mEditLonSpan;
		private EditText mEditLatSpan;
		private Button mBtnSetBound;

		private SeekBar mSeekLevel;
		private TextView mTextLevel;

		private TextView mTextTips;

		private PolygonOverlay mOverlay;

        public Tab2(Activity activity, View view) {

			mEditLonSpan = (EditText) view.findViewById(R.id.editTextLonSpan);
			mEditLatSpan = (EditText) view.findViewById(R.id.editTextLatSpan);

			mBtnSetBound = (Button) view.findViewById(R.id.buttonBound);
			mBtnSetBound.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					double dLonSpan = MapControllDemo.getDouble(mEditLonSpan.getText().toString());
					double dLatSpan = MapControllDemo.getDouble(mEditLatSpan.getText().toString());
					mMapView.getController().zoomToSpan((int) (dLatSpan * 1E6), (int) (dLonSpan * 1E6));
					
					int lonSpan = (int) (dLonSpan * 1E6);
					int latSpan = (int) (dLatSpan * 1E6);
					int lonCenter = mMapView.getMapCenter().getLongitudeE6();
					int latCenter = mMapView.getMapCenter().getLatitudeE6();
					ArrayList<GeoPoint> plist = new ArrayList<GeoPoint>();
					plist.add(new GeoPoint(latCenter - latSpan / 2, lonCenter - lonSpan / 2));
					plist.add(new GeoPoint(latCenter - latSpan / 2, lonCenter + lonSpan / 2));
					plist.add(new GeoPoint(latCenter + latSpan / 2, lonCenter + lonSpan / 2));
					plist.add(new GeoPoint(latCenter + latSpan / 2, lonCenter - lonSpan / 2));
					mOverlay.setPoints(plist);
				}
			});
			
			mSeekLevel = (SeekBar) view.findViewById(R.id.seekBarLevel);
			mSeekLevel.setMax(mMapView.getMaxZoomLevel() - 1);
			mSeekLevel.setProgress(mMapView.getZoomLevel());
			mSeekLevel.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					int value = seekBar.getProgress() + 1;
					mTextLevel.setText(String.format(Locale.getDefault(), "%2d", value));
					mMapView.getController().setZoom(value);
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					int value = seekBar.getProgress() + 1;
					mTextLevel.setText(String.format(Locale.getDefault(), "%2d", value));
				}
			});
			mTextLevel = (TextView) view.findViewById(R.id.textViewLevel);
			int value = mSeekLevel.getProgress() + 1;
			mTextLevel.setText(String.format(Locale.getDefault(), "%2d", value));

			mTextTips = (TextView) view.findViewById(R.id.textViewTips);
			
			double dLonSpan = 0.1f;
			double dLatSpan = 0.1f;
			mEditLonSpan.setText(String.format(Locale.getDefault(), "%.6f", dLonSpan));
			mEditLatSpan.setText(String.format(Locale.getDefault(), "%.6f", dLatSpan));

			PlaneOption planeOption = new PlaneOption();
			planeOption.setFillColor(0x33FFFF00);
			mOverlay = new PolygonOverlay();
			mOverlay.setOption(planeOption);
			mMapView.addOverlay(mOverlay);
		}
		
		/**
		 * 更新提示信息
		 */
		void updateTips() {
			int lonSpan = mMapView.getLongitudeSpan();
			int latSpan = mMapView.getLatitudeSpan();
			String tips = String.format(Locale.getDefault(), "%s\n%s", //
					MapControllDemo.getSpan(lonSpan, latSpan),// 经度纬度跨度
					MapControllDemo.getZoomLevel(mMapView)); // 比例尺
			mTextTips.setText(tips);
		}
	}

	/**
	 * Tab3
	 * 地图视角
	 */
	class Tab3 {
		// 旋转
		private final static int ROTATE_MAX = 360;
		
		private SeekBar mSeekRotate;
		private TextView mTextRotate;

		// 俯视
		private final static int LOOKDOWN_MAX = 45;
		
		private SeekBar mSeekLookdown;
		private TextView mTextLookdown;

		private TextView mTextTips;

		public Tab3(Activity activity, View view) {
			// 旋转
			mSeekRotate = (SeekBar) view.findViewById(R.id.seekBarRotate);
			mSeekRotate.setMax(ROTATE_MAX);
			mSeekRotate.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					int value = seekBar.getProgress();
					mTextRotate.setText(String.format(Locale.getDefault(), "%3d", value));
					mMapView.setMapRotation(value);
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {

				}

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					mTextRotate.setText(String.format(Locale.getDefault(), "%3d", progress));
				}
			});

			mTextRotate = (TextView) view.findViewById(R.id.textViewRotate);

			// 俯视
			mSeekLookdown = (SeekBar) view.findViewById(R.id.seekBarLookdown);
			mSeekLookdown.setMax(LOOKDOWN_MAX);
			mSeekLookdown.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					int value = seekBar.getProgress();
					mTextLookdown.setText(String.format(Locale.getDefault(), "%3d", -value));
					mMapView.setLookDownAngle(-value);
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
					mTextLookdown.setText(String.format(Locale.getDefault(), "%3d", -progress));
				}
			});

			mTextLookdown = (TextView) view.findViewById(R.id.textViewLookdown);

			mTextTips = (TextView) view.findViewById(R.id.textViewTips);

		}
		
		/**
		 * 更新提示信息
		 */
		void updateTips() {
			float dRotate = mMapView.getMapRotation();
			float dLookdown = mMapView.getLookDownAngle();
			String tips = String.format(Locale.getDefault(),
					"旋转角度 = %.2f(%d-%d)\n俯视角度 = %.2f(%d-%d)\n%s", //
					dRotate, 0, ROTATE_MAX, // 旋转角度
					dLookdown, -LOOKDOWN_MAX, 0, // 俯视角度
					MapControllDemo.getZoomLevel(mMapView)); // 比例尺
			mTextTips.setText(tips);
		}
	}
}
