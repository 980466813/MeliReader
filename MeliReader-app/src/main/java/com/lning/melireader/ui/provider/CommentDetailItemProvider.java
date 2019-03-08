package com.lning.melireader.ui.provider;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/19.
 */

public class CommentDetailItemProvider extends ItemViewBinder<CommentVo, CommentDetailItemProvider.ViewHolder> {

    private final OnItemClickListener mListener;

    public CommentDetailItemProvider(OnItemClickListener listener) {
        this.mListener = listener;
    }


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_comment_detail_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final CommentVo item) {
        final Resources resources = holder.itemView.getResources();
        GlideUtils.loadUserHead(holder.adapterCommentDetailIconIv, String.format(AppConstant.IMAGE_URL, item.getUserProfile()));
        holder.adapterCommentDetailNameTv.setText(item.getUserNickname());
        holder.adapterCommentDetailContentTv.setText(item.getContent());
        holder.adapterCommentDetailDateTv.setText(CommonUtils.formatPublishDate(item.getCreated()));
        holder.adapterCommentDetailLikeIv.setImageResource(item.isLiked() ? R.mipmap.ic_like_seleced : R.mipmap.ic_like_normal);
        holder.adapterCommentDetailLikeNumTv.setText(item.getLikeCount() + "");
        holder.adapterCommentDetailLikeNumTv.setTextColor(resources
                .getColor(item.isLiked() ? R.color.md_selected_blue : R.color.md_black_300));
        List<ReplyVo> replyVos = item.getReplyList();
        int size = replyVos == null ? 0 : replyVos.size();
        holder.adapterCommentDetailCommentNumTv.setText(size + "");
        holder.adapterCommentDetail2Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 回复
                mListener.OnItemClick(null, item, 0);
            }
        });
        holder.adapterCommentDetail1Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.adapterCommentDetailLikeIv.setImageResource(R.mipmap.ic_like_seleced);
                AnimUtils.setScaleAnimation(holder.adapterCommentDetailLikeIv, 1000);
                if (!item.isLiked()) {
                    // 点赞
                    mListener.OnItemClick(view, item.getId(), 0);
                    item.setLikeCount(item.getLikeCount() + 1);
                    item.setLiked(true);
                    getAdapter().notifyItemChanged(0);
                    holder.adapterCommentDetailLikeNumTv.setText(item.getLikeCount() + "");
                    holder.adapterCommentDetailLikeNumTv.setTextColor(resources.getColor(R.color.md_selected_blue));
                }

            }
        });
        holder.adapterCommentDetail3Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnItemClick(view, item);
                // 分享
            }
        });
        holder.adapterCommentDetailIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnItemClick(null, new SearchChannelItem(item.getUserId(), item.getUserNickname(), item.getUserProfile()));
            }
        });
        holder.adapterCommentDetailNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnItemClick(null, new SearchChannelItem(item.getUserId(), item.getUserNickname(), item.getUserProfile()));
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView adapterCommentDetailIconIv;
        AppCompatTextView adapterCommentDetailNameTv;
        AppCompatTextView adapterCommentDetailContentTv;
        AppCompatTextView adapterCommentDetailDateTv;
        AppCompatImageView adapterCommentDetailLikeIv;
        AppCompatTextView adapterCommentDetailLikeNumTv;
        AppCompatTextView adapterCommentDetailCommentNumTv;
        LinearLayout adapterCommentDetail1Ll;
        LinearLayout adapterCommentDetail2Ll;
        LinearLayout adapterCommentDetail3Ll;

        ViewHolder(View itemView) {
            super(itemView);
            adapterCommentDetailIconIv = itemView.findViewById(R.id.adapter_comment_detail_icon_iv);
            adapterCommentDetailNameTv = itemView.findViewById(R.id.adapter_comment_detail_name_tv);
            adapterCommentDetailContentTv = itemView.findViewById(R.id.adapter_comment_detail_content_tv);
            adapterCommentDetailDateTv = itemView.findViewById(R.id.adapter_comment_detail_date_tv);
            adapterCommentDetailCommentNumTv = itemView.findViewById(R.id.adapter_comment_detail_comment_num_tv);
            adapterCommentDetailLikeIv = itemView.findViewById(R.id.adapter_comment_detail_like_iv);
            adapterCommentDetailLikeNumTv = itemView.findViewById(R.id.adapter_comment_detail_like_num_tv);
            adapterCommentDetail1Ll = itemView.findViewById(R.id.adapter_comment_detail_1_ll);
            adapterCommentDetail2Ll = itemView.findViewById(R.id.adapter_comment_detail_2_ll);
            adapterCommentDetail3Ll = itemView.findViewById(R.id.adapter_comment_detail_3_ll);
        }
    }
}
