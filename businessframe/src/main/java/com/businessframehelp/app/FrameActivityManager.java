package com.businessframehelp.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import com.kymjs.common.Log;

import java.util.Stack;


/**
 * Created by Administrator on 2017/5/10.
 * Activity栈
 */

public class FrameActivityManager {
    private static Stack<Activity> activityStack;
    private static Context application;

    public static void setApplication(Context application) {
        FrameActivityManager.application = application;
    }
    public void startActivity(Class clas){
        application.startActivity(new Intent(application,clas).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    private static final FrameActivityManager instance = new FrameActivityManager();

    private FrameActivityManager() {
    }
    public void showToast(String message){

        Toast.makeText(application,message,Toast.LENGTH_SHORT).show();

    }
    public static FrameActivityManager instance() {
        return instance;
    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    /**
     * 获取当前Activity栈中元素个数
     */
    public int getCount() {
        return activityStack.size();
    }

    public void showAllStack() {
        for (Activity activity : activityStack) {
            Log.v("KJActivityStackShow", activity.getClass().getName());
        }
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        Log.v("KJActivityStack", "add");
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（栈顶Activity）
     */
    public Activity topActivity() {
        if (activityStack == null) {
            throw new NullPointerException(
                    "Activity stack is Null,your Activity must extend KJActivity");
        }
        if (activityStack.isEmpty()) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return (Activity) activity;
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public Activity findActivity(Class<?> cls) {
        Activity activity = null;
        for (Activity aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return (Activity) activity;
    }

    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity((Activity) activity);
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Activity activity) {
        Log.v("KJActivityStackNull", activity.getClass().getName());
        if (activity != null) {

            try {
                Log.v("KJActivityStackRemove", activity.getClass().getName());
                boolean flag = activityStack.remove((Activity) activity);
                // activity.finish();//此处不用finish
                activity = null;

            } catch (Exception e) {
                // TODO Auto-generated catch block

            }
        }
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                ((Activity) activity).finish();
            }
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     *
     * @param cls
     */
    public void finishOthersActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (!(activity.getClass().equals(cls))) {
                Log.v("KJActivityStackFinish", "finish1");
                try {
                    ((Activity) activity).finish();
                } catch (Exception e) {

                }
            }
        }
        Log.v("KJActivityStackFinishCount", activityStack.size() + "");
        for (int i = 0; i < activityStack.size(); i++) {
            if (!activityStack.get(i).getClass().getName()
                    .equals(cls.getName())) {
                Log.v("KJActivityStackFinish", "finish3");
                activityStack.remove(i);
                Log.v("KJActivityStackCountS", "OK");
                i--;
            }
        }
        Log.v("KJActivityStackCount", FrameActivityManager.instance().getCount() + "");

    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                ((Activity) activityStack.get(i)).finish();
            }
        }
        activityStack.clear();
    }

    @Deprecated
    public void AppExit(Context cxt) {
        appExit(cxt);
    }

    /**
     * 应用程序退出
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            Runtime.getRuntime().exit(-1);
        }
    }
}
