package com.lning.melireader;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lning.melireader.core.ui.activity.SimpleActivity;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.ToastUtils;
import com.lning.melireader.ui.LauncherFragment;
import com.lning.melireader.ui.login.LoginFragment;
import com.lning.melireader.ui.main.MainFragment;
import com.lning.melireader.ui.main.news.NewsTabFragment;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by Ning on 2018/11/15.
 */

public class AppActivity extends SimpleActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ISupportFragment setRootDelegate() {
        return LauncherFragment.newInstance();
    }


    @Override
    public void onBackPressedSupport() {
        BaseFragment fragment = (BaseFragment) getTopFragment();
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1
                || fragment instanceof MainFragment || fragment instanceof LoginFragment) {
            if (System.currentTimeMillis() - BACK_TIME < WAIT_TIME) {
                if (fragment instanceof LoginFragment) {
                    finish();
                } else {
                    popTo(MainFragment.class, false, new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                }
            } else {
                BACK_TIME = System.currentTimeMillis();
                ToastUtils.show(this, "再次点击，退出应用");
            }
        } else {
//            if (fragment.getPreFragment() instanceof NewsTabFragment) {
//                setImmersiveStatusBar(false, getResources().getColor(R.color.md_selected_blue));
//            } else {
//                setImmersiveStatusBar(true,  getResources().getColor(R.color.md_white));
//            }
            pop();
        }
    }
}
