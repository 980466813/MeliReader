package com.lning.melireader.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.lning.melireader.core.repository.http.bean.FavouriteChannel;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.ui.main.news.article.NewsListFragment;
import com.lning.melireader.ui.search.SearchResultFragment;
import com.lning.melireader.ui.search.list.SearchResultListFragment;

/**
 * Created by Ning on 2019/2/8.
 */

public class SearchResultTabAdapter extends FragmentPagerAdapter {

    private SearchResultFragment.Item[] mItems;
    private SearchResultListFragment[] mFragments;
    private final String keyword;
    private int curPosition;

    public SearchResultTabAdapter(FragmentManager fm
            , SearchResultFragment.Item[] items, String keyword) {
        super(fm);
        this.keyword = keyword;
        this.mItems = items;
        this.mFragments = new SearchResultListFragment[items.length];
    }

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
            mFragments[position] = SearchResultListFragment.newInstance(keyword, mItems[position].ctype);
        }
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return this.mItems.length;
    }

    public SearchResultListFragment getCurrentItem() {
        if (mFragments != null && mFragments.length > 0) {
            Log.d("TAG", "curPosition:" + curPosition);
            return mFragments[curPosition];
        }
        return null;
    }
}
