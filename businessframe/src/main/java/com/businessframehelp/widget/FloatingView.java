package com.businessframehelp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

//import com.nineoldandroids.view.ViewHelper;

//import com.cxmax.library.listener.ScrollDirectionListener;

/**
 * 配合DragLayout使用的浮动窗口
 * 1.绑定滑动监听，View在滑动过程中显示隐藏的动画效果。
 * 2.自定义View的绘制，主要用了Bitmap的绘制，在目标广告图的右上角用遮罩绘制删除按钮
 * 3.View的点击事件OnTouchEvent的处理
 * Created by CaiXi on 2016/8/23
 */
@SuppressWarnings("ResourceType")
public class FloatingView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener {//, ScrollDirectionListener.ScrollViewListener {
    private final static String TAQ = FloatingView.class.getSimpleName();
    private final static int MAX_WIDTH = 90;
    private final static int MAX_HEIGHT = 90;
    private final static int DELETE_DEFAULT_WIDTH = 20;//删除图标的默认宽高

    private static final int TRANSLATE_DURATION_MILLIS = 200;//进入和移出的动画时间

    private boolean mVisible;//当前view是否在屏幕内可见
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator(); //动画插值器
    private int mScrollThreshold;
    private boolean mNeedAnimation; //滑动监听动画

    private Context mContext;
    private boolean mMarginSet;
    private int mWidth, mHeight, mBitmapWidth, mBitmapHeight;
    private Paint mPaint;
    private Bitmap mBitmap;
    private LayerDrawable mLayerDrawable;
    private Matrix mMatrix;
    private OnFloatClickListener mOnFloatClickListener;

    private boolean isSetColor;
    private int mDeleteColor;
    public boolean isshow = true;

    public interface OnFloatClickListener {
        void floatClick(View view);

        void floatCloseClick();
    }

    public FloatingView(Context context) {
        this(context, null);
    }

    public FloatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FloatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mWidth = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            mWidth = Math.min(dip2px(mContext, MAX_WIDTH), specSize);
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mHeight = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            mHeight = Math.min(dip2px(mContext, MAX_HEIGHT), specSize);
        }

        setMargins();

        setMeasuredDimension(mWidth, mHeight);


    }

    boolean needdrawsrc = true;

    @Override
    protected void onDraw(Canvas canvas) {
        if (needdrawsrc) {
            super.onDraw(canvas);
        }
        if (isSetColor && mDeleteColor != 0) {
            mBitmap = createLayerDrawable(mDeleteColor);
        } else {
            mBitmap = createLayerDrawable();
        }
        mMatrix.setTranslate(mWidth - mBitmapHeight, dip2px(mContext, 4));
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("点击事件:FloatingView");
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (isClickable() && needdrawsrc) {
//                    needdrawsrc=!needdrawsrc;
                    performClick();
                    setVisibility(INVISIBLE);//想要把此处改成将主图隐藏只留x号
                    taskChange();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if (mBitmap != null) {
                    boolean touchable = (event.getX() > (mWidth - mBitmapWidth) && event.getY() < mBitmapHeight);
                    if (touchable) {
                        needdrawsrc = !needdrawsrc;
                        invalidate();
                        setVisibility(INVISIBLE);//想要把此处改成将主图隐藏只留x号
                        taskChange();
                        mOnFloatClickListener.floatCloseClick();
                        setClickable(false);
                    } else {
                        setClickable(true);
                        mOnFloatClickListener.floatClick(this);
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void taskChange() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getVisibility() != VISIBLE) {
                    setVisibility(VISIBLE);
                }

            }
        }, Integer.parseInt("60000"));
    }

    private void init(Context context, AttributeSet attributeSet) {
        mVisible = true;
        mContext = context;
        mPaint = new Paint();
        mBitmapWidth = dip2px(mContext, (float) DELETE_DEFAULT_WIDTH);
        mBitmapHeight = dip2px(mContext, (float) DELETE_DEFAULT_WIDTH);
        if (mBitmap == null) {
            mBitmap = createLayerDrawable();
        }
        mMatrix = new Matrix();
        //初始化自定义属性
        if (attributeSet != null) {
            initAttributes(context, attributeSet);
        }
        mOnFloatClickListener = new OnFloatClickListener() {
            @Override
            public void floatClick(View view) {

            }

            @Override
            public void floatCloseClick() {

            }
        };
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
//        TypedArray attr = getTypedArray(context,attributeSet, R.styleable.FloatingView);
//        if (attr != null){
//            mNeedAnimation = attr.getBoolean(R.styleable.FloatingView_cx_animation,true);
//        }
        mNeedAnimation = false;
    }

    private TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    private Bitmap createLayerDrawable() {
        if (mLayerDrawable == null) {
            Drawable[] layers = new Drawable[2];
            layers[0] = ImageUtilz.loadImageFromAsserts(getContext(), "float_ad_close_background.png", DisplayMetrics.DENSITY_MEDIUM);
            layers[1] = ImageUtilz.loadImageFromAsserts(getContext(), "float_ad_close.png", DisplayMetrics.DENSITY_MEDIUM);
            mLayerDrawable = new LayerDrawable(layers);
        }
        return drawableToBitmap(mLayerDrawable);
    }


    private Bitmap createLayerDrawable(int color) {
        if (mLayerDrawable == null) {
            Drawable[] layers = new Drawable[2];
            layers[0] = ImageUtilz.loadImageFromAsserts(getContext(), "float_ad_close_background.png", DisplayMetrics.DENSITY_MEDIUM);
            layers[1] = ImageUtilz.loadImageFromAsserts(getContext(), "float_ad_close.png", DisplayMetrics.DENSITY_MEDIUM);
            mLayerDrawable = new LayerDrawable(layers);
        }
        Drawable background = mLayerDrawable.getDrawable(0);
        background.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        return drawableToBitmap(mLayerDrawable);
    }

    private void setMargins() {
        if (!mMarginSet) {
            if (getLayoutParams() instanceof ViewGroup.LayoutParams) {
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
                int leftMargin = lp.leftMargin;
                int topMargin = lp.topMargin;
                int rightMargin = lp.rightMargin;
                int bottomMargin = lp.bottomMargin;
                lp.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

                requestLayout();
                mMarginSet = true;
            }
        }
    }

    private boolean hasHoneycombApi() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

//    /**
//     * 绑定recyclerview的滑动监听,上滑出现,下滑隐藏
//     */
//    public void attachToRecyclerView(RecyclerView recyclerView) {
//        attachToRecyclerView(recyclerView, null, null);
//    }
//
//    public void attachToRecyclerView(RecyclerView recyclerView, ScrollDirectionListener scrollDirectionListener) {
//        attachToRecyclerView(recyclerView, scrollDirectionListener, null);
//    }
//
//    public void attachToRecyclerView(RecyclerView recyclerView,
//                                     ScrollDirectionListener scrollDirectionlistener,
//                                     RecyclerView.OnScrollListener onScrollListener) {
//        if (mNeedAnimation) {
//            RecyclerViewScrollDetectorImpl scrollDetector = new RecyclerViewScrollDetectorImpl();
//            scrollDetector.setScrollDirectionListener(scrollDirectionlistener, this);
//            scrollDetector.setOnScrollListener(onScrollListener);
//            scrollDetector.setScrollThreshold(mScrollThreshold);
//            recyclerView.addOnScrollListener(scrollDetector);
//        }
//    }
//
//    public void attachToViewPager(ViewPager viewPager) {
//        attachToViewPager(viewPager, null, null);
//    }
//
//    public void attachToViewPager(ViewPager viewPager, ScrollDirectionListener scrollDirectionListener) {
//        attachToViewPager(viewPager, scrollDirectionListener, null);
//    }
//
//    public void attachToViewPager(ViewPager viewPager, ScrollDirectionListener scrollDirectionListener, ViewPager.OnPageChangeListener onPageChangeListener) {
//        if (mNeedAnimation) {
//            ViewPagerScrollDetectorImpl viewPagerScrollDetector = new ViewPagerScrollDetectorImpl();
//            viewPagerScrollDetector.setScrollDirectionListener(scrollDirectionListener, this);
//            viewPagerScrollDetector.setmPageChangeListener(onPageChangeListener);
//            viewPager.addOnPageChangeListener(viewPagerScrollDetector);
//        }
//    }
//
//    /**
//     * 绑定ScrollView的滑动监听,上滑出现,下滑隐藏
//     */
//    public void attachToScrollView(@NonNull ObservableScrollView scrollView) {
//        attachToScrollView(scrollView, null, null);
//    }
//
//    public void attachToScrollView(@NonNull ObservableScrollView scrollView,
//                                   ScrollDirectionListener scrollDirectionListener) {
//        attachToScrollView(scrollView, scrollDirectionListener, null);
//    }
//
//    public void attachToScrollView(@NonNull ObservableScrollView scrollView,
//                                   ScrollDirectionListener scrollDirectionListener,
//                                   ObservableScrollView.OnScrollChangedListener onScrollChangedListener) {
//        ScrollViewScrollDetectorImpl scrollDetector = new ScrollViewScrollDetectorImpl();
//        scrollDetector.setScrollDirectionListener(scrollDirectionListener, this);
//        scrollDetector.setOnScrollChangedListener(onScrollChangedListener);
//        scrollDetector.setScrollThreshold(mScrollThreshold);
//        scrollView.setOnScrollChangedListener(scrollDetector);
//    }

//    @Override
//    public void show() {
//        showAnimation(true);
//    }
//
//    @Override
//    public void hide() {
//        hideAnimation(true);
//    }

    public void showAnimation(boolean animate) {
        toggle(true, animate, false);
    }

    public void hideAnimation(boolean animate) {
        toggle(false, animate, false);
    }

    private void toggle(final boolean visible, final boolean animate, boolean force) {
        if (mVisible != visible || force) {
            mVisible = visible;
            int height = getHeight();
            if (height == 0 && !force) {
                ViewTreeObserver vto = getViewTreeObserver();
                if (vto.isAlive()) {
                    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            ViewTreeObserver currentVto = getViewTreeObserver();
                            if (currentVto.isAlive()) {
                                currentVto.removeOnPreDrawListener(this);
                            }
                            toggle(visible, animate, true);
                            return false;
                        }
                    });
                    return;
                }
            }
            int translationY = visible ? 0 : height + getMarginBottom();
            if (animate) {
//                com.nineoldandroids.view.ViewPropertyAnimator.animate(this).setInterpolator(mInterpolator)
//                        .setDuration(TRANSLATE_DURATION_MILLIS)
//                        .translationY(translationY);
            } else {
                this.setTranslationY(translationY);
            }

            //正在移动的view仍然可以被点击,因此我们需要将它的点击事件手动的disable
            if (!hasHoneycombApi()) {
                setClickable(visible);
            }
        }
    }

    private int getMarginBottom() {
        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return marginBottom;
    }

    public void setOnFloatClickListener(OnFloatClickListener listener) {
        mOnFloatClickListener = listener;
    }

    private Drawable getDrawable(@DimenRes int id) {
        return getResources().getDrawable(id);
    }
//    /** 从assets 文件夹中读取图片 */
//    public static Drawable loadImageFromAsserts(final Context ctx, String fileName) {
//        try {
//            InputStream is = ctx.getResources().getAssets().open(fileName);
//            return Drawable.createFromResourceStream(ctx.getResources(), null, is, fileName, null);
//        } catch (IOException e) {
//            if (e != null) {
//                e.printStackTrace();
//            }
//        } catch (OutOfMemoryError e) {
//            if (e != null) {
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            if (e != null) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }


    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, mBitmapWidth, mBitmapHeight);
        drawable.draw(canvas);
        return bitmap;
    }


    @Override
    public void onGlobalLayout() {
        if (getVisibility() == GONE) {
            if (!mBitmap.isRecycled()) {
                mBitmap.recycle();
                mBitmap = null;
            }

        }
    }


    /**
     * 设置关闭背景颜色
     */
    public void setCloseColor(int color) {
        isSetColor = true;
        this.mDeleteColor = color;
        invalidate();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        invalidate();
    }
}
