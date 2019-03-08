package com.lning.melireader.ui.provider;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.repository.http.bean.CommentNewsListVo;
import com.lning.melireader.core.utils.AnimUtils;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.GlideUtils;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/25.
 */

public class UserCommentListItemProvider extends ItemViewBinder<CommentNewsListVo, UserCommentListItemProvider.ViewHolder> {
    private final OnItemClickListener mListener;

    public UserCommentListItemProvider(OnItemClickListener simpleOnItemClickListener) {
        this.mListener = simpleOnItemClickListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_user_news_comment_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final CommentNewsListVo item) {
        final Resources resources = holder.itemView.getResources();
        GlideUtils.loadUserHead(holder.adapterUserNewsCommentIconIv, String.format(AppConstant.IMAGE_URL, item.getPublisherProfile()));
        holder.adapterUserNewsCommentNameTv.setText(item.getPublisherName());
        holder.adapterUserNewsCommentDateTv.setText(CommonUtils.formatPublishDate(item.getCreated()));
        holder.adapterUserNewsCommentContentTv.setText(item.getContent());
        holder.adapterUserNewsCommentSummaryTv.setText(item.getSummary());
        holder.adapterUserNewsCommentTitleTv.setText(item.getTitle());
        String image = item.getImage();
        if (!TextUtils.isEmpty(image)) {
            String[] images = image.split(",");
            GlideUtils.loadImage(holder.adapterUserNewsCommentImageIv, String.format(AppConstant.IMAGE_URL, images[0]));
        } else {
            holder.adapterUserNewsCommentImageIv.setVisibility(View.GONE);
        }
        holder.adapterUserNewsCommentLikeIv.setImageResource(item.isLiked() ? R.mipmap.ic_like_seleced : R.mipmap.ic_like_normal);
        holder.adapterUserNewsCommentLikeNumTv.setText(item.getLikeCount() + "");
        holder.adapterUserNewsCommentLikeNumTv.setTextColor(resources
                .getColor(item.isLiked() ? R.color.md_selected_blue : R.color.md_black_300));
        holder.adapterUserNewsCommentNumTv.setText(item.getCommentCount() + "");
        holder.adapterUserNewsCommentLikeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.adapterUserNewsCommentLikeIv.setImageResource(R.mipmap.ic_like_seleced);
                AnimUtils.setScaleAnimation(holder.adapterUserNewsCommentLikeIv, 1000);
                if (!item.isLiked()) {
                    // 点赞
                    mListener.OnItemClick(view, item.getId(), 0);
                    item.setLikeCount(item.getLikeCount() + 1);
                    item.setLiked(true);
                    getAdapter().notifyItemChanged(0);
                    holder.adapterUserNewsCommentLikeNumTv.setText(item.getLikeCount() + "");
                    holder.adapterUserNewsCommentLikeNumTv.setTextColor(resources.getColor(R.color.md_selected_blue));
                }
            }
        });
        holder.adapterUserNewsCommentClickLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnItemClick(null, CommonUtils.parseNewsListVo(item));
            }
        });
        holder.adapterUserNewsInfoLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnItemClick(null, CommonUtils.parseNewsListVo(item));
            }
        });
        holder.adapterUserNewsCommentShareLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView adapterUserNewsCommentIconIv;
        AppCompatTextView adapterUserNewsCommentNameTv;
        AppCompatTextView adapterUserNewsCommentDateTv;
        AppCompatTextView adapterUserNewsCommentContentTv;
        AppCompatTextView adapterUserNewsCommentSummaryTv;
        AppCompatTextView adapterUserNewsCommentTitleTv;
        AppCompatImageView adapterUserNewsCommentImageIv;
        LinearLayout adapterUserNewsCommentLikeLl;
        AppCompatImageView adapterUserNewsCommentLikeIv;
        AppCompatTextView adapterUserNewsCommentLikeNumTv;
        LinearLayout adapterUserNewsCommentClickLl;
        AppCompatTextView adapterUserNewsCommentNumTv;
        LinearLayout adapterUserNewsCommentShareLl;
        LinearLayout adapterUserNewsInfoLl;


        public ViewHolder(View itemView) {
            super(itemView);
            adapterUserNewsCommentIconIv = itemView.findViewById(R.id.adapter_user_news_comment_icon_iv);
            adapterUserNewsCommentNameTv = itemView.findViewById(R.id.adapter_user_news_comment_name_tv);
            adapterUserNewsCommentDateTv = itemView.findViewById(R.id.adapter_user_news_comment_date_tv);
            adapterUserNewsCommentContentTv = itemView.findViewById(R.id.adapter_user_news_comment_content_tv);
            adapterUserNewsCommentSummaryTv = itemView.findViewById(R.id.adapter_user_news_comment_summary_tv);
            adapterUserNewsCommentImageIv = itemView.findViewById(R.id.adapter_user_news_comment_image_tv);
            adapterUserNewsCommentTitleTv = itemView.findViewById(R.id.adapter_user_news_comment_title_tv);
            adapterUserNewsCommentLikeLl = itemView.findViewById(R.id.adapter_user_news_comment_like_ll);
            adapterUserNewsCommentLikeIv = itemView.findViewById(R.id.adapter_user_news_comment_like_iv);
            adapterUserNewsCommentLikeNumTv = itemView.findViewById(R.id.adapter_user_news_comment_like_num_tv);
            adapterUserNewsCommentClickLl = itemView.findViewById(R.id.adapter_user_news_comment_click_ll);
            adapterUserNewsCommentNumTv = itemView.findViewById(R.id.adapter_user_news_comment_num_tv);
            adapterUserNewsCommentShareLl = itemView.findViewById(R.id.adapter_user_news_comment_share_ll);
            adapterUserNewsInfoLl = itemView.findViewById(R.id.adapter_user_news_info_ll);
//            itemView.findViewById(R.id.adapter_user_news_comment_icon_iv);
        }
    }
}
