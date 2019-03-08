package com.lning.melireader.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.lning.melireader.core.repository.http.bean.FavouriteChannel;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.ui.main.news.article.NewsListFragment;

/**
 * Created by Ning on 2019/2/8.
 */

public class NewsTabAdapter extends FragmentPagerAdapter {

    //    private final String token;
    private final FavouriteChannel[] favouriteChannels;
    private NewsListFragment[] mFragments;
    private int curPosition;

    public NewsTabAdapter(FragmentManager fm, FavouriteChannel[] favouriteChannels) {
        super(fm);
//        this.token = token;
        this.favouriteChannels = favouriteChannels;
        this.mFragments = new NewsListFragment[favouriteChannels.length];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return favouriteChannels[position].getChannel_name();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        curPosition = position;
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments[position] == null) {
            mFragments[position] = NewsListFragment.newInstance(position, favouriteChannels[position]);
        }
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return this.favouriteChannels.length;
    }

    public NewsListFragment getCurrentItem() {
        if (mFragments != null && mFragments.length > 0) {
            Log.d("TAG", "curPosition:" + curPosition);
            return mFragments[curPosition];
        }
        return null;
    }
}
