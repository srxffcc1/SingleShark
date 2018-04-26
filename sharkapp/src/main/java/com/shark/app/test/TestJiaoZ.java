package com.shark.app.test;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.businessframehelp.widget.CustomMediaPlayer.JZMediaIjkplayer;
import com.businessframehelp.widget.CustomView.MyJZVideoPlayerStandard;
import com.shark.app.R;

import java.io.File;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class TestJiaoZ  extends FrameActivity {

    MyJZVideoPlayerStandard myJZVideoPlayerStandard;
    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public int getMenuid() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_jiaozi);
        JZVideoPlayer.setMediaInterface(new JZMediaIjkplayer());
        myJZVideoPlayerStandard = findViewById(R.id.jz_video);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myJZVideoPlayerStandard.setUp(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"testcompany.mp4")).toString()
                        , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "点位视频");
                myJZVideoPlayerStandard.startVideo();
            }
        },300);


    }

    public void init(){

    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_searchs){
//            //System.out.println("点击了综合查询");
        }
        return super.onOptionsItemSelected(item);
    }

}
