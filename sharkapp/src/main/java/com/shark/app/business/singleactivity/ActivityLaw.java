package com.shark.app.business.singleactivity;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ActivityLaw extends AbstractActivitySearchList {


    @Override
    public void onRefresh() {

    }

    @Override
    public void onCreateImp(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void clickMultiSearch() {

    }

    @Override
    public MatrixCursor getSuggestionCursor() {
        return null;
    }

    @Override
    public RecyclerView.Adapter getRecycleAdapter() {
        return null;
    }

    @Override
    public void toSearchResult(String search) {

    }

    @Override
    public String getSearchHint() {
        return "请输入信息";
    }

    @Override
    public void handleMessage(Message msg) {

    }
}
