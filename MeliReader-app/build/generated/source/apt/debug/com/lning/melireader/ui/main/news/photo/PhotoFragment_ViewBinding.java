// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.news.photo;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PhotoFragment_ViewBinding implements Unbinder {
  private PhotoFragment target;

  @UiThread
  public PhotoFragment_ViewBinding(PhotoFragment target, View source) {
    this.target = target;

    target.photoPagerVg = Utils.findRequiredViewAsType(source, R.id.photo_pager_vg, "field 'photoPagerVg'", ViewPager.class);
    target.photoPageIndicator = Utils.findRequiredViewAsType(source, R.id.photo_page_indicator, "field 'photoPageIndicator'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PhotoFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.photoPagerVg = null;
    target.photoPageIndicator = null;
  }
}
