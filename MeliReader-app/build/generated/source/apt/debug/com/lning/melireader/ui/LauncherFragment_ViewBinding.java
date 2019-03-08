// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LauncherFragment_ViewBinding implements Unbinder {
  private LauncherFragment target;

  private View view2131231027;

  @UiThread
  public LauncherFragment_ViewBinding(final LauncherFragment target, View source) {
    this.target = target;

    View view;
    target.launcherShowIv = Utils.findRequiredViewAsType(source, R.id.launcher_show_iv, "field 'launcherShowIv'", ImageView.class);
    target.launcherLoadingPg = Utils.findRequiredViewAsType(source, R.id.launcher_loading_pg, "field 'launcherLoadingPg'", ProgressBar.class);
    view = Utils.findRequiredView(source, R.id.launcher_skip_btn, "field 'launcherSkipBtn' and method 'onClick'");
    target.launcherSkipBtn = Utils.castView(view, R.id.launcher_skip_btn, "field 'launcherSkipBtn'", AppCompatButton.class);
    view2131231027 = view;
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
    LauncherFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.launcherShowIv = null;
    target.launcherLoadingPg = null;
    target.launcherSkipBtn = null;

    view2131231027.setOnClickListener(null);
    view2131231027 = null;
  }
}
