package com.lning.melireader.ui.provider;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.repository.http.bean.SearchChannelItem;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.GlideUtils;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/27.
 */

public class AttentionTabSingleItemProvider extends ItemViewBinder<NewsListVo, AttentionTabSingleItemProvider.ViewHolder> {

    private final OnItemClickListener mListener;

    public AttentionTabSingleItemProvider(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_attention_tab_single_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final NewsListVo item) {
        Resources resources = holder.itemView.getResources();
        GlideUtils.loadUserHead(holder.adapterAttentionTabSingleIconIv, String.format(AppConstant.IMAGE_URL, item.getPublisherProfile()));
        holder.adapterAttentionTabSingleNameTv.setText(item.getPublisherName());
        holder.adapterAttentionTabSingleTitleTv.setText(Html.fromHtml(item.getTitle()));
        holder.adapterAttentionTabSingleCommentNumTv.setText(resources.getString(R.string.tips_news_comment_num_template, item.getCommentCount()));
        holder.adapterAttentionTabSingleDateTv.setText(CommonUtils.formatPublishDate(item.getCreated()));
        holder.adapterAttentionTabSingleDomainTv.setText(item.getSource());
        holder.adapterAttentionTabSingleSubCb.setChecked(item.isSubscripted());
        holder.adapterAttentionTabSingleSubCb.setText(holder.itemView.getResources().getString(item.isSubscripted() ?
                R.string.tips_already_subscripted : R.string.tips_not_subscript));
        String image = item.getImage();
        if (TextUtils.isEmpty(image)) {
            holder.adapterAttentionTabSingleImageIv.setVisibility(View.GONE);
            holder.adapterAttentionTabSingleImageIv.setVisibility(View.GONE);
            holder.adapterAttentionTabSingleIsVideoIv.setVisibility(View.GONE);
        } else {
            String[] images = image.split(",");
            GlideUtils.loadImage(holder.adapterAttentionTabSingleImageIv, String.format(AppConstant.IMAGE_URL, images[0]));
            boolean isVideo = AppConstant.TYPE_VIDEO.equals(item.getCtype());
            if (isVideo) {
                holder.adapterAttentionTabSingleImageNumTv.setVisibility(View.GONE);
                holder.adapterAttentionTabSingleIsVideoIv.setVisibility(View.VISIBLE);
            } else {
                holder.adapterAttentionTabSingleImageNumTv.setVisibility(View.VISIBLE);
                holder.adapterAttentionTabSingleIsVideoIv.setVisibility(View.GONE);
            }
            holder.adapterAttentionTabSingleImageNumTv.setText(resources.getString(R.string.tips_image_num_template, images.length));
        }

        holder.adapterAttentionTabSingleIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.OnItemClick(holder.itemView, new SearchChannelItem(item.getChannelId(), item.getPublisherName(), item.getPublisherProfile()));
                }
            }
        });
        holder.adapterAttentionTabSingleNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.OnItemClick(holder.itemView, new SearchChannelItem(item.getChannelId(), item.getPublisherName(), item.getPublisherProfile()));
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.OnItemClick(holder.itemView, item, holder.getAdapterPosition());
                }
            }
        });
        holder.adapterAttentionTabSingleSubCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setSubscripted(holder.adapterAttentionTabSingleSubCb.isChecked());
                mListener.OnItemCheckChange(null, item.getChannelId(), holder.adapterAttentionTabSingleSubCb.isChecked());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView adapterAttentionTabSingleIconIv;
        AppCompatTextView adapterAttentionTabSingleNameTv;
        AppCompatTextView adapterAttentionTabSingleTitleTv;
        AppCompatCheckBox adapterAttentionTabSingleSubCb;
        AppCompatImageView adapterAttentionTabSingleImageIv;
        AppCompatImageView adapterAttentionTabSingleIsVideoIv;
        AppCompatTextView adapterAttentionTabSingleImageNumTv;
        AppCompatTextView adapterAttentionTabSingleDomainTv;
        AppCompatTextView adapterAttentionTabSingleDateTv;
        AppCompatTextView adapterAttentionTabSingleCommentNumTv;

        public ViewHolder(View itemView) {
            super(itemView);
            adapterAttentionTabSingleIconIv = itemView.findViewById(R.id.adapter_attention_tab_single_icon_iv);
            adapterAttentionTabSingleNameTv = itemView.findViewById(R.id.adapter_attention_tab_single_name_tv);
            adapterAttentionTabSingleTitleTv = itemView.findViewById(R.id.adapter_attention_tab_single_content_tv);
            adapterAttentionTabSingleSubCb = itemView.findViewById(R.id.adapter_attention_tab_single_sub_cb);
            adapterAttentionTabSingleImageIv = itemView.findViewById(R.id.adapter_attention_tab_single_image_iv);
            adapterAttentionTabSingleIsVideoIv = itemView.findViewById(R.id.adapter_attention_tab_single_isvideo_iv);
            adapterAttentionTabSingleImageNumTv = itemView.findViewById(R.id.adapter_attention_tab_single_image_num_tv);
            adapterAttentionTabSingleDomainTv = itemView.findViewById(R.id.adapter_attention_tab_single_domain_tv);
            adapterAttentionTabSingleDateTv = itemView.findViewById(R.id.adapter_attention_tab_single_date_tv);
            adapterAttentionTabSingleCommentNumTv = itemView.findViewById(R.id.adapter_attention_tab_single_comment_num_tv);
        }
    }
}
