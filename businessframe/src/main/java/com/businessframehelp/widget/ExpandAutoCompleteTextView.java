package com.businessframehelp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

/**
 * Created by King6rf on 2017/8/2.
 *
 */
@Deprecated
public class ExpandAutoCompleteTextView extends AutoCompleteTextView {
    public ExpandAutoCompleteTextView(Context context) {
        super(context);
    }

    public ExpandAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override//次数会进行筛选
    protected void performFiltering(CharSequence text, int keyCode) {
        super.performFiltering(text, keyCode);
    }

    @Override//筛选结束
    public void onFilterComplete(int count) {
        super.onFilterComplete(count);
    }
}

