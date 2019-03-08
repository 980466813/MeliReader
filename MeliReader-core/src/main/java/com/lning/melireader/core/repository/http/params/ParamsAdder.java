package com.lning.melireader.core.repository.http.params;

import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by Ning on 2019/2/4.
 */

public interface ParamsAdder {

    Map<String, String> getParams();

    boolean needAddParams(Request request);


    ParamsAdder DEFAULT = new ParamsAdder() {

        @Override
        public Map<String, String> getParams() {
            return null;
        }

        @Override
        public boolean needAddParams(Request request) {
            return false;
        }


    };
}
