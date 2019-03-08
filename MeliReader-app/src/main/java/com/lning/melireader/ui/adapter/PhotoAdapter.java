package com.lning.melireader.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.lning.melireader.core.utils.GlideUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.ui.main.news.photo.PhotoFragment;

/**
 * Created by Ning on 2019/2/18.
 */

public class PhotoAdapter extends PagerAdapter {
    private String[] imageUrls;
    private PhotoFragment fragment;
    private int curPosition = 0;

    public PhotoAdapter(String[] imageUrls, PhotoFragment fragment) {
        this.imageUrls = imageUrls;
        this.fragment = fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String url = imageUrls[position];
        PhotoView photoView = new PhotoView(fragment.getSimpleActivity());
        GlideUtils.loadImage(photoView, url);
        container.addView(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.d("onClick: ");
                fragment.getSimpleActivity().onBackPressedSupport();
            }
        });
        return photoView;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        curPosition = position;
        return super.getPageTitle(position);
    }

    public int getCurPosition() {
        return curPosition;
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.length : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}