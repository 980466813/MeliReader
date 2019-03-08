// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.video.list;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lning.melireader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VideoListFragment_ViewBinding implements Unbinder {
  private VideoListFragment target;

  @UiThread
  public VideoListFragment_ViewBinding(VideoListFragment target, View source) {
    this.target = target;

    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.view_main, "field 'mRecyclerView'", XRecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    VideoListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;
  }
}
