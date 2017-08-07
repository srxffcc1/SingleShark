package com.tianditu.sdkDemo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianditu.android.maps.GeoBound;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.overlay.MarkerOverlay;
import com.tianditu.android.maps.overlay.MarkerOverlay.OnMarkerClickListener;
import com.tianditu.android.maps.overlay.MarkerOverlay.OnMarkerDragListener;
import com.tianditu.android.maps.overlay.PolylineOverlay;
import com.tianditu.android.maps.renderoption.DrawableOption;
import com.tianditu.android.maps.renderoption.LineOption;

public class MarkerOverlayDemo extends Activity implements OnMarkerClickListener,
		OnMarkerDragListener {

	private MapView mMapView = null;
	private TextView mTvTips = null;
	private CheckBox mCbDraggable = null;
	private ImageView mIvMarker = null;
	private MarkerOverlay mMarker[] = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.markeroverlaydemo);
		
		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setLogoPos(MapView.LOGO_RIGHT_TOP);
		mMapView.setBuiltInZoomControls(true);

		double dLon = 116.359291;
		double dLat = 39.892616;
		double dSpan = 0.025062;
		int resID[] = { R.drawable.poiresult,
				R.drawable.icon_overlay_nearby_center,
				R.drawable.icon_overlay_turn, R.drawable.icon_overlay_turn_sel,
				R.drawable.poiresult_sel };
		float anchor[][] = { { 0.5f, 1.0f }, { 0.5f, (54.0f - 9.0f) / 54.0f },
				{ 0.5f, 0.5f }, { 0.5f, 0.5f }, { 0.5f, 1.0f } };
		int size = resID.length;

		mMarker = new MarkerOverlay[size];
		for (int i = 0; i < size; ++i) {
			GeoPoint geoPoint = new GeoPoint((int) (dLat * 1E6), (int) ((dLon + dSpan * i) * 1E6));
			DrawableOption option = new DrawableOption();
			option.setAnchor(anchor[i][0], anchor[i][1]);
			if (i == 3)
				option.setRotate(270);

			mMarker[i] = new MarkerOverlay();
			mMarker[i].setOption(option);
			mMarker[i].setPosition(geoPoint);
			mMarker[i].setIcon(getResources().getDrawable(resID[i]));
			mMarker[i].setTitle("Marker" + (i + 1));
			mMarker[i].setClickListener(this);
			if (i == 4) {
				mMarker[i].setDraggable(true);
				mMarker[i].setDragListener(this);
			}
			mMapView.addOverlay(mMarker[i]);
		}

		// 添加水平参考线
		ArrayList<GeoPoint> points = new ArrayList<GeoPoint>();
		points.add(new GeoPoint((int) (dLat * 1E6), (int) ((dLon + dSpan * -1) * 1E6)));
		points.add(new GeoPoint((int) (dLat * 1E6), (int) ((dLon + dSpan * size) * 1E6)));
		
		LineOption option = new LineOption();
		option.setStrokeWidth((int) (1.0f * getResources().getDisplayMetrics().density));
		option.setDottedLine(true);
		option.setStrokeColor(0xAAFF0000);
		
		PolylineOverlay line = new PolylineOverlay();
		line.setOption(option);
		line.setPoints(points);
		mMapView.addOverlay(line, 0);

		GeoPoint geoPoint = new GeoPoint((int) ((dLat) * 1E6),
				(int) ((dLon + dSpan * size / 2) * 1E6));
		int span = Math.abs((int) (dSpan * 1E6 * size));
		GeoBound bound = new GeoBound(geoPoint, span, span);
		mMapView.getController().setMapBound(bound);

		mTvTips = (TextView) this.findViewById(R.id.textViewTips);
		mCbDraggable = (CheckBox) this.findViewById(R.id.draggable);
		mIvMarker = (ImageView) this.findViewById(R.id.imageViewMarker);
		updateTips(null);
	}

	@Override
	public boolean onMarkerClick(MarkerOverlay paramMarker) {
		updateTips(paramMarker);
		mCbDraggable.setChecked(paramMarker.isDraggable());
		mIvMarker.setImageDrawable(paramMarker.getIcon());
		return false;
	}

	/**
	 * 更新提示信息
	 */
	private void updateTips(MarkerOverlay marker) {
		StringBuffer tips = new StringBuffer("title = ");
		if (marker == null) {
			tips.append("xxxx\nlon = xxxx, lot = xxxx\nAnchor x = xx, y = xx\nrotote = xx");
		} else {
			tips.append(marker.getTitle());
			tips.append("\nlon = " + marker.getPosition().getLongitudeE6());
			tips.append(", lot = " + marker.getPosition().getLatitudeE6());
			tips.append("\nAnchor x = " + marker.getOption().getAnchorX());
			tips.append(", y = " + marker.getOption().getAnchorY());
			tips.append("\nrotote = " + marker.getOption().getRotate());
		}
		mTvTips.setText(tips.toString());
	}

	@Override
	public void onMarkerDragStart(MarkerOverlay paramMarker) {
	}

	@Override
	public void onMarkerDrag(MarkerOverlay paramMarker) {
	}

	@Override
	public void onMarkerDragEnd(MarkerOverlay paramMarker) {
	}

}
