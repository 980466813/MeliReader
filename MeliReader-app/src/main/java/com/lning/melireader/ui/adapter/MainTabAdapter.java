package com.lning.melireader.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.ui.main.attention.AttentionTabFragment;
import com.lning.melireader.ui.main.news.NewsTabFragment;
import com.lning.melireader.ui.main.user.UserCenterFragment;
import com.lning.melireader.ui.main.video.VideoTabFragment;

/**
 * Created by Ning on 2019/2/10.
 */

public class MainTabAdapter extends FragmentPagerAdapter {

    private BaseFragment[] mFragments;
    private int curPosition;
    private final String json;

    public MainTabAdapter(FragmentManager fm, String json) {
        super(fm);
        this.mFragments = new BaseFragment[4];
        this.json = json;
    }

    @Override
    public Fragment getItem(int position) {
        curPosition = position;
        if (mFragments[position] == null) {
            switch (position) {
                case 0:
                    mFragments[position] = NewsTabFragment.newInstance(json);
                    break;
                case 1:
                    mFragments[position] = VideoTabFragment.newInstance(json);
                    break;
                case 2:
                    mFragments[position] = AttentionTabFragment.newInstance();
                    break;
                case 3:
                    mFragments[position] = UserCenterFragment.newInstance();
                    break;
            }
        }
        return mFragments[position];
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
