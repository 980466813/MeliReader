// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.user.history;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HistoryFragment_ViewBinding implements Unbinder {
  private HistoryFragment target;

  @UiThread
  public HistoryFragment_ViewBinding(HistoryFragment target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.toolbarLeftBtn = Utils.findRequiredViewAsType(source, R.id.toolbar_left_btn, "field 'toolbarLeftBtn'", AppCompatImageButton.class);
    target.toolbarTabTl = Utils.findRequiredViewAsType(source, R.id.toolbar_tab_tl, "field 'toolbarTabTl'", TabLayout.class);
    target.toolbarRightBtn = Utils.findRequiredViewAsType(source, R.id.toolbar_right_btn, "field 'toolbarRightBtn'", AppCompatTextView.class);
    target.historyContainerVp = Utils.findRequiredViewAsType(source, R.id.history_container_vp, "field 'historyContainerVp'", ViewPager.class);
    target.mRefreshLayout = Utils.findRequiredViewAsType(source, R.id.view_base_refresh_layout, "field 'mRefreshLayout'", SmartRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HistoryFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.toolbarLeftBtn = null;
    target.toolbarTabTl = null;
    target.toolbarRightBtn = null;
    target.historyContainerVp = null;
    target.mRefreshLayout = null;
  }
}
