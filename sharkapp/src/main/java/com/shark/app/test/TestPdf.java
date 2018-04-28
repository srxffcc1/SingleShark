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
import com.shark.pdfedit.fragment.BookFragment;
import com.wisdomregulation.data.entitybase.Base_Entity;
import com.wisdomregulation.data.entitybook2017.Entity_Book_2017_0;
import com.wisdomregulation.data.entitybook2017.Entity_Book_2017_2;
import com.wisdomregulation.data.entitybook2017.Entity_Book_2017_4;

import java.io.File;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class TestPdf extends FrameActivity {


    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public boolean needActionBar() {
        return false;
    }

    @Override
    public int getMenuid() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_pdf);
        Base_Entity base_entity=new Entity_Book_2017_2();
        BookFragment bookFragment = BookFragment.getInstance2017(base_entity,BookFragment.TYPE_ADD);
        getFragmentManager().beginTransaction().replace(R.id.needp, bookFragment).commitAllowingStateLoss();

    }

    public void init(){

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_searchs){
//            //System.out.println("点击了综合查询");
        }
        return super.onOptionsItemSelected(item);
    }

}
