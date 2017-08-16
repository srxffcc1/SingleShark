package com.shark.app.business.singleactivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shark.app.R;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ActivityError extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    public void init(){
        //此处先find了一下activity_main得组件 没错
        //然后他么得莫名奇妙开始了
        // LayoutFlater一个《异世界itemview》 在itemview里find一堆itemview里得组件 其中包括一个number
    }
    public void zxing(){
        //次数扫描二维码 返回后handle方法里setadapter
    }
    public void setAdapter(){
        //new了一个SimpleAdapter getview当然是item啦 然后啥也没做
        //然后显示所描结果 用number设置数字
        //大哥 两个次元 一个是listview里得item 一个是存在《异世界itemview》
    }
}
