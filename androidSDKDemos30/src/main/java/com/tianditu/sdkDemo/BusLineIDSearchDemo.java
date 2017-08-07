package com.tianditu.sdkDemo;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.TBusLineInfo;
import com.tianditu.android.maps.TBusLineSearch;
import com.tianditu.android.maps.TBusLineSearch.OnBusLineResultListener;
import com.tianditu.android.maps.TBusStationInfo;
import com.tianditu.android.maps.TErrorCode;
import com.tianditu.android.maps.overlayutil.BusLineOverlay;

/**
 * 代码示例-公交线路搜索.
 */
public class BusLineIDSearchDemo extends Activity implements OnBusLineResultListener {
	private static final int DIALOG_STATION_LIST = 1;

	private MapView mMapView;

	private EditText mEditID;
	private Button mBtnSearch;
	private Button mBtnList;
	private TextView mTextTips;

	private String mLineID;
	private TBusLineSearch mBusLineSearch;
	private TBusLineInfo mLine;
	private BusLineOverlay mOverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buslineidsearchdemo);

		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setLogoPos(MapView.LOGO_RIGHT_TOP);
		mMapView.setBuiltInZoomControls(true);

		// edit
		mEditID = (EditText) findViewById(R.id.editID);
		mEditID.setInputType(InputType.TYPE_CLASS_NUMBER);
		mEditID.setText("22141");

		// search
		mBtnSearch = (Button) findViewById(R.id.buttonSearch);
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				String strID = mEditID.getText().toString();
				searchBusLineID(strID);
			}

		};
		mBtnSearch.setOnClickListener(listener);

		// list
		mBtnList = (Button) findViewById(R.id.buttonList);
		listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				removeDialog(DIALOG_STATION_LIST);
				showDialog(DIALOG_STATION_LIST);
			}

		};
		mBtnList.setOnClickListener(listener);
		mBtnList.setEnabled(false);

		// tips
		mTextTips = (TextView) findViewById(R.id.textViewTips);
		mTextTips.setText("");
	}

	private void searchBusLineID(String strID) {
		mLineID = new String(strID);
		mTextTips.setText("开始搜索线路 id = " + strID);
		if (mOverlay != null) {
			mMapView.removeOverlay(mOverlay);
			mOverlay = null;
		}
		mLine = null;
		mBtnList.setEnabled(false);

		// 开始搜索
		try {
			int id = Integer.parseInt(strID);
			if (mBusLineSearch == null)
				mBusLineSearch = new TBusLineSearch(this, mMapView);
			mBusLineSearch.search(id);
		} catch (NumberFormatException e) {
			mTextTips.setText("解析线路ID失败 id = " + strID);
			e.printStackTrace();
		}
	}

	@Override
	public void onBusLineResult(ArrayList<TBusLineInfo> lines, int error) {
		if (error != TErrorCode.OK) {
			mTextTips.setText("未找到线路 id = " + mLineID + ", error = " + error);
			return;
		}
		int size = lines != null ? lines.size() : 0;
		if (size == 0) {
			mTextTips.setText("未找到线路 id = " + mLineID);
			return;
		}
		mLine = lines.get(0);

		// 添加覆盖物
		mOverlay = new BusLineOverlay(this);
		mOverlay.setData(mLine);
		mMapView.addOverlay(mOverlay);
		mBtnList.setEnabled(false);

		// 提示
		mTextTips.setText("找到线路 id = " + mLineID + ", size = " + size);
		mBtnList.setEnabled(true);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_STATION_LIST:
			return showStationList();
		default:
			break;
		}
		return null;
	}

	private int mPosition = -1;

	public Dialog showStationList() {
		mPosition = mOverlay != null ? mOverlay.getFocusID() : -1;

		ArrayList<TBusStationInfo> list = mLine != null ? mLine.getStations() : null;
		final int size = list != null ? list.size() : 0;
		String[] items = new String[size];
		for (int i = 0; i < size; i++) {
			items[i] = list.get(i).getName() + ", id = " + list.get(i).getId();
		}

		String title = mLine != null ? mLine.getName() : null;
		DialogInterface.OnClickListener listenerList = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				mPosition = which;
			}
		};

		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (mPosition >= 0 && mPosition < size) {
					TBusStationInfo info = mLine.getStations().get(mPosition);
					mMapView.getController().setCenter(info.getPoint());
				}
			}
		};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setSingleChoiceItems(items, mPosition, listenerList);
		builder.setPositiveButton("确定", listener);
		return builder.create();
	}

}
