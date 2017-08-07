package com.tianditu.sdkDemo;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;

public class MyLocationDemo extends Activity {
	TabHost mTabHost;
	Tab1 mTab1;
	Tab2 mTab2;
	Tab3 mTab3;
	Tab4 mTab4;

	MapView mMapView;
	MyLocationOverlay mMyLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylocationdemo);
		
		// 地图视图
		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setLogoPos(MapView.LOGO_LEFT_TOP);
		mMapView.setBuiltInZoomControls(true);

		mMyLocation = new MyOverlay(this, mMapView);
		mMapView.addOverlay(mMyLocation);

		// Tab视图
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		FrameLayout contentView = mTabHost.getTabContentView();
		LayoutInflater inflater = LayoutInflater.from(this);
		inflater.inflate(R.layout.mylocationdemotab1, contentView);
		inflater.inflate(R.layout.mylocationdemotab2, contentView);
		inflater.inflate(R.layout.mylocationdemotab3, contentView);
		inflater.inflate(R.layout.mylocationdemotab4, contentView);
		
		TabSpec tab = mTabHost.newTabSpec("tab1");
		tab.setIndicator("定位&指南针");
		tab.setContent(R.id.LinearLayout01);
		mTabHost.addTab(tab);

		tab = mTabHost.newTabSpec("tab2");
		tab.setIndicator("我的位置");
		tab.setContent(R.id.LinearLayout02);
		mTabHost.addTab(tab);

		tab = mTabHost.newTabSpec("tab3");
		tab.setIndicator("操作");
		tab.setContent(R.id.LinearLayout03);
		mTabHost.addTab(tab);
		
		tab = mTabHost.newTabSpec("tab4");
		tab.setIndicator("自定义");
		tab.setContent(R.id.LinearLayout04);
		mTabHost.addTab(tab);
		
		TabWidget tabWidget = mTabHost.getTabWidget();
		int tabHeight = getResources().getDimensionPixelSize(R.dimen.tabtitleheight);
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			tabWidget.getChildAt(i).getLayoutParams().height = tabHeight;
		}
		
		mTab1 = new Tab1(this, contentView.getChildAt(0));
		mTab2 = new Tab2(this, contentView.getChildAt(1));
		mTab3 = new Tab3(this, contentView.getChildAt(2));
		mTab4 = new Tab4(this, contentView.getChildAt(2));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if (mMyLocation.isCompassEnabled())
			mMyLocation.disableCompass();
		if (mMyLocation.isMyLocationEnabled())
			mMyLocation.disableMyLocation();
	}
	
	MyLocationOverlay getOverlay() {
		return mMyLocation;
	}
	
	/**
	 * Tab1
	 * 定位&指南针
	 */
	class Tab1 {
		CheckBox mCheckCompass;
		CheckBox mCheckLocation;
		CheckBox mCheckFollow;
		TextView mTextTips;

		public Tab1(Activity activity, View view) {
			mCheckCompass = (CheckBox) view.findViewById(R.id.checkCompass);
			mCheckCompass.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked)
						getOverlay().enableCompass();
					else
						getOverlay().disableCompass();
				}
			});

			mCheckLocation = (CheckBox) view.findViewById(R.id.checkLocation);
			mCheckLocation.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked)
						getOverlay().enableMyLocation();
					else
						getOverlay().disableMyLocation();
				}
			});
			
			mCheckFollow = (CheckBox) view.findViewById(R.id.checkFollow);
			mCheckFollow.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					getOverlay().setGpsFollow(isChecked);
				}
			});
			
			mTextTips = (TextView) view.findViewById(R.id.textViewTips);
		}
	}

	/**
	 * Tab2
	 * 我的位置
	 */
	class Tab2 {
		Button mBtnLocation;
		TextView mTextTips;

		public Tab2(Activity activity, View view) {
			mBtnLocation = (Button) view.findViewById(R.id.buttonLocation);
			mTextTips = (TextView) view.findViewById(R.id.textViewTips);
		}

	}

	/**
	 * Tab3
	 * 操作
	 */
	class Tab3 {
		TextView mTextTips;

		public Tab3(Activity activity, View view) {
			mTextTips = (TextView) view.findViewById(R.id.textViewTips);
		}

	}

	/**
	 * Tab4
	 * 自定义
	 */
	class Tab4 {
		CheckBox mCheckDrawCompass;
		CheckBox mCheckDrawLocation;
		TextView mTextTips;

		public Tab4(Activity activity, View view) {
			mCheckDrawCompass = (CheckBox) view.findViewById(R.id.checkDrawCompass);
			mCheckDrawLocation = (CheckBox) view.findViewById(R.id.checkDrawLocation);

			mTextTips = (TextView) view.findViewById(R.id.textViewTips);
		}
	}
	
	class MyOverlay extends MyLocationOverlay {
		public MyOverlay(Context context, MapView mapView) {
			super(context, mapView);
		}

		/*
		 * 处理在"我的位置"上的点击事件
		 */
		protected boolean dispatchTap() {
			return true;
		}

		@Override
		public void onLocationChanged(Location location) {
			super.onLocationChanged(location);
		}
	}
}
