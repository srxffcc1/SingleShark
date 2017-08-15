package com.shark.app.business.interfaces;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

/**
 * Created by King6rf on 2017/8/15.
 */

public interface ISearch {
     void onCreateImp(@Nullable Bundle savedInstanceState);
     void clickMultiSearch();
     MatrixCursor getSuggestionCursor();
    RecyclerView.Adapter getRecycleAdapter();
    void toSearchResult(String search);
    String getSearchHint();
}
