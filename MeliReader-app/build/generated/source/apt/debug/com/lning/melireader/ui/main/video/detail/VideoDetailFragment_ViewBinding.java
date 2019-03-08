// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.video.detail;

import am.widget.wraplayout.WrapLayout;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lning.melireader.R;
import com.lning.melireader.widget.SampleCoverVideo;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VideoDetailFragment_ViewBinding implements Unbinder {
  private VideoDetailFragment target;

  private View view2131231310;

  private View view2131231312;

  private View view2131231318;

  private View view2131231303;

  private View view2131231304;

  private View view2131231308;

  private View view2131231306;

  @UiThread
  public VideoDetailFragment_ViewBinding(final VideoDetailFragment target, View source) {
    this.target = target;

    View view;
    target.videoDetailTitleTv = Utils.findRequiredViewAsType(source, R.id.video_detail_title_tv, "field 'videoDetailTitleTv'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.video_detail_icon_iv, "field 'videoDetailIconIv' and method 'onUserClick'");
    target.videoDetailIconIv = Utils.castView(view, R.id.video_detail_icon_iv, "field 'videoDetailIconIv'", AppCompatImageView.class);
    view2131231310 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onUserClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.video_detail_name_tv, "field 'videoDetailNameTv' and method 'onUserClick'");
    target.videoDetailNameTv = Utils.castView(view, R.id.video_detail_name_tv, "field 'videoDetailNameTv'", AppCompatTextView.class);
    view2131231312 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onUserClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.video_detail_sub_cb, "field 'videoDetailSubCb' and method 'onSubChanged'");
    target.videoDetailSubCb = Utils.castView(view, R.id.video_detail_sub_cb, "field 'videoDetailSubCb'", AppCompatCheckBox.class);
    view2131231318 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSubChanged();
      }
    });
    view = Utils.findRequiredView(source, R.id.video_detail_arrow_iv, "field 'videoDetailArrowIv' and method 'onArrowClick'");
    target.videoDetailArrowIv = Utils.castView(view, R.id.video_detail_arrow_iv, "field 'videoDetailArrowIv'", AppCompatImageView.class);
    view2131231303 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onArrowClick();
      }
    });
    target.videoDetailTagWl = Utils.findRequiredViewAsType(source, R.id.video_detail_tag_wl, "field 'videoDetailTagWl'", WrapLayout.class);
    target.videoDetailNestedScroll = Utils.findRequiredViewAsType(source, R.id.video_detail_nested_scroll, "field 'videoDetailNestedScroll'", NestedScrollView.class);
    target.videoDetailDateTv = Utils.findRequiredViewAsType(source, R.id.video_detail_date_tv, "field 'videoDetailDateTv'", AppCompatTextView.class);
    target.videoDetailReadCountTv = Utils.findRequiredViewAsType(source, R.id.video_detail_read_count_tv, "field 'videoDetailReadCountTv'", AppCompatTextView.class);
    target.videoDetailInfoLl = Utils.findRequiredViewAsType(source, R.id.video_detail_info_ll, "field 'videoDetailInfoLl'", ConstraintLayout.class);
    target.videoDetailPlayerGsy = Utils.findRequiredViewAsType(source, R.id.video_detail_player_gsy, "field 'videoDetailPlayerGsy'", SampleCoverVideo.class);
    target.videoDetailCommentNumTv = Utils.findRequiredViewAsType(source, R.id.video_detail_comment_num_tv, "field 'videoDetailCommentNumTv'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.video_detail_collection_iv, "field 'videoDetailCollectionIv' and method 'onCollectionClick'");
    target.videoDetailCollectionIv = Utils.castView(view, R.id.video_detail_collection_iv, "field 'videoDetailCollectionIv'", AppCompatImageView.class);
    view2131231304 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCollectionClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.video_detail_comment_tv, "method 'onCommentClick'");
    view2131231308 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCommentClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.video_detail_comment_iv, "method 'onCommentClick'");
    view2131231306 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCommentClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    VideoDetailFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.videoDetailTitleTv = null;
    target.videoDetailIconIv = null;
    target.videoDetailNameTv = null;
    target.videoDetailSubCb = null;
    target.videoDetailArrowIv = null;
    target.videoDetailTagWl = null;
    target.videoDetailNestedScroll = null;
    target.videoDetailDateTv = null;
    target.videoDetailReadCountTv = null;
    target.videoDetailInfoLl = null;
    target.videoDetailPlayerGsy = null;
    target.videoDetailCommentNumTv = null;
    target.videoDetailCollectionIv = null;

    view2131231310.setOnClickListener(null);
    view2131231310 = null;
    view2131231312.setOnClickListener(null);
    view2131231312 = null;
    view2131231318.setOnClickListener(null);
    view2131231318 = null;
    view2131231303.setOnClickListener(null);
    view2131231303 = null;
    view2131231304.setOnClickListener(null);
    view2131231304 = null;
    view2131231308.setOnClickListener(null);
    view2131231308 = null;
    view2131231306.setOnClickListener(null);
    view2131231306 = null;
  }
}
