// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.user.detail;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserFragment_ViewBinding implements Unbinder {
  private UserFragment target;

  private View view2131231298;

  private View view2131231283;

  private View view2131231262;

  private View view2131231240;

  @UiThread
  public UserFragment_ViewBinding(final UserFragment target, View source) {
    this.target = target;

    View view;
    target.userCollapsingTl = Utils.findRequiredViewAsType(source, R.id.user_collapsing_tl, "field 'userCollapsingTl'", CollapsingToolbarLayout.class);
    target.userIconIv = Utils.findRequiredViewAsType(source, R.id.user_icon_iv, "field 'userIconIv'", AppCompatImageView.class);
    target.userSummaryTv = Utils.findRequiredViewAsType(source, R.id.user_summary_tv, "field 'userSummaryTv'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.user_sub_cb, "field 'userSubCb' and method 'onSubChanged'");
    target.userSubCb = Utils.castView(view, R.id.user_sub_cb, "field 'userSubCb'", AppCompatCheckBox.class);
    view2131231298 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSubChanged();
      }
    });
    view = Utils.findRequiredView(source, R.id.user_edit_tv, "field 'userEditTv' and method 'onUserEditClick'");
    target.userEditTv = Utils.castView(view, R.id.user_edit_tv, "field 'userEditTv'", AppCompatTextView.class);
    view2131231283 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onUserEditClick();
      }
    });
    target.userTabTl = Utils.findRequiredViewAsType(source, R.id.user_tab_tl, "field 'userTabTl'", TabLayout.class);
    target.userAttentionTv = Utils.findRequiredViewAsType(source, R.id.user_attention_tv, "field 'userAttentionTv'", AppCompatTextView.class);
    target.userFansTv = Utils.findRequiredViewAsType(source, R.id.user_fans_tv, "field 'userFansTv'", AppCompatTextView.class);
    target.userPagerVp = Utils.findRequiredViewAsType(source, R.id.user_pager_vp, "field 'userPagerVp'", ViewPager.class);
    target.mRefreshLayout = Utils.findRequiredViewAsType(source, R.id.view_base_refresh_layout, "field 'mRefreshLayout'", SmartRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.user_arrow_iv, "method 'onSummaryClick'");
    view2131231262 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSummaryClick();
      }
    });
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
    UserFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userCollapsingTl = null;
    target.userIconIv = null;
    target.userSummaryTv = null;
    target.userSubCb = null;
    target.userEditTv = null;
    target.userTabTl = null;
    target.userAttentionTv = null;
    target.userFansTv = null;
    target.userPagerVp = null;
    target.mRefreshLayout = null;

    view2131231298.setOnClickListener(null);
    view2131231298 = null;
    view2131231283.setOnClickListener(null);
    view2131231283 = null;
    view2131231262.setOnClickListener(null);
    view2131231262 = null;
    view2131231240.setOnClickListener(null);
    view2131231240 = null;
  }
}
