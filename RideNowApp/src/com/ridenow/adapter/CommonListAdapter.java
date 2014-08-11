package com.ridenow.adapter;

import java.util.List;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * 
 * 
 * @author Manoj Prasad
 * 
 * @param <T>
 */
public abstract class CommonListAdapter<T> extends ArrayAdapter<T>
{

	private static final String TAG = "BucketListAdapter";
	private final boolean DEBUG = false;
	protected Activity ctx;
	protected Integer bucketSize;

	public CommonListAdapter(Activity ctx, List<T> elements)
	{
		this(ctx, elements, 1);
	}

	public CommonListAdapter(Activity ctx, List<T> elements, Integer bucketSize)
	{
		super(ctx, 0, elements);
		this.ctx = ctx;
		this.bucketSize = bucketSize;
	}

	public void enableAutoMeasure(float minBucketElementWidthDip)
	{
		float screenWidth = getScreenWidthInDip();

		if (minBucketElementWidthDip >= screenWidth)
		{
			bucketSize = 1;
		}
		else
		{
			bucketSize = (int) (screenWidth / minBucketElementWidthDip);
		}
	}

	@Override
	public int getCount()
	{
		return (super.getCount() + bucketSize - 1) / bucketSize;
	}

	@Override
	public T getItem(int position)
	{
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int bucketPosition, View convertView, ViewGroup parent)
	{
		LinearLayout bucket;
		if (convertView != null)
		{
			bucket = (LinearLayout) convertView;
		}
		else
		{
			bucket = new LinearLayout(ctx);
			bucket.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
			bucket.setOrientation(LinearLayout.HORIZONTAL);
		}
		int j = 0;
		for (int i = (bucketPosition * bucketSize); i < ((bucketPosition * bucketSize) + bucketSize); i++)
		{
			FrameLayout bucketElementFrame;
			if (j < bucket.getChildCount())
			{
				bucketElementFrame = (FrameLayout) bucket.getChildAt(j);
				if (i < super.getCount())
				{
					View currentConvertView = bucketElementFrame.getChildAt(0);
				   if (DEBUG)
					{
						Log.i(TAG, "Reusing element view");
					}
					if (currentConvertView != null)
					{
						currentConvertView.setVisibility(View.VISIBLE);
					}
				}
				else
				{
					View currentConvertView = bucketElementFrame.getChildAt(0);
					if (currentConvertView != null)
					{
						currentConvertView.setVisibility(View.GONE);
					}
				}
			}
			else
			{
				bucketElementFrame = new FrameLayout(ctx);
				bucketElementFrame.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT, 1));

				if (i < super.getCount())
				{
					View current = getMyElement(i, getItem(i), null);
					bucketElementFrame.addView(current);
				}
				bucket.addView(bucketElementFrame);
			}
			j++;
		}

		return bucket;
	}

	protected abstract View getMyElement(final int position, T currentElement, View convertView);

	protected float getScreenWidthInDip()
	{
		WindowManager wm = ctx.getWindowManager();
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int screenWidth_in_pixel = dm.widthPixels;
		float screenWidth_in_dip = screenWidth_in_pixel / dm.density;

		return screenWidth_in_dip;
	}
}
