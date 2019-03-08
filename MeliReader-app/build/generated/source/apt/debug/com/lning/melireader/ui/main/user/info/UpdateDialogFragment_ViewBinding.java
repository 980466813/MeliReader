// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.user.info;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.RadioGroup;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UpdateDialogFragment_ViewBinding implements Unbinder {
  private UpdateDialogFragment target;

  @UiThread
  public UpdateDialogFragment_ViewBinding(UpdateDialogFragment target, View source) {
    this.target = target;

    target.userInfoSigatureCl = Utils.findRequiredViewAsType(source, R.id.user_info_signature_cl, "field 'userInfoSigatureCl'", AppCompatEditText.class);
    target.userInfoNicknameTv = Utils.findRequiredViewAsType(source, R.id.user_info_nickname_tv, "field 'userInfoNicknameTv'", AppCompatEditText.class);
    target.userInfoAddressIv = Utils.findRequiredViewAsType(source, R.id.user_info_address_tv, "field 'userInfoAddressIv'", AppCompatEditText.class);
    target.userInfoGenderTv = Utils.findRequiredViewAsType(source, R.id.user_info_gender_tv, "field 'userInfoGenderTv'", RadioGroup.class);
    target.userInfoBirthdayTv = Utils.findRequiredViewAsType(source, R.id.user_info_birthday_tv, "field 'userInfoBirthdayTv'", AppCompatEditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    UpdateDialogFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userInfoSigatureCl = null;
    target.userInfoNicknameTv = null;
    target.userInfoAddressIv = null;
    target.userInfoGenderTv = null;
    target.userInfoBirthdayTv = null;
  }
}
