package com.tianditu.sdkDemo;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.TBusLineInfo;
import com.tianditu.android.maps.TBusStationInfo;
import com.tianditu.android.maps.TBusStationSearch;
import com.tianditu.android.maps.TBusStationSearch.OnStationResultListener;
import com.tianditu.android.maps.TErrorCode;
import com.tianditu.android.maps.overlay.MarkerOverlay;
import com.tianditu.android.maps.renderoption.DrawableOption;

/**
 * 代码示例-公交站点搜索.
 */
public class BusStationIDSearchDemo extends Activity implements OnStationResultListener {
	private static final int DIALOG_LINE_LIST = 1;

	private MapView mMapView;

	private EditText mEditID;
	private Button mBtnSearch;
	private Button mBtnList;
	private TextView mTextTips;

	private String mStationID;
	private TBusStationSearch mBusStationSearch;
	private TBusStationInfo mStationInfo;
	private MarkerOverlay mOverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busstationidsearchdemo);

		mMapView = (MapView) this.findViewById(R.id.mapview);
		mMapView.setLogoPos(MapView.LOGO_RIGHT_TOP);
		mMapView.setBuiltInZoomControls(true);

		// edit
		mEditID = (EditText) this.findViewById(R.id.editID);
		mEditID.setInputType(InputType.TYPE_CLASS_NUMBER);
		mEditID.setText("128081");

		// search
		mBtnSearch = (Button) findViewById(R.id.buttonSearch);
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				String strID = mEditID.getText().toString();
				searchBusStationID(strID);
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

	private void searchBusStationID(String strID) {
		mStationID = new String(strID);
		mTextTips.setText("开始搜索线路 id = " + strID);
		if (mOverlay != null) {
			mMapView.removeOverlay(mOverlay);
			mOverlay = null;
		}
		mStationInfo = null;
		mBtnList.setEnabled(false);

		// 开始搜索
		if (mBusStationSearch == null)
			mBusStationSearch = new TBusStationSearch(this);
		mBusStationSearch.search(strID);
	}

	/**
	 * 站点查询结果
	 */
	@Override
	public void onStationResult(TBusStationInfo info, int error) {
		mStationInfo = info;
		if (error != TErrorCode.OK) {
			mTextTips.setText("未找到公交站点 id = " + mStationID + ", error = " + error);
			return;
		}
		if (info == null) {
			mTextTips.setText("未找到公交站点 id = " + mStationID);
			return;
		}

		// 添加覆盖物
		Drawable drawable = getResources().getDrawable(R.drawable.icon_busroute_change_sub);
		DrawableOption option = new DrawableOption();
		option.setAnchor(0.5f, 0.5f);
		mOverlay = new MarkerOverlay();
		mOverlay.setOption(option);
		mOverlay.setPosition(info.getPoint());
		mOverlay.setIcon(drawable);
		mMapView.addOverlay(mOverlay);

		// 提示
		int lineSize = info.getBusLines() != null ? info.getBusLines().size() : 0;
		String tips = String.format(Locale.getDefault(),
				"找到公交站点 id = %s\nid = %s, name = %s, linesize = %s", mStationID, info.getId(),
				info.getName(), lineSize);
		mTextTips.setText(tips);
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
		ArrayList<TBusLineInfo> list = mStationInfo != null ? mStationInfo.getBusLines() : null;
		int size = list != null ? list.size() : 0;
		String[] items = new String[size];
		for (int i = 0; i < size; i++) {
			items[i] = list.get(i).getName() + ", id = " + list.get(i).getId();
		}

		String title = "站点 id = " + mStationID + "\n经过该站的公交线路";
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setItems(items, null);
		return builder.create();
	}
}
