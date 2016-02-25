package com.base.app.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

import com.base.app.R;
import com.base.app.bean.BaseUI;
import com.base.app.common.Constant;
import com.base.app.global.BaseApplication;
import com.base.app.imageloader.fresco.ConfigConstants;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;

public class BaseActivity extends FragmentActivity implements BaseUI{

	public static final String TAG = BaseActivity.class.getSimpleName();
	private BaseApplication mApplication;
	private View emptyView;
	
	@SuppressLint("UseSparseArrays") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		Constant.screenW = displayMetrics.widthPixels;
		Constant.screenH = displayMetrics.heightPixels;
		
		mApplication = BaseApplication.getInstance();
		mApplication.addActivity(this);
		
		FLog.setMinimumLoggingLevel(FLog.WARN);// 日志打印等级
		ConfigConstants.init(getResources());// 初始化默认图片（占位图，错误图）
		Fresco.initialize(this,ConfigConstants.getImagePipelineConfig(this));// 图片缓存初始化配置
		
		init();
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ButterKnife.bind(this);
	}

	@Override
	protected void onDestroy() {
		Fresco.shutDown();
		mApplication.removeActivity(this);
		super.onDestroy();
	}
	
	
	public void setListEmptyView()
	{
		loadEmptyView(R.layout.empty_view);
	}
	
	public void loadEmptyView(int resLayoutiId)
	{
		if(emptyView == null)
		{
			emptyView = getLayoutInflater().inflate(resLayoutiId, null);
			emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
			emptyView.setVisibility(View.GONE);
		}
	}

	public void init() {}
	
	@Override
	public void onUICallback(int type, Object object) {
	}
	
}
