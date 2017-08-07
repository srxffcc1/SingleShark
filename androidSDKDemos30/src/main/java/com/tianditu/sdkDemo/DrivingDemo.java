package com.tianditu.sdkDemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.TDrivingRoute;
import com.tianditu.android.maps.TDrivingRoute.OnDrivingResultListener;
import com.tianditu.android.maps.TDrivingRouteResult;
import com.tianditu.android.maps.overlay.MarkerOverlay;
import com.tianditu.android.maps.overlayutil.TdrivingRouteOverlay;
import com.tianditu.android.maps.renderoption.DrawableOption;

public class DrivingDemo extends Activity implements OnClickListener,
		OnDrivingResultListener {

	public final int DRIVING_ONTAP_TYPE_START = 0;
	public final int DRIVING_ONTAP_TYPE_END = 1;
	public final int DRIVING_ONTAP_TYPE_AVE1 = 2;
	public final int DRIVING_ONTAP_TYPE_AVE2 = 3;
	public final int DRIVING_ONTAP_TYPE_AVE3 = 4;
	public final int DRIVING_ONTAP_TYPE_AVE4 = 5;

	private MapView mMapView = null;
	private Button mBtnStart = null;
	private Button mBtnEnd = null;
	private Button mBtnRoute = null;
	private Button mBtnAddAve = null;
	private Button mBtnReset = null;
	private Button mBtnAve1 = null;
	private Button mBtnAve2 = null;
	private Button mBtnAve3 = null;
	private Button mBtnAve4 = null;
	private EditText mEtStart = null;
	private EditText mEtEnd = null;
	private EditText mEtAve1 = null;
	private EditText mEtAve2 = null;
	private EditText mEtAve3 = null;
	private EditText mEtAve4 = null;

	private int mTapType = -1;
	private TDrivingRoute mRoute = null;
	private TdrivingRouteOverlay mTrOverlay = null;
	private ArrayList<GeoPoint> mAvePoints = new ArrayList<GeoPoint>();
	private GeoPoint mStartPoint = null;
	private GeoPoint mEndPoint = null;
	private MarkerOverlay mStartMarker = null;
	private MarkerOverlay mEndMarker = null;
	private MarkerOverlay mAveMarker1 = null;
	private MarkerOverlay mAveMarker2 = null;
	private MarkerOverlay mAveMarker3 = null;
	private MarkerOverlay mAveMarker4 = null;
	private LinearLayout mLayoutAve1 = null;
	private LinearLayout mLayoutAve2 = null;
	private LinearLayout mLayoutAve3 = null;
	private LinearLayout mLayoutAve4 = null;
	private int mClickIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drivingdemo);
		mMapView = (MapView) findViewById(R.id.driving_mapview);
		mBtnStart = (Button) this.findViewById(R.id.start);
		mBtnEnd = (Button) this.findViewById(R.id.end);
		mBtnRoute = (Button) this.findViewById(R.id.route);
		mBtnReset = (Button) this.findViewById(R.id.reset);
		mBtnAddAve = (Button) this.findViewById(R.id.add_ave);
		mBtnAve1 = (Button) this.findViewById(R.id.ave_1);
		mBtnAve2 = (Button) this.findViewById(R.id.ave_2);
		mBtnAve3 = (Button) this.findViewById(R.id.ave_3);
		mBtnAve4 = (Button) this.findViewById(R.id.ave_4);
		mEtStart = (EditText) this.findViewById(R.id.et_start);
		mEtEnd = (EditText) this.findViewById(R.id.et_end);
		mEtAve1 = (EditText) this.findViewById(R.id.et_ave1);
		mEtAve2 = (EditText) this.findViewById(R.id.et_ave2);
		mEtAve3 = (EditText) this.findViewById(R.id.et_ave3);
		mEtAve4 = (EditText) this.findViewById(R.id.et_ave4);
		mLayoutAve1 = (LinearLayout) this.findViewById(R.id.layout_ave1);
		mLayoutAve2 = (LinearLayout) this.findViewById(R.id.layout_ave2);
		mLayoutAve3 = (LinearLayout) this.findViewById(R.id.layout_ave3);
		mLayoutAve4 = (LinearLayout) this.findViewById(R.id.layout_ave4);
		mMapView.addOverlay(new MyOverlay(this));
		mBtnStart.setOnClickListener(this);
		mBtnEnd.setOnClickListener(this);
		mBtnRoute.setOnClickListener(this);
		mBtnReset.setOnClickListener(this);
		mBtnAddAve.setOnClickListener(this);
		mBtnAve1.setOnClickListener(this);
		mBtnAve2.setOnClickListener(this);
		mBtnAve3.setOnClickListener(this);
		mBtnAve4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start:
			if (mStartMarker != null) {
				mMapView.removeOverlay(mStartMarker);
				mStartMarker = null;
			}
			mTapType = DRIVING_ONTAP_TYPE_START;
			break;
		case R.id.end:
			if (mEndMarker != null) {
				mMapView.removeOverlay(mEndMarker);
				mEndMarker = null;
			}
			mTapType = DRIVING_ONTAP_TYPE_END;
			break;
		case R.id.route:
			// 规划
			if (mStartPoint == null || mEndPoint == null) {
				return;
			}
			mRoute = new TDrivingRoute(DrivingDemo.this);
			if (mAvePoints != null && mAvePoints.size() > 0)
				mRoute.startRoute(mStartPoint, mEndPoint, mAvePoints,
						TDrivingRoute.DRIVING_TYPE_NOHIGHWAY);
			else
				mRoute.startRoute(mStartPoint, mEndPoint, null,
						TDrivingRoute.DRIVING_TYPE_NOHIGHWAY);
			break;
		case R.id.reset:
			mAvePoints.clear();
			if (mStartMarker != null) {
				mMapView.removeOverlay(mStartMarker);
				mStartMarker = null;
			}
			if (mTrOverlay != null) {
				mMapView.removeOverlay(mTrOverlay);
				mTrOverlay = null;
			}
			if (mEndMarker != null) {
				mMapView.removeOverlay(mEndMarker);
				mEndMarker = null;
			}
			if (mAveMarker1 != null) {
				mMapView.removeOverlay(mAveMarker1);
				mAveMarker1 = null;
			}
			if (mAveMarker2 != null) {
				mMapView.removeOverlay(mAveMarker2);
				mAveMarker2 = null;
			}
			if (mAveMarker3 != null) {
				mMapView.removeOverlay(mAveMarker3);
				mAveMarker3 = null;
			}
			if (mAveMarker4 != null) {
				mMapView.removeOverlay(mAveMarker4);
				mAveMarker4 = null;
			}
			mEtStart.setText("起点经纬度");
			mEtEnd.setText("终点经纬度");
			mEtAve1.setText("设置途经点1");
			mEtAve2.setText("设置途经点2");
			mEtAve3.setText("设置途经点3");
			mEtAve4.setText("设置途经点4");
			mLayoutAve1.setVisibility(View.GONE);
			mLayoutAve2.setVisibility(View.GONE);
			mLayoutAve3.setVisibility(View.GONE);
			mLayoutAve4.setVisibility(View.GONE);
			mMapView.postInvalidate();
			break;
		case R.id.add_ave:
			mClickIndex++;
			if (mClickIndex > 4)
				return;
			switch (mClickIndex) {
			case 1:
				mLayoutAve1.setVisibility(View.VISIBLE);
				break;
			case 2:
				mLayoutAve2.setVisibility(View.VISIBLE);
				break;
			case 3:
				mLayoutAve3.setVisibility(View.VISIBLE);
				break;
			case 4:
				mLayoutAve4.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
			break;
		case R.id.ave_1:
			if (mAveMarker1 != null) {
				mMapView.removeOverlay(mAveMarker1);
				mAveMarker1 = null;
			}
			mTapType = DRIVING_ONTAP_TYPE_AVE1;
			break;
		case R.id.ave_2:
			if (mAveMarker2 != null) {
				mMapView.removeOverlay(mAveMarker2);
				mAveMarker2 = null;
			}
			mTapType = DRIVING_ONTAP_TYPE_AVE2;
			break;
		case R.id.ave_3:
			if (mAveMarker3 != null) {
				mMapView.removeOverlay(mAveMarker3);
				mAveMarker3 = null;
			}
			mTapType = DRIVING_ONTAP_TYPE_AVE3;
			break;
		case R.id.ave_4:
			if (mAveMarker4 != null) {
				mMapView.removeOverlay(mAveMarker4);
				mAveMarker4 = null;
			}
			mTapType = DRIVING_ONTAP_TYPE_AVE4;
			break;
		default:
			break;
		}
	}

	public class MyOverlay extends Overlay {
		private Drawable mDrawableStart = null; // 开始点图标
		private Drawable mDrawableEnd = null; // 终点图标
		private Drawable mDrawableAve; // 途经点图标

		public MyOverlay(Context con) {
			mDrawableStart = (Drawable) con.getResources().getDrawable(
					R.drawable.icon_route_start);
			mDrawableEnd = (Drawable) con.getResources().getDrawable(
					R.drawable.icon_route_end);
			mDrawableAve = (Drawable) con.getResources().getDrawable(
					R.drawable.icon_route_mid);
		}

		@Override
		public boolean onTap(GeoPoint point, MapView mapView) {
			if (mTapType == -1)
				return false;
			switch (mTapType) {
			case DRIVING_ONTAP_TYPE_START: {
				mStartPoint = point;
				DrawableOption option = new DrawableOption();
				option.setAnchor(1.6f, 1.2f);
				
				mStartMarker = new MarkerOverlay();
				mStartMarker.setOption(option);
				mStartMarker.setPosition(point);
				mStartMarker.setIcon(mDrawableStart);
				mMapView.addOverlay(mStartMarker);
				mEtStart.setText(point.getLatitudeE6() + ","
						+ point.getLongitudeE6());
				break;
			}
			case DRIVING_ONTAP_TYPE_END: {
				mEndPoint = point;
				DrawableOption option = new DrawableOption();
				option.setAnchor(1.6f, 1.2f);
				
				mEndMarker = new MarkerOverlay();
				mEndMarker.setOption(option);
				mEndMarker.setPosition(point);
				mEndMarker.setIcon(mDrawableEnd);
				mMapView.addOverlay(mEndMarker);
				mEtEnd.setText(point.getLatitudeE6() + ","
						+ point.getLongitudeE6());
				break;
			}
			case DRIVING_ONTAP_TYPE_AVE1: {
				mAvePoints.add(point);
				
				DrawableOption option = new DrawableOption();
				option.setAnchor(1.6f, 1.2f);
				
				mAveMarker1 = new MarkerOverlay();
				mAveMarker1.setOption(option);
				mAveMarker1.setPosition(point);
				mAveMarker1.setIcon(mDrawableAve);
				mMapView.addOverlay(mAveMarker1);
				mEtAve1.setText(point.getLatitudeE6() + ","
						+ point.getLongitudeE6());
				break;
			}
			case DRIVING_ONTAP_TYPE_AVE2: {
				DrawableOption option = new DrawableOption();
				option.setAnchor(1.6f, 1.2f);
				
				mAvePoints.add(point);
				mAveMarker2 = new MarkerOverlay();
				mAveMarker2.setOption(option);
				mAveMarker2.setPosition(point);
				mAveMarker2.setIcon(mDrawableAve);
				mMapView.addOverlay(mAveMarker2);
				mEtAve2.setText(point.getLatitudeE6() + ","
						+ point.getLongitudeE6());
				break;
			}
			case DRIVING_ONTAP_TYPE_AVE3: {
				DrawableOption option = new DrawableOption();
				option.setAnchor(1.6f, 1.2f);
			
				mAvePoints.add(point);
				mAveMarker3 = new MarkerOverlay();
				mAveMarker3.setOption(option);
				mAveMarker3.setPosition(point);
				mAveMarker3.setIcon(mDrawableAve);
				mMapView.addOverlay(mAveMarker3);
				mEtAve3.setText(point.getLatitudeE6() + ","
						+ point.getLongitudeE6());
				break;
			}
			case DRIVING_ONTAP_TYPE_AVE4: {
				DrawableOption option = new DrawableOption();
				option.setAnchor(1.6f, 1.2f);
				
				mAvePoints.add(point);
				mAveMarker4 = new MarkerOverlay();
				mAveMarker4.setOption(option);
				mAveMarker4.setPosition(point);
				mAveMarker4.setIcon(mDrawableAve);
				mMapView.addOverlay(mAveMarker4);
				mEtAve4.setText(point.getLatitudeE6() + ","
						+ point.getLongitudeE6());
				break;
			}
			default:
				break;
			}
			mTapType = -1;
			mapView.postInvalidate();
			return true;
		}
	}

	@Override
	public void onDrivingResult(TDrivingRouteResult result, int errCode) {
		if (errCode != 0) {
			Toast.makeText(this, "规划出错！" + errCode, Toast.LENGTH_SHORT).show();
			return;
		}
		if (mStartMarker != null) {
			mMapView.removeOverlay(mStartMarker);
			mStartMarker = null;
		}
		if (mEndMarker != null) {
			mMapView.removeOverlay(mEndMarker);
			mEndMarker = null;
		}
		mTrOverlay = new TdrivingRouteOverlay(this, mMapView.getResources()
				.getDrawable(R.drawable.icon_route_mid));
		mTrOverlay.setData(result);
		mMapView.addOverlay(mTrOverlay);
		mMapView.postInvalidate();
	}
}
