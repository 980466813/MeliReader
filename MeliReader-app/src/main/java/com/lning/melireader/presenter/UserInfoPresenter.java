package com.lning.melireader.presenter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.lning.melireader.R;
import com.lning.melireader.app.event.UpdateUserEvent;
import com.lning.melireader.contact.UserInfoContact;
import com.lning.melireader.core.mvp.BasePresenter;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.PermissionUtils;
import com.lning.melireader.model.impl.UserModel;
import com.lning.melireader.ui.main.user.info.UserInfoFragment;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class UserInfoPresenter extends BasePresenter<UserInfoFragment, UserModel>
        implements UserInfoContact.Presenter<UserInfoFragment> {

    private TimePickerView dialog;

    @Inject
    public UserInfoPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void getUserDetailInfo() {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.getUserInfo("detail", mMvpModel.getLocalLoginUserToken())
                .subscribe(new Consumer<UserVo>() {
                    @Override
                    public void accept(UserVo userVo) throws Exception {
                        mMvpView.onGetUserDetailInfoSuccess(userVo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void uploadProfile(final UserVo userVo, File file) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        mMvpView.showDialog("正在上传");
        mMvpModel.addSubscribe(mMvpModel.uploadProfile(userVo, file)
                .subscribe(new Consumer<UpdateUserEvent>() {
                    @Override
                    public void accept(UpdateUserEvent updateUserEvent) throws Exception {
                        mMvpView.dismissDialog();
                        mMvpView.showMessage("上传图片成功");
                        mMvpView.onUpdateUserInfoSuccess(updateUserEvent);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void updateUserBirthday(UserVo userVo, final Date date) {
        boolean isNetworkAvailable = mMvpView.isNetworkAvailable();
        if (!isNetworkAvailable) {
            mMvpView.showMessage("当前位置电波无法到达...");
            return;
        }
        mMvpView.showDialog("正在更改");
        mMvpModel.addSubscribe(mMvpModel.updateUserInfo(userVo.getId(), userVo.getRid(), userVo.getNickname(), userVo.getGender(), userVo.getAddress(), date, userVo.getAddress(), userVo.getSignature())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        mMvpView.showDialog("编辑信息成功");
                        mMvpView.onUpdateUserInfoSuccess(UpdateUserEvent.createBirthdayEvent(CommonUtils.formatDate("yyyy-MM-dd", date)));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handlerApiError(throwable);
                    }
                }));
    }

    @Override
    public void showTimePickerDialog(Context context, final String title, Date date, final OnTimeSelectListener listener) {
        final java.util.Calendar selectedDate = java.util.Calendar.getInstance();
        if (date != null) {
            selectedDate.setTime(date);
        }
        java.util.Calendar startDate = java.util.Calendar.getInstance();
        java.util.Calendar endDate = java.util.Calendar.getInstance();
        int year = startDate.get(Calendar.YEAR);
        startDate.set(2016, 0, 1);
        endDate.set(year + 5, 11, 31);
        dialog = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                listener.onTimeSelect(date, v);
            }
        })
                .setLayoutRes(R.layout.view_timepicker_dialog, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        v.findViewById(R.id.dialog_confirm_btn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.returnData();
                                dialog.dismiss();
                            }
                        });
                        v.findViewById(R.id.dialog_cancel_btn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        TextView titleTv = v.findViewById(R.id.dialog_title_tv);
                        titleTv.setText(title);
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setContentTextSize(18)//滚轮文字大小
                .setTextColorOut(0xFF999999)
                .setTextColorCenter(0xFF000000)
                .isCyclic(true)//是否循环滚动
//                .setSubmitColor(context.getResources().getColor(R.color.md_blue_300))//确定按钮文字颜色
//                .setCancelColor(context.getResources().getColor(R.color.md_black_300))//取消按钮文字颜色
                .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "", "", "", "")//默认设置为年月日时分秒
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)
                .build();
        dialog.show();
    }

    @Override
    public void clearAllLoginUserInfo() {
        clearAllRequest();
        mMvpModel.clearAllUserInfo();
    }

    @Override
    public void requestPermission(final int type) {
        PermissionUtils.RequestPermission callback = new PermissionUtils.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                mMvpView.onGetPermissionSuccess(type);
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mMvpView.showMessage("上传图片需要授权");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mMvpView.onGetPermissionSuccess(type);
            }
        };
        if (type == 0) {
            PermissionUtils.launchCamera(callback, mMvpView.getRxPermissions());
        } else {
            PermissionUtils.readStorage(callback, mMvpView.getRxPermissions());
        }

    }


}
