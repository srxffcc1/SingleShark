package com.shark.app.singleactivity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.businessframehelp.app.FrameActivity;
import com.businessframehelp.enums.ORIENTATION;
import com.shark.app.R;
import com.wisdomregulation.data.entitybase.Base_Entity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by King6rf on 2017/7/18.
 */

public class ActivityBook extends FrameActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bookview)
    RecyclerView mBookview;
    private Base_Entity bookentity;

    @Override
    public ORIENTATION getORIENTATION() {
        return null;
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_layout);
        mBookview.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick({R.id.toolbar, R.id.bookview})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar:
                break;
            case R.id.bookview:
                break;
        }
    }
}
