package com.tianditu.sdkDemo;

import java.util.ArrayList;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.tianditu.android.Engine.PosInfo;
import com.tianditu.android.Engine.PosInfos;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.overlay.PolylineOverlay;
import com.tianditu.android.maps.renderoption.LineOption;
import com.tianditu.maps.Utils.UtilTimer;
import com.tianditu.maps.Utils.UtilTimer.OnTimerListener;
import com.tianditu.maps.Utils.UtilsFile;
import com.tianditu.maps.Utils.UtilsFolder;

public class ActivitySimulateGpsDemo extends MyLocationDemo {
	PolylineOverlay mOverlay;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		float density = this.getResources().getDisplayMetrics().density;
		LineOption option = new LineOption();
		option.setStrokeWidth((int) (2 * density));
		option.setStrokeColor(0xFFFF0000);
		
		mOverlay = new PolylineOverlay();
		mOverlay.setOption(option);
        mMapView.addOverlay(mOverlay, 0);

		simulateEnableLocation();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		simulateDisableLocation();
	}
	
	/**
	 * 读文件模拟信号
	 */
	private final String JSON_FILE_NAME = "/tianditu/gps.txt";
	private UtilTimer mGpsSimulateTimer = null;
	private PosInfos mSimulateList;
	private int mSimulateIndex = -1;

	private void simulateRead() {
		if (mSimulateList != null)
			return;

		String strFolder = UtilsFolder.getExernalSDCardPath();
		String strFile = strFolder + JSON_FILE_NAME;
		String json = UtilsFile.readFile(strFile);
		mSimulateList = new PosInfos();
		mSimulateList.setValue(json);
		
		ArrayList<GeoPoint> list = new ArrayList<GeoPoint>();
		int size = mSimulateList.size();
		for (int i = 0; i < size; ++i) {
			list.add(mSimulateList.get(i).getPoint());
		}
		mOverlay.setPoints(list);
	}

	private void simulateEnableLocation() {
		simulateRead();
		if (mGpsSimulateTimer == null) {

			OnTimerListener listener = new OnTimerListener() {
				@Override
				public void onTimer(int timerID) {
					if (!getOverlay().isMyLocationEnabled())
						return;
					int size = mSimulateList.size();
					if (size == 0)
						return;
					++mSimulateIndex;
					if (mSimulateIndex < 0 || mSimulateIndex >= size)
						mSimulateIndex = 0;
					PosInfo pos = mSimulateList.get(mSimulateIndex);
					Location location = new Location(LocationManager.GPS_PROVIDER);
					location.setLatitude(pos.mLat);
					location.setLongitude(pos.mLon);
					mMyLocation.onLocationChanged(location);
				}

				@Override
				public void onTimerEnd(int timerID) {
					mMyLocation.onLocationChanged(null);
				}
			};
			mGpsSimulateTimer = new UtilTimer(listener);
		}
		mGpsSimulateTimer.startTimer(0, 1000);
	}

	private void simulateDisableLocation() {
		// 销毁定位
		if (mGpsSimulateTimer != null)
			mGpsSimulateTimer.stopTimer();
	}
}
