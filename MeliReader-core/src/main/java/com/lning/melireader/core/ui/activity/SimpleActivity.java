package com.lning.melireader.core.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.lning.melireader.core.R;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.ToastUtils;

/**
 * Created by Ning on 2018/11/16.
 */

public abstract class SimpleActivity extends AppStatusBarActivity {

    protected static final int WAIT_TIME = 1000;
    protected long BACK_TIME = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImmersiveStatusBar(true,  getResources().getColor(R.color.md_white));
    }


    @Override
    public void onBackPressedSupport() {
        BaseFragment fragment = (BaseFragment) getTopFragment();
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if (System.currentTimeMillis() - BACK_TIME < WAIT_TIME) {
                finish();
            } else {
                BACK_TIME = System.currentTimeMillis();
                ToastUtils.show(this, "再次点击，退出应用");
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            onBackPressedSupport();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
