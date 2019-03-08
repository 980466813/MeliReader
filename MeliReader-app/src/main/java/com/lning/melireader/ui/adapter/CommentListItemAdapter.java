package com.lning.melireader.ui.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Notification;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.ReplyVo;
import com.lning.melireader.core.repository.http.bean.SearchChannelItem;
import com.lning.melireader.core.utils.AnimUtils;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.GlideUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/19.
 */

public class CommentListItemAdapter extends RecyclerView.Adapter<CommentListItemAdapter.ViewHolder> {

    private final OnItemClickListener mListener;
    private final LinkedList<CommentVo> mList;

    public CommentListItemAdapter(LinkedList<CommentVo> list, OnItemClickListener listener) {
        this.mList = list;
        this.mListener = listener;
    }

    private View makeMoreText(View itemView, int cha) {
        return null;
    }

    private View makeTextView(final View itemView, final CommentVo commentVo, ReplyVo replyVo, final int position) {
        AppCompatTextView textView = new AppCompatTextView(itemView.getContext());
        if (!TextUtils.isEmpty(replyVo.getReplyId()) && !replyVo.getReplyId().equals(commentVo.getId())) {
            textView.setText(Html.fromHtml(itemView.getResources()
                    .getString(R.string.tips_one_reply_one_with_content_template, replyVo.getUserNickname(), replyVo.getReplyNickname(), replyVo.getContent())));
        } else {
            textView.setText(Html.fromHtml(itemView.getResources()
                    .getString(R.string.tips_one_reply_null_with_content_template, replyVo.getUserNickname(), replyVo.getContent())));
        }
        textView.setPadding(6, 3, 10, 6);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0F);
        textView.setBackgroundResource(R.drawable.selector_reply_tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 回复内点击事件
                mListener.OnItemClick(null, commentVo, position);
            }
        });
        return textView;
    }

    public void addFirst(CommentVo commentVo) {
        mList.addFirst(commentVo);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mList.remove(index);
        notifyDataSetChanged();
    }

    public void addLast(CommentVo commentVo) {
        mList.addLast(commentVo);
        notifyDataSetChanged();
    }

    public void addReply(CommentVo commentVo, ReplyVo replyVo) {
        if (mList.contains(commentVo)) {
            List<ReplyVo> replyVos = commentVo.getReplyList();
            if (replyVos == null) replyVos = new ArrayList<>();
            replyVos.add(replyVo);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_comment_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CommentVo item = mList.get(position);
        if (item == null) return;
        final Resources resources = holder.itemView.getResources();
        GlideUtils.loadUserHead(holder.adapterRecommendCommentIconIv, String.format(AppConstant.IMAGE_URL, item.getUserProfile()));
        holder.adapterRecommendCommentNameTv.setText(item.getUserNickname());
        holder.adapterRecommendCommentContentTv.setText(item.getContent());
        holder.adapterRecommendCommentLikeTv.setVisibility(item.getLikeCount() == 0 ? View.GONE : View.VISIBLE);
        holder.adapterRecommendCommentLikeTv.setText(item.getLikeCount() + "");
        holder.adapterRecommendCommentLikeTv.setTextColor(resources.getColor(item.isLiked() ? R.color.md_selected_blue : R.color.md_black_300));
        holder.adapterRecommendCommentDateTv.setText(CommonUtils.formatPublishDate(item.getCreated()));
        holder.adapterRecommendCommentLikeIv.setImageResource(item.isLiked() ? R.mipmap.ic_like_seleced : R.mipmap.ic_like_normal);
        List<ReplyVo> replyVoList = item.getReplyList();
        final boolean isVisible = replyVoList != null && replyVoList.size() > 0;
        holder.adapterRecommendCommentReplyLl.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        holder.adapterRecommendCommentContainerLl.removeAllViews();
        int replySize = isVisible ? (replyVoList.size() > 2 ? 3 : replyVoList.size()) : 0;
        for (int i = 0; i < replySize; i++) {
            holder.adapterRecommendCommentContainerLl.addView(makeTextView(holder.itemView, item, replyVoList.get(i), i));
        }
        int cha = isVisible ? replyVoList.size() - 3 : 0;
        if (cha > 0) {
            holder.adapterRecommendCommentContainerLl.addView(makeMoreText(holder.itemView, cha));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVisible) {
                    mListener.OnItemClick(null, item, -1);
                } else {
                    mListener.OnItemClick(null, item, -2);
                }
            }
        });
        holder.adapterRecommendCommentLikeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int likeCount = item.getLikeCount();
                if (likeCount == 0) {
                    holder.adapterRecommendCommentLikeTv.setTextColor(holder.itemView.getResources().getColor(R.color.md_selected_blue));
                    holder.adapterRecommendCommentLikeTv.setVisibility(View.VISIBLE);
                }
                AnimUtils.setScaleAnimation(holder.adapterRecommendCommentLikeIv, 1000);
                notifyDataSetChanged();
                if (!item.isLiked()) {
                    mListener.OnItemClick(view, item.getId(), position);
                    item.setLiked(true);
                    item.setLikeCount(likeCount + 1);
                }
            }
        });
        holder.adapterRecommendCommentIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnItemClick(null, new SearchChannelItem(item.getUserId(), item.getUserNickname(), item.getUserProfile()));
            }
        });
        holder.adapterRecommendCommentNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnItemClick(null, new SearchChannelItem(item.getUserId(), item.getUserNickname(), item.getUserProfile()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView adapterRecommendCommentIconIv;
        AppCompatTextView adapterRecommendCommentNameTv;
        AppCompatTextView adapterRecommendCommentContentTv;
        AppCompatTextView adapterRecommendCommentDateTv;
        AppCompatTextView adapterRecommendCommentReplyTv;
        AppCompatTextView adapterRecommendCommentLikeTv;
        AppCompatImageView adapterRecommendCommentLikeIv;
        LinearLayout adapterRecommendCommentReplyLl;
        LinearLayout adapterRecommendCommentContainerLl;

        public ViewHolder(View itemView) {
            super(itemView);
            adapterRecommendCommentIconIv = itemView.findViewById(R.id.adapter_recommend_comment_icon_iv);
            adapterRecommendCommentNameTv = itemView.findViewById(R.id.adapter_recommend_comment_name_tv);
            adapterRecommendCommentContentTv = itemView.findViewById(R.id.adapter_recommend_comment_content_tv);
            adapterRecommendCommentDateTv = itemView.findViewById(R.id.adapter_recommend_comment_date_tv);
            adapterRecommendCommentReplyTv = itemView.findViewById(R.id.adapter_recommend_comment_reply_tv);
            adapterRecommendCommentLikeTv = itemView.findViewById(R.id.adapter_recommend_comment_like_tv);
            adapterRecommendCommentLikeIv = itemView.findViewById(R.id.adapter_recommend_comment_like_iv);
            adapterRecommendCommentReplyLl = itemView.findViewById(R.id.adapter_recommend_comment_reply_ll);
            adapterRecommendCommentContainerLl = itemView.findViewById(R.id.adapter_recommend_comment_container_ll);
        }
    }
}
