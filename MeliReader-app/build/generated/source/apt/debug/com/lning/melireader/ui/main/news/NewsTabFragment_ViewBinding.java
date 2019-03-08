// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.news;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsTabFragment_ViewBinding implements Unbinder {
  private NewsTabFragment target;

  private View view2131231003;

  private View view2131231001;

  @UiThread
  public NewsTabFragment_ViewBinding(final NewsTabFragment target, View source) {
    this.target = target;

    View view;
    target.homePagerVg = Utils.findRequiredViewAsType(source, R.id.home_pager_vg, "field 'homePagerVg'", ViewPager.class);
    target.homeTabTl = Utils.findRequiredViewAsType(source, R.id.home_tab_tl, "field 'homeTabTl'", TabLayout.class);
    target.homeRefreshSrl = Utils.findRequiredViewAsType(source, R.id.home_refresh_srl, "field 'homeRefreshSrl'", SmartRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.home_user_icon_iv, "field 'homeUserIconIv' and method 'onClick'");
    target.homeUserIconIv = Utils.castView(view, R.id.home_user_icon_iv, "field 'homeUserIconIv'", AppCompatImageView.class);
    view2131231003 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.home_search_rl, "method 'onSearchClick'");
    view2131231001 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSearchClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsTabFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.homePagerVg = null;
    target.homeTabTl = null;
    target.homeRefreshSrl = null;
    target.homeUserIconIv = null;

    view2131231003.setOnClickListener(null);
    view2131231003 = null;
    view2131231001.setOnClickListener(null);
    view2131231001 = null;
  }
}
