package com.lning.melireader.presenter;

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

import com.jakewharton.rxbinding2.view.RxView;
import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.repository.http.bean.ReplyVo;
import com.lning.melireader.core.repository.http.bean.SearchChannelItem;
import com.lning.melireader.core.utils.AnimUtils;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.GlideUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/20.
 */

public class ReplyListItemProvider extends ItemViewBinder<ReplyVo, ReplyListItemProvider.ViewHolder> {
    private final OnItemClickListener mListener;
    private final String commendId;

    public ReplyListItemProvider(OnItemClickListener listener, String commentId) {
        this.mListener = listener;
        this.commendId = commentId;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_reply_list_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final ReplyVo item) {
        final Resources resources = holder.itemView.getResources();
        GlideUtils.loadUserHead(holder.adapterReplyIconIv, String.format(AppConstant.IMAGE_URL, item.getUserProfile()));
        holder.adapterReplyName1Tv.setText(item.getUserNickname());
        if (!TextUtils.isEmpty(item.getReplyId()) && !item.getReplyId().equals(commendId)) {
            holder.centerFlag.setVisibility(View.VISIBLE);
            holder.adapterReplyName2Tv.setVisibility(View.VISIBLE);
            holder.adapterReplyName2Tv.setText(item.getReplyNickname());
            RxView.clicks(holder.adapterReplyName2Tv).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {
                    mListener.OnItemClick(null, item.getReplyUserId());
                }
            });
        }
        holder.adapterReplyContentTv.setText(item.getContent());
        holder.adapterReplyDateTv.setText(CommonUtils.formatPublishDate(item.getCreated()));
        holder.adapterReplyLikeIv.setImageResource(item.isLiked() ? R.mipmap.ic_like_seleced : R.mipmap.ic_like_normal);
        holder.adapterReplyLikeNumTv.setText(item.getLikeCount() + "");
        holder.adapterReplyLikeNumTv.setTextColor(resources
                .getColor(item.isLiked() ? R.color.md_selected_blue : R.color.md_black_300));
        RxView.clicks(holder.adapterReplyLikeIv).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                holder.adapterReplyLikeIv.setImageResource(R.mipmap.ic_like_seleced);
                AnimUtils.setScaleAnimation(holder.adapterReplyLikeIv, 1000);
                if (!item.isLiked()) {
                    // 点赞
                    mListener.OnItemClick(null, item.getId(),
                            holder.getAdapterPosition() - 2);
                    item.setLikeCount(item.getLikeCount() + 1);
                    item.setLiked(true);
                    getAdapter().notifyItemChanged(holder.getAdapterPosition());
                    holder.adapterReplyLikeNumTv.setTextColor(resources.getColor(R.color.md_selected_blue));
                    holder.adapterReplyLikeNumTv.setText(item.getLikeCount() + "");
                }
            }
        });
        RxView.clicks(holder.adapterReplyContentTv).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                mListener.OnItemClick(null, item, holder.getAdapterPosition());
            }
        });
        RxView.clicks(holder.adapterReplyName1Tv).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                mListener.OnItemClick(null, new SearchChannelItem(item.getUserId(), item.getUserNickname(), item.getUserProfile()));
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView adapterReplyIconIv;
        AppCompatTextView adapterReplyName1Tv;
        AppCompatTextView centerFlag;
        AppCompatTextView adapterReplyName2Tv;
        AppCompatTextView adapterReplyContentTv;
        AppCompatTextView adapterReplyDateTv;
        AppCompatImageView adapterReplyLikeIv;
        AppCompatTextView adapterReplyLikeNumTv;

        ViewHolder(View itemView) {
            super(itemView);
            adapterReplyIconIv = itemView.findViewById(R.id.adapter_reply_icon_iv);
            adapterReplyName1Tv = itemView.findViewById(R.id.adapter_reply_name_1_tv);
            centerFlag = itemView.findViewById(R.id.centerFlag);
            adapterReplyName2Tv = itemView.findViewById(R.id.adapter_reply_name_2_tv);
            adapterReplyContentTv = itemView.findViewById(R.id.adapter_reply_content_tv);
            adapterReplyDateTv = itemView.findViewById(R.id.adapter_reply_date_tv);
            adapterReplyLikeIv = itemView.findViewById(R.id.adapter_reply_like_iv);
            adapterReplyLikeNumTv = itemView.findViewById(R.id.adapter_reply_like_num_tv);
        }
    }
}
