// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import com.lning.melireader.widget.BaseViewPager;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainFragment_ViewBinding implements Unbinder {
  private MainFragment target;

  @UiThread
  public MainFragment_ViewBinding(MainFragment target, View source) {
    this.target = target;

    target.mainContainerFl = Utils.findRequiredViewAsType(source, R.id.main_container_fl, "field 'mainContainerFl'", BaseViewPager.class);
    target.mainBottomBbl = Utils.findRequiredViewAsType(source, R.id.main_bottom_bbl, "field 'mainBottomBbl'", TabLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mainContainerFl = null;
    target.mainBottomBbl = null;
  }
}
