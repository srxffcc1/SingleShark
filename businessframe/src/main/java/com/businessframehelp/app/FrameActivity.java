package com.businessframehelp.app;

import android.app.Fragment;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.businessframehelp.enums.ORIENTATION;
import com.businessframehelp.inter.IFrameActivity;
import com.businessframehelp.listen.IBroadCastListener;
import com.kymjs.common.NetworkUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.util.UUID;

import butterknife.ButterKnife;



/**
 * Created by Administrator on 2017/5/10.
 */

public abstract class FrameActivity extends AutoLayoutActivity implements IFrameActivity,IBroadCastListener {
    private LocalActivityManager mLocalActivityManager;
    private boolean cantoast=true;
    private boolean isTabChild=false;
    private KJHttp kjHttp;
    private ORIENTATION newstatus;
    private ORIENTATION nowstatus;
    private boolean isauthentic=false;
    private  Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            FrameActivity.this.handleMessage(msg);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

    }
    public void setNewstatus(ORIENTATION newstatus) {
        this.newstatus = newstatus;
        Log.v("SRXCREATE",""+this.newstatus);
        changeOrientation(newstatus);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mLocalActivityManager = new LocalActivityManager(this, true);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        BroadCastManager.getInstance().registerListtener(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        try {
//            getSupportActionBar().hide();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        super.onCreate(savedInstanceState);
        kjHttp = new KJHttp();
        if(savedInstanceState!=null&&savedInstanceState.getSerializable("newstatus")!=null){
            newstatus= (ORIENTATION) savedInstanceState.getSerializable("newstatus");
        }
        if(savedInstanceState!=null&&savedInstanceState.getSerializable("nowstatus")!=null){
            nowstatus= (ORIENTATION) savedInstanceState.getSerializable("nowstatus");
        }
        Log.v("SRXCREATE",""+newstatus);
        if(newstatus!=null){
            changeOrientation(newstatus);
        }else{
            changeOrientation(getORIENTATION());
        }
        isTabChild = this.getIntent().getBooleanExtra("isTabChild", false);// 判断是否属于内部activity
    }
    public void changeOrientation(ORIENTATION status){
        if(status!=null&&status==nowstatus){
            
        }else{
            if (status== ORIENTATION.PORTRAIT||status==null) {
                nowstatus=ORIENTATION.PORTRAIT;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                nowstatus=ORIENTATION.LANDSCAPE;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } 
        }
        
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        isauthentic=false;
        super.onResume();
        mLocalActivityManager.dispatchResume();// mLocalActivityManager为将activity抽取成view做准备
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if(!isauthentic){
            //do 可能被弹窗拦截了
        }
        mLocalActivityManager.dispatchPause(isFinishing());// mLocalActivityManager为将activity抽取成view做准备
    }
    @Override
    protected void onSaveInstanceState(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(arg0);
        arg0.putSerializable("newstatus",newstatus);
        arg0.putSerializable("nowstatus",nowstatus);
        mLocalActivityManager.saveInstanceState();
    }
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mLocalActivityManager.dispatchStop();
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        handler=null;
        BroadCastManager.getInstance().unRegisterListener(this);
        super.onDestroy();
        try {
            mLocalActivityManager.dispatchDestroy(isFinishing());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        kjHttp=null;
    }
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this) ;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this) ;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this) ;
    }
    @Override
    public View getIntentContentView(
            ViewGroup tabMianContent, String mTag, Intent mIntent) {
        if (mLocalActivityManager == null) {
            throw new IllegalStateException(
                    "Did you forget to call 'public void setup(LocalActivityManager activityGroup)'?");
        }
        mIntent.putExtra("isTabChild", true);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        String tags= UUID.randomUUID().toString().replaceAll("-", "");
        final Window w = mLocalActivityManager.startActivity(tags, mIntent);
        final View wd = w != null ? w.getDecorView() : null;
        if (wd.getParent() != null) {
            ((ViewGroup) wd.getParent()).removeView(wd);
        }
        return wd;
    }
    public void toastShow(String contecnt){
        Toast roast=Toast.makeText(this, contecnt, Toast.LENGTH_SHORT);
        if(cantoast){
            cantoast=false;
            roast.show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    cantoast=true;
                }
            },500);
        }
    }
    abstract public void handleMessage(Message msg);
    final public boolean checkNet(){
        return NetworkUtils.checkNet(this);
    }
    public void httpPost(String url, HttpParams httpParams, HttpCallBack httpCallBack){
        kjHttp.post(url,httpParams,httpCallBack);
    }
    public void sendMessage(Message message){
            handler.sendMessage(message);

    }
    public void sendEmptyMessage(int what){
            handler.sendEmptyMessage(what);

    }
    public void sendEmptyMessageAtTime(int what, long uptimeMillis){
            handler.sendEmptyMessageAtTime(what,uptimeMillis);

    }
    public void sendEmptyMessageDelayed(int what, long delayMillis){
            handler.sendEmptyMessageDelayed(what,delayMillis);

    }
    public void sendMessageAtTime(Message msg, long uptimeMillis){
            handler.sendMessageAtTime(msg,uptimeMillis);

    }

    @Override
    public void startActivity(Intent intent) {
        isauthentic=true;
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        isauthentic=true;
        super.startActivityForResult(intent, requestCode);
    }

    /**
     * 应用内发广播
     * @param intent
     */
    public void sendFrameBroadCast(Intent intent){
        BroadCastManager.getInstance().sendBroadCast(intent);
    }

    /**
     * 应用内接收广播
     * @param action
     */
    public void reciverFrameBroadCast(Intent action){

    }

    @Override
    public void onBackPressed() {
        isauthentic=true;
        super.onBackPressed();
        newstatus=getORIENTATION();
        changeOrientation(getORIENTATION());
    }
    /**
     * 封装两个打开fragment的简单方法
     * @param fragment
     * @param requestCode
     */
    public void startFragmentForResult(android.support.v4.app.Fragment fragment, int requestCode){
        isauthentic=true;
        Bundle fragmentb=new Bundle();
        fragmentb.putInt("requestCode",requestCode);
        fragment.setArguments(fragmentb);
        getSupportFragmentManager().beginTransaction().addToBackStack("null").add(android.R.id.content,fragment,fragment.getClass().getSimpleName()).commit();
    }
    /**
     * 封装两个打开fragment的简单方法
     * @param fragment
     * @param requestCode
     */
    public void startFragmentForResult(Fragment fragment, int requestCode){
        isauthentic=true;
        Bundle fragmentb=new Bundle();
        fragmentb.putInt("requestCode",requestCode);
        fragment.setArguments(fragmentb);
        getFragmentManager().beginTransaction().addToBackStack("null").add(android.R.id.content,fragment,fragment.getClass().getSimpleName()).commit();
    }
    /**
     * 封装两个打开fragment的简单方法
     * @param fragment
     * @param requestCode
     */
    public void startFragmentForResult(BaseFragment fragment,int requestCode){
        Bundle fragmentb=new Bundle();
        fragmentb.putInt("requestCode",requestCode);
        fragment.setArguments(fragmentb);
        getFragmentManager().beginTransaction().addToBackStack("null").add(android.R.id.content,fragment,fragment.getClass().getSimpleName()).commit();
    }

    /**
     * 封装两个打开fragment的简单方法
     * @param fragment
     * @param requestCode
     */
    public void startFragmentForResult(BaseSupportFragment fragment,int requestCode){
        Bundle fragmentb=new Bundle();
        fragmentb.putInt("requestCode",requestCode);
        fragment.setArguments(fragmentb);
        getSupportFragmentManager().beginTransaction().addToBackStack("null").add(android.R.id.content,fragment,fragment.getClass().getSimpleName()).commit();
    }
    public void startThread(Runnable runnable){//使用一个随这个当前activity死亡就自动结束的线程

    }
}
