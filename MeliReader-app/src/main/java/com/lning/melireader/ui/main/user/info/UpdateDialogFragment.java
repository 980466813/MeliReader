package com.lning.melireader.ui.main.user.info;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.event.UpdateUserEvent;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.UserInfoContact;
import com.lning.melireader.contact.UserUpdateContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.repository.http.bean.UserVo;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.presenter.UpdateDialogPresenter;
import com.lning.melireader.presenter.UserInfoPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by Ning on 2019/2/8.
 */

public class UpdateDialogFragment extends BaseFragment<UpdateDialogPresenter>
        implements UserUpdateContact.View, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.user_info_signature_cl)
    AppCompatEditText userInfoSigatureCl;

    @BindView(R.id.user_info_nickname_tv)
    AppCompatEditText userInfoNicknameTv;

    @BindView(R.id.user_info_address_tv)
    AppCompatEditText userInfoAddressIv;

    @BindView(R.id.user_info_gender_tv)
    RadioGroup userInfoGenderTv;

    @BindView(R.id.user_info_birthday_tv)
    AppCompatEditText userInfoBirthdayTv;

    private String userId;
    private UpdateUserEvent event;
    private byte gender = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_update_dialog;
    }

    @Override
    protected void init(Bundle savedInstanceState, View mView) {
        initBasicData();
        initToolbar(mView, event.title, R.mipmap.ic_navigation_back, getString(R.string.tips_finish));
        setSwipeBackEnable(true);
        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_LEFT);
        initView(mView);
        userInfoGenderTv.setOnCheckedChangeListener(this);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    private void initView(View rootView) {
        rootView.findViewById(event.viewId).setVisibility(View.VISIBLE);
        if (event.viewId == R.id.user_info_gender_tv) {
            gender = Byte.parseByte(event.value);
            if ("0".equals(event.value)) {
                ((RadioButton) rootView.findViewById(R.id.user_update_male_rb)).setChecked(true);
            } else if ("1".equals(event.value)) {
                ((RadioButton) rootView.findViewById(R.id.user_update_female_rb)).setChecked(true);
            }
        } else if (event.viewId == R.id.user_info_birthday_tv) {
            ((AppCompatEditText) rootView.findViewById(event.viewId)).setText(event.value);
        } else {
            ((AppCompatEditText) rootView.findViewById(event.viewId)).setText(event.value);
            ((AppCompatEditText) rootView.findViewById(event.viewId)).setSelection(event.value.length());
        }
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        userId = bundle.getString(AppConstant.USER_ID, "");
        event = bundle.getParcelable(AppConstant.OBJECT);
    }

    @Override
    protected void onNavigationClickListener(View view) {
        getSimpleActivity().onBackPressedSupport();
    }

    @Override
    protected void onToolbarRightClickListener(View view) {
        UserVo userVo = new UserVo();
        if (event.viewId == UpdateUserEvent.NICKNAME_TYPE) {
            userVo.setNickname(userInfoNicknameTv.getText().toString());
        } else if (event.viewId == UpdateUserEvent.ADDRESS_TYPE) {
            userVo.setAddress(userInfoAddressIv.getText().toString());
        } else if (event.viewId == UpdateUserEvent.BIRTHDAY_TYPE) {
            userVo.setBirthday(CommonUtils.formatDate("yyyy-MM-dd",userInfoBirthdayTv.getText().toString()));
        } else if (event.viewId == UpdateUserEvent.GENDER_TYPE) {
            userVo.setGender(gender);
        } else {
            userVo.setSignature(userInfoSigatureCl.getText().toString());
        }
        mPresenter.updateUserInfo(event.viewId, userId, userVo.getNickname(), userVo.getGender(), userVo.getAddress(), userVo.getBirthday(), userVo.getSignature());
    }


    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    public static BaseFragment newInstance(String userId, UpdateUserEvent event) {
        UpdateDialogFragment fragment = new UpdateDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.USER_ID, userId);
        bundle.putParcelable(AppConstant.OBJECT, event);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onUpdateInfoSuccess(UpdateUserEvent userEvent) {
        LogUtils.d(userEvent.toString());
        EventBus.getDefault().post(userEvent);
        pop();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (R.id.user_update_male_rb == i) {
            gender = 0;
        } else {
            gender = 1;
        }
    }
}
