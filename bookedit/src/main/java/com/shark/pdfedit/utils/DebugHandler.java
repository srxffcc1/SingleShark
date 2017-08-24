package com.shark.pdfedit.utils;

import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by King6rf on 2017/7/6.
 */

public class DebugHandler {
    private static Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            Log.v("DebugHandler","分配:"+msg.what+"");
            HandlerListener listener=handlerListenerMap.get(msg.what);
            if(listener!=null){
//                Log.v("DebugHandler","开始:"+msg.what+"");
                listener.hand(msg);
            }
        }
    };
    private static Map<Integer,HandlerListener> handlerListenerMap=new HashMap<>();

    public interface HandlerListener {
        void hand(Message msg);
    }
    private static final DebugHandler instance=new DebugHandler();
    private DebugHandler(){
    }
    public static DebugHandler instance(){
        return instance;
    }
    public  DebugHandler sendMessage(Message msg){
        handler.sendMessage(msg);
        return instance;
    }
    public  DebugHandler sendEmptyMessage(int what){
        handler.sendEmptyMessage(what);
        return instance;
    }
    public  DebugHandler addListener(int key,HandlerListener value){
//        Log.v("DebugHandler","注册:"+key);
        handlerListenerMap.put(key,value);
        return instance;
    }
    public  DebugHandler removeListener(int... key){
//        Log.v("DebugHandler","移除:"+key);
        for (int i : key) {
            handlerListenerMap.remove(i);
        }

        return instance;
    }
}
