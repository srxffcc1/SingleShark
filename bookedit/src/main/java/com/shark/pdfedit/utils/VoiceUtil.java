package com.shark.pdfedit.utils;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.kymjs.common.NetworkUtils;
import com.kymjs.common.SystemTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by King6rf on 2018/4/24.讯飞工具
 */

public class VoiceUtil {
    public static void startVoiceDialog(Activity activity, final EditText textView){
        if(NetworkUtils.checkNet(activity)){
            String orgtext=textView.getText().toString();
            RecognizerDialog dialog= new RecognizerDialog(activity, null);//创建语音输入框
            dialog.setListener(new RecognizerDialogListener() {//设置监听
                @Override
                public void onResult(RecognizerResult recognizerResult, boolean b) {
                    StringBuffer strBuffer = new StringBuffer();
                    try {

                        JSONObject joResult = new JSONObject(recognizerResult.getResultString());

                        JSONArray words = joResult.getJSONArray("ws");
                        for (int i = 0; i < words.length(); i++) {
                            // 转写结果词，默认使用第一个结果
                            JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                            JSONObject obj = items.getJSONObject(0);
                            strBuffer.append(obj.getString("w"));

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String sn = null;
                    try {
                        JSONObject resultJson = new JSONObject(recognizerResult.getResultString());
                        sn = resultJson.optString("sn");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    HashMap<String, String> hashMapTexts = new LinkedHashMap<>();
                    //(3) 解析语音文本<< 将文本叠加成语音分析结果  >>
                    hashMapTexts.put(sn, strBuffer.toString());
                    StringBuffer resultBuffer = new StringBuffer();  //最后结果
                    for (String key : hashMapTexts.keySet()) {
                        resultBuffer.append(hashMapTexts.get(key));
                    }
                    textView.setText(textView.getText().toString()+resultBuffer.toString());
                    textView.requestFocus();//获取焦点
                    textView.setSelection(textView.length());//将光标定位到文字最后，以便修改
                }

                @Override
                public void onError(SpeechError speechError) {
                    speechError.getPlainDescription(true);
                }
            });
            dialog.show();
            TextView txt = (TextView)dialog.getWindow().getDecorView().findViewWithTag("textlink");//去掉下面的产品说明
            txt.setText("");
        }else{
            Toast.makeText(activity,"语音转换请保持网络畅通",Toast.LENGTH_SHORT).show();
        }

    }
}
