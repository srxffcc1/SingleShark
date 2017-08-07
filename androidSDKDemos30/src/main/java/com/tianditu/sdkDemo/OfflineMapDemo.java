package com.tianditu.sdkDemo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MapView.TMapType;
import com.tianditu.android.maps.TErrorCode;
import com.tianditu.android.maps.TOfflineMapInfo;
import com.tianditu.android.maps.TOfflineMapManager;
import com.tianditu.android.maps.TOfflineMapManager.City;
import com.tianditu.android.maps.TOfflineMapManager.MapAdminSet;
import com.tianditu.android.maps.TOfflineMapManager.OnGetMapsResult;

public class OfflineMapDemo extends Activity implements OnGetMapsResult{
	private MapView        mMapView        = null;
	private TOfflineMapManager offlineMapMng= null;
	private ProgressBar    mPb             = null;
	private Handler        mUpdateHandler  = null;
	private ProgressDialog mDlg            = null;
	private MapController  mController     = null;
	private ArrayList<MapAdminSet>  mAllMaps = null; //所有的地图相关数据
	private Timer mTimer = null;
	private TimerTask mTimerTask = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offlinemapdemo);
		offlineMapMng = new TOfflineMapManager(this, this);
		offlineMapMng.setMapPath("/sdcard/tianditu/staticmap/");
		mMapView = (MapView)findViewById(R.id.offlinemap_mapview);
		mMapView.setCachePath("/sdcard/tianditu/map/");
		mController = mMapView.getController();
		mController.setZoom(10);
		mController.animateTo(new GeoPoint(39665124,116065124));
		offlineMapMng.getMapList();
//		mMapView.setOfflineMaps(offlineMapMng.searchLocalMaps());
		
		final EditText etCity = (EditText)findViewById(R.id.offlinemap_et_city);
		Button   btn = (Button)findViewById(R.id.offlinemap_btn_start);
		
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				mPb.setVisibility(View.VISIBLE);
				offlineMapMng.startDownload(etCity.getText().toString(), TMapType.MAP_TYPE_VEC);
				mTimer = new Timer();
				mTimerTask = new TimerTask(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message msg = mUpdateHandler.obtainMessage();
						msg.what = 1;
						mUpdateHandler.dispatchMessage(msg);
					}};
				mTimer.schedule(mTimerTask, 0, 1000);
			}
		});
		
		btn = (Button)findViewById(R.id.offlinemap_btn_pause);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				offlineMapMng.pauseDownload();
				if(mTimerTask != null)
					mTimerTask.cancel();
				mTimerTask = null;
				if(mTimer != null)
					mTimer.cancel();
				mTimer = null;
				ArrayList<TOfflineMapInfo> infos = offlineMapMng.getPausedMaps();
				if(infos == null){
					return;
				}
				
			}
			
		});
		
		mUpdateHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch(msg.what)
				{
				case 1:
					TOfflineMapInfo info = offlineMapMng.getDownloadInfo(etCity.getText().toString(), TMapType.MAP_TYPE_VEC);
					if(info != null)
						mPb.setProgress(info.getDownloadedSize()*100/info.getSize());
					if(info == null || info.getState() != TOfflineMapManager.OFFLINEMAP_DOWNLOADING)
					{
						return;
					}
					
					//处理下载完成
					if(info.getState() == TOfflineMapManager.OFFLINEMAP_DOWNLOAD_FINISHED)
					{
			//			mMapView.setOfflineMaps(offlineMapMng.searchLocalMaps());
					}
					break;
					
				}
				
			}
			
		};
		mPb = (ProgressBar)findViewById(R.id.offlinemap_progress);
				
		mDlg = new ProgressDialog( this);
		mDlg.setTitle( "天地图" );
		mDlg.setProgressStyle( ProgressDialog.STYLE_SPINNER );
		mDlg.setMessage( "正在初始化..." );
		mDlg.setCanceledOnTouchOutside( false );
		mDlg.setCancelable( true );
		mDlg.show();
		if(mAllMaps == null)
			offlineMapMng.getMapList();		
		
	}

	@Override
	public void onGetResult(ArrayList<MapAdminSet> maps, int error) {
		// TODO Auto-generated method stub
		mDlg.dismiss();
		if(error != TErrorCode.OK)
			return;
		mAllMaps = maps;
		int size = maps.size();
		String str = "";
		for(int i = 0;i < size;i++){
			MapAdminSet adminSet = maps.get(i);
			str += adminSet.getName();
			ArrayList<City> citys = adminSet.getCitys();
			str += "城市:";
			for(int k=0; k<citys.size(); k++)
			{
				str += citys.get(k).getName() + " ";
			}
			str += "\n";
		}
		Toast.makeText(OfflineMapDemo.this, str, Toast.LENGTH_SHORT).show();
	}
	
}
