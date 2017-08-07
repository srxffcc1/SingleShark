package com.tianditu.sdkDemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.TMapLayerManager;

public class MapCustomSourceDemo extends Activity {
	private MapView mMapView;
	private TMapLayerManager mLayerMnger;
	private RadioGroup mRadioGroup;
	private TextView mTextViewTips;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maplayerdemo);

		// 地图视图
		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setLogoPos(MapView.LOGO_LEFT_TOP);
		mMapView.setBuiltInZoomControls(true);

		// 设置地图服务端数据源网址，
		mMapView.setCustomTileService("http://t1.tianditu.cn/DataServer?");

		// 图层管理类
		mLayerMnger = new TMapLayerManager(mMapView);

		// 地图类型
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		int mapType = mMapView.getMapType();
		if (mapType == MapView.TMapType.MAP_TYPE_VEC)
			mRadioGroup.check(R.id.radio0);
		else if (mapType == MapView.TMapType.MAP_TYPE_IMG)
			mRadioGroup.check(R.id.radio1);
		else if (mapType == MapView.TMapType.MAP_TYPE_TERRAIN)
			mRadioGroup.check(R.id.radio2);

		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radio0)
					mMapView.setMapType(MapView.TMapType.MAP_TYPE_VEC);
				else if (checkedId == R.id.radio1)
					mMapView.setMapType(MapView.TMapType.MAP_TYPE_IMG);
				else if (checkedId == R.id.radio2)
					mMapView.setMapType(MapView.TMapType.MAP_TYPE_TERRAIN);

				updateTips();
			}

		});

	}

	/**
	 * 显示图层列表
	 */
	void showMapLayerList() {
		int mapType = mMapView.getMapType();
		String title = mLayerMnger.getMapName(mapType);
		final String[] allLayers = mLayerMnger.getLayers(mapType);
		final String[] showLayers = mLayerMnger.getLayersShow();
		final int size = allLayers.length;

		// 开关状态
		final boolean[] checkedItems = new boolean[size];
		List<String> list = Arrays.asList(showLayers);
		for (int i = 0; i < size; ++i) {
			if (list.contains(allLayers[i]))
				checkedItems[i] = true;
			else
				checkedItems[i] = false;
		}

		OnMultiChoiceClickListener choiceListener = new OnMultiChoiceClickListener() {
			public void onClick(DialogInterface dialog, int whichButton,
					boolean isChecked) {
				// 设置开关状态
				checkedItems[whichButton] = isChecked;
			}
		};

		DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// 设置可见图层
				List<String> list = Arrays.asList(showLayers);
				ArrayList<String> showList = new ArrayList<String>(list);
				for (int i = size - 1; i >= 0; --i) {
					String item = allLayers[i];
					if (checkedItems[i] && !showList.contains(item))
						showList.add(item);
					else if (!checkedItems[i] && showList.contains(item))
						showList.remove(item);
				}
				String[] showLayers = showList.toArray(new String[showList
						.size()]);
				mLayerMnger.setLayersShow(showLayers);
				updateTips();
			}
		};

		DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		};

		// 创建对话框
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMultiChoiceItems(allLayers, checkedItems, choiceListener);
		builder.setPositiveButton("确定", okListener);
		builder.setNegativeButton("取消", cancelListener);
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	/**
	 * 更新提示信息
	 */
	void updateTips() {
		String[] allLayers = mLayerMnger.getLayers(mMapView.getMapType());
		String tips = "包含图层：";
		for (String layer : allLayers)
			tips += layer + " ";

		String[] showLayers = mLayerMnger.getLayersShow();
		tips += "\n可见图层：";
		for (String layer : showLayers)
			tips += layer + " ";

		mTextViewTips.setText(tips);
	}
}
