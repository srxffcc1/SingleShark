package com.businessframehelp.design;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 *
 * @author zhy
 *
 */
public class DividerGridItemDecoration extends RecyclerView.ItemDecoration
{
	private Paint mDividerPaint = new Paint();
	private DisplayMetrics mDisplayMetrics;
	private int mleftSpace;
	private int mrightSpace;
	private int mtopSpace;
	private int mbottomSpace;
	private int mEdgeSpace;
	private int recycleviewwidth;
	private int recycleviewheight;
	float totalSpaceV =-1;
	float totalSpaceH =-1;

	public DividerGridItemDecoration(Context context) {
		mDisplayMetrics = context.getResources().getDisplayMetrics();
	}

	public DividerGridItemDecoration setSpace(int left, int top, int right, int bottom) {
		mleftSpace = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, left, mDisplayMetrics) + 0.5f);
		mtopSpace = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, top, mDisplayMetrics) + 0.5f);
		mrightSpace = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, right, mDisplayMetrics) + 0.5f);
		mbottomSpace = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, bottom, mDisplayMetrics) + 0.5f);
		return this;
	}
	public DividerGridItemDecoration setSpace(int space) {
		mleftSpace = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space, mDisplayMetrics) + 0.5f);
		mtopSpace = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space, mDisplayMetrics) + 0.5f);
		mrightSpace = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space, mDisplayMetrics) + 0.5f);
		mbottomSpace = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, space, mDisplayMetrics) + 0.5f);
		return this;
	}

	public DividerGridItemDecoration setEdgeSpace(int edgeSpace) {
		mEdgeSpace = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, edgeSpace, mDisplayMetrics) + 0.5f);
		return this;
	}

	public DividerGridItemDecoration setSpaceColor(int spaceColor) {
		mDividerPaint.setColor(spaceColor);
		return this;
	}


	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		final RecyclerView.LayoutManager manager = parent.getLayoutManager();
		final int childPosition = parent.getChildAdapterPosition(view);
		recycleviewwidth=parent.getMeasuredWidth();
		recycleviewheight=parent.getMeasuredHeight();
		 int childWidth=view.getWidth();
		 int childHeight=view.getHeight();
		final int itemCount = parent.getAdapter().getItemCount();

		if (manager != null) {
			if (manager instanceof GridLayoutManager) {
				if(((GridLayoutManager) manager).getSpanCount()>1){
					if(((GridLayoutManager) manager).getOrientation() == GridLayoutManager.VERTICAL){
						if(totalSpaceH !=-1){
							totalSpaceH =recycleviewwidth-(childWidth*((GridLayoutManager) manager).getSpanCount());
						}

					}else{
						if(totalSpaceV !=-1){
							totalSpaceV =recycleviewheight-(childHeight*((GridLayoutManager) manager).getSpanCount());
						}

					}

				}else{

				}
				setGrid(((GridLayoutManager) manager).getOrientation(), ((GridLayoutManager) manager).getSpanCount(), outRect, childPosition, itemCount,childWidth,childHeight);
			} else if (manager instanceof LinearLayoutManager) {
				setLinear(((LinearLayoutManager) manager).getOrientation(), outRect, childPosition, itemCount,childWidth,childHeight);
			}
		}
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
		final RecyclerView.LayoutManager manager = parent.getLayoutManager();
		if (manager != null) {
			if (manager instanceof GridLayoutManager) {

			} else if (manager instanceof LinearLayoutManager) {
				drawLinear(((LinearLayoutManager) manager).getOrientation(), c, parent);
			}
		}
	}

	private void setLinear(int orientation, Rect outRect, int childPosition, int itemCount,int viewwidth,int viewheoght) {
		if (orientation == LinearLayoutManager.VERTICAL) {
			if (childPosition == 0) {
				outRect.set(0, mEdgeSpace, 0, mbottomSpace);
			} else if (childPosition == itemCount - 1) {
				outRect.set(0, 0, 0, mEdgeSpace);
			} else {
				outRect.set(0, 0, 0, mbottomSpace);
			}
		} else if (orientation == LinearLayoutManager.HORIZONTAL) {
			if (childPosition == 0) {
				outRect.set(mEdgeSpace, 0, mrightSpace, 0);
			} else if (childPosition == itemCount - 1) {
				outRect.set(0, 0, mEdgeSpace, 0);
			} else {
				outRect.set(0, 0, mrightSpace, 0);
			}
		}
	}
	//	private int mleftSpace;
//	private int mrightSpace;
//	private int mtopSpace;
//	private int mbottomSpace;
//	set(int left, int top, int right, int bottom)
	private void setGrid(int orientation, int spanCount, Rect outRect, int childPosition, int itemCount,int viewwidth,int viewheoght) {
		int column = childPosition % spanCount;
		int row = childPosition / spanCount;
		float left=0;
		float right=0;
		float top=0;
		float bottom=0;
		if (orientation == GridLayoutManager.VERTICAL) {
			if((spanCount==1&&childPosition<itemCount)){
				bottom=mbottomSpace;
			}


		}else{
			if((spanCount==1&&childPosition<itemCount)) {
				right = mrightSpace;
			}
		}




//		System.out.println(totalSpace+":"+mEdgeSpace);
//		System.out.println(itemCount+":"+childPosition);
//		System.out.println(totalSpace+":"+eachSpace+":"+column+":"+row);


//		if (orientation == GridLayoutManager.VERTICAL) {
//
//			if(row+1==spanCount||spanCount==1){
//				right=0;
//			}

//			top = 0; // 默认 top为0
//			bottom = mbottomSpace; // 默认bottom为间距值
//			if (mEdgeSpace == 0) {
//				left = column * eachSpace / (spanCount - 1);
//				right = eachSpace - left;
//				// 无边距的话  只有最后一行bottom为0
//				if (itemCount / spanCount == row) {
//					bottom = 0;
//				}
//			} else {
//				if (childPosition < spanCount) {
//					// 有边距的话 第一行top为边距值
//					top = mEdgeSpace;
//				} else if (itemCount / spanCount == row) {
//					// 有边距的话 最后一行bottom为边距值
//					bottom = mEdgeSpace;
//				}
//				left = column * (eachSpace - mEdgeSpace - mEdgeSpace) / (spanCount - 1) + mEdgeSpace;
//				right = eachSpace - left;
//			}
//		} else {
			// orientation == GridLayoutManager.HORIZONTAL 跟上面的大同小异, 将top,bottom替换为left,right即可
//			left = 0;
//			right = mrightSpace;
//			if (mEdgeSpace == 0) {
//				top = column * eachSpace / (spanCount - 1);
//				bottom = eachSpace - top;
//				if (itemCount / spanCount == row) {
//					right = 0;
//				}
//			} else {
//				if (childPosition < spanCount) {
//					left = mEdgeSpace;
//				} else if (itemCount / spanCount == row) {
//					right = mEdgeSpace;
//				}
//				top = column * (eachSpace - mEdgeSpace - mEdgeSpace) / (spanCount - 1) + mEdgeSpace;
//				bottom = eachSpace - top;
//			}
//		}
		System.out.println(childPosition+",left:"+left+",right:"+right+",top:"+top+",bottom:"+bottom);
		outRect.set((int) left, (int) top, (int) right, (int) bottom);
	}

	private void drawLinear(int orientation, Canvas c, RecyclerView parent) {
		if (orientation == LinearLayoutManager.VERTICAL) {
			final int left = parent.getPaddingLeft();
			final int right = parent.getWidth() - parent.getPaddingRight();

			final int childCount = parent.getChildCount();
			for (int i = 0; i < childCount; i++) {
				final View child = parent.getChildAt(i);
				final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
						.getLayoutParams();
				final int top = child.getBottom() + params.bottomMargin +
						Math.round(ViewCompat.getTranslationY(child));
				final int bottom = top + mleftSpace;
				c.drawRect(left, top, right, bottom, mDividerPaint);
			}

		} else if (orientation == LinearLayoutManager.HORIZONTAL) {
			final int top = parent.getPaddingTop();
			final int bottom = parent.getHeight() - parent.getPaddingBottom();
			final int childCount = parent.getChildCount();
			for (int i = 0; i < childCount; i++) {
				final View child = parent.getChildAt(i);
				final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
						.getLayoutParams();
				final int left = child.getRight() + params.rightMargin +
						Math.round(ViewCompat.getTranslationX(child));
				final int right = left + mleftSpace;
				c.drawRect(left, top, right, bottom, mDividerPaint);
			}
		}
	}
}


