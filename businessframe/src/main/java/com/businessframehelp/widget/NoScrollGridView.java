package com.businessframehelp.widget;

import android.widget.GridView;
@Deprecated
public class NoScrollGridView extends GridView
{
	public NoScrollGridView(android.content.Context context,
                            android.util.AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * 设置不滚动
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}