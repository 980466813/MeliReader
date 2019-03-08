// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.news.recommend;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsRecommendFragment_ViewBinding implements Unbinder {
  private NewsRecommendFragment target;

  @UiThread
  public NewsRecommendFragment_ViewBinding(NewsRecommendFragment target, View source) {
    this.target = target;

    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.view_main, "field 'mRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsRecommendFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;
  }
}
