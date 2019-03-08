// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.user.collection;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CollectionListFragment_ViewBinding implements Unbinder {
  private CollectionListFragment target;

  @UiThread
  public CollectionListFragment_ViewBinding(CollectionListFragment target, View source) {
    this.target = target;

    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.view_main, "field 'mRecyclerView'", RecyclerView.class);
    target.mRefreshLayout = Utils.findRequiredViewAsType(source, R.id.view_base_refresh_layout, "field 'mRefreshLayout'", SmartRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CollectionListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;
    target.mRefreshLayout = null;
  }
}
