package com.lning.melireader.ui.main.user.collection;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.CollectionUpdateContact;
import com.lning.melireader.core.app.constant.TokenInvalid;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.repository.db.pojo.TagPojo;
import com.lning.melireader.core.repository.http.bean.CollectionVo;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.presenter.CollectionUpdatePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.TimeUnit;

import am.widget.wraplayout.WrapLayout;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by Ning on 2019/2/16.
 */

public class CollectionUpdateFragment extends BaseFragment<CollectionUpdatePresenter>
        implements CollectionUpdateContact.View {

    @BindView(R.id.collection_tag_options_container_wl)
    WrapLayout collectionTagOptionsContainerWl;

    @BindView(R.id.collection_tag_create_et)
    AppCompatEditText collectionTagCreateEt;

    @BindView(R.id.collection_tag_empty_tv)
    AppCompatTextView collectionTagEmptyTv;

    private CollectionVo curCollectionVo;
    private int position;
    private String userId;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection_tag_update;
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        initToolbar(view, R.string.tips_edit_tag, R.mipmap.ic_navigation_back, R.string.tips_finish);
        initBasicData();
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        curCollectionVo = bundle.getParcelable(AppConstant.OBJECT);
        position = bundle.getInt(AppConstant.POSITION);
        if (curCollectionVo == null) {
            showMessage("当前收藏信息为空");
            getSimpleActivity().onBackPressedSupport();
        }
        mPresenter.getUserTagList();

    }

    @Override
    protected void onNavigationClickListener(View view) {
        getSimpleActivity().onBackPressedSupport();
    }

    @Override
    protected void onToolbarRightClickListener(View view) {
        LogUtils.d("完成");
        StringBuilder sb = new StringBuilder();
        String createTag = collectionTagCreateEt.getText().toString();
        // TODO 标签过滤
        if (!TextUtils.isEmpty(createTag)) {
            sb.append(createTag);
            mPresenter.insertTagPojo(createTag);
        }
        int childCount = collectionTagOptionsContainerWl.getChildCount();
        for (int i = 0; i < childCount; i++) {
            CheckBox checkBox = (CheckBox) collectionTagOptionsContainerWl.getChildAt(i);
            if (checkBox.isChecked()) {
                if (sb.toString().length() == 0) {
                    sb.append(checkBox.getText().toString());
                } else {
                    sb.append("," + checkBox.getText().toString());
                }
            }
        }
        mPresenter.updateCollection(curCollectionVo.getNewsId(), sb.toString());
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build().inject(this);
    }

    public static CollectionUpdateFragment newInstance(CollectionVo collectionVo, int position) {
        CollectionUpdateFragment fragment = new CollectionUpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstant.OBJECT, collectionVo);
        bundle.putInt(AppConstant.POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showAllUserTagList(List<TagPojo> tagPojos) {
        if (tagPojos != null && tagPojos.size() > 0) {
            String tag = curCollectionVo.getTag();
            for (TagPojo tagPojo : tagPojos) {
                collectionTagOptionsContainerWl.addView(makeTagView(tag, tagPojo));
            }
        } else {
            collectionTagOptionsContainerWl.setVisibility(View.GONE);
            collectionTagEmptyTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onUpdateCollectionTagSuccess() {
        Observable.timer(300, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                pop();
            }
        });
    }


    private CheckBox makeTagView(String tag, TagPojo tagPojo) {
        final CheckBox checkBox = new CheckBox(getContext());
        if (TextUtils.isEmpty(userId)) userId = tagPojo.getUserId();
        checkBox.setButtonDrawable(null);
        checkBox.setChecked(!TextUtils.isEmpty(tag) && tag.contains(tagPojo.getContent()));
        checkBox.setTextColor(checkBox.isChecked() ? getResources().getColor(R.color.md_white) : getResources().getColor(R.color.md_grey_400));
        checkBox.setBackgroundResource(R.drawable.bg_tag_tv);
        checkBox.setText(tagPojo.getContent());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkBox.setTextColor(b ? getResources().getColor(R.color.md_white) : getResources().getColor(R.color.md_grey_400));
            }
        });
        return checkBox;
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTokenInvalid(TokenInvalid tokenInvalid) {
        LogUtils.d("token 验证失效，请重新登录");
    }

    @Override
    public void onStop() {
        Bundle bundle = new Bundle();
        setFragmentResult(0x10001, bundle);
        super.onStop();
    }

}
