package com.lning.melireader.core.ui.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.lning.melireader.core.R;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.core.utils.PhoneUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Win on 2018/5/5.
 */

public abstract class AppStatusBarActivity extends SupportActivity {

    private View mViewStatusBarPlace;
    private ContentFrameLayout mViewContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(@Nullable Bundle savedInstanceState) {
        super.setContentView(R.layout.app_base_container);
        mViewContainer = findViewById(R.id.view_container_place);
        mViewStatusBarPlace = findViewById(R.id.view_status_bar);
        ViewGroup.LayoutParams params = mViewStatusBarPlace.getLayoutParams();
        params.height = getStatusBarHeight();
        mViewStatusBarPlace.setLayoutParams(params);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.view_container_place, setRootDelegate());
        }

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        mViewContainer.addView(view);
    }

    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        Log.i("onPre", "statusBarHeight:" + statusBarHeight);
        return statusBarHeight;
    }

    @Override
    public Resources getResources() {
        Resources mResources = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        mResources.updateConfiguration(config, mResources.getDisplayMetrics());
        return mResources;
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param fontIconDark 状态栏字体和图标颜色是否为深色
     */
    public void setImmersiveStatusBar(boolean fontIconDark, int statusBarPlaceColor) {
        setImmersiveStatusBar(false, fontIconDark, statusBarPlaceColor);
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param fontIconDark 状态栏字体和图标颜色是否为深色
     */
    public void setImmersiveStatusBar(boolean isResId, boolean fontIconDark, int statusBarPlaceColor) {
        setTranslucentStatus();
        if (fontIconDark) {
            if (PhoneUtils.isMIUI()) {
                setMIUIStatusBarFontIconDark(true);
            } else if (PhoneUtils.isFlyme()) {
                setFlymeStatusBarFontIconDark(true);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setAndroid23StatusBarFontIconDark(true);
            }
        }
        setStatusBarPlaceColor(isResId, statusBarPlaceColor);
    }

    /**
     * 设置状态栏底色
     *
     * @param isResId
     * @param statusColor
     */
    public void setStatusBarPlaceColor(boolean isResId, int statusColor) {
        if (mViewStatusBarPlace == null) {
            mViewStatusBarPlace = findViewById(R.id.view_status_bar);
        }
        if (!isResId) {
            mViewStatusBarPlace.setBackgroundColor(statusColor);
            LogUtils.d("设置颜色：" + statusColor);
        } else {
            mViewStatusBarPlace.setBackgroundResource(statusColor);
        }
        mViewStatusBarPlace.invalidate();
    }

    /**
     * 设置状态栏透明
     */
    private void setTranslucentStatus() {
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     * @param dark 状态栏字体是否为深色
     */
    private void setMIUIStatusBarFontIconDark(boolean dark) {
        try {
            Window window = getWindow();
            Class clazz = getWindow().getClass();
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {       //清除黑色字体
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAndroid23StatusBarFontIconDark(boolean dark) {
        // android6.0+系统
        // 这个设置和在xml的style文件中用这个<item name="android:windowLightStatusBar">true</item>属性是一样的
        if (dark) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    private void setFlymeStatusBarFontIconDark(boolean dark) {
        try {
            Window window = getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 由于是单Activity模式，故当Activity结束时，程序也已经被关闭，故在此种做垃圾回收的操作，但不一定执行
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

    protected abstract ISupportFragment setRootDelegate();
}
