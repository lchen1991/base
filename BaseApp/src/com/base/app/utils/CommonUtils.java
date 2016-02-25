package com.base.app.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout.LayoutParams;

public class CommonUtils {

	//∫· ˙∆¡º∆À„Õº∆¨øÌ∏ﬂ
	public static int calcDesiredSize(Context context,int parentWidth, int parentHeight) {
		int orientation = context.getResources().getConfiguration().orientation;
		int desiredSize = (orientation == Configuration.ORIENTATION_LANDSCAPE) ? parentHeight / 2 : parentHeight / 3;
		return Math.min(desiredSize, parentWidth);
	}
	
	//∫· ˙∆¡…Ë÷√Õº∆¨øÌ∏ﬂ
	public static void updateViewLayoutParams(View view, int width, int height) {
		if (view.getLayoutParams() == null || view.getLayoutParams().height != width || view.getLayoutParams().width != height) {
			view.getLayoutParams().width = width;
			view.getLayoutParams().height = height;
		}
	}
}
