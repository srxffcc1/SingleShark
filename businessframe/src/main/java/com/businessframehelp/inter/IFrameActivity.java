package com.businessframehelp.inter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.businessframehelp.enums.ORIENTATION;

/**
 * Created by Administrator on 2017/5/10.
 */

public interface IFrameActivity {
    /**
     * 将intent转为view
     * @param tabMianContent
     * @param mTag
     * @param mIntent
     * @return
     */
    View getIntentContentView(ViewGroup tabMianContent, String mTag, Intent mIntent);

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

}
