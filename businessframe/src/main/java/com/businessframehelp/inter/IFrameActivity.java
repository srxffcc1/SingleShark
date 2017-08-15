package com.businessframehelp.inter;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;

import com.businessframehelp.enums.ORIENTATION;

/**
 * Created by Administrator on 2017/5/10.
 */

public interface IFrameActivity {
    /**
     * 将intent转为view
     * @param mTag
     * @param mIntent
     * @return
     */
    View getIntentContentView(String mTag, Intent mIntent);
    void addIntentContentView(ViewGroup content,String mTag, Intent mIntent);
    void addIntentContentView(int contentRid,String mTag, Intent mIntent);
    /**
     * toast
     * @param contecnt
     */
    void toastShow(String contecnt);

    /**
     * 设置屏幕朝向
     * @return
     */
    ORIENTATION getORIENTATION();

    /**
     * 检查网络
     * @return
     */
    boolean checkNet();

    /**
     * 获得菜单id
     * @return
     */
    int getMenuid();

    /**
     * 用于handle
     * @param msg
     */
    void handleMessage(Message msg);
}
