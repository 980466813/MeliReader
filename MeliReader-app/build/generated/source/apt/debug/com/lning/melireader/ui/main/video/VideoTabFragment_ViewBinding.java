// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.video;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VideoTabFragment_ViewBinding implements Unbinder {
  private VideoTabFragment target;

  private View view2131231322;

  @UiThread
  public VideoTabFragment_ViewBinding(final VideoTabFragment target, View source) {
    this.target = target;

    View view;
    target.videoHomePagerVg = Utils.findRequiredViewAsType(source, R.id.video_home_pager_vg, "field 'videoHomePagerVg'", ViewPager.class);
    target.videoHomeTabTl = Utils.findRequiredViewAsType(source, R.id.video_home_tab_tl, "field 'videoHomeTabTl'", TabLayout.class);
    view = Utils.findRequiredView(source, R.id.video_home_search_iv, "field 'videoHomeSearchIv' and method 'onClick'");
    target.videoHomeSearchIv = Utils.castView(view, R.id.video_home_search_iv, "field 'videoHomeSearchIv'", AppCompatImageView.class);
    view2131231322 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    VideoTabFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.videoHomePagerVg = null;
    target.videoHomeTabTl = null;
    target.videoHomeSearchIv = null;

    view2131231322.setOnClickListener(null);
    view2131231322 = null;
  }
}
