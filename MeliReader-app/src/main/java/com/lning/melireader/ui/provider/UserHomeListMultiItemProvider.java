package com.lning.melireader.ui.provider;

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
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.GlideUtils;
import com.lning.melireader.ui.main.user.detail.UserHomeListFragment;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/24.
 */

public class UserHomeListMultiItemProvider extends ItemViewBinder<NewsListVo, UserHomeListMultiItemProvider.ViewHolder> {

    private final OnItemClickListener mListener;

    public UserHomeListMultiItemProvider(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_user_news_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final NewsListVo item) {
        GlideUtils.loadUserHead(holder.adapterUserNewsIconIv, String.format(AppConstant.IMAGE_URL, item.getPublisherProfile()));
        holder.adapterUserNewsNameTv.setText(item.getPublisherName());
        holder.adapterUserNewsDateTv.setText(CommonUtils.formatPublishDate(item.getCreated()));
        holder.adapterUserNewsTitleTv.setText(item.getTitle());
        final String image = item.getImage();
        if (!TextUtils.isEmpty(image)) {
            holder.adapterUserNewsLl.setVisibility(View.VISIBLE);
            String[] images = image.split(",");
            for (int i = 0; i < 3; i++) {
                holder.adapterUserNewsImageIv[i].setVisibility(View.VISIBLE);
                GlideUtils.loadImage(holder.adapterUserNewsImageIv[i], String.format(AppConstant.IMAGE_URL, images[i]));
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnItemClick(null, item);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView adapterUserNewsIconIv;
        AppCompatTextView adapterUserNewsNameTv;
        AppCompatTextView adapterUserNewsDateTv;
        LinearLayout adapterUserNewsLl;
        AppCompatTextView adapterUserNewsTitleTv;
        AppCompatImageView[] adapterUserNewsImageIv;


        public ViewHolder(View itemView) {
            super(itemView);
            adapterUserNewsIconIv = itemView.findViewById(R.id.adapter_user_news_icon_iv);
            adapterUserNewsNameTv = itemView.findViewById(R.id.adapter_user_news_name_tv);
            adapterUserNewsDateTv = itemView.findViewById(R.id.adapter_user_news_date_tv);
            adapterUserNewsTitleTv = itemView.findViewById(R.id.adapter_user_news_title_tv);
            adapterUserNewsLl = itemView.findViewById(R.id.adapter_user_news_ll);
            adapterUserNewsImageIv = new AppCompatImageView[3];
            adapterUserNewsImageIv[0] = itemView.findViewById(R.id.adapter_user_news_image_1_iv);
            adapterUserNewsImageIv[1] = itemView.findViewById(R.id.adapter_user_news_image_2_iv);
            adapterUserNewsImageIv[2] = itemView.findViewById(R.id.adapter_user_news_image_3_iv);
        }
    }
}
