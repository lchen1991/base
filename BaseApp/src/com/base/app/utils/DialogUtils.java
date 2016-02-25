package com.base.app.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.base.app.R;
import com.base.app.common.Constant;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;

public class DialogUtils extends Dialog {

	private int showSize = 2;
	
	private DialogUtils(Context context) {
		super(context);
		Window window = this.getWindow();
		window.setBackgroundDrawableResource(android.R.color.transparent);
		window.setLayout(Constant.screenW / showSize, Constant.screenH / (showSize*2));

	}

	public static DialogUtils getDialog(Context context)
	{
		return new DialogUtils(context);
	}
	
	public void showCustomDialog(int layoutId)
	{
		View view = LayoutInflater.from(getContext()).inflate(layoutId, null);
		ButterKnife.bind(this,view);
		setContentView(view);
	}
	
	@SuppressLint("NewApi") @OnClick({R.id.btn1,R.id.btn2})
    public void onClick(View view)
    {
		switch (view.getId()) {
		case R.id.btn1:
			ToastUtil.showShort(getContext(), "this is onClick 1");
			break;
		case R.id.btn2:
			ToastUtil.showShort(getContext(), "this is onClick 2");
			break;
		}
    }
	
}
