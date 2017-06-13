package com.businessframehelp.threads;

/**
 * Created by King6rf on 2017/5/22.
 */

public class ActivityThread extends Thread {
    Runnable runnable;
    public ActivityThread(Runnable runnable){
        this.runnable=runnable;
    }
    @Override
    public void run() {
        runnable.run();
    }

}
