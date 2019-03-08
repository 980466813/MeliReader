package com.lning.melireader.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.lning.melireader.core.repository.http.bean.FavouriteChannel;
import com.lning.melireader.ui.main.video.list.VideoListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ning on 2019/2/8.
 */

public class VideoTabAdapter extends FragmentPagerAdapter {

    private final List<FavouriteChannel> mFavouriteChannels;
    private VideoListFragment[] mFragments;
    private int curPosition;

    public VideoTabAdapter(FragmentManager fm, FavouriteChannel[] favouriteChannels) {
        super(fm);
        this.mFavouriteChannels = new ArrayList<>();
        for (FavouriteChannel favouriteChannel : favouriteChannels) {
            if (!favouriteChannel.getChannel_name().equals("视频")) {
                mFavouriteChannels.add(favouriteChannel);
            }

        }
        this.mFragments = new VideoListFragment[favouriteChannels.length];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFavouriteChannels.get(position).getChannel_name();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        curPosition = position;
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments[position] == null) {
            mFragments[position] = VideoListFragment.newInstance(mFavouriteChannels.get(position));
        }
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return this.mFavouriteChannels.size();
    }

    public VideoListFragment getCurrentItem() {
        if (mFragments != null && mFragments.length > 0) {
            Log.d("TAG", "curPosition:" + curPosition);
            return mFragments[curPosition];
        }
        return null;
    }
}
