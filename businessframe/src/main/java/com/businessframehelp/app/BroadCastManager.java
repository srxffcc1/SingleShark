package com.businessframehelp.app;

import android.content.Intent;

import com.businessframehelp.listen.IBroadCastListener;

import java.util.Stack;

/**
 * Created by Administrator on 2017/5/13.
 *
 */

@Deprecated
public class BroadCastManager {
    /**
     * 单例模式
     */
    public static final BroadCastManager listenerManager=new BroadCastManager();

    /**
     * 注册的接口集合，发送广播的时候都能收到
     */
    private Stack<IBroadCastListener> iListenerList = new Stack<IBroadCastListener>();

    /**
     * 获得单例对象
     */
    public static BroadCastManager getInstance() {
        return listenerManager;
    }

    /**
     * 注册监听
     */
    public void registerListtener(IBroadCastListener iListener) {
        iListenerList.add(iListener);
    }

    /**
     * 注销监听
     */
    public void unRegisterListener(IBroadCastListener iListener) {
        if (iListenerList.contains(iListener)) {
            iListenerList.remove(iListener);
        }
    }

    /**
     * 发送广播
     */
    public void sendBroadCast(Intent intent) {
        for (IBroadCastListener iListener : iListenerList) {
            iListener.reciverFrameBroadCast(intent);
        }
    }

}
