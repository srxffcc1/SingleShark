package com.shark.app.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.View;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.businessframehelp.listen.StyleDialogListener;
import com.shark.app.singleactivity.ActivityPdfBook;
import com.businessframehelp.utils.FrameUtil;
import com.businessframehelp.utils.ZipUtil;
import com.hss01248.dialog.StyledDialog;
import com.hzy.archiver.IArchiverListener;
import com.shark.app.R;

public class TestMainActivity extends FrameActivity {

    public int index = 1;


    @Override
    public ORIENTATION getORIENTATION() {
        return ORIENTATION.PORTRAIT;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO:OnCreate Method has been created, run ButterKnife again to generate code
        setContentView(R.layout.test_main);

    }
    public void startRtsp(View view){
//        startFragmentForResult(new vlc.video.VideoFragment(),100);
    }
    public void startPlugin(View view){

        startFragmentForResult(new ApkFragment(),100);
    }
    @Override
    public void sendFrameBroadCast(Intent intent) {
        super.sendFrameBroadCast(intent);
    }

    @Override
    public void reciverFrameBroadCast(Intent action) {
        super.reciverFrameBroadCast(action);
    }

    @Override
    public void handleMessage(Message msg) {

    }

    public void startCamera(View view) {
        FrameUtil.instance().startCamera(this, 100);
    }

    public void startVideo(View view) {
        FrameUtil.instance().startVideo(this, 100);
    }

    public void startRecord(View view) {
        FrameUtil.instance().startRecord(this, 101, Environment.getExternalStorageDirectory() + "/shark.mp3");
    }

    public void startCameraIntent(View view) {
        FrameUtil.instance().startCameraIntent(this, 100, Environment.getExternalStorageDirectory() + "/shark.jpg");
    }

    public void startVideoIntent(View view) {
        FrameUtil.instance().startVideoIntent(this, 100, Environment.getExternalStorageDirectory() + "/shark.mp4");
    }

    public void startRecordIntent(View view) {
        FrameUtil.instance().startRecordIntent(this, 101);
    }

    public void startTime(View view) {
        FrameUtil.instance().startTimePicker(this, null);
    }

    public void addBadge(View view) {
        FrameUtil.instance().simpleBadgeView(this, view, index++);
    }

    public void startFileChoser(View view) {
        FrameUtil.instance().startFileChoser(this, 102);
    }

    public void startZip(View view) {
        ZipUtil.zip(Environment.getExternalStorageDirectory() + "/Font.7z", Environment.getExternalStorageDirectory() + "/FontHome","", new IArchiverListener() {
            @Override
            public void onStartArchiver() {

            }

            @Override
            public void onProgressArchiver(int current, int total) {

            }

            @Override
            public void onEndArchiver() {
                TestMainActivity.this.toastShow("解压完毕");
            }
        });
    }
    public void startDialog(View view){
        StyledDialog.init(getApplicationContext());
        StyledDialog.buildIosAlertVertical("测试", "中间的", new StyleDialogListener() {
            @Override
            public void onFirst() {

            }

            @Override
            public void onSecond() {

            }
        }).show();
    }
    public void startTree(View view){
        startActivity(new Intent(this,ListTestActivity.class));
    }
    public void testPdf(View view){
        startActivity(new Intent(this, ActivityPdfBook.class));
//        Intent intent=new Intent(this, DocumentActivity.class);
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setData(Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/test2017.pdf")));
//        startActivity(intent);
    }

}
