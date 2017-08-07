package com.tianditu.sdkDemo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import com.tianditu.android.maps.GeoBound;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.overlay.PolylineOverlay;
import com.tianditu.android.maps.overlay.TextOverlay;
import com.tianditu.android.maps.renderoption.FontOption;
import com.tianditu.android.maps.renderoption.LineOption;

public class TextOverlayDemo extends Activity {
	private MapView mMapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textoverlaydemo);

		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setLogoPos(MapView.LOGO_RIGHT_TOP);
		mMapView.setBuiltInZoomControls(true);

		int size = 4;
		FontOption option[] = new FontOption[size];
		double dLon = 116.359291;
		double dLat = 39.892616;
		double dSpan = -0.025062;

		float density = getResources().getDisplayMetrics().density;
		option[0] = new FontOption();
		option[0].setAlign(FontOption.ALIGN_CENTER_HORIZONTAL,
				FontOption.ALIGN_CENTER_VERTICAL);
		option[0].setFillColor(0xFFFFFF00);
		option[0].setFontColor(0xFF000000);
		option[0].setFontSize((int) (14 * density));
		option[0].setStrokeWidth((int) (1 * density));
		option[0].setStrokeColor(0xFFFFFFFF);

		option[1] = new FontOption();
		option[1].setAlign(FontOption.ALIGN_RIGHT,
				FontOption.ALIGN_CENTER_VERTICAL);
		option[1].setFontColor(0xFFFF0000);
		option[1].setFontSize((int) (16 * density));
		option[1].setStrokeWidth((int) (2 * density));
		option[1].setStrokeColor(0xFFFFFFFF);

		option[2] = new FontOption();
		option[2].setAlign(FontOption.ALIGN_LEFT,
				FontOption.ALIGN_CENTER_VERTICAL);
		option[2].setFontColor(0xFF68360D);
		option[2].setFontSize((int) (18 * density));
		option[2].setStrokeWidth((int) (2 * density));
		option[2].setStrokeColor(0xFFFFFFE6);

		option[3] = new FontOption();
		option[3].setAlign(FontOption.ALIGN_CENTER_HORIZONTAL,
				FontOption.ALIGN_CENTER_VERTICAL);
		option[3].setFontColor(0xFF6B00E0);
		option[3].setFontSize((int) (20 * density));
		option[3].setTextRotate(30);

		TextOverlay[] overlays = new TextOverlay[size];
		for (int i = 0; i < size; ++i) {
			GeoPoint geoPoint = new GeoPoint((int) ((dLat + dSpan * i) * 1E6),
					(int) ((dLon) * 1E6));
			overlays[i] = new TextOverlay();
			overlays[i].setOption(option[i]);
			overlays[i].setGeoPoint(geoPoint);
			overlays[i].setText("文本TextOverlay" + (i + 1));
			mMapView.addOverlay(overlays[i]);
		}

		// 添加垂直参考线
		ArrayList<GeoPoint> points = new ArrayList<GeoPoint>();
		points.add(new GeoPoint((int) ((dLat + dSpan * -1) * 1E6),
				(int) ((dLon) * 1E6)));
		points.add(new GeoPoint((int) ((dLat + dSpan * size) * 1E6),
				(int) ((dLon) * 1E6)));

		LineOption lineOption = new LineOption();
		lineOption.setStrokeWidth((int) (1.0f * getResources()
				.getDisplayMetrics().density));
		lineOption.setDottedLine(true);
		lineOption.setStrokeColor(0xAAFF0000);

		PolylineOverlay line = new PolylineOverlay();
		line.setOption(lineOption);
		line.setPoints(points);
		mMapView.addOverlay(line, 0);

		GeoPoint geoPoint = new GeoPoint(
				(int) ((dLat + dSpan * size / 2) * 1E6), (int) ((dLon) * 1E6));
		int span = Math.abs((int) (dSpan * 1E6 * size));
		GeoBound bound = new GeoBound(geoPoint, span, span);
		// mMapView.getController().setMapBound(bound);
		mMapView.getController().setCenter(
				new GeoPoint((int) (dLat * 1000000), (int) (dLon * 1000000)));
	}

}
