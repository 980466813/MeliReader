// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.search;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchResultFragment_ViewBinding implements Unbinder {
  private SearchResultFragment target;

  private View view2131231166;

  private View view2131231240;

  @UiThread
  public SearchResultFragment_ViewBinding(final SearchResultFragment target, View source) {
    this.target = target;

    View view;
    target.searchResultPagerVg = Utils.findRequiredViewAsType(source, R.id.search_result_pager_vg, "field 'searchResultPagerVg'", ViewPager.class);
    target.searchResultTabTl = Utils.findRequiredViewAsType(source, R.id.search_result_tab_tl, "field 'searchResultTabTl'", TabLayout.class);
    view = Utils.findRequiredView(source, R.id.search_result_keyword_tv, "field 'searchResultKeywordTv' and method 'onKeywordTvClick'");
    target.searchResultKeywordTv = Utils.castView(view, R.id.search_result_keyword_tv, "field 'searchResultKeywordTv'", AppCompatTextView.class);
    view2131231166 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onKeywordTvClick();
      }
    });
    target.searchResultRefreshSrl = Utils.findRequiredViewAsType(source, R.id.search_result_refresh_srl, "field 'searchResultRefreshSrl'", SmartRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.toolbar_left_btn, "method 'onNavigationClickListener'");
    view2131231240 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onNavigationClickListener(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchResultFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.searchResultPagerVg = null;
    target.searchResultTabTl = null;
    target.searchResultKeywordTv = null;
    target.searchResultRefreshSrl = null;

    view2131231166.setOnClickListener(null);
    view2131231166 = null;
    view2131231240.setOnClickListener(null);
    view2131231240 = null;
  }
}
