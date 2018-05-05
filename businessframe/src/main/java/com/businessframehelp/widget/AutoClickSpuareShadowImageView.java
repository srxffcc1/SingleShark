package com.businessframehelp.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class AutoClickSpuareShadowImageView extends ImageView {

    public AutoClickSpuareShadowImageView(Context context) {
        super(context);
    }
    public AutoClickSpuareShadowImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public AutoClickSpuareShadowImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.setColorFilter(0x33000000, PorterDuff.Mode.SRC_OVER);
                //重写触摸事件的方法,当按钮被点击的时候
                if(mOnClickListener!=null){

                    mOnClickListener.onClick();
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.setColorFilter(0x00000000);
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 定义点击的接口
     */
    interface OnClickListener {
        void onClick();
    }

    private OnClickListener mOnClickListener;

    public void setOnClickListener (OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }


}
