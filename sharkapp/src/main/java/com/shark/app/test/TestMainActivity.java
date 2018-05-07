package com.shark.app.test;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.businessframehelp.adapter.AutoCompleteAdapter;
import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.entity.WikiItem;
import com.businessframehelp.enums.ORIENTATION;
import com.businessframehelp.listen.StyleDialogListener;
import com.businessframehelp.staticlib.StaticAppInfo;
import com.businessframehelp.staticlib.StaticSdkTool;
import com.businessframehelp.utils.FrameUtil;
import com.businessframehelp.utils.HttpUrlConnectUtil;
import com.businessframehelp.utils.PrintUtil;
import com.businessframehelp.utils.ZipUtil;
import com.hss01248.dialog.StyledDialog;
import com.hzy.archiver.IArchiverListener;
import com.ksyun.player.disk.ui.activity.player.TextureVideoActivity;
import com.kymjs.common.FileUtils;
import com.kymjs.common.Log;
import com.shark.app.R;
import com.shark.app.business.ui.tab.ActivityPdfBook;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class TestMainActivity extends FrameActivity {

    public int index = 1;


    @Override
    public ORIENTATION getORIENTATION() {
        return ORIENTATION.PORTRAIT;
    }


    @Override
    public int getMenuid() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO:OnCreate Method has been created, run ButterKnife again to generate code
        setContentView(R.layout.test_main);
//        testAuto();
    }

    private void testAuto() {
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.id_appbar);
        autoCompleteTextView.setAdapter(new AutoCompleteAdapter<WikiItem>(this) {

            @Override
            public ArrayList<WikiItem> parseResponse(String response) {
                Log.d("OpMainActivity", "Response: " + response);
                ArrayList<WikiItem> models = new ArrayList<WikiItem>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray != null) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            models.add(new WikiItem(jsonArray.getJSONObject(i).optString("yhmc")));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return models;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent, WikiItem entity) {
                convertView= LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent,false);
		        ((TextView)convertView.findViewById(android.R.id.text1)).setText(entity.getItem());
                return convertView;
            }

            @Override
            public String getResponseNoLine() {
                return null;
            }

            @Override
            public String getRespinseOnline(String query) {
//                KJHttp kjHttp=new KJHttp();
//                HttpParams params = new HttpParams();
//                params.put("yhjck.yhmc", Uri.encode(query)); //传递参数
//                Request request=new FormRequest(Request.HttpMethod.POST,"http://116.52.6.33:8080/kmzzhaj/yhjck/yhjckAction!listByYhmc",params,null);
//                HttpConnectStack httpConnectStack=new HttpConnectStack();
//                StringBuilder jsonResults = new StringBuilder();
//                try {
//                    KJHttpResponse kjHttpResponse=httpConnectStack.performRequest(request,new HashMap<String, String>());
//
//                    InputStreamReader in = new InputStreamReader(kjHttpResponse.getContentStream());
//                    if (in == null) {
//                    }
//                    int read;
//                    char[] buff = new char[512];
//                    while ((read = in.read(buff)) != -1) {
//                        jsonResults.append(buff, 0, read);
//                    }
//                    return jsonResults.toString();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return "";
                try {
                    return HttpUrlConnectUtil.doGet("http://116.52.6.33:8080/kmzzhaj/yhjck/yhjckAction!listByYhmc?yhjck.yhmc="+ Uri.encode(query),null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return query;
            }
        });
    }

    public void startRtsp(View view) {
//        startFragmentForResult(new vlc.video.VideoFragment(),100);
        startActivity(new Intent(this, TextureVideoActivity.class).putExtra("path","rtsp://admin:12345@124.152.9.144:20041/PSIA/streaming/channels/301"));
    }

    public void startPlugin(View view) {

//        startFragmentForResult(new ApkFragment(), 100);
    }




    @Override
    public void handleMessage(Message msg) {

    }

    public void startCamera(View view) {
//        startActivity(new Intent(this, CameraStartActivity.class));
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
        ZipUtil.zip(Environment.getExternalStorageDirectory() + "/Font.7z", Environment.getExternalStorageDirectory() + "/FontHome", "", new IArchiverListener() {
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
    Dialog menudialog;
    public void startDialog(View view) {
        StyledDialog.init(getApplicationContext());
        menudialog=StyledDialog.buildIosAlertVertical("测试", "中间的", new StyleDialogListener() {
            @Override
            public void onFirst() {

            }

            @Override
            public void onSecond() {

            }
        }).show();
    }

    public void startTree(View view) {
        startActivity(new Intent(this, ListTestActivity.class));
    }

    public void testPdf(View view) {
        startActivity(new Intent(this, ActivityPdfBook.class));
//        Intent intent=new Intent(this, DocumentActivity.class);
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setData(Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/test2017.pdf")));
//        startActivity(intent);
    }

    public void testPDfPrint(View view) {
//        PrintUtil.print(FileUtils.assetsCopy(this, StaticAppInfo.getInstance().getProjcetDir()+ StaticSdkTool.TEST,"wtest"), this, 1000);
    }

    public void testWeb(View view){
//        KJHttp http=new KJHttp();
//        HttpParams params = new HttpParams();
////        params.put("yhjck.yhmc", "今年"); //传递参数
//        final long oldtime=System.currentTimeMillis();
//        http.post("http://116.52.6.33:8080/kmzzhaj/yhjck/yhjckAction!listByYhmc?yhjck.yhmc=今年",params,false,new HttpCallBack() {
//
//            @Override
//            public void onSuccess(String t) {
//                super.onSuccess(t);
////                //System.out.println("新耗时:"+(System.currentTimeMillis()-oldtime));
//                Log.d("log:" + t.toString());
//            }
//
//            @Override
//            public void onCookieTimeOut() {
//                finish();
//                startActivity(new Intent(getContext(), ActivityLogin.class));
//            }
//        });
    }
    public void testWeb2(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                HttpConnector.doGet("http://116.52.6.33:8080/kmzzhaj/yhjck/yhjckAction!listByYhmc?yhjck.yhmc="+ Uri.encode("今年"));
            }
        }).start();

    }
    public void testAlbum(View view){
        FrameUtil.instance().startAlbum(this,100);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        //System.out.println("返回的:requestCode"+requestCode+",resultCode:"+resultCode);
    }
    public void onlocation(View view){
//        startActivity(new Intent(this,com.amap.location.demo.StartActivity.class));
    }
    public void onnavigation(View view){
//        startActivity(new Intent(this,com.amap.navi.demo.activity.IndexActivity.class));
    }

    public void onmap(View view){
//        startActivity(new Intent(this,com.amap.map3d.demo.MainActivity.class));
    }
    public void ongettui(View view){


    }
    public void startTestVideo(View view){
//        String path=Uri.fromFile(new File(StaticAppInfo.getInstance().getProjcetDir()+StaticSdkTool.TEST+"/"+"testcompany.mp4")).toString();
        startActivity(new Intent(this, TestJiaoZ.class));
}
}
