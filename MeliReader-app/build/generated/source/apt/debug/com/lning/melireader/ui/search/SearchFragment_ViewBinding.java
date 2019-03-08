// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.search;

import am.widget.wraplayout.WrapLayout;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchFragment_ViewBinding implements Unbinder {
  private SearchFragment target;

  private View view2131231159;

  private View view2131231241;

  @UiThread
  public SearchFragment_ViewBinding(final SearchFragment target, View source) {
    this.target = target;

    View view;
    target.searchHistoryWrapWl = Utils.findRequiredViewAsType(source, R.id.search_history_wrap_wl, "field 'searchHistoryWrapWl'", WrapLayout.class);
    target.searchHistoryKeywordEt = Utils.findRequiredViewAsType(source, R.id.search_history_keyword_et, "field 'searchHistoryKeywordEt'", AppCompatEditText.class);
    view = Utils.findRequiredView(source, R.id.search_history_clear_btn, "field 'searchHistoryClearBtn' and method 'onClearClick'");
    target.searchHistoryClearBtn = Utils.castView(view, R.id.search_history_clear_btn, "field 'searchHistoryClearBtn'", AppCompatButton.class);
    view2131231159 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClearClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.toolbar_right_btn, "method 'onSearchClick'");
    view2131231241 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSearchClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.searchHistoryWrapWl = null;
    target.searchHistoryKeywordEt = null;
    target.searchHistoryClearBtn = null;

    view2131231159.setOnClickListener(null);
    view2131231159 = null;
    view2131231241.setOnClickListener(null);
    view2131231241 = null;
  }
}
