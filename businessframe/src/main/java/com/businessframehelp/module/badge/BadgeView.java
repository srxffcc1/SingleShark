package com.businessframehelp.module.badge;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * A simple text label view that can be applied as a "badge" to any given {@link View}.
 * This class is intended to be instantiated at runtime rather than included in XML layouts.
 * 
 * @author Jeff Gilfelt
 */
public class BadgeView extends TextView {

	public static final int POSITION_TOP_LEFT = 1;
	public static final int POSITION_TOP_RIGHT = 2;
	public static final int POSITION_BOTTOM_LEFT = 3;
	public static final int POSITION_BOTTOM_RIGHT = 4;
	public static final int POSITION_CENTER = 5;
	
	private static final int DEFAULT_MARGIN_DIP = 5;
	private static final int DEFAULT_LR_PADDING_DIP = 5;
	private static final int DEFAULT_CORNER_RADIUS_DIP = 8;
	private static final int DEFAULT_POSITION = POSITION_TOP_RIGHT;
	private static final int DEFAULT_BADGE_COLOR = Color.parseColor("#CCFF0000"); //Color.RED;
	private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
	
	private static Animation fadeIn;
	private static Animation fadeOut;
	
	private Context context;
	private View target;
	
	private int badgePosition;
	private int badgeMarginH;
	private int badgeMarginV;
	private int badgeColor;
	
	private boolean isShown;
	
	private ShapeDrawable badgeBg;
	
	private int targetTabIndex;
	
	public BadgeView(Context context) {
		this(context, (AttributeSet) null, android.R.attr.textViewStyle);
	}
	
	public BadgeView(Context context, AttributeSet attrs) {
		 this(context, attrs, android.R.attr.textViewStyle);
	}
	
	/**
     * Constructor -
     * 
     * instance a new BadgeView instance attached to a target {@link View}.
     *
     * @param context context for this view.
     * @param target the View to attach the badge to.
     */
	public BadgeView(Context context, View target) {
		 this(context, null, android.R.attr.textViewStyle, target, 0);
	}
	
	/**
     * Constructor -
     * 
     * instance a new BadgeView instance attached to a target {@link TabWidget}
     * tab at a given index.
     *
     * @param context context for this view.
     * @param target the TabWidget to attach the badge to.
     * @param index the position of the tab within the target.
     */
	public BadgeView(Context context, TabWidget target, int index) {
		this(context, null, android.R.attr.textViewStyle, target, index);
	}
	
	public BadgeView(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs, defStyle, null, 0);
	}
	
	public BadgeView(Context context, AttributeSet attrs, int defStyle, View target, int tabIndex) {
		super(context, attrs, defStyle);
		init(context, target, tabIndex);
	}

	private BadgeView init(Context context, View target, int tabIndex) {
		
		this.context = context;
		this.target = target;
		this.targetTabIndex = tabIndex;
		
		// apply defaults
		badgePosition = DEFAULT_POSITION;
		badgeMarginH = dipToPixels(DEFAULT_MARGIN_DIP);
		badgeMarginV = badgeMarginH;
		badgeColor = DEFAULT_BADGE_COLOR;
		
		setTypeface(Typeface.DEFAULT_BOLD);
		int paddingPixels = dipToPixels(DEFAULT_LR_PADDING_DIP);
		setPadding(paddingPixels, 0, paddingPixels, 0);
		setTextColor(DEFAULT_TEXT_COLOR);
		
		fadeIn = new AlphaAnimation(0, 1);
		fadeIn.setInterpolator(new DecelerateInterpolator());
		fadeIn.setDuration(200);

		fadeOut = new AlphaAnimation(1, 0);
		fadeOut.setInterpolator(new AccelerateInterpolator());
		fadeOut.setDuration(200);
		
		isShown = false;
		
		if (this.target != null) {
			applyTo(this.target);
		} else {
			show();
		}
		return  this;
		
	}

	private BadgeView applyTo(View target) {
		
		LayoutParams lp = target.getLayoutParams();
		ViewParent parent = target.getParent();
		FrameLayout container = new FrameLayout(context);
		if (target instanceof TabWidget) {
			
			// set target to the relevant tab child container
			target = ((TabWidget) target).getChildTabViewAt(targetTabIndex);
			this.target = target;
			
			((ViewGroup) target).addView(container, 
					new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			
			this.setVisibility(View.GONE);
			container.addView(this);
			
		} else {
			
			// TODO verify that parent is indeed a ViewGroup
			ViewGroup group = (ViewGroup) parent;
			int index = group.indexOfChild(target);
			target.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			group.removeView(target);
			group.addView(container, index, lp);
			FrameLayout container2 = new FrameLayout(context);
			container2.addView(target);
			container.addView(container2);
	
			this.setVisibility(View.GONE);
			container.addView(this);
			
			group.invalidate();
			
		}
		return  this;
		
	}
	
	/**
     * Make the badge visible in the UI.
     * 
     */
	public BadgeView show() {
		show(false, null);
		return  this;
	}
	
	/**
     * Make the badge visible in the UI.
     *
     * @param animate flag to apply the default fade-in animation.
     */
	public BadgeView show(boolean animate) {
		show(animate, fadeIn);
		return  this;
	}
	
	/**
     * Make the badge visible in the UI.
     *
     * @param anim Animation to apply to the view when made visible.
     */
	public BadgeView show(Animation anim) {
		show(true, anim);
		return  this;
	}
	
	/**
     * Make the badge non-visible in the UI.
     * 
     */
	public BadgeView hide() {
		hide(false, null);
		return  this;
	}
	
	/**
     * Make the badge non-visible in the UI.
     *
     * @param animate flag to apply the default fade-out animation.
     */
	public BadgeView hide(boolean animate) {
		hide(animate, fadeOut);
		return  this;
	}
	
	/**
     * Make the badge non-visible in the UI.
     *
     * @param anim Animation to apply to the view when made non-visible.
     */
	public BadgeView hide(Animation anim) {
		hide(true, anim);
		return  this;
	}
	
	/**
     * Toggle the badge visibility in the UI.
     * 
     */
	public BadgeView toggle() {
		toggle(false, null, null);
		return  this;
	}
	
	/**
     * Toggle the badge visibility in the UI.
     * 
     * @param animate flag to apply the default fade-in/out animation.
     */
	public BadgeView toggle(boolean animate) {
		toggle(animate, fadeIn, fadeOut);
		return  this;
	}
	
	/**
     * Toggle the badge visibility in the UI.
     *
     * @param animIn Animation to apply to the view when made visible.
     * @param animOut Animation to apply to the view when made non-visible.
     */
	public BadgeView toggle(Animation animIn, Animation animOut) {
		toggle(true, animIn, animOut);
		return  this;
	}
	
	private BadgeView show(boolean animate, Animation anim) {
		if (getBackground() == null) {
			if (badgeBg == null) {
				badgeBg = getDefaultBackground();
			}
			setBackgroundDrawable(badgeBg);
		}
		applyLayoutParams();
		
		if (animate) {
			this.startAnimation(anim);
		}
		this.setVisibility(View.VISIBLE);
		isShown = true;
		return  this;
	}
	
	private BadgeView hide(boolean animate, Animation anim) {
		this.setVisibility(View.GONE);
		if (animate) {
			this.startAnimation(anim);
		}
		isShown = false;
		return  this;
	}
	
	private BadgeView toggle(boolean animate, Animation animIn, Animation animOut) {
		if (isShown) {
			hide(animate && (animOut != null), animOut);	
		} else {
			show(animate && (animIn != null), animIn);
		}
		return  this;
	}
	
	/**
     * Increment the numeric badge label. If the current badge label cannot be converted to
     * an integer value, its label will be set to "0".
     * 
     * @param offset the increment offset.
     */
	public int increment(int offset) {
		CharSequence txt = getText();
		int i;
		if (txt != null) {
			try {
				i = Integer.parseInt(txt.toString());
			} catch (NumberFormatException e) {
				i = 0;
			}
		} else {
			i = 0;
		}
		i = i + offset;
		setText(String.valueOf(i));
		return i;
	}
	
	/**
     * Decrement the numeric badge label. If the current badge label cannot be converted to
     * an integer value, its label will be set to "0".
     * 
     * @param offset the decrement offset.
     */
	public int decrement(int offset) {
		return increment(-offset);
	}
	
	private ShapeDrawable getDefaultBackground() {
		
		int r = dipToPixels(DEFAULT_CORNER_RADIUS_DIP);
		float[] outerR = new float[] {r, r, r, r, r, r, r, r};
        
		RoundRectShape rr = new RoundRectShape(outerR, null, null);
		ShapeDrawable drawable = new ShapeDrawable(rr);
		drawable.getPaint().setColor(badgeColor);
		
		return drawable;
		
	}
	
	private BadgeView applyLayoutParams() {
		
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		switch (badgePosition) {
		case POSITION_TOP_LEFT:
			lp.gravity = Gravity.LEFT | Gravity.TOP;
			lp.setMargins(badgeMarginH, badgeMarginV, 0, 0);
			break;
		case POSITION_TOP_RIGHT:
			lp.gravity = Gravity.RIGHT | Gravity.TOP;
			lp.setMargins(0, badgeMarginV, badgeMarginH, 0);
			break;
		case POSITION_BOTTOM_LEFT:
			lp.gravity = Gravity.LEFT | Gravity.BOTTOM;
			lp.setMargins(badgeMarginH, 0, 0, badgeMarginV);
			break;
		case POSITION_BOTTOM_RIGHT:
			lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
			lp.setMargins(0, 0, badgeMarginH, badgeMarginV);
			break;
		case POSITION_CENTER:
			lp.gravity = Gravity.CENTER;
			lp.setMargins(0, 0, 0, 0);
			break;
		default:
			break;
		}
		
		setLayoutParams(lp);
		return  this;
		
	}

	/**
     * Returns the target View this badge has been attached to.
     * 
     */
	public View getTarget() {
		return target;
	}

	/**
     * Is this badge currently visible in the UI?
     * 
     */
	@Override
	public boolean isShown() {
		return isShown;
	}

	/**
     * Returns the positioning of this badge.
     * 
     * one of POSITION_TOP_LEFT, POSITION_TOP_RIGHT, POSITION_BOTTOM_LEFT, POSITION_BOTTOM_RIGHT, POSTION_CENTER.
     * 
     */
	public int getBadgePosition() {
		return badgePosition;
	}

	/**
     * Set the positioning of this badge.
     * 
     * @param layoutPosition one of POSITION_TOP_LEFT, POSITION_TOP_RIGHT, POSITION_BOTTOM_LEFT, POSITION_BOTTOM_RIGHT, POSTION_CENTER.
     * 
     */
	public BadgeView setBadgePosition(int layoutPosition) {
		this.badgePosition = layoutPosition;
		return  this;
	}

	/**
     * Returns the horizontal margin from the target View that is applied to this badge.
     * 
     */
	public int getHorizontalBadgeMargin() {
		return badgeMarginH;
	}
	
	/**
     * Returns the vertical margin from the target View that is applied to this badge.
     * 
     */
	public int getVerticalBadgeMargin() {
		return badgeMarginV;
	}

	/**
     * Set the horizontal/vertical margin from the target View that is applied to this badge.
     * 
     * @param badgeMargin the margin in pixels.
     */
	public BadgeView setBadgeMargin(int badgeMargin) {
		this.badgeMarginH = badgeMargin;
		this.badgeMarginV = badgeMargin;
		return  this;
	}
	
	/**
     * Set the horizontal/vertical margin from the target View that is applied to this badge.
     * 
     * @param horizontal margin in pixels.
     * @param vertical margin in pixels.
     */
	public BadgeView setBadgeMargin(int horizontal, int vertical) {
		this.badgeMarginH = horizontal;
		this.badgeMarginV = vertical;
		return  this;
	}
	
	/**
     * Returns the color value of the badge background.
     * 
     */
	public int getBadgeBackgroundColor() {
		return badgeColor;
	}

	/**
     * Set the color value of the badge background.
     * 
     * @param badgeColor the badge background color.
     */
	public BadgeView setBadgeBackgroundColor(int badgeColor) {
		this.badgeColor = badgeColor;
		badgeBg = getDefaultBackground();
		return  this;
	}
	
	private int dipToPixels(int dip) {
		Resources r = getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
		return (int) px;
	}

}
