// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.news.comment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lning.melireader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsCommentFragment_ViewBinding implements Unbinder {
  private NewsCommentFragment target;

  @UiThread
  public NewsCommentFragment_ViewBinding(NewsCommentFragment target, View source) {
    this.target = target;

    target.adapterRecommendSubTitleTv = Utils.findRequiredViewAsType(source, R.id.adapter_recommend_subtitle_tv, "field 'adapterRecommendSubTitleTv'", AppCompatTextView.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.view_main, "field 'mRecyclerView'", XRecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsCommentFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.adapterRecommendSubTitleTv = null;
    target.mRecyclerView = null;
  }
}
