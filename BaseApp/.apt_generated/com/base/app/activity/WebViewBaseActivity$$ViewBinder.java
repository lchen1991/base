// Generated code from Butter Knife. Do not modify!
package com.base.app.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WebViewBaseActivity$$ViewBinder<T extends com.base.app.activity.WebViewBaseActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131034141, "field 'mPullToRefreshWebView'");
    target.mPullToRefreshWebView = finder.castView(view, 2131034141, "field 'mPullToRefreshWebView'");
  }

  @Override public void unbind(T target) {
    target.mPullToRefreshWebView = null;
  }
}
