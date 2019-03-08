// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.user;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserCenterFragment_ViewBinding implements Unbinder {
  private UserCenterFragment target;

  private View view2131231271;

  private View view2131231272;

  private View view2131231279;

  private View view2131231275;

  private View view2131231269;

  private View view2131231276;

  private View view2131231267;

  @UiThread
  public UserCenterFragment_ViewBinding(final UserCenterFragment target, View source) {
    this.target = target;

    View view;
    target.userCenterLoginCl = Utils.findRequiredViewAsType(source, R.id.user_center_login_cl, "field 'userCenterLoginCl'", ConstraintLayout.class);
    view = Utils.findRequiredView(source, R.id.user_center_info_cl, "field 'userCenterInfoCl' and method 'onUserInfoClick'");
    target.userCenterInfoCl = Utils.castView(view, R.id.user_center_info_cl, "field 'userCenterInfoCl'", ConstraintLayout.class);
    view2131231271 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onUserInfoClick();
      }
    });
    target.userCenterIconIv = Utils.findRequiredViewAsType(source, R.id.user_center_icon_iv, "field 'userCenterIconIv'", AppCompatImageView.class);
    target.userCenterNameTv = Utils.findRequiredViewAsType(source, R.id.user_center_name_tv, "field 'userCenterNameTv'", AppCompatTextView.class);
    target.userCenterAttentionTv = Utils.findRequiredViewAsType(source, R.id.user_center_attention_tv, "field 'userCenterAttentionTv'", AppCompatTextView.class);
    target.userCenterFansTv = Utils.findRequiredViewAsType(source, R.id.user_center_fans_tv, "field 'userCenterFansTv'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.user_center_login_btn, "method 'onLoginClick'");
    view2131231272 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onLoginClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.user_center_setting_rl, "method 'onSettingClick'");
    view2131231279 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSettingClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.user_center_manage_channel_rl, "method 'onManageChannelClick'");
    view2131231275 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onManageChannelClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.user_center_history_ll, "method 'onThreeClick'");
    view2131231269 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onThreeClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.user_center_message_ll, "method 'onThreeClick'");
    view2131231276 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onThreeClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.user_center_collection_ll, "method 'onThreeClick'");
    view2131231267 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onThreeClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    UserCenterFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userCenterLoginCl = null;
    target.userCenterInfoCl = null;
    target.userCenterIconIv = null;
    target.userCenterNameTv = null;
    target.userCenterAttentionTv = null;
    target.userCenterFansTv = null;

    view2131231271.setOnClickListener(null);
    view2131231271 = null;
    view2131231272.setOnClickListener(null);
    view2131231272 = null;
    view2131231279.setOnClickListener(null);
    view2131231279 = null;
    view2131231275.setOnClickListener(null);
    view2131231275 = null;
    view2131231269.setOnClickListener(null);
    view2131231269 = null;
    view2131231276.setOnClickListener(null);
    view2131231276 = null;
    view2131231267.setOnClickListener(null);
    view2131231267 = null;
  }
}
