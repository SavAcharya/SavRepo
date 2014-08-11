package com.ridenow.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.ridenow.ride.uinterface.OnDetectScrollListener;

public class ViewList extends android.widget.ListView {

    private OnScrollListener onScrollListener;
    private OnDetectScrollListener onDetectScrollListener;

    public ViewList(Context context) {
        super(context);
       onCreate(context, null, null);
    }

    public ViewList(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreate(context, attrs, null);
    }

    public ViewList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        onCreate(context, attrs, defStyle);
    }

    @SuppressWarnings("UnusedParameters")
    private void onCreate(Context context, AttributeSet attrs, Integer defStyle) {
        setListeners();
    }

    private void setListeners() {
        super.setOnScrollListener(new OnScrollListener() {

            private int oldTop;
            private int oldFirstVisibleItem;

            @Override
            public void onScroll(android.widget.AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
				 if (onScrollListener != null) {
	                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
	                }

	                if (onDetectScrollListener != null) {
	                    onDetectedListScroll(view, firstVisibleItem);
	                }
	            
            }

            

            private void onDetectedListScroll(android.widget.AbsListView view2, int firstVisibleItem) {
                View view = view2.getChildAt(0);
                int top = (view == null) ? 0 : view.getTop();

                if (firstVisibleItem == oldFirstVisibleItem) {
                    if (top > oldTop) {
                        onDetectScrollListener.onUpScrolling();
                    } else if (top < oldTop) {
                        onDetectScrollListener.onDownScrolling();
                    }
                } else {
                    if (firstVisibleItem < oldFirstVisibleItem) {
                        onDetectScrollListener.onUpScrolling();
                    } else {
                        onDetectScrollListener.onDownScrolling();
                    }
                }

                oldTop = top;
                oldFirstVisibleItem = firstVisibleItem;
            }

			
			@Override
            public void onScrollStateChanged(android.widget.AbsListView view, int scrollState)
            {
				  if (onScrollListener != null) {
	                    onScrollListener.onScrollStateChanged(view, scrollState);
	                }
	            
            }
        });
    }

    @Override
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public void setOnDetectScrollListener(OnDetectScrollListener onDetectScrollListener) {
        this.onDetectScrollListener = onDetectScrollListener;
    }
}
