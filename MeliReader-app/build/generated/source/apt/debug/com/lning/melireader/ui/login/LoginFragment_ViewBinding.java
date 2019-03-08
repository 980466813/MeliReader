// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.login;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.CompoundButton;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginFragment_ViewBinding implements Unbinder {
  private LoginFragment target;

  private View view2131231057;

  private View view2131231049;

  private View view2131231046;

  private View view2131231053;

  @UiThread
  public LoginFragment_ViewBinding(final LoginFragment target, View source) {
    this.target = target;

    View view;
    target.loginPasswordEt = Utils.findRequiredViewAsType(source, R.id.login_password_et, "field 'loginPasswordEt'", AppCompatEditText.class);
    view = Utils.findRequiredView(source, R.id.login_username_et, "field 'loginUsernameEt' and method 'onLoginUsernameFocusChange'");
    target.loginUsernameEt = Utils.castView(view, R.id.login_username_et, "field 'loginUsernameEt'", AppCompatEditText.class);
    view2131231057 = view;
    view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View p0, boolean p1) {
        target.onLoginUsernameFocusChange(p1);
      }
    });
    target.loginUserIconIv = Utils.findRequiredViewAsType(source, R.id.login_user_icon_iv, "field 'loginUserIconIv'", AppCompatImageView.class);
    view = Utils.findRequiredView(source, R.id.login_password_cb, "method 'onPasswordShowCbCheck'");
    view2131231049 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onPasswordShowCbCheck(p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.login_btn, "method 'onLoginClick'");
    view2131231046 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onLoginClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.login_register_tv, "method 'onRegisterClick'");
    view2131231053 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRegisterClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.loginPasswordEt = null;
    target.loginUsernameEt = null;
    target.loginUserIconIv = null;

    view2131231057.setOnFocusChangeListener(null);
    view2131231057 = null;
    ((CompoundButton) view2131231049).setOnCheckedChangeListener(null);
    view2131231049 = null;
    view2131231046.setOnClickListener(null);
    view2131231046 = null;
    view2131231053.setOnClickListener(null);
    view2131231053 = null;
  }
}
