/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lning.melireader.core.utils;

import android.Manifest;
import android.os.Environment;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;


public class PermissionUtils {
    public static final String TAG = "Permission";


    private PermissionUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public interface RequestPermission {
        /**
         * 权限请求成功
         */
        void onRequestPermissionSuccess();

        /**
         * 用户拒绝了权限请求, 权限请求失败, 但还可以继续请求该权限
         *
         * @param permissions 请求失败的权限名
         */
        void onRequestPermissionFailure(List<String> permissions);

        /**
         * 用户拒绝了权限请求并且用户选择了以后不再询问, 权限请求失败, 这时将不能继续请求该权限, 需要提示用户进入设置页面打开该权限
         *
         * @param permissions 请求失败的权限名
         */
        void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions);
    }


    public static void requestPermission(final RequestPermission requestPermission, RxPermissions rxPermissions, String... permissions) {
        if (permissions == null || permissions.length == 0) return;

        List<String> needRequest = new ArrayList<>();
        for (String permission : permissions) { //过滤调已经申请过的权限
            if (!rxPermissions.isGranted(permission)) {
                needRequest.add(permission);
            }
        }
        if (needRequest.isEmpty()) {//全部权限都已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过,则开始申请
            rxPermissions.requestEach(needRequest.toArray(new String[needRequest.size()]))
                    .buffer(permissions.length)
                    .subscribe(new Consumer<List<Permission>>() {
                        @Override
                        public void accept(List<Permission> permissions) throws Exception {
                            List<String> failurePermissions = new ArrayList<>();
                            List<String> askNeverAgainPermissions = new ArrayList<>();
                            for (Permission p : permissions) {
                                if (!p.granted) {
                                    if (p.shouldShowRequestPermissionRationale) {
                                        failurePermissions.add(p.name);
                                    } else {
                                        askNeverAgainPermissions.add(p.name);
                                    }
                                }
                            }
                            if (failurePermissions.size() > 0) {
                                LogUtils.d("Request permissions failure");
                                requestPermission.onRequestPermissionFailure(failurePermissions);
                            }
                            if (askNeverAgainPermissions.size() > 0) {
                                LogUtils.d("Request permissions failure with ask never again");
                                requestPermission.onRequestPermissionFailureWithAskNeverAgain(askNeverAgainPermissions);
                            }
                            if (failurePermissions.size() == 0 && askNeverAgainPermissions.size() == 0) {
                                LogUtils.d("Request permissions success");
                                requestPermission.onRequestPermissionSuccess();
                            }
                        }
                    });
        }

    }

    /**
     * 请求摄像头权限
     */
    public static void launchCamera(RequestPermission requestPermission, RxPermissions rxPermissions) {
        requestPermission(requestPermission, rxPermissions, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }

    /**
     * 请求外部存储的权限
     */
    public static void externalStorage(RequestPermission requestPermission, RxPermissions rxPermissions) {
        requestPermission(requestPermission, rxPermissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static void readStorage(RequestPermission requestPermission, RxPermissions rxPermissions) {
        requestPermission(requestPermission, rxPermissions, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    /**
     * 请求发送短信权限
     */
    public static void sendSms(RequestPermission requestPermission, RxPermissions rxPermissions) {
        requestPermission(requestPermission, rxPermissions, Manifest.permission.SEND_SMS);
    }

    /**
     * 请求打电话权限
     */
    public static void callPhone(RequestPermission requestPermission, RxPermissions rxPermissions) {
        requestPermission(requestPermission, rxPermissions, Manifest.permission.CALL_PHONE);
    }

    /**
     * 请求获取手机状态的权限
     */
    public static void readPhoneState(RequestPermission requestPermission, RxPermissions rxPermissions) {
        requestPermission(requestPermission, rxPermissions, Manifest.permission.READ_PHONE_STATE);
    }

}

