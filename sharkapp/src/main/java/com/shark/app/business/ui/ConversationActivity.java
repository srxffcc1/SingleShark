package com.shark.app.business.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.shark.app.R;


/**
 * 作者: CoolTone
 * 描述: 聊天页面
 */
public class ConversationActivity extends FragmentActivity {
    private TextView mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);



    }
}
