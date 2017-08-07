package com.tianditu.sdkDemo;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MapViewRender;
import com.tianditu.android.maps.MyLocationOverlay;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.renderoption.DrawableOption;

public class OverlayDemo extends Activity implements OnClickListener {
	private MapView mMapView = null;
	private MyOverlay mOverlay = null;
	private EditText mEditTextLon = null;
	private EditText mEditTextLat = null;
	private Button mBtnAdd = null;
	private Button mBtnDelete = null;
	private CheckBox mCbShowView = null;
	private TextView mTvTips = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overlaydemo);
		
		mMapView = (MapView) this.findViewById(R.id.overlay_mapview);
		mMapView.setBuiltInZoomControls(true);
		
		MyLocationOverlay mLocation = new MyLocationOverlay(this, mMapView);
		mLocation.enableMyLocation();
		mMapView.getController().setCenter(mLocation.getMyLocation());
		mMapView.addOverlay(mLocation);
		
		mOverlay = new MyOverlay();
		mMapView.addOverlay(mOverlay);
		
		mEditTextLon = (EditText) this.findViewById(R.id.editTextLon);
		mEditTextLat = (EditText) this.findViewById(R.id.editTextLat);
		mBtnAdd = (Button) this.findViewById(R.id.buttonadd);
		mBtnDelete = (Button) this.findViewById(R.id.buttondelete);
		mCbShowView = (CheckBox) this.findViewById(R.id.showview);
		mTvTips = (TextView) this.findViewById(R.id.textViewTips);
		mBtnAdd.setOnClickListener(this);
		mBtnDelete.setOnClickListener(this);
		mCbShowView.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mOverlay.setVisible(isChecked);
				mMapView.postInvalidate();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonadd:
			String lon = mEditTextLon.getText().toString();
			String lat = mEditTextLat.getText().toString();
			if (lon != null && lat != null && !lon.equals("")
					&& !lat.equals("")) {
				GeoPoint point = new GeoPoint(Integer.valueOf(lon),
						Integer.valueOf(lat));
				mOverlay.setGeoPoint(point);
				mMapView.postInvalidate();
			}
			break;
		case R.id.buttondelete:
			mMapView.removeOverlay(mOverlay);
			
			mEditTextLon.setText("精度");
			mEditTextLat.setText("纬度");
			mTvTips.setText("操作提示");
			mCbShowView.setChecked(false);
			mMapView.postInvalidate();
			
			mOverlay = new MyOverlay();
			mMapView.addOverlay(mOverlay);
			break;
		default:
			break;
		}
	}

	public class MyOverlay extends Overlay {
		private Drawable mDrawable;
		private GeoPoint mGeoPoint;
		private DrawableOption mOption;

		public MyOverlay() {
			mDrawable = OverlayDemo.this.getResources().getDrawable(R.drawable.poiresult);
			mOption = new DrawableOption();
			mOption.setAnchor(0.5f, 1.0f);
		}

		public void setGeoPoint(GeoPoint point) {
			mGeoPoint = point;
		}

		@Override
		public boolean onTap(GeoPoint point, MapView mapView) {
			mGeoPoint = point;
			mEditTextLon.setText("" + point.getLongitudeE6());
			mEditTextLat.setText("" + point.getLatitudeE6());
			mCbShowView.setChecked(true);
			return true;
		}

		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event, MapView mapView) {
			mTvTips.setText("onKeyUp:" + keyCode);
			return super.onKeyUp(keyCode, event, mapView);
		}

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event, MapView mapView) {
			mTvTips.setText("onKeyDown:" + keyCode);
			return super.onKeyDown(keyCode, event, mapView);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event, MapView mapView) {
			switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				mTvTips.setText("ACTION_DOWN" + event.getX() + ","
						+ event.getY());
				break;
			case MotionEvent.ACTION_MOVE:
				mTvTips.setText("ACTION_MOVE" + event.getX() + ","
						+ event.getY());
				break;
			case MotionEvent.ACTION_UP:
				mTvTips.setText("ACTION_UP" + event.getX() + "," + event.getY());
				break;
			default:
				break;
			}
			return super.onTouchEvent(event, mapView);
		}

		@Override
		public boolean onLongPress(GeoPoint p, MapView mapView) {
			mTvTips.setText("onLongPress:" + p.getLatitudeE6() + ","
					+ p.getLongitudeE6());
			return super.onLongPress(p, mapView);
		}

		@Override
		public boolean isVisible() {
			return super.isVisible();
		}

		@Override
		public void setVisible(boolean b) {
			super.setVisible(b);
		}

		@Override
		public void draw(GL10 gl, MapView mapView, boolean shadow) {
			if (shadow)
				return;

			MapViewRender render = mapView.getMapViewRender();
			render.drawDrawable(gl, mOption, mDrawable, mGeoPoint);
		}
	}

}