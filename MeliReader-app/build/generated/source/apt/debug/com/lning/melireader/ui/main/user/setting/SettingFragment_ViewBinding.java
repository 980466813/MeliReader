// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.user.setting;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingFragment_ViewBinding implements Unbinder {
  private SettingFragment target;

  private View view2131231194;

  private View view2131231188;

  private View view2131231181;

  private View view2131231177;

  private View view2131231174;

  private View view2131231185;

  @UiThread
  public SettingFragment_ViewBinding(final SettingFragment target, View source) {
    this.target = target;

    View view;
    target.settingTextTv = Utils.findRequiredViewAsType(source, R.id.setting_text_tv, "field 'settingTextTv'", AppCompatTextView.class);
    target.settingClearTv = Utils.findRequiredViewAsType(source, R.id.setting_clear_tv, "field 'settingClearTv'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.setting_wifi_tips_sc, "field 'settingWifiTipsSc' and method 'onWifiTipsScCheckChange'");
    target.settingWifiTipsSc = Utils.castView(view, R.id.setting_wifi_tips_sc, "field 'settingWifiTipsSc'", SwitchCompat.class);
    view2131231194 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onWifiTipsScCheckChange(p1);
      }
    });
    target.settingUpdateTv = Utils.findRequiredViewAsType(source, R.id.setting_update_tv, "field 'settingUpdateTv'", AppCompatTextView.class);
    target.settingUserTv = Utils.findRequiredViewAsType(source, R.id.setting_user_tv, "field 'settingUserTv'", AppCompatTextView.class);
    target.settingUserStatusTv = Utils.findRequiredViewAsType(source, R.id.setting_user_status_tv, "field 'settingUserStatusTv'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.setting_user_cl, "method 'onSettingUserClick'");
    view2131231188 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSettingUserClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.setting_text_cl, "method 'onSettingTextClick'");
    view2131231181 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSettingTextClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.setting_clear_cl, "method 'onSettingClearClick'");
    view2131231177 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSettingClearClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.setting_about_cl, "method 'onSettingAboutClick'");
    view2131231174 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSettingAboutClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.setting_update_cl, "method 'onSettingUpdateClick'");
    view2131231185 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSettingUpdateClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SettingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.settingTextTv = null;
    target.settingClearTv = null;
    target.settingWifiTipsSc = null;
    target.settingUpdateTv = null;
    target.settingUserTv = null;
    target.settingUserStatusTv = null;

    ((CompoundButton) view2131231194).setOnCheckedChangeListener(null);
    view2131231194 = null;
    view2131231188.setOnClickListener(null);
    view2131231188 = null;
    view2131231181.setOnClickListener(null);
    view2131231181 = null;
    view2131231177.setOnClickListener(null);
    view2131231177 = null;
    view2131231174.setOnClickListener(null);
    view2131231174 = null;
    view2131231185.setOnClickListener(null);
    view2131231185 = null;
  }
}
