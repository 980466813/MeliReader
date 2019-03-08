// Generated code from Butter Knife. Do not modify!
package com.lning.melireader.ui.main.news.detail;

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
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsDetailFragment_ViewBinding implements Unbinder {
  private NewsDetailFragment target;

  private View view2131231088;

  private View view2131231087;

  private View view2131231091;

  private View view2131231092;

  private View view2131231071;

  private View view2131231072;

  private View view2131231073;

  private View view2131231075;

  private View view2131231077;

  private View view2131231094;

  private View view2131231084;

  private View view2131231080;

  @UiThread
  public NewsDetailFragment_ViewBinding(final NewsDetailFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.news_detail_publisher_icon_iv, "field 'newsDetailPublisherIconIv' and method 'onUserClick'");
    target.newsDetailPublisherIconIv = Utils.castView(view, R.id.news_detail_publisher_icon_iv, "field 'newsDetailPublisherIconIv'", AppCompatImageView.class);
    view2131231088 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onUserClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.news_detail_publisher_icon_2_iv, "field 'newsDetailPublisherIcon2Iv' and method 'onUserClick'");
    target.newsDetailPublisherIcon2Iv = Utils.castView(view, R.id.news_detail_publisher_icon_2_iv, "field 'newsDetailPublisherIcon2Iv'", AppCompatImageView.class);
    view2131231087 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onUserClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.news_detail_publisher_name_2_tv, "field 'newsDetailPublisherName2Tv' and method 'onUserClick'");
    target.newsDetailPublisherName2Tv = Utils.castView(view, R.id.news_detail_publisher_name_2_tv, "field 'newsDetailPublisherName2Tv'", AppCompatTextView.class);
    view2131231091 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onUserClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.news_detail_publisher_name_tv, "field 'newsDetailPublisherNameTv' and method 'onUserClick'");
    target.newsDetailPublisherNameTv = Utils.castView(view, R.id.news_detail_publisher_name_tv, "field 'newsDetailPublisherNameTv'", AppCompatTextView.class);
    view2131231092 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onUserClick();
      }
    });
    target.newsDetailDateSummaryTv = Utils.findRequiredViewAsType(source, R.id.news_detail_date_summary_tv, "field 'newsDetailDateSummaryTv'", AppCompatTextView.class);
    target.newsDetailTitleTv = Utils.findRequiredViewAsType(source, R.id.news_detail_title_tv, "field 'newsDetailTitleTv'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.news_detail_attention_cb, "field 'newsDetailAttentionCb' and method 'onNewsDetailAttentionChanged'");
    target.newsDetailAttentionCb = Utils.castView(view, R.id.news_detail_attention_cb, "field 'newsDetailAttentionCb'", AppCompatCheckBox.class);
    view2131231071 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onNewsDetailAttentionChanged();
      }
    });
    view = Utils.findRequiredView(source, R.id.news_detail_attention_cb_2, "field 'newsDetailAttentionCb2' and method 'onNewsDetailAttentionChanged2'");
    target.newsDetailAttentionCb2 = Utils.castView(view, R.id.news_detail_attention_cb_2, "field 'newsDetailAttentionCb2'", AppCompatCheckBox.class);
    view2131231072 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onNewsDetailAttentionChanged2();
      }
    });
    target.newsDetailPublisherInfo2Cl = Utils.findRequiredViewAsType(source, R.id.news_detail_publisher_info_2_cl, "field 'newsDetailPublisherInfo2Cl'", ConstraintLayout.class);
    target.newsDetailPublisherInfoCl = Utils.findRequiredViewAsType(source, R.id.news_detail_publisher_info_cl, "field 'newsDetailPublisherInfoCl'", ConstraintLayout.class);
    target.newsDetailOperateCl = Utils.findRequiredViewAsType(source, R.id.news_detail_operate_cl, "field 'newsDetailOperateCl'", ConstraintLayout.class);
    target.newsDetailWrapLayout = Utils.findRequiredViewAsType(source, R.id.news_detail_tag_wl, "field 'newsDetailWrapLayout'", WrapLayout.class);
    target.newsDetailCommentNumTv = Utils.findRequiredViewAsType(source, R.id.news_detail_comment_num_tv, "field 'newsDetailCommentNumTv'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.news_detail_collection_iv, "field 'newsDetailCollectionIv' and method 'onCollectionClick'");
    target.newsDetailCollectionIv = Utils.castView(view, R.id.news_detail_collection_iv, "field 'newsDetailCollectionIv'", AppCompatImageView.class);
    view2131231073 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCollectionClick();
      }
    });
    target.newsDetailLikeBtn = Utils.findRequiredViewAsType(source, R.id.news_detail_like_btn, "field 'newsDetailLikeBtn'", AppCompatImageView.class);
    target.newsDetailLikeTv = Utils.findRequiredViewAsType(source, R.id.news_detail_like_tv, "field 'newsDetailLikeTv'", AppCompatTextView.class);
    target.newsDetailDislikeTv = Utils.findRequiredViewAsType(source, R.id.news_detail_dislike_tv, "field 'newsDetailDislikeTv'", AppCompatTextView.class);
    target.newsDetailScrollNsv = Utils.findRequiredViewAsType(source, R.id.news_detail_scroll_nsv, "field 'newsDetailScrollNsv'", NestedScrollView.class);
    view = Utils.findRequiredView(source, R.id.news_detail_comment_iv, "method 'onCommentClick'");
    view2131231075 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCommentClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.news_detail_comment_tv, "method 'onCommentClick'");
    view2131231077 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCommentClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.news_detail_share_iv, "method 'onShareClick'");
    view2131231094 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onShareClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.news_detail_like_ll, "method 'onLikeClick'");
    view2131231084 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onLikeClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.news_detail_dislike_ll, "method 'onDislikeClick'");
    view2131231080 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDislikeClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsDetailFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.newsDetailPublisherIconIv = null;
    target.newsDetailPublisherIcon2Iv = null;
    target.newsDetailPublisherName2Tv = null;
    target.newsDetailPublisherNameTv = null;
    target.newsDetailDateSummaryTv = null;
    target.newsDetailTitleTv = null;
    target.newsDetailAttentionCb = null;
    target.newsDetailAttentionCb2 = null;
    target.newsDetailPublisherInfo2Cl = null;
    target.newsDetailPublisherInfoCl = null;
    target.newsDetailOperateCl = null;
    target.newsDetailWrapLayout = null;
    target.newsDetailCommentNumTv = null;
    target.newsDetailCollectionIv = null;
    target.newsDetailLikeBtn = null;
    target.newsDetailLikeTv = null;
    target.newsDetailDislikeTv = null;
    target.newsDetailScrollNsv = null;

    view2131231088.setOnClickListener(null);
    view2131231088 = null;
    view2131231087.setOnClickListener(null);
    view2131231087 = null;
    view2131231091.setOnClickListener(null);
    view2131231091 = null;
    view2131231092.setOnClickListener(null);
    view2131231092 = null;
    view2131231071.setOnClickListener(null);
    view2131231071 = null;
    view2131231072.setOnClickListener(null);
    view2131231072 = null;
    view2131231073.setOnClickListener(null);
    view2131231073 = null;
    view2131231075.setOnClickListener(null);
    view2131231075 = null;
    view2131231077.setOnClickListener(null);
    view2131231077 = null;
    view2131231094.setOnClickListener(null);
    view2131231094 = null;
    view2131231084.setOnClickListener(null);
    view2131231084 = null;
    view2131231080.setOnClickListener(null);
    view2131231080 = null;
  }
}
