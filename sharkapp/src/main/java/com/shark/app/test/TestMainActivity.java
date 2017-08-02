package com.shark.app.test;

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
import com.businessframehelp.utils.FrameUtil;
import com.businessframehelp.utils.HttpConnector;
import com.businessframehelp.utils.ZipUtil;
import com.hss01248.dialog.StyledDialog;
import com.hzy.archiver.IArchiverListener;
import com.kymjs.common.Log;
import com.shark.app.R;
import com.shark.app.singleactivity.ApkFragment;
import com.shark.app.singleactivity.tab.ActivityPdfBook;

import org.json.JSONArray;
import org.json.JSONException;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.util.ArrayList;

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
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.testcomplete);
        autoCompleteTextView.setAdapter(new AutoCompleteAdapter<WikiItem>(this) {

            @Override
            public ArrayList<WikiItem> parseResponse(String response) {
                Log.d("MainActivity", "Response: " + response);
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
                return HttpConnector.getResponse("http://116.52.6.33:8080/kmzzhaj/yhjck/yhjckAction!listByYhmc?yhjck.yhmc="+Uri.encode(query));
            }
        });
    }

    public void startRtsp(View view) {
//        startFragmentForResult(new vlc.video.VideoFragment(),100);
    }

    public void startPlugin(View view) {

        startFragmentForResult(new ApkFragment(), 100);
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

    public void startDialog(View view) {
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
        PdfPrintHelp.print(Environment.getExternalStorageDirectory() + "/test2017.pdf", this, 1000);
    }

    public void testWeb(View view){
        KJHttp http=new KJHttp();
        HttpParams params = new HttpParams();
        params.put("yhjck.yhmc", "隐患"); //传递参数
        final long oldtime=System.currentTimeMillis();
        http.get("http://116.52.6.33:8080/kmzzhaj/yhjck/yhjckAction!listByYhmc?yhjck.yhmc="+Uri.encode("今年"),new HttpCallBack() {

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                System.out.println("新耗时:"+(System.currentTimeMillis()-oldtime));
                Log.d("log:" + t.toString());
            }
        });
    }

}
