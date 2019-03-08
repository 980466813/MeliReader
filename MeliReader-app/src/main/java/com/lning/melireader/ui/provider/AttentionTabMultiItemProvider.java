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
import android.widget.LinearLayout;

import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.GlideUtils;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/27.
 */

public class AttentionTabMultiItemProvider extends ItemViewBinder<NewsListVo, AttentionTabMultiItemProvider.ViewHolder> {

    private final OnItemClickListener mListener;

    public AttentionTabMultiItemProvider(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_attention_tab_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final NewsListVo item) {
        Resources resources = holder.itemView.getResources();
        GlideUtils.loadUserHead(holder.adapterAttentionTabIconIv, String.format(AppConstant.IMAGE_URL, item.getPublisherProfile()));
        holder.adapterAttentionTabNameTv.setText(item.getPublisherName());
        holder.adapterAttentionTabTitleTv.setText(Html.fromHtml(item.getTitle()));
        holder.adapterAttentionTabSubCb.setChecked(item.isSubscripted());
        holder.adapterAttentionTabSubCb.setText(holder.itemView.getResources().getString(item.isSubscripted() ?
                R.string.tips_already_subscripted : R.string.tips_not_subscript));
        holder.adapterAttentionTabCommentNumTv.setText(resources.getString(R.string.tips_news_comment_num_template, item.getCommentCount()));
        holder.adapterAttentionTabDateTv.setText(CommonUtils.formatPublishDate(item.getCreated()));
        holder.adapterAttentionTabDomainTv.setText(item.getSource());
        final String image = item.getImage();
        if (!TextUtils.isEmpty(image)) {
            holder.adapterAttentionTabMultiLl.setVisibility(View.VISIBLE);
            String[] images = image.split(",");
            for (int i = 0; i < 3; i++) {
                holder.adapterAttentionTabImagesIv[i].setVisibility(View.VISIBLE);
                GlideUtils.loadImage(holder.adapterAttentionTabImagesIv[i], String.format(AppConstant.IMAGE_URL, images[i]));
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.OnItemClick(holder.itemView, item, holder.getAdapterPosition());
                }
            }
        });
        holder.adapterAttentionTabSubCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setSubscripted(holder.adapterAttentionTabSubCb.isChecked());
                mListener.OnItemCheckChange(null, item.getChannelId(), holder.adapterAttentionTabSubCb.isChecked());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView adapterAttentionTabIconIv;
        AppCompatTextView adapterAttentionTabNameTv;
        AppCompatTextView adapterAttentionTabTitleTv;
        AppCompatCheckBox adapterAttentionTabSubCb;

        LinearLayout adapterAttentionTabMultiLl;
        AppCompatImageView[] adapterAttentionTabImagesIv;
        AppCompatTextView adapterAttentionTabDomainTv;
        AppCompatTextView adapterAttentionTabDateTv;
        AppCompatTextView adapterAttentionTabCommentNumTv;

        public ViewHolder(View itemView) {
            super(itemView);
            adapterAttentionTabIconIv = itemView.findViewById(R.id.adapter_attention_tab_icon_iv);
            adapterAttentionTabNameTv = itemView.findViewById(R.id.adapter_attention_tab_name_tv);
            adapterAttentionTabTitleTv = itemView.findViewById(R.id.adapter_attention_tab_content_tv);
            adapterAttentionTabSubCb = itemView.findViewById(R.id.adapter_attention_tab_sub_cb);

            adapterAttentionTabMultiLl = itemView.findViewById(R.id.adapter_attention_tab_multi_ll);
            adapterAttentionTabImagesIv = new AppCompatImageView[3];
            adapterAttentionTabImagesIv[0] = itemView.findViewById(R.id.adapter_attention_tab_image_1_tv);
            adapterAttentionTabImagesIv[1] = itemView.findViewById(R.id.adapter_attention_tab_image_2_tv);
            adapterAttentionTabImagesIv[2] = itemView.findViewById(R.id.adapter_attention_tab_image_3_tv);

            adapterAttentionTabDomainTv = itemView.findViewById(R.id.adapter_attention_tab_domain_tv);
            adapterAttentionTabDateTv = itemView.findViewById(R.id.adapter_attention_tab_date_tv);
            adapterAttentionTabCommentNumTv = itemView.findViewById(R.id.adapter_attention_tab_comment_num_tv);
        }
    }
}
