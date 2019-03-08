package com.lning.melireader.ui.main.user.info;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;

import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.event.UpdateUserEvent;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.UserInfoContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.GlideUtils;
import com.lning.melireader.presenter.UserInfoPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ning on 2019/2/8.
 */

public class UserInfoFragment extends BaseFragment<UserInfoPresenter>
        implements UserInfoContact.View {

    @BindView(R.id.user_info_nickname_tv)
    AppCompatTextView userInfoNicknameTv;

    @BindView(R.id.user_info_profile_iv)
    AppCompatImageView userInfoProfileIv;

    @BindView(R.id.user_info_address_tv)
    AppCompatTextView userInfoAddressIv;

    @BindView(R.id.user_info_gender_tv)
    AppCompatTextView userInfoGenderTv;

    @BindView(R.id.user_info_birthday_tv)
    AppCompatTextView userInfoBirthdayTv;

    private UserVo mUserVo;
    private String userId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected void init(Bundle savedInstanceState, View mView) {
        initToolbar(mView, getString(R.string.tips_user_info), R.mipmap.ic_navigation_back);
        initBasicData();
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        userId = bundle.getString(AppConstant.USER_ID, "");
        mUserVo = new UserVo();
        mUserVo.setId(userId);
        mUserVo.setProfile("123456.jpg");
        mUserVo.setBirthday(new Date());
        mUserVo.setAddress("福建省福州市");
        mUserVo.setSignature(null);
        mUserVo.setGender((byte) -1);
        mUserVo.setNickname("测试用户名");
        onGetUserDetailInfoSuccess(mUserVo);
    }

    @OnClick(value = {R.id.user_info_profile_cl, R.id.user_info_address_cl, R.id.user_info_birthday_cl,
            R.id.user_info_nickname_cl, R.id.user_info_signature_cl, R.id.user_info_gender_cl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_info_profile_cl:
//                start(UpdateDialogFragment.newInstance(userId, UpdateUserEvent.createProfileEvent(mUserVo.getProfile())));
                break;
            case R.id.user_info_address_cl:
                start(UpdateDialogFragment.newInstance(userId, UpdateUserEvent.createAddressEvent(mUserVo.getAddress())));
                break;
            case R.id.user_info_birthday_cl:
                start(UpdateDialogFragment.newInstance(userId, UpdateUserEvent.createBirthdayEvent(
                        CommonUtils.formatDate("yyyy-MM-dd HH:mm:ss", mUserVo.getBirthday() == null ? new Date() : mUserVo.getBirthday()))));
                break;
            case R.id.user_info_nickname_cl:
                start(UpdateDialogFragment.newInstance(userId, UpdateUserEvent.createNicknameEvent(mUserVo.getNickname())));
                break;
            case R.id.user_info_signature_cl:
                start(UpdateDialogFragment.newInstance(userId, UpdateUserEvent.createSignatureEvent(mUserVo.getSignature())));
                break;
            case R.id.user_info_gender_cl:
                start(UpdateDialogFragment.newInstance(userId, UpdateUserEvent.createGenderEvent(mUserVo.getGender() + "")));
                break;
            default:
                break;
        }
    }

    public static BaseFragment newInstance(String userId) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.USER_ID, userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void onNavigationClickListener(View view) {
        getSimpleActivity().onBackPressedSupport();
    }

    @Override
    public void onGetUserDetailInfoSuccess(UserVo userVo) {
        mUserVo = userVo;
        GlideUtils.loadUserHead(userInfoProfileIv, String.format(AppConstant.IMAGE_URL, userVo.getProfile()));
        userInfoNicknameTv.setText(userVo.getNickname());
        userInfoAddressIv.setText(userVo.getAddress());
        userInfoGenderTv.setText(userVo.getGender() == -1 ? getString(R.string.tips_secret) : userVo.getGender() == 1 ? getString(R.string.tips_user_male)
                : getString(R.string.tips_user_female));
        userInfoBirthdayTv.setText(userVo.getBirthday() == null ? getString(R.string.tips_secret) : CommonUtils.formatDate("yyyy-MM-dd", userVo.getBirthday()));
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().post(mUserVo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateUserInfoSuccess(UpdateUserEvent event) {
        View view = mView.findViewById(event.viewId);
        if (view instanceof AppCompatTextView) {
            if (event.viewId == UpdateUserEvent.GENDER_TYPE) {
                ((AppCompatTextView) view).setText("0".equals(event.value) ? "男" : "1".equals(event.value) ? "女" : "保密");
            } else {
                ((AppCompatTextView) view).setText(event.value);
            }
        }
        mUserVo.setNickname(event.viewId == UpdateUserEvent.NICKNAME_TYPE ? event.value : mUserVo.getNickname());
        mUserVo.setProfile(event.viewId == UpdateUserEvent.IMAGE_TYPE ? event.value : mUserVo.getProfile());
        mUserVo.setSignature(event.viewId == UpdateUserEvent.SIGNATURE_TYPE ? event.value : mUserVo.getSignature());
        mUserVo.setAddress(event.viewId == UpdateUserEvent.ADDRESS_TYPE ? event.value : mUserVo.getAddress());
        mUserVo.setGender(event.viewId == UpdateUserEvent.GENDER_TYPE ? Byte.parseByte(event.value) : mUserVo.getGender());
        mUserVo.setBirthday(event.viewId == UpdateUserEvent.BIRTHDAY_TYPE ? CommonUtils.formatDate("yyyy-MM-dd", event.value) : mUserVo.getBirthday());
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }
}
