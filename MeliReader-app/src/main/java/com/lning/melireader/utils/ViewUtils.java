package com.lning.melireader.utils;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.lning.melireader.R;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.utils.ScreenUtils;

/**
 * Created by Ning on 2019/2/24.
 */

public class ViewUtils {

    public static AppCompatTextView makeTagView(Context context, final String id, final String tag, final OnItemClickListener simpleItemClickListener) {
        AppCompatTextView tagView = new AppCompatTextView(context);
        tagView.setBackgroundResource(R.drawable.bg_common_tag_tv);
        int paSE = ScreenUtils.dip2px(context, 10);
        int paTB = ScreenUtils.dip2px(context, 3);
        tagView.setPadding(paSE, paTB, paSE, paTB);
        tagView.setText(tag);
        tagView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleItemClickListener.OnItemClick(null, tag);
            }
        });
        return tagView;
    }
}
