/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.base.app.widget.pulltorefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.base.app.R;
import com.base.app.widget.pulltorefresh.internal.LoadingLayout;

public class PullToRefreshWebView extends PullToRefreshBase<WebView> {

	private boolean mWebViewExtrasEnabled;

	public PullToRefreshWebView(Context context) {
		super(context);

	}

	public PullToRefreshWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullToRefreshWebView(Context context, Mode mode) {
		super(context, mode);

		/**
		 * Added so that by default, Pull-to-Refresh refreshes the page
		 */
//		setOnRefreshListener(defaultOnRefreshListener);
//		mRefreshableView.setWebChromeClient(defaultWebChromeClient);
	}

	public PullToRefreshWebView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);

		/**
		 * Added so that by default, Pull-to-Refresh refreshes the page
		 */
//		setOnRefreshListener(defaultOnRefreshListener);
//		mRefreshableView.setWebChromeClient(defaultWebChromeClient);
	}

	@Override
	public final Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	@Override
	protected WebView createRefreshableView(Context context, AttributeSet attrs) {
		WebView webView;
		if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			webView = new InternalWebViewSDK9(context, attrs);
		} else {
			webView = new WebView(context, attrs);
		}

		webView.setId(R.id.webview);
		return webView;
	}

	@Override
	protected boolean isReadyForPullStart() {
		return mRefreshableView.getScrollY() == 0;
	}

	@Override
	protected boolean isReadyForPullEnd() {
		float exactContentHeight = FloatMath.floor(mRefreshableView.getContentHeight() * mRefreshableView.getScale());
		return mRefreshableView.getScrollY() >= (exactContentHeight - mRefreshableView.getHeight());
	}

	@Override
	protected void onPtrRestoreInstanceState(Bundle savedInstanceState) {
		super.onPtrRestoreInstanceState(savedInstanceState);
		mRefreshableView.restoreState(savedInstanceState);
	}

	@Override
	protected void onPtrSaveInstanceState(Bundle saveState) {
		super.onPtrSaveInstanceState(saveState);
		mRefreshableView.saveState(saveState);
	}

	@TargetApi(9)
	final class InternalWebViewSDK9 extends WebView {

		// WebView doesn't always scroll back to it's edge so we add some
		// fuzziness
		static final int OVERSCROLL_FUZZY_THRESHOLD = 2;

		// WebView seems quite reluctant to overscroll so we use the scale
		// factor to scale it's value
		static final float OVERSCROLL_SCALE_FACTOR = 1.5f;

		public InternalWebViewSDK9(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
				int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

			final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
					scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

			// Does all of the hard work...
			OverscrollHelper.overScrollBy(PullToRefreshWebView.this, deltaX, scrollX, deltaY, scrollY,
					getScrollRange(), OVERSCROLL_FUZZY_THRESHOLD, OVERSCROLL_SCALE_FACTOR, isTouchEvent);

			return returnValue;
		}

		private int getScrollRange() {
			return (int) Math.max(0, FloatMath.floor(mRefreshableView.getContentHeight() * mRefreshableView.getScale())
					- (getHeight() - getPaddingBottom() - getPaddingTop()));
		}
	}
	
	@Override
	protected void onRefreshing(boolean doScroll) {
		/**
		 * If we're not showing the Refreshing view, or the list is empty, the
		 * the header/footer views won't show so we use the normal method.
		 */
		if (!mWebViewExtrasEnabled|| !getShowViewWhileRefreshing() 
				/*|| null == adapter || adapter.isEmpty()*/) {
			super.onRefreshing(doScroll);
			return;
		}

		super.onRefreshing(false);

		LoadingLayout origLoadingView = null;
		LoadingLayout webViewLoadingView = null;
		int scrollToY = 0;

		switch (getCurrentMode()) {
			case MANUAL_REFRESH_ONLY:
			case PULL_FROM_END:
				break;
			case PULL_FROM_START:
			default:
				origLoadingView = getHeaderLayout();
				webViewLoadingView = mHeaderLoadingView;
				scrollToY = getScrollY() + getHeaderSize();
				break;
		}
		origLoadingView.reset();
		origLoadingView.hideAllViews();

		// Show the WebView Loading View and set it to refresh.
		webViewLoadingView.setVisibility(View.VISIBLE);
		webViewLoadingView.refreshing();

		if (doScroll) {
			// We need to disable the automatic visibility changes for now
			disableLoadingLayoutVisibilityChanges();

			// We scroll slightly so that the ListView's header/footer is at the
			// same Y position as our normal header/footer
			setHeaderScroll(scrollToY);
			// Make sure the ListView is scrolled to show the loading
			// header/footer

			// Smooth scroll as normal
			smoothScrollTo(0);
		}
	}
	
	@Override
	protected void onReset() {

		if (!mWebViewExtrasEnabled) {
			super.onReset();
			return;
		}
		LoadingLayout originalLoadingLayout = null, webViewLoadingLayout = null;
		int scrollToHeight = 0 ;

		switch (getCurrentMode()) {
			case MANUAL_REFRESH_ONLY:
			case PULL_FROM_END:
//				break;
			case PULL_FROM_START:
			default:
				originalLoadingLayout = getHeaderLayout();
				webViewLoadingLayout = mHeaderLoadingView;
				scrollToHeight = -getHeaderSize();
				break;
		}

		// flip so that the original one is showing instead
		if (webViewLoadingLayout!=null&&webViewLoadingLayout.getVisibility() == View.VISIBLE) {
			// Set our Original View to Visible
			originalLoadingLayout.showInvisibleViews();

			// Hide the ListView Header/Footer
			webViewLoadingLayout.setVisibility(View.GONE);

			/**
			 * Scroll so the View is at the same Y as the ListView
			 * header/footer, but only scroll if: we've pulled to refresh, it's
			 * positioned correctly
			 */
			setHeaderScroll(scrollToHeight);
		}
		else
		{
			
		}
		mWebViewExtrasEnabled=false;
		super.onReset();
	}
	
	@Override
	protected LoadingLayoutProxy createLoadingLayoutProxy(final boolean includeStart, final boolean includeEnd) {
		LoadingLayoutProxy proxy = super.createLoadingLayoutProxy(includeStart, includeEnd);

		if (mWebViewExtrasEnabled) {
			final Mode mode = getMode();

			if (includeStart && mode.showHeaderLoadingLayout()) {
				proxy.addLayout(mHeaderLoadingView);
			}
		}
		return proxy;
	}
	
	private LoadingLayout mHeaderLoadingView;
	
	@Override
	protected void handleStyledAttributes(TypedArray a) {
		super.handleStyledAttributes(a);

		mWebViewExtrasEnabled = a.getBoolean(R.styleable.PullToRefresh_ptrListViewExtrasEnabled, true);
		
		if(mWebViewExtrasEnabled)
		{
			final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);

			// Create Loading Views ready for use later
			FrameLayout frame = new FrameLayout(getContext());
			mHeaderLoadingView = createLoadingLayout(getContext(), Mode.PULL_FROM_START, a);
			mHeaderLoadingView.setVisibility(View.GONE);
			frame.addView(mHeaderLoadingView, lp);
			getRefreshableViewWrapper().addView(frame);
			/**
			 * If the value for Scrolling While Refreshing hasn't been
			 * explicitly set via XML, enable Scrolling While Refreshing.
			 */
			if (!a.hasValue(R.styleable.PullToRefresh_ptrScrollingWhileRefreshingEnabled)) {
				setScrollingWhileRefreshingEnabled(true);
			}
		}
		
	}
	
	
}
