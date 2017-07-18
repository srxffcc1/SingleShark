package com.businessframehelp.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.businessframehelp.R;
import com.danielkim.soundrecorder.activities.RecordActivity;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.sandrios.sandriosCamera.internal.SandriosCamera;
import com.sandrios.sandriosCamera.internal.configuration.CameraConfiguration;

import java.io.File;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by Administrator on 2017/5/9.
 */

public class FrameUtil {
    private static final FrameUtil frameUtil=new FrameUtil();
    private FrameUtil(){

    }
    public static FrameUtil instance(){
        return frameUtil;
    }
    public void startScanZxingWithCamera(Activity activity,int REQUEST_CODE){
        activity.startActivityForResult(new Intent(activity, com.uuzuche.lib_zxing.activity.CaptureActivity.class),REQUEST_CODE);
//        if (requestCode == REQUEST_CODE) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
//                }
//            }
//        }

    }
    public void startScanZxingWithBitmap(Activity activity,int REQUEST_CODE){
//        try {
//            Bitmap mBitmap = MediaStore.Images.Media.getBitmap(cr, uri);//显得到bitmap图片
//
//            CodeUtils.analyzeBitmap(mBitmap, new CodeUtils.AnalyzeCallback() {
//                @Override
//                public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//                    Toast.makeText(MainActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onAnalyzeFailed() {
//                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
//                }
//            });
//
//            if (mBitmap != null) {
//                mBitmap.recycle();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    public void startCropWithSquare(Activity activity, Uri inputUri, Uri outputUri){

    }
    public void startCropWithRound(Activity activity, Uri inputUri, Uri outputUri){

    }
    public void startVideo(Activity activity,int REQUEST_CODE){

        new SandriosCamera(activity, REQUEST_CODE)
                .setShowPicker(false)
                .setVideoFileSize(15) //File Size in MB: Default is no limit
                .setMediaAction(CameraConfiguration.MEDIA_ACTION_VIDEO) // default is CameraConfiguration.MEDIA_ACTION_BOTH
                .enableImageCropping(true) // Default is false.
                .launchCamera();
//        if (requestCode == CAPTURE_MEDIA && resultCode == RESULT_OK) {
//            Log.e("File", "" + data.getStringExtra(SandriosCameraConfiguration.Arguments.FILE_PATH));
//            Toast.makeText(this, "Media captured.", Toast.LENGTH_SHORT).show();
//        }
    }
    public void startVideoIntent(Activity activity,int REQUEST_CODE,String path){
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT,5491520L);
        Uri uri = Uri.fromFile(new File(path));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }
    public void startRecordIntent(Activity activity,int REQUEST_CODE){
        try {
            Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            activity.startActivityForResult(intent,REQUEST_CODE);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void startCameraIntent(Activity activity,int REQUEST_CODE,String path){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri mOutPutFileUri = Uri.fromFile(new File(path));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }
    public void startCamera(Activity activity,int REQUEST_CODE){


        new SandriosCamera(activity, REQUEST_CODE)
                .setShowPicker(false)
                .setVideoFileSize(15) //File Size in MB: Default is no limit
                .setMediaAction(CameraConfiguration.MEDIA_ACTION_PHOTO) // default is CameraConfiguration.MEDIA_ACTION_BOTH
                .enableImageCropping(true) // Default is false.
                .launchCamera();
//        if (requestCode == CAPTURE_MEDIA && resultCode == RESULT_OK) {
//            Log.e("File", "" + data.getStringExtra(SandriosCameraConfiguration.Arguments.FILE_PATH));
//            Toast.makeText(this, "Media captured.", Toast.LENGTH_SHORT).show();
//        }
    }
    public void startRecord(Activity activity,int REQUEST_CODE,String filepath){
//        int requestCode = 0;
//        AndroidAudioRecorder.with(activity)
//                // Required
//                .setFilePath(filepath)
//                .setRequestCode(requestCode)
//
//                // Optional
//                .setSource(AudioSource.MIC)
//                .setChannel(AudioChannel.STEREO)
//                .setSampleRate(AudioSampleRate.HZ_48000)
//                .setAutoStart(true)
//                .setKeepDisplayOn(true)
//
//                // Start recording
//                .record();
        activity.startActivityForResult(new Intent(activity, RecordActivity.class), REQUEST_CODE);
    }
    public void startFileChoser(Activity activity,int REQUEST_CODE){
        new MaterialFilePicker()
                .withActivity(activity)
                .withRequestCode(REQUEST_CODE)
//                .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
                .withFilterDirectories(true) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();

//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
//            // Do anything with file
//        }
    }
    public void simpleBadgeView(Activity activity, View target,int number){
        new QBadgeView(activity)
                .bindTarget(target)
                .setBadgeNumber(5)
                .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
            @Override
            public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                badge.hide(true);
            }
        });

//        BadgeView badgeView=new BadgeView(this,target).setBadgeMargin(15, 10).toggle(true);
//        badgeView.setText(2+"");
//        badgeView.show();
    }
    public void startTimePicker(Activity activity, OnDateSetListener back){
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        new TimePickerDialog.Builder()
                .setCallBack(back)
                .setCancelStringId("Cancel")
                .setSureStringId("Sure")
                .setTitleStringId("TimePicker")
                .setYearText("Year")
                .setMonthText("Month")
                .setDayText("Day")
                .setHourText("Hour")
                .setMinuteText("Minute")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(activity.getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(activity.getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(activity.getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build()
        .show(((FragmentActivity)activity).getSupportFragmentManager(),"timepicker");
    }

}
