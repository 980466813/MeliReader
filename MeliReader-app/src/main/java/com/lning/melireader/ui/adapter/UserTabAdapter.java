package com.lning.melireader.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.ui.main.user.detail.UserFragment;
import com.lning.melireader.ui.main.user.detail.UserHomeListFragment;

import java.util.LinkedList;

/**
 * Created by Ning on 2019/2/10.
 */

public class UserTabAdapter extends FragmentPagerAdapter {

    private BaseFragment[] mFragments;
    private LinkedList<UserFragment.Item> mItems;
    private final String userId;
    private final String ownerId;
    private int curPosition;

    public UserTabAdapter(FragmentManager fm, LinkedList<UserFragment.Item> items, String userId, String ownerId) {
        super(fm);
        this.mItems = items;
        this.userId = userId;
        this.ownerId = ownerId;
        this.mFragments = new BaseFragment[this.mItems.size()];
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments[position] == null) {
            UserFragment.Item item = mItems.get(position);
            mFragments[position] = UserHomeListFragment.newInstance(userId, ownerId, item.type, item.defaultValue);
        }
        return mFragments[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        this.curPosition = position;
        return this.mItems.get(position).title;
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.length : 0;
    }

    public BaseFragment getCurFragment() {
        if (mFragments != null && mFragments.length > 0) {
            Log.d("TAG", "curPosition:" + curPosition);
            return mFragments[curPosition];
        }
        return null;
    }
}