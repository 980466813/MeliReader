package com.lning.melireader.core.utils;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.lning.melireader.core.R;

/**
 * Created by Win on 2018/5/3.
 */

public class GlideUtils {

    public static void loadUserHead(final ImageView imageView, String url) {
        String imgUrl;
        if (TextUtils.isEmpty(url)) {
            imgUrl = "";
        } else {
            if (url.startsWith("http")) {
                imgUrl = url;
            } else {
                imgUrl = "http://" + url;
            }
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_user_default_phone)
                .error(R.mipmap.ic_user_default_phone)
                .fitCenter()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
        Glide.with(imageView.getContext())
                .load(imgUrl)
                .apply(options)
                .into(imageView);
    }

    public static void loadUserHead(final ImageView imageView, String url, int width, int height) {
        String imgUrl;
        if (TextUtils.isEmpty(url)) {
            imgUrl = "";
        } else {
            if (url.startsWith("http")) {
                imgUrl = url;
            } else {
                imgUrl = "http://" + url;
            }
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_empty_data)
                .error(R.mipmap.ic_empty_data)
                .override(width, height)
                .fitCenter()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略

        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void loadImage(final ImageView imageView, String url) {
        String imgUrl;
        if (TextUtils.isEmpty(url)) {
            imgUrl = "";
        } else {
            if (url.startsWith("http")) {
                imgUrl = url;
            } else {
                imgUrl = "http://" + url;
            }
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_empty_data)
                .error(R.mipmap.ic_empty_data)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略

        Glide.with(imageView.getContext())
                .load(imgUrl)
                .apply(options)
                .into(imageView);
    }

    public static void loadImage(final ImageView imageView, String url, int width, int height) {
        String imgUrl;
        if (TextUtils.isEmpty(url)) {
            imgUrl = "";
        } else {
            if (url.startsWith("http")) {
                imgUrl = url;
            } else {
                imgUrl = "http://" + url;
            }
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_empty_data)
                .error(R.mipmap.ic_empty_data)
                .fitCenter()
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略

        Glide.with(imageView.getContext())
                .load(imgUrl)
                .apply(options)
                .into(imageView);
    }
}
