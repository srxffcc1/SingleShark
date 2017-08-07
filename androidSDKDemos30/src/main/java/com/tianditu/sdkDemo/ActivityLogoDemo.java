package com.tianditu.sdkDemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.tianditu.android.maps.MapView;

public class ActivityLogoDemo extends Activity {
	private MapView mMapView;
	private RadioGroup mRadioGroup;
	private CheckBox mCheckZoom;
	private CheckBox mCheckOverlay;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitylogodemo);

		// 地图视图
		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setLogoPos(MapView.LOGO_LEFT_TOP);
		mMapView.setBuiltInZoomControls(true);
		mMapView.setDrawOverlayWhenZooming(false);
		
		// Logo
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		mRadioGroup.check(R.id.radio0);

		mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radio0)
					mMapView.setLogoPos(MapView.LOGO_LEFT_TOP);
				else if (checkedId == R.id.radio1)
					mMapView.setLogoPos(MapView.LOGO_LEFT_BOTTOM);
				else if (checkedId == R.id.radio2)
					mMapView.setLogoPos(MapView.LOGO_RIGHT_TOP);
				else if (checkedId == R.id.radio3)
					mMapView.setLogoPos(MapView.LOGO_RIGHT_BOTTOM);
			}
		});

		mCheckZoom = (CheckBox) findViewById(R.id.checkZoom);
		mCheckZoom.setChecked(true);
		mCheckZoom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mMapView.setBuiltInZoomControls(isChecked);
			}
		});
		
		mCheckOverlay = (CheckBox) findViewById(R.id.checkDrawOverlayWhenZooming);
		mCheckOverlay.setChecked(false);
		mCheckOverlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mMapView.setDrawOverlayWhenZooming(isChecked);
			}
		});

	}
}
