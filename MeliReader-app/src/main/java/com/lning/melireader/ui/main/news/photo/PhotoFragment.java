package com.lning.melireader.ui.main.news.photo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.ui.adapter.PhotoAdapter;

import butterknife.BindView;

/**
 * Created by Ning on 2019/2/18.
 */

public class PhotoFragment extends BaseFragment
        implements ViewPager.OnPageChangeListener {

    @BindView(R.id.photo_pager_vg)
    ViewPager photoPagerVg;

    @BindView(R.id.photo_page_indicator)
    AppCompatTextView photoPageIndicator;

    PhotoAdapter mAdapter;

    private String[] images;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_photo;
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        initBasicData();
        initToolbar(view, "", R.mipmap.ic_navigation_back_white);
        photoPageIndicator.setText("1/" + images.length);
        mAdapter = new PhotoAdapter(images, this);
        photoPagerVg.setAdapter(mAdapter);
        photoPagerVg.addOnPageChangeListener(this);
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        images = bundle.getStringArray(AppConstant.PHOTO_URLS);
        if (images == null || images.length == 0) {
            throw new IllegalArgumentException("图片数据为空");
        }
    }

    @Override
    protected void onNavigationClickListener(View view) {
        getSimpleActivity().onBackPressedSupport();
    }

    public static PhotoFragment newsInstance(String imageUrls) {
        String[] images = imageUrls.split(",");
        PhotoFragment fragment = new PhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray(AppConstant.PHOTO_URLS, images);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        if (photoPagerVg != null) {
            photoPagerVg.removeOnPageChangeListener(this);
        }
        super.onDestroyView();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        LogUtils.d("onPageScrolled:" + position);
    }

    @Override
    public void onPageSelected(int position) {
        LogUtils.d("onPageSelected:" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
