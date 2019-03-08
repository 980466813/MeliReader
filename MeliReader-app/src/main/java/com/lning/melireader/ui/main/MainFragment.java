package com.lning.melireader.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.event.TabSelectedEvent;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.ui.adapter.MainTabAdapter;
import com.lning.melireader.widget.BaseViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by Ning on 2019/2/8.
 */

public class MainFragment extends BaseFragment
        implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.main_container_fl)
    BaseViewPager mainContainerFl;

    @BindView(R.id.main_bottom_bbl)
    TabLayout mainBottomBbl;

    private MainTabAdapter mAdapter;

    private String[] mTabTitle = new String[]{"新闻", "视频", "关注", "我的"};
    private int[] mTabIndicatorImg = new int[]{
            R.drawable.selector_news_tab,
            R.drawable.selector_video_tab,
            R.drawable.selector_sub_tab,
            R.drawable.selector_center_tab
    };


    public static MainFragment newInstance(String json) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.FAVOURITE_CHANNEL, json);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View mView) {
        Bundle bundle = getArguments();
        String json = bundle.getString(AppConstant.FAVOURITE_CHANNEL);
        mAdapter = new MainTabAdapter(getChildFragmentManager(), json);
        mainContainerFl.setAdapter(mAdapter);
        mainContainerFl.setOffscreenPageLimit(4);
        initTabLayout();
        initListener();
    }

    private void initTabLayout() {
        for (int i = 0; i < 4; i++) {
            mainBottomBbl.addTab(mainBottomBbl.newTab()
                    .setCustomView(getTabItemCustomView(mTabIndicatorImg[i], mTabTitle[i])));
        }
    }

    private void initListener() {
        mainBottomBbl.addOnTabSelectedListener(this);
    }

    private View getTabItemCustomView(int drawableId, String title) {
        View rootView = LayoutInflater.from(_mActivity).inflate(R.layout.tablayout_img_tv, null);
        ImageView img = rootView.findViewById(R.id.tab_img);
        TextView tv = rootView.findViewById(R.id.tab_tv);
        img.setImageResource(drawableId);
        tv.setText(title);
        return rootView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mainContainerFl.setCurrentItem(tab.getPosition(), false);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        EventBus.getDefault().post(new TabSelectedEvent(tab.getPosition()));
    }
}
