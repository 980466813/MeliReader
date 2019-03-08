package com.lning.melireader.ui.provider;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.GlideUtils;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/24.
 */

public class UserHomeListSingleItemProvider extends ItemViewBinder<NewsListVo, UserHomeListSingleItemProvider.ViewHolder> {

    private final OnItemClickListener mListener;

    public UserHomeListSingleItemProvider(OnItemClickListener listener) {
        this.mListener = listener;
    }


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_user_news_single_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final NewsListVo item) {
        GlideUtils.loadUserHead(holder.adapterUserNewsIconIv, String.format(AppConstant.IMAGE_URL, item.getPublisherProfile()));
        holder.adapterUserNewsTitleTv.setText(Html.fromHtml(item.getTitle()));
        holder.adapterUserNewsDateTv.setText(CommonUtils.formatPublishDate(item.getCreated()));
        holder.adapterUserNewsNameTv.setText(item.getPublisherName());
        String image = item.getImage();
        if (TextUtils.isEmpty(image)) {
            holder.adapterUserNewsIsVideoIv.setVisibility(View.GONE);
            holder.adapterUserNewsImageTv.setVisibility(View.GONE);
        } else {
            String[] images = image.split(",");
            holder.adapterUserNewsIsVideoIv.setVisibility("video".equals(item.getCtype()) ? View.VISIBLE : View.GONE);
            GlideUtils.loadImage(holder.adapterUserNewsImageTv, String.format(AppConstant.IMAGE_URL, images[0]));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.OnItemClick(null, item);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView adapterUserNewsIconIv;
        AppCompatImageView adapterUserNewsIsVideoIv;
        AppCompatTextView adapterUserNewsNameTv;
        AppCompatTextView adapterUserNewsDateTv;
        AppCompatTextView adapterUserNewsTitleTv;
        AppCompatImageView adapterUserNewsImageTv;


        public ViewHolder(View itemView) {
            super(itemView);
            adapterUserNewsIconIv = itemView.findViewById(R.id.adapter_user_news_single_icon_iv);
            adapterUserNewsNameTv = itemView.findViewById(R.id.adapter_user_news_single_name_tv);
            adapterUserNewsDateTv = itemView.findViewById(R.id.adapter_user_news_single_date_tv);
            adapterUserNewsTitleTv = itemView.findViewById(R.id.adapter_user_news_single_Title_tv);
            adapterUserNewsIsVideoIv = itemView.findViewById(R.id.adapter_user_news_single_isvideo_iv);
            adapterUserNewsImageTv = itemView.findViewById(R.id.adapter_user_news_single_image_iv);
        }
    }
}
