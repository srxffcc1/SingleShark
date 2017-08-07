package com.tianditu.sdkDemo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import com.tianditu.android.maps.GeoBound;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.overlay.ArcOverlay;
import com.tianditu.android.maps.overlay.CircleArcOverlay;
import com.tianditu.android.maps.overlay.CircleOverlay;
import com.tianditu.android.maps.overlay.OvalOverlay;
import com.tianditu.android.maps.overlay.PolygonOverlay;
import com.tianditu.android.maps.overlay.PolylineOverlay;
import com.tianditu.android.maps.renderoption.ArcOption;
import com.tianditu.android.maps.renderoption.CircleArcOption;
import com.tianditu.android.maps.renderoption.CircleOption;
import com.tianditu.android.maps.renderoption.LineOption;
import com.tianditu.android.maps.renderoption.PlaneOption;

public class GeometryOverlayDemo extends Activity {

	final double OFFSET = 1.2;
	final double LON = 116.359291; // 起始经度
	final double LAT = 39.892616; // 起始纬度
	final double SPANLON = 0.02; // 经度跨度
	final double SPANLAT = 0.02; // 纬度跨度

	private MapView mMapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geometryoverlaydemo);

		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setLogoPos(MapView.LOGO_RIGHT_TOP);
		mMapView.setBuiltInZoomControls(true);

		double dLon = LON;
		double dLat = LAT;

		/** 折线 */
		addPolyline(dLon, dLat);
		dLat -= SPANLAT * OFFSET;

		/** 多边形 */
		addPolygon(dLon, dLat);
		dLat -= SPANLAT * OFFSET;

		/** 圆形 */
		addCircle(dLon, dLat);
		dLat -= SPANLAT * OFFSET;

		/** 圆弧线 */
		addCircleArc(dLon, dLat);
		dLat -= SPANLAT * OFFSET;

		/** 椭圆 */
		addOval(dLon, dLat);
		dLat -= SPANLAT * OFFSET;

		/** 椭圆弧线 */
		addOvalArc(dLon, dLat);
	}

	/** 折线 */
	void addPolyline(double dLon, double dLat) {
		for (int i = 0; i < 3; ++i) {
			ArrayList<GeoPoint> points = new ArrayList<GeoPoint>();
			points.add(new GeoPoint((int) (dLat * 1E6), (int) (dLon * 1E6)));
			points.add(new GeoPoint((int) (dLat * 1E6), (int) ((dLon + SPANLON) * 1E6)));
			points.add(new GeoPoint((int) ((dLat + SPANLAT) * 1E6), (int) ((dLon + SPANLON) * 1E6)));

			LineOption option = new LineOption();
			if (i == 0) {
				option.setStrokeWidth(5);
				option.setDottedLine(false);
				option.setStrokeColor(0xAA000000);
			} else if (i == 1) {
				option.setStrokeWidth(5);
				option.setDottedLine(true);
				option.setIntervals(new int[] { 15, 10, 15, 10 });
				option.setStrokeColor(0xAA000000);
			} else if (i == 2) {
				option.setStrokeWidth(5);
				option.setDottedLine(true);
				option.setIntervals(new int[] { 5, 2, 5, 2 });
				option.setStrokeColor(0xAA000000);
			}
			PolylineOverlay overlay = new PolylineOverlay();
			overlay.setOption(option);
			overlay.setPoints(points);
			mMapView.addOverlay(overlay);

			dLon += SPANLON * OFFSET;
		}
	}

	/** 多边形 */
	void addPolygon(double dLon, double dLat) {
		for (int i = 0; i < 3; ++i) {
			ArrayList<GeoPoint> points = new ArrayList<GeoPoint>();
			points.add(new GeoPoint((int) (dLat * 1E6), (int) (dLon * 1E6)));
			points.add(new GeoPoint((int) (dLat * 1E6), (int) ((dLon + SPANLON) * 1E6)));
			points.add(new GeoPoint((int) ((dLat + SPANLAT) * 1E6), (int) ((dLon + SPANLON) * 1E6)));
			points.add(new GeoPoint((int) ((dLat + SPANLAT) * 1E6), (int) (dLon * 1E6)));

			PlaneOption option = new PlaneOption();
			if (i == 0) {
				option.setFillColor(0xAAFF0000);
			} else if (i == 1) {
				option.setStrokeColor(0xFF000000);
				option.setStrokeWidth(5);
				option.setFillColor(0xAAFF0000);
			} else if (i == 2) {
				option.setStrokeColor(0xFF000000);
				option.setStrokeWidth(5);
				option.setDottedLine(true);
			}
			PolygonOverlay overlay = new PolygonOverlay();
			overlay.setOption(option);
			overlay.setPoints(points);
			mMapView.addOverlay(overlay);

			dLon += SPANLON * OFFSET;
		}
	}

	/** 圆形 */
	void addCircle(double dLon, double dLat) {
		for (int i = 0; i < 3; ++i) {
			GeoPoint point = new GeoPoint((int) ((dLat + SPANLAT / 2) * 1E6),
					(int) ((dLon + SPANLON / 2) * 1E6));

			CircleOption option = new CircleOption();
			if (i == 0) {
				option.setFillColor(0xAAFF0000);
			} else if (i == 1) {
				option.setFillColor(0xAAFF0000);
				option.setStrokeWidth(5);
				option.setStrokeColor(0xFF000000);
			} else if (i == 2) {
				option.setStrokeWidth(5);
				option.setStrokeColor(0xFF000000);
			}

			CircleOverlay overlay = new CircleOverlay();
			overlay.setOption(option);
			overlay.setCenter(point);
			overlay.setRadius(800 + 100 * i);
			mMapView.addOverlay(overlay);

			dLon += SPANLON * OFFSET;
		}
	}

	/** 椭圆 */
	void addCircleArc(double dLon, double dLat) {
		for (int i = 0; i < 3; ++i) {
			GeoPoint point = new GeoPoint((int) ((dLat + SPANLAT / 2) * 1E6),
					(int) ((dLon + SPANLON / 2) * 1E6));

			CircleArcOption option = new CircleArcOption();
			if (i == 0) {
				option.setAngle(0, 30);
				option.setStrokeColor(0xFF000000);
				option.setStrokeWidth(5);
			} else if (i == 1) {
				option.setAngle(0, 120);
				option.setStrokeColor(0xFF000000);
				option.setStrokeWidth(5);
			} else if (i == 2) {
				option.setAngle(0, 120);
				option.setStrokeColor(0xFF000000);
				option.setFillColor(0xFFFF0000);
				option.setUseCenter(true);
			}

			CircleArcOverlay overlay = new CircleArcOverlay();
			overlay.setOption(option);
			overlay.setCenter(point);
			overlay.setRadius(800 + 100 * i);
			mMapView.addOverlay(overlay);

			dLon += SPANLON * OFFSET;
		}
	}

	/** 圆弧线 */
	void addOval(double dLon, double dLat) {
		for (int i = 0; i < 3; ++i) {
			GeoPoint point = new GeoPoint((int) ((dLat + SPANLAT / 2) * 1E6),
					(int) ((dLon + SPANLON / 2) * 1E6));
			GeoBound bound = new GeoBound(point, (int) (SPANLAT * 1E6), (int) (SPANLON * 1E6));

			PlaneOption option = new PlaneOption();
			if (i == 0) {
				option.setFillColor(0xAAFF0000);
			} else if (i == 1) {
				option.setFillColor(0xAAFF0000);
				option.setStrokeWidth(5);
				option.setStrokeColor(0xFF000000);
			} else if (i == 2) {
				option.setStrokeWidth(5);
				option.setStrokeColor(0xFF000000);
			}

			OvalOverlay overlay = new OvalOverlay();
			overlay.setOption(option);
			overlay.setGeoBound(bound);
			mMapView.addOverlay(overlay);

			dLon += SPANLON * OFFSET;
		}
	}

	/** 椭圆弧线 */
	void addOvalArc(double dLon, double dLat) {
		for (int i = 0; i < 3; ++i) {
			GeoPoint point = new GeoPoint((int) ((dLat + SPANLAT / 2) * 1E6),
					(int) ((dLon + SPANLON / 2) * 1E6));
			GeoBound bound = new GeoBound(point, (int) (SPANLAT * 1E6), (int) (SPANLON * 1E6));

			ArcOption option = new ArcOption();
			if (i == 0) {
				option.setAngle(0, 30);
				option.setStrokeColor(0xFF000000);
				option.setStrokeWidth(5);
			} else if (i == 1) {
				option.setAngle(0, 120);
				option.setStrokeColor(0xFF000000);
				option.setStrokeWidth(5);
			} else if (i == 2) {
				option.setAngle(0, 120);
				option.setStrokeColor(0xFF000000);
				option.setFillColor(0xFFFF0000);
				option.setUseCenter(true);
			}

			ArcOverlay overlay = new ArcOverlay();
			overlay.setOption(option);
			overlay.setGeoBound(bound);
			mMapView.addOverlay(overlay);

			dLon += SPANLON * OFFSET;
		}
	}
}
