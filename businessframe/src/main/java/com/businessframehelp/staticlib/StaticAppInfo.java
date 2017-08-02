package com.businessframehelp.staticlib;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;

import static com.kymjs.common.SystemTool.getBigSdCardPath;

/**
 * app信息记录类 用于初始化app数据 注意 init和 setUserentity
 * @author King2016s
 *
 */
public class StaticAppInfo implements IStaticAppInfo{
	public static final String projectdir="/.sharkZZ/";

	public static final String androiddir="/android/data/";
	private String mpkName;
	private String mversionName;
	private int mversionCode;

	private int px;
	private int py;
	private StaticMode mode;
	private Context mcontext;
	private int privatekey = 2;//控制默认的app写入路劲 1 为 app文件内部 2为sd卡
	private Object userentity;
	public void setUserentity(Object userentity){
		this.userentity=userentity;
	}
	public static final StaticAppInfo instance=new StaticAppInfo();
	private StaticAppInfo(){

	}
	public static StaticAppInfo getInstance(){
		return instance;
	}
	public void init(Context context,StaticMode mode){
		this.mode=mode;
		mcontext=context;
		Point point = new Point();
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		manager.getDefaultDisplay().getRealSize(point);
		manager.getDefaultDisplay().getMetrics(dm);
//		double dpi = dm.densityDpi;
//		double x = Math.pow(point.x / dm.xdpi, 2);
//		double y = Math.pow(point.y / dm.ydpi, 2);
//		double screenInches = Math.sqrt(x + y);
		 px = point.x;
		 py = point.y;
		try {
			mpkName = mcontext.getPackageName();
			mversionName = mcontext.getPackageManager().getPackageInfo(mpkName, 0).versionName;
			mversionCode = mcontext.getPackageManager().getPackageInfo(mpkName, 0).versionCode;
		} catch (Exception e) {

		}
	}
	@Override
	public String getProjcetDir() {
		String path="";
		SharedPreferences sd=mcontext.getSharedPreferences("config", Context.MODE_PRIVATE);
		path=sd.getString("projectpath", "");
		if(!"".equals(path)){
			if(new File(path+"/").exists()){//路径存在
				return path;
			}else{
				sd.edit().putString("projectpath","");//说明路径不存在 可能存在换了sd卡了
				return getProjcetDir();
			}

		}
		switch (privatekey) {
			case 1:
				path = mcontext.getFilesDir().toString()+projectdir+"/";
				break;
			case 2:
				path=getBigSdCardPath(mcontext)+androiddir+mpkName+projectdir+"/";
				//System.out.println("需要构造的存储目录"+path);
				File tmp=new File(path);
				tmp.mkdirs();
				if(tmp.exists()){

				}else{//说明又不存在
					path=getBigSdCardPath(mcontext,tmp);//那就换个大容量地址存储了
				}
				break;

			default:
				break;
		}
		return path;
	}

	@Override
	public double getScreenWidth() {
		return px;
	}

	@Override
	public double getScreenHeight() {
		return py;
	}

	@Override
	public Object getUserEntity() {
		return userentity;
	}

	@Override
	public String getIpHead() {
		return mcontext.getSharedPreferences("user",Context.MODE_PRIVATE).getString("ip","");
	}

	@Override
	public Context getAppLicationContext() {
		return mcontext;
	}

}
interface IStaticAppInfo{
	String getProjcetDir();
	double getScreenWidth();
	double getScreenHeight();
	Object getUserEntity();
	String getIpHead();
	Context getAppLicationContext();
}
 enum  StaticMode {
	demo, test, online
}