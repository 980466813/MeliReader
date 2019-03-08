// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.user.info;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserInfoFragment_ViewBinding implements Unbinder {
  private UserInfoFragment target;

  private View view2131231294;

  private View view2131231286;

  private View view2131231288;

  private View view2131231292;

  private View view2131231296;

  private View view2131231290;

  @UiThread
  public UserInfoFragment_ViewBinding(final UserInfoFragment target, View source) {
    this.target = target;

    View view;
    target.userInfoNicknameTv = Utils.findRequiredViewAsType(source, R.id.user_info_nickname_tv, "field 'userInfoNicknameTv'", AppCompatTextView.class);
    target.userInfoProfileIv = Utils.findRequiredViewAsType(source, R.id.user_info_profile_iv, "field 'userInfoProfileIv'", AppCompatImageView.class);
    target.userInfoAddressIv = Utils.findRequiredViewAsType(source, R.id.user_info_address_tv, "field 'userInfoAddressIv'", AppCompatTextView.class);
    target.userInfoGenderTv = Utils.findRequiredViewAsType(source, R.id.user_info_gender_tv, "field 'userInfoGenderTv'", AppCompatTextView.class);
    target.userInfoBirthdayTv = Utils.findRequiredViewAsType(source, R.id.user_info_birthday_tv, "field 'userInfoBirthdayTv'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.user_info_profile_cl, "method 'onClick'");
    view2131231294 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.user_info_address_cl, "method 'onClick'");
    view2131231286 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.user_info_birthday_cl, "method 'onClick'");
    view2131231288 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.user_info_nickname_cl, "method 'onClick'");
    view2131231292 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.user_info_signature_cl, "method 'onClick'");
    view2131231296 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.user_info_gender_cl, "method 'onClick'");
    view2131231290 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    UserInfoFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userInfoNicknameTv = null;
    target.userInfoProfileIv = null;
    target.userInfoAddressIv = null;
    target.userInfoGenderTv = null;
    target.userInfoBirthdayTv = null;

    view2131231294.setOnClickListener(null);
    view2131231294 = null;
    view2131231286.setOnClickListener(null);
    view2131231286 = null;
    view2131231288.setOnClickListener(null);
    view2131231288 = null;
    view2131231292.setOnClickListener(null);
    view2131231292 = null;
    view2131231296.setOnClickListener(null);
    view2131231296 = null;
    view2131231290.setOnClickListener(null);
    view2131231290 = null;
  }
}
