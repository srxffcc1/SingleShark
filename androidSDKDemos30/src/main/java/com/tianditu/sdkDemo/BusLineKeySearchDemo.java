package com.tianditu.sdkDemo;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.TBusLineInfo;
import com.tianditu.android.maps.TBusLineSearch;
import com.tianditu.android.maps.TBusLineSearch.OnBusLineResultListener;
import com.tianditu.android.maps.TErrorCode;

/**
 * 代码示例-公交线路搜索.
 */
public class BusLineKeySearchDemo extends Activity implements OnBusLineResultListener {
	private static final int DIALOG_LINE_LIST = 1;

	private MapView mMapView;

	private EditText mEditKey;
	private Button mBtnSearch;
	private Button mBtnList;
	private TextView mTextTips;

	private String mLineKey;
	private TBusLineSearch mBusLineSearch;
	private ArrayList<TBusLineInfo> mLines;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buslinekeysearchdemo);

		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setLogoPos(MapView.LOGO_RIGHT_TOP);
		mMapView.setBuiltInZoomControls(true);

		// edit
		mEditKey = (EditText) findViewById(R.id.editKey);
		mEditKey.setText("14路");

		// search
		mBtnSearch = (Button) findViewById(R.id.buttonSearch);
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				String key = mEditKey.getText().toString();
				searchBusLineID(key);
			}

		};
		mBtnSearch.setOnClickListener(listener);

		// list
		mBtnList = (Button) findViewById(R.id.buttonList);
		listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				removeDialog(DIALOG_LINE_LIST);
				showDialog(DIALOG_LINE_LIST);
			}

		};
		mBtnList.setOnClickListener(listener);
		mBtnList.setEnabled(false);

		// tips
		mTextTips = (TextView) findViewById(R.id.textViewTips);
		mTextTips.setText("");
	}

	private void searchBusLineID(String key) {
		mLineKey = new String(key);
		mTextTips.setText("开始搜索线路 id = " + key);
		mLines = null;
		mBtnList.setEnabled(false);

		// 开始搜索
		if (mBusLineSearch == null)
			mBusLineSearch = new TBusLineSearch(this, mMapView);
		mBusLineSearch.search(mLineKey);
	}

	@Override
	public void onBusLineResult(ArrayList<TBusLineInfo> lines, int error) {
		mLines = lines;
		if (error != TErrorCode.OK) {
			mTextTips.setText("未找到线路 id = " + mLineKey + ", error = " + error);
			return;
		}
		int size = lines != null ? lines.size() : 0;
		if (size == 0) {
			mTextTips.setText("未找到线路 id = " + mLineKey);
			return;
		}
		mTextTips.setText("找到线路 id = " + mLineKey + ", size = " + size);

		removeDialog(DIALOG_LINE_LIST);
		showDialog(DIALOG_LINE_LIST);
		mBtnList.setEnabled(true);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_LINE_LIST:
			return showLineList();
		default:
			break;
		}
		return null;
	}

	private Dialog showLineList() {
		ArrayList<TBusLineInfo> list = mLines;
		int size = list != null ? list.size() : 0;
		String[] items = new String[size];
		for (int i = 0; i < size; i++) {
			items[i] = list.get(i).getName() + ", id = " + list.get(i).getId();
		}

		String title = "线路 id = " + mLineKey;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setItems(items, null);
		return builder.create();
	}
}
