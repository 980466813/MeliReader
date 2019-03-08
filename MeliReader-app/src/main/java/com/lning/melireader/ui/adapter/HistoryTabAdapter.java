package com.lning.melireader.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.lning.melireader.ui.main.user.history.HistoryFragment;
import com.lning.melireader.ui.main.user.history.HistoryListFragment;

/**
 * Created by Ning on 2019/2/13.
 */

public class HistoryTabAdapter extends FragmentPagerAdapter {

    private final HistoryFragment.Item[] mItems;
    private final HistoryListFragment[] mFragments;
    private int curPosition = 0;

    public HistoryTabAdapter(FragmentManager fm, HistoryFragment.Item[] items) {
        super(fm);
        this.mItems = items;
        this.mFragments = new HistoryListFragment[items.length];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mItems[position].title;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        curPosition = position;
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments[position] == null) {
            mFragments[position] = HistoryListFragment.newInstance(mItems[position].type, position);
        }
        return mFragments[position];
    }

    public HistoryListFragment getCurrentItem() {
        if (mFragments != null && mFragments.length > 0) {
            Log.d("TAG", "curPosition:" + curPosition);
            return mFragments[curPosition];
        }
        return null;
    }

    @Override
    public int getCount() {
        return mItems.length;
    }
}
