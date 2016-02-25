package com.base.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.graphics.Path;
import android.graphics.drawable.shapes.Shape;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.OnClick;

import com.base.app.activity.NetBaseActivity;
import com.base.app.fragment.BaseFragment;
import com.base.app.imageloader.fresco.ConfigConstants;
import com.base.app.imageloader.fresco.instrumentation.InstrumentedDraweeView;
import com.base.app.imageloader.fresco.instrumentation.PerfListener;
import com.base.app.imageloader.fresco.zoomable.ZoomableDraweeView;
import com.base.app.net.manager.RequestManager;
import com.base.app.net.parser.ResponseDataToJSON;
import com.base.app.net.parser.ResponseDataToXML;
import com.base.app.utils.CommonUtils;
import com.base.app.utils.DialogUtils;
import com.base.app.utils.LogUtil;
import com.base.app.utils.ToastUtil;
import com.base.app.widget.ShimmerFrameLayout;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;

public class DebugActivity extends NetBaseActivity implements OnClickListener{

//	RelativeLayout relativeLayout;
//	Button mButton = null;
////	@Bind(R.id.main_img)
////	RelativeLayout relativeLayout;
//	ShimmerFrameLayout mShimmerViewContainer;
//	ZoomableDraweeView zoomableDraweeView;
//	InstrumentedDraweeView inDraweeView;
	String url = "https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=c9dd5806ab6eddc439e7b2fb09dab6a2/377adab44aed2e734cfdf0cf8101a18b87d6fa39.jpg";
	
	
	private ResponseDataToJSON jsonParser = new ResponseDataToJSON();
	private ResponseDataToXML xmlParser = new ResponseDataToXML();
	
	private  TextView textView;
	
	@Bind(R.id.tab_viewpage)
	ViewPager viewPager;
	@Bind(R.id.tab1)
	ImageView tab1;
	@Bind(R.id.tab1_select)
	ImageView tab1_select;
	@Bind(R.id.tab2)
	ImageView tab2;
	@Bind(R.id.tab2_select)
	ImageView tab2_select;
	@Bind(R.id.tab3)
	ImageView tab3;
	@Bind(R.id.tab3_select)
	ImageView tab3_select;
	@Bind(R.id.tab4)
	ImageView tab4;
	@Bind(R.id.tab4_select)
	ImageView tab4_select;
	List<ImageView> unIvTab = new ArrayList<ImageView>();
	List<ImageView> IvTab = new ArrayList<ImageView>();
	@Bind(R.id.tab_layout_1)
	FrameLayout tab_layout_1;
	@Bind(R.id.tab_layout_2)
	FrameLayout tab_layout_2;
	@Bind(R.id.tab_layout_3)
	FrameLayout tab_layout_3;
	@Bind(R.id.tab_layout_4)
	FrameLayout tab_layout_4;
	private String[] titles = new String[]{"微信","通讯录","发现","我"};

	private int mCurrentIndex = 0;
	private int mNextOrUpIndex = 0;
	private boolean isScroll = false;
	
	@SuppressLint("NewApi") @Override
	public void init() {
		Log.e("info", "init");
		setContentView(R.layout.activity_main_tab);
		RequestManager.getInstance().setParser(xmlParser);
		
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("password", "gf4s3HHhQiStHeN8lgAUtA==");
		maps.put("username", "cl1771066100");
		RequestManager.getInstance().post("http://ms.csdn.net/v3/login", maps, this, String.class, 1000);
		
		
//		maps.put("id", "1471841726");
//		addGetNetRequest("http://ms.csdn.net/api/blog/get_blog_detail?SessionId=bI4hwHjScQgrsgMMnqFYnhGVBZvjxUrmlN9wzhb5TQoObKtOFnu4KcrqK4iAjtrGyAJXbGCKLxtcDjxJCQE8XA%3D%3D&articleId=50169771&username=yzzst",
//				null,this, StringBuffer.class,false,R.id.REQUEST_TAG_USER_INFO);
		
		unIvTab.add(tab1);
		unIvTab.add(tab2);
		unIvTab.add(tab3);
		unIvTab.add(tab4);
		
		IvTab.add(tab1_select);
		IvTab.add(tab2_select);
		IvTab.add(tab3_select);
		IvTab.add(tab4_select);
		
		tab1_select.setAlpha(1.0f);
		tab2_select.setAlpha(0.0f);
		tab3_select.setAlpha(0.0f);
		tab4_select.setAlpha(0.0f);
		//设置图片大小
		//inDraweeView = new InstrumentedDraweeView(this, ConfigConstants.getGenericDraweeHierarchy(this))	;
//		CommonUtils.updateViewLayoutParams(inDraweeView, 500, 500);
//		inDraweeView.initInstrumentation(url, new PerfListener());
//		inDraweeView.setHierarchy(ConfigConstants.getGenericDraweeHierarchy(this));
//		inDraweeView.setController(ConfigConstants.getDraweeController(ConfigConstants.getImageRequest(inDraweeView,url), inDraweeView));
		//relativeLayout.addView(inDraweeView);
//		RequestManager.getInstance().setParser(jsonParser);
//		RequestManager.getInstance().post("http://221.194.44.189:8089//Data.jsp",
//				"{\"PGRequestType\":\"EdGetDatas\",\"password\":\"e10adc3949ba59abbe56e057f20f883e\",\"phone\":\"18811051758\",\"type\":\"0\",\"version\":\"1.0\",\"userType\":\"0\"}", this, VideoInfos.class, 1000);
//		RequestManager.getInstance().setParser(xmlParser);
//		RequestManager.getInstance().get("http://app.wenwen.sogou.com/front/rankoftoday?count=10", this, String.class, 1000);
	
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
//		CommonUtils.updateViewLayoutParams(zoomableDraweeView, mDisplayMetrics.widthPixels/2, mDisplayMetrics.heightPixels/2);
//		zoomableDraweeView.setHierarchy(ConfigConstants.getGenericDraweeHierarchy(this));
//		zoomableDraweeView.setController(
//                 Fresco.newDraweeControllerBuilder()
//                         .setUri(Uri.parse(url))
//                         .build());
		
//		DialogUtils dialogUtils =DialogUtils.getDialog(this);
//		dialogUtils.showCustomDialog(R.layout.dialog_base);
//		dialogUtils.show();
		
		viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				return unIvTab.size();
			}
			
			@Override
			public BaseFragment getItem(int arg0) {
				BaseFragment baseFragment = new BaseFragment();
				Bundle mBundle = new Bundle();
				mBundle.putString("title",titles[arg0] );
				baseFragment.setArguments(mBundle);
				return baseFragment;
			}
		});
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				Log.e("info", "onPageSelected -- arg0:"+arg0);
			}

			@SuppressLint("NewApi") @Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				Log.e("info", "onPageScrolled -- 当前滚动页:"+arg0+";当前值："+mCurrentIndex+";偏移值："+arg1);
				
				if (mCurrentIndex == arg0) // 向前中
				{
					Log.e("info","向前中...");
					int next = mCurrentIndex + 1;
					if (next < unIvTab.size()) {
//						if(mNextOrUpIndex!=next&&mNextOrUpIndex!=mCurrentIndex)
//						{
							IvTab.get(mNextOrUpIndex).setAlpha(0.0f);
//						}
						mNextOrUpIndex = next;
						ImageView mCurrIv = IvTab.get(mCurrentIndex);
						ImageView mNextIv = IvTab.get(next);
						mCurrIv.setAlpha(1 - arg1);
						mNextIv.setAlpha(arg1);
					}
				}
				else if (mCurrentIndex - 1 == arg0) // 向后中
				{
					Log.e("info","向后中...");
					int up = mCurrentIndex - 1;
					if (up >= 0) {
//						if(mNextOrUpIndex!=up&&mNextOrUpIndex!=mCurrentIndex)
//						{
							IvTab.get(mNextOrUpIndex).setAlpha(0.0f);
//						}
						mNextOrUpIndex = up;
						ImageView mCurrIv = IvTab.get(mCurrentIndex);
						ImageView mUpIv = IvTab.get(up);
						mCurrIv.setAlpha(arg1);
						mUpIv.setAlpha(1 - arg1);
					}
					mCurrentIndex = arg0;
				}
				else
				{
					Log.e("info","确定中...");
					IvTab.get(mCurrentIndex).setAlpha(0.0f);
//					if(mNextOrUpIndex!=arg0&&mNextOrUpIndex!=mCurrentIndex)
//					{
						IvTab.get(mNextOrUpIndex).setAlpha(0.0f);
//					}
					IvTab.get(arg0).setAlpha(1.0f);
					mCurrentIndex = arg0;
				}
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				Log.e("info", "onPageScrollStateChanged -- arg0:"+arg0);
			}
		});
	}

	
	@SuppressLint("NewApi") public void propertyValuesHolder(View view)  
    {  
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,0f);  
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY",view.getY(),view.getY()-150);
        ObjectAnimator.ofPropertyValuesHolder(view,pvhX, pvhY).setDuration(800)
        .start();  
    }  

	@SuppressLint("NewApi") @Override
	public boolean onTouchEvent(MotionEvent event) {
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_UP:
//			textView = new TextView(this);
//			textView.setText("+1");
//			textView.setTextColor(this.getResources().getColor(android.R.color.black));
//			textView.setTextSize(16);
//			relativeLayout.addView(textView);
//			textView.setX(event.getX());
//			textView.setY(event.getY());
//			propertyValuesHolder(textView);
//			break;

//		default:
//			break;
//		}
		return super.onTouchEvent(event);
	}
	
//	@OnClick({R.id.btn})
//    public void onClick(View view)
//    {
//		switch (view.getId()) {
//		case R.id.btn:
//			ToastUtil.showShort(this, "this is onClick 0");
//			break;
//		}
//    }

    @Override
    public void onNetResponseSuccess(Object response,
    		Map<String, String> headers, String url, int actionId) {
    	
    	switch (actionId) {
		case R.id.REQUEST_TAG_USER_INFO:
			LogUtil.e("info", "onNetResponseSuccess:REQUEST_TAG_USER_INFO"+((String)response).toString());
			break;

		default:
			break;
		}
    }

    @Override
    public void onNetResponseError(String errorMsg, String url, int actionId) {
    }
    
    private Toast mPresetToast;
    @Override
    protected void onStart() {
    	super.onStart();
//    	selectPreset(0, false);
    }
    
    @Override
    public void onResume() {
      super.onResume();
//      mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
//      mShimmerViewContainer.stopShimmerAnimation();
      super.onPause();
    }
    
    /**
     * Select one of the shimmer animation presets.
     *
     * @param preset index of the shimmer animation preset
     * @param showToast whether to show a toast describing the preset, or not
     */
//    private void selectPreset(int preset, boolean showToast) {
//      // Save the state of the animation
//      boolean isPlaying = mShimmerViewContainer.isAnimationStarted();
//
//      // Reset all parameters of the shimmer animation
//      mShimmerViewContainer.useDefaults();
//      // If a toast is already showing, hide it
//      if (mPresetToast != null) {
//        mPresetToast.cancel();
//      }
//
//      switch (preset) {
//        default:
//        case 0:
//          // Default
//          mPresetToast = Toast.makeText(this, "Default", Toast.LENGTH_SHORT);
//          break;
//        case 1:
//          // Slow and reverse
//          mShimmerViewContainer.setDuration(5000);
//          mShimmerViewContainer.setRepeatMode(ObjectAnimator.REVERSE);
//          mPresetToast = Toast.makeText(this, "Slow and reverse", Toast.LENGTH_SHORT);
//          break;
//        case 2:
//          // Thin, straight and transparent
//          mShimmerViewContainer.setBaseAlpha(0.1f);
//          mShimmerViewContainer.setDropoff(0.1f);
//          mShimmerViewContainer.setTilt(0);
//          mPresetToast = Toast.makeText(this, "Thin, straight and transparent", Toast.LENGTH_SHORT);
//          break;
//        case 3:
//          // Sweep angle 90
//          mShimmerViewContainer.setAngle(ShimmerFrameLayout.MaskAngle.CW_90);
//          mPresetToast = Toast.makeText(this, "Sweep angle 90", Toast.LENGTH_SHORT);
//          break;
//        case 4:
//          // Spotlight
//          mShimmerViewContainer.setBaseAlpha(0);
//          mShimmerViewContainer.setDuration(2000);
//          mShimmerViewContainer.setDropoff(0.1f);
//          mShimmerViewContainer.setIntensity(0.35f);
//          mShimmerViewContainer.setMaskShape(ShimmerFrameLayout.MaskShape.RADIAL);
//          mPresetToast = Toast.makeText(this, "Spotlight", Toast.LENGTH_SHORT);
//          break;
//      }
//
//      // Show toast describing the chosen preset, if necessary
//      if (showToast) {
//        mPresetToast.show();
//      }
//
//      // Setting a value on the shimmer layout stops the animation. Restart it, if necessary.
//      if (isPlaying) {
//        mShimmerViewContainer.startShimmerAnimation();
//      }
//    }


	@SuppressLint("NewApi")
	@OnClick({R.id.tab_layout_1,R.id.tab_layout_2,R.id.tab_layout_3,R.id.tab_layout_4})
	public void onClick(View v) {
		Log.e("info","curr : "+mCurrentIndex);
		switch (v.getId()) {
		case R.id.tab_layout_1:
//			IvTab.get(mCurrentIndex).setAlpha(0.0f);
//			mCurrentIndex = 0;
//			IvTab.get(mCurrentIndex).setAlpha(1.0f);
			viewPager.setCurrentItem(0,false);
			break;
		case R.id.tab_layout_2:
//			IvTab.get(mCurrentIndex).setAlpha(0.0f);
//			mCurrentIndex = 1;
//			IvTab.get(mCurrentIndex).setAlpha(1.0f);
			viewPager.setCurrentItem(1,false);
			break;
		case R.id.tab_layout_3:
//			IvTab.get(mCurrentIndex).setAlpha(0.0f);
//			mCurrentIndex = 2;
//			IvTab.get(mCurrentIndex).setAlpha(1.0f);
			viewPager.setCurrentItem(2,false);
			break;
		case R.id.tab_layout_4:
//			IvTab.get(mCurrentIndex).setAlpha(0.0f);
//			mCurrentIndex = 3;
//			IvTab.get(mCurrentIndex).setAlpha(1.0f);
			viewPager.setCurrentItem(3,false);
			break;
		}
	}
}
