// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.news.comment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsCommentDetailFragment_ViewBinding implements Unbinder {
  private NewsCommentDetailFragment target;

  private View view2131230935;

  @UiThread
  public NewsCommentDetailFragment_ViewBinding(final NewsCommentDetailFragment target,
      View source) {
    this.target = target;

    View view;
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.view_main, "field 'mRecyclerView'", RecyclerView.class);
    target.mViewToolbarContainer = Utils.findRequiredViewAsType(source, R.id.view_toolbar, "field 'mViewToolbarContainer'", FrameLayout.class);
    target.mViewBottomContainer = Utils.findRequiredViewAsType(source, R.id.view_bottom, "field 'mViewBottomContainer'", FrameLayout.class);
    view = Utils.findRequiredView(source, R.id.comment_detail_tv, "field 'commentDetailTv' and method 'onCommentDetailTvClick'");
    target.commentDetailTv = Utils.castView(view, R.id.comment_detail_tv, "field 'commentDetailTv'", AppCompatTextView.class);
    view2131230935 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCommentDetailTvClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsCommentDetailFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;
    target.mViewToolbarContainer = null;
    target.mViewBottomContainer = null;
    target.commentDetailTv = null;

    view2131230935.setOnClickListener(null);
    view2131230935 = null;
  }
}
