package com.tianditu.sdkDemo;

import java.util.Locale;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.TErrorCode;
import com.tianditu.android.maps.TGeoAddress;
import com.tianditu.android.maps.TGeoDecode;
import com.tianditu.android.maps.TGeoDecode.OnGeoResultListener;
import com.tianditu.android.maps.overlay.MarkerOverlay;
import com.tianditu.android.maps.renderoption.DrawableOption;

public class GeoDecodeDemo extends Activity implements OnGeoResultListener {

	private MapView mMapView;

	private EditText mEditLon;
	private EditText mEditLat;
	private Button mBtnSearch;
	private TextView mTextTips;

	private GeoPoint mGeoPoint;
	private TGeoDecode mGeoDecode;
	private MarkerOverlay mOverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geodecodedemo);

		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setLogoPos(MapView.LOGO_RIGHT_TOP);
		mMapView.setBuiltInZoomControls(true);

		mEditLon = (EditText) findViewById(R.id.editTextLon);
		mEditLat = (EditText) findViewById(R.id.editTextLat);

		mBtnSearch = (Button) findViewById(R.id.buttonSearch);
		mBtnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				double dLon = MapControllDemo.getDouble(mEditLon.getText().toString());
				double dLat = MapControllDemo.getDouble(mEditLat.getText().toString());
				GeoPoint geoPoint = new GeoPoint((int) (dLat * 1E6), (int) (dLon * 1E6));
				searchGeoDecode(geoPoint);
			}
		});

		// tips
		mTextTips = (TextView) findViewById(R.id.textViewTips);
		mTextTips.setText("");

		double dLon = 116.4;
		double dLat = 39.9;
		mEditLon.setText(String.format(Locale.getDefault(), "%.6f", dLon));
		mEditLat.setText(String.format(Locale.getDefault(), "%.6f", dLat));
		mGeoPoint = new GeoPoint((int) (dLat * 1E6), (int) (dLon * 1E6));

		// 添加覆盖物
		Drawable drawable = getResources().getDrawable(R.drawable.icon_overlay_nearby_center);
		DrawableOption option = new DrawableOption();
		option.setAnchor(0.5f, (54.0f - 9.0f) / 54.0f);
		mOverlay = new MarkerOverlay();
		mOverlay.setOption(option);
		mOverlay.setPosition(mGeoPoint);
		mOverlay.setIcon(drawable);
		mMapView.addOverlay(mOverlay);

		mMapView.getController().setMapBound(mGeoPoint, 11);
	}

	private void searchGeoDecode(GeoPoint geoPoint) {
		mGeoPoint = geoPoint;
		mTextTips.setText("开始获取地址 point =  " + mGeoPoint.toString());

		mOverlay.setPosition(mGeoPoint);
		mMapView.getController().setMapBound(mGeoPoint, 11);

		// 开始搜索
		if (mGeoDecode == null)
			mGeoDecode = new TGeoDecode(this);
		mGeoDecode.search(geoPoint);
	}

	@Override
	public void onGeoDecodeResult(TGeoAddress addr, int errCode) {
		if (errCode != TErrorCode.OK) {
			mTextTips.setText("获取地址失败 point =  " + mGeoPoint.toString() + ", error = " + errCode);
			return;
		}
		if (addr == null) {
			mTextTips.setText("获取地址失败 point =  " + mGeoPoint.toString());
			return;
		}
		mOverlay.setPosition(mGeoPoint);
		mMapView.getController().setMapBound(mGeoPoint, 11);

		// 提示
		String str = "point =  " + mGeoPoint.toString() + "\n";
		str += "最近的poi名称:" + addr.getPoiName() + "\n";
		str += "最近poi的方位:" + addr.getPoiDirection() + "\n";
		str += "最近poi的距离:" + addr.getPoiDistance() + "\n";
		str += "城市名称:" + addr.getCity() + "\n";
		str += "全称:" + addr.getFullName() + "\n";
		str += "最近的地址:" + addr.getAddress() + "\n";
		str += "最近地址的方位:" + addr.getAddrDirection() + "\n";
		str += "最近地址的距离:" + addr.getAddrDistance() + "\n";
		str += "最近的道路名称:" + addr.getRoadName() + "\n";
		str += "最近道路的距离:" + addr.getRoadDistance();
		mTextTips.setText(str);
	}

}
