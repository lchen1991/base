// Generated code from Butter Knife. Do not modify!
package com.base.app;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DebugActivity$$ViewBinder<T extends com.base.app.DebugActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131034162, "field 'tab_layout_4' and method 'onClick'");
    target.tab_layout_4 = finder.castView(view, 2131034162, "field 'tab_layout_4'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131034151, "field 'viewPager'");
    target.viewPager = finder.castView(view, 2131034151, "field 'viewPager'");
    view = finder.findRequiredView(source, 2131034157, "field 'tab2'");
    target.tab2 = finder.castView(view, 2131034157, "field 'tab2'");
    view = finder.findRequiredView(source, 2131034158, "field 'tab2_select'");
    target.tab2_select = finder.castView(view, 2131034158, "field 'tab2_select'");
    view = finder.findRequiredView(source, 2131034155, "field 'tab1_select'");
    target.tab1_select = finder.castView(view, 2131034155, "field 'tab1_select'");
    view = finder.findRequiredView(source, 2131034156, "field 'tab_layout_2' and method 'onClick'");
    target.tab_layout_2 = finder.castView(view, 2131034156, "field 'tab_layout_2'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131034154, "field 'tab1'");
    target.tab1 = finder.castView(view, 2131034154, "field 'tab1'");
    view = finder.findRequiredView(source, 2131034160, "field 'tab3'");
    target.tab3 = finder.castView(view, 2131034160, "field 'tab3'");
    view = finder.findRequiredView(source, 2131034159, "field 'tab_layout_3' and method 'onClick'");
    target.tab_layout_3 = finder.castView(view, 2131034159, "field 'tab_layout_3'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131034164, "field 'tab4_select'");
    target.tab4_select = finder.castView(view, 2131034164, "field 'tab4_select'");
    view = finder.findRequiredView(source, 2131034161, "field 'tab3_select'");
    target.tab3_select = finder.castView(view, 2131034161, "field 'tab3_select'");
    view = finder.findRequiredView(source, 2131034163, "field 'tab4'");
    target.tab4 = finder.castView(view, 2131034163, "field 'tab4'");
    view = finder.findRequiredView(source, 2131034153, "field 'tab_layout_1' and method 'onClick'");
    target.tab_layout_1 = finder.castView(view, 2131034153, "field 'tab_layout_1'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.tab_layout_4 = null;
    target.viewPager = null;
    target.tab2 = null;
    target.tab2_select = null;
    target.tab1_select = null;
    target.tab_layout_2 = null;
    target.tab1 = null;
    target.tab3 = null;
    target.tab_layout_3 = null;
    target.tab4_select = null;
    target.tab3_select = null;
    target.tab4 = null;
    target.tab_layout_1 = null;
  }
}
