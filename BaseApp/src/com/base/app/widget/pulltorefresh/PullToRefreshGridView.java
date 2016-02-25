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
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.base.app.R;
import com.base.app.widget.pulltorefresh.internal.EmptyViewMethodAccessor;
import com.base.app.widget.pulltorefresh.internal.LoadingLayout;

public class PullToRefreshGridView extends
		PullToRefreshAdapterViewBase<GridView> {

	private boolean mGridViewExtrasEnabled;
	private LoadingLayout mHeaderLoadingView;

	public PullToRefreshGridView(Context context) {
		super(context);
	}

	public PullToRefreshGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullToRefreshGridView(Context context, Mode mode) {
		super(context, mode);
	}

	public PullToRefreshGridView(Context context, Mode mode,
			AnimationStyle style) {
		super(context, mode, style);
	}

	@Override
	public final Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	@Override
	protected final GridView createRefreshableView(Context context,
			AttributeSet attrs) {
		final GridView gv;
		if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			gv = new InternalGridViewSDK9(context, attrs);
		} else {
			gv = new InternalGridView(context, attrs);
		}

		// Use Generated ID (from res/values/ids.xml)
		gv.setId(R.id.gridview);
		return gv;
	}

	class InternalGridView extends GridView implements EmptyViewMethodAccessor {

		public InternalGridView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyView(View emptyView) {
			PullToRefreshGridView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}
	}

	@TargetApi(9)
	final class InternalGridViewSDK9 extends InternalGridView {

		public InternalGridViewSDK9(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
				int scrollY, int scrollRangeX, int scrollRangeY,
				int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

			final boolean returnValue = super.overScrollBy(deltaX, deltaY,
					scrollX, scrollY, scrollRangeX, scrollRangeY,
					maxOverScrollX, maxOverScrollY, isTouchEvent);
			// Does all of the hard work...
			OverscrollHelper.overScrollBy(PullToRefreshGridView.this, deltaX,
					scrollX, deltaY, scrollY, isTouchEvent);
			return returnValue;
		}
	}

	@Override
	protected void onRefreshing(boolean doScroll) {
		/**
		 * If we're not showing the Refreshing view, or the list is empty, the
		 * the header/footer views won't show so we use the normal method.
		 */
		if (!mGridViewExtrasEnabled || !getShowViewWhileRefreshing()
		/* || null == adapter || adapter.isEmpty() */) {
			super.onRefreshing(doScroll);
			return;
		}

		super.onRefreshing(false);

		LoadingLayout origLoadingView = null;
		LoadingLayout gridViewLoadingView = null;
		int scrollToY = 0;

		switch (getCurrentMode()) {
		case MANUAL_REFRESH_ONLY:
		case PULL_FROM_END:
			break;
		case PULL_FROM_START:
		default:
			origLoadingView = getHeaderLayout();
			gridViewLoadingView = mHeaderLoadingView;
			scrollToY = getScrollY() + getHeaderSize();
			break;
		}
		origLoadingView.reset();
		origLoadingView.hideAllViews();

		// Show the WebView Loading View and set it to refresh.
		gridViewLoadingView.setVisibility(View.VISIBLE);
		gridViewLoadingView.refreshing();

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

		if (!mGridViewExtrasEnabled) {
			super.onReset();
			return;
		}
		LoadingLayout originalLoadingLayout = null, gridViewLoadingLayout = null;
		int scrollToHeight = 0;

		switch (getCurrentMode()) {
		case MANUAL_REFRESH_ONLY:
		case PULL_FROM_END:
			break;
		case PULL_FROM_START:
		default:
			originalLoadingLayout = getHeaderLayout();
			gridViewLoadingLayout = mHeaderLoadingView;
			scrollToHeight = -getHeaderSize();
			break;
		}

		// flip so that the original one is showing instead
		if (gridViewLoadingLayout.getVisibility() == View.VISIBLE) {
			// Set our Original View to Visible
			originalLoadingLayout.showInvisibleViews();

			// Hide the ListView Header/Footer
			gridViewLoadingLayout.setVisibility(View.GONE);

			/**
			 * Scroll so the View is at the same Y as the ListView
			 * header/footer, but only scroll if: we've pulled to refresh, it's
			 * positioned correctly
			 */
			setHeaderScroll(scrollToHeight);
		}

		mGridViewExtrasEnabled = false;
		super.onReset();
	}

	@Override
	protected LoadingLayoutProxy createLoadingLayoutProxy(
			final boolean includeStart, final boolean includeEnd) {
		LoadingLayoutProxy proxy = super.createLoadingLayoutProxy(includeStart,
				includeEnd);

		if (mGridViewExtrasEnabled) {
			final Mode mode = getMode();
			if (includeStart && mode.showHeaderLoadingLayout()) {
				proxy.addLayout(mHeaderLoadingView);
			}
		}
		return proxy;
	}

	@Override
	protected void handleStyledAttributes(TypedArray a) {
		super.handleStyledAttributes(a);
		
		mGridViewExtrasEnabled = a.getBoolean(
				R.styleable.PullToRefresh_ptrListViewExtrasEnabled, true);

		if (mGridViewExtrasEnabled) {
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
