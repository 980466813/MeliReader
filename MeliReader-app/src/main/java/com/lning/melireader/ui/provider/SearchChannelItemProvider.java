package com.lning.melireader.ui.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;
import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
import com.lning.melireader.core.repository.http.bean.SearchChannelItem;
import com.lning.melireader.core.utils.GlideUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/24.
 */

public class SearchChannelItemProvider extends ItemViewBinder<SearchChannelItem, SearchChannelItemProvider.ViewHolder> {
    private final SimpleItemClickListenerAdapter mListener;

    public SearchChannelItemProvider(SimpleItemClickListenerAdapter simpleOnItemClickListener) {
        this.mListener = simpleOnItemClickListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_search_channel_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final SearchChannelItem item) {
        GlideUtils.loadUserHead(holder.adapterSearchChannelImageIv, String.format(AppConstant.IMAGE_URL, item.getChannelImage()));
        holder.adapterSearchChannelNameTv.setText(Html.fromHtml(item.getChannelName()));
        RxView.clicks(holder.itemView).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                mListener.OnItemClick(null, item);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView adapterSearchChannelNameTv;
        AppCompatImageView adapterSearchChannelImageIv;

        public ViewHolder(View itemView) {
            super(itemView);

            adapterSearchChannelImageIv = itemView.findViewById(R.id.adapter_search_channel_image_iv);
            adapterSearchChannelNameTv = itemView.findViewById(R.id.adapter_search_channel_name_tv);
        }
    }
}
