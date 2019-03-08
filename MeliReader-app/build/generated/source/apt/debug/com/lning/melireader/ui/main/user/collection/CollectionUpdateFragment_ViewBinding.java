// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.user.collection;

import am.widget.wraplayout.WrapLayout;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CollectionUpdateFragment_ViewBinding implements Unbinder {
  private CollectionUpdateFragment target;

  @UiThread
  public CollectionUpdateFragment_ViewBinding(CollectionUpdateFragment target, View source) {
    this.target = target;

    target.collectionTagOptionsContainerWl = Utils.findRequiredViewAsType(source, R.id.collection_tag_options_container_wl, "field 'collectionTagOptionsContainerWl'", WrapLayout.class);
    target.collectionTagCreateEt = Utils.findRequiredViewAsType(source, R.id.collection_tag_create_et, "field 'collectionTagCreateEt'", AppCompatEditText.class);
    target.collectionTagEmptyTv = Utils.findRequiredViewAsType(source, R.id.collection_tag_empty_tv, "field 'collectionTagEmptyTv'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CollectionUpdateFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.collectionTagOptionsContainerWl = null;
    target.collectionTagCreateEt = null;
    target.collectionTagEmptyTv = null;
  }
}
