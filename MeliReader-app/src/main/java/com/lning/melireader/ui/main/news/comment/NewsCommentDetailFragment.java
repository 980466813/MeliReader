package com.lning.melireader.ui.main.news.comment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.NewsCommentDetailContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.ReplyVo;
import com.lning.melireader.core.repository.http.bean.SearchChannelItem;
import com.lning.melireader.core.ui.fragment.RootFragment;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.presenter.NewsCommentDetailPresenter;
import com.lning.melireader.presenter.ReplyListItemProvider;
import com.lning.melireader.ui.main.user.detail.UserFragment;
import com.lning.melireader.ui.provider.CommentDetailItemProvider;
import com.lning.melireader.ui.provider.RecommendListSubTitleProvider;
import com.lning.melireader.widget.CommentDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.SwipeBackLayout;

/**
 * Created by Ning on 2019/2/20.
 */

public class NewsCommentDetailFragment extends RootFragment<NewsCommentDetailPresenter>
        implements NewsCommentDetailContact.View {

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    @BindView(R.id.view_toolbar)
    FrameLayout mViewToolbarContainer;

    @BindView(R.id.view_bottom)
    FrameLayout mViewBottomContainer;

    @BindView(R.id.comment_detail_tv)
    AppCompatTextView commentDetailTv;

    private String ctype;
    private CommentVo commentVo;
    private String commentId;
    private CommentDialog commentDialog;
    private CommentVo replyComment;

    @Inject
    Items mItems;
    @Inject
    MultiTypeAdapter mMultiTypeAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment_detail;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);
        initBasicData();
        initMultiTypeAdapter();
        setSwipeBackEnable(true);
        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_LEFT);
        if (TextUtils.isEmpty(commentId)) throw new IllegalArgumentException("参数不能为空");
        if (commentVo == null) {
            mPresenter.getNewsCommentById(commentId);
        } else {
            onGetCommentVoSuccess(commentVo);
        }
    }

    private void initMultiTypeAdapter() {
        mMultiTypeAdapter.register(CommentVo.class, new CommentDetailItemProvider(simpleOnClickListenerAdapter()));
        mMultiTypeAdapter.register(String.class, new RecommendListSubTitleProvider());
        mMultiTypeAdapter.register(ReplyVo.class, new ReplyListItemProvider(simpleOnClickListenerAdapter(), commentId));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mMultiTypeAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMultiTypeAdapter.notifyDataSetChanged();
    }

    private OnItemClickListener simpleOnClickListenerAdapter() {
        return new SimpleItemClickListenerAdapter() {
            @Override
            public void OnItemClick(View view, Object object) {
                if (object instanceof SearchChannelItem) {
                    SearchChannelItem item = (SearchChannelItem) object;
                    ((SupportFragment) getParentFragment())
                            .start(UserFragment.newInstance(item.getChannelId(), item.getChannelName(), item.getChannelImage()));
                } else if (object instanceof CommentVo) {
                    // 分享
                    LogUtils.d("分享");
                }
            }

            @Override
            public void OnItemClick(View view, Object object, int position) {
                if (object instanceof CommentVo) {
                    getReplyInfo(commentVo);
                    showCommentDialog(getString(R.string.tips_comment_detail_tv_template, commentVo.getUserNickname()));
                } else if (object instanceof String) {
                    if (view != null) {
                        mPresenter.likeObject(object.toString());
                    } else {
                        if (replyComment != null)
                            mPresenter.addNewsComment(commentVo.getObjectId(), replyComment, object.toString());
                    }
                } else if (object instanceof ReplyVo) {
                    getReplyInfo((ReplyVo) object);
                    showCommentDialog(getString(R.string.tips_comment_detail_tv_template, ((ReplyVo) object).getUserNickname()));
                }
            }
        };
    }

    private void showCommentDialog(String hint) {
        if (commentDialog == null || !commentDialog.isAdded()) {
            commentDialog = new CommentDialog(hint, simpleOnClickListenerAdapter());
            commentDialog.show(getFragmentManager(), "commentDialog");
        }
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        ctype = bundle.getString(AppConstant.TYPE, "");
        commentVo = bundle.getParcelable(AppConstant.COMMENT);
        commentId = bundle.getString(AppConstant.ID, "");
        mViewToolbarContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (AppConstant.TYPE_VIDEO.equals(ctype)) {
            View view = inflater.inflate(R.layout.adapter_subtitle_toolbar_item, null);
            AppCompatImageButton button = view.findViewById(R.id.toolbar_right_btn);
            button.setImageResource(R.mipmap.ic_close);
            button.setScaleType(ImageView.ScaleType.FIT_CENTER);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNavigationClickListener(view);
                }
            });
            mViewToolbarContainer.addView(view);
            mViewBottomContainer.setVisibility(View.GONE);
        } else {
            View view = inflater.inflate(R.layout.view_toolbar_title_no_merge, null);
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            toolbar.setBackgroundColor(Color.WHITE);
            initToolbar(view, R.string.tips_news_detail_comment, R.mipmap.ic_navigation_back);
            mViewToolbarContainer.addView(view);
            commentDetailTv.setText(getString(R.string.tips_comment_detail_tv_template, commentVo.getUserNickname()));
        }
    }

    @Override
    protected void onNavigationClickListener(View view) {
        getSimpleActivity().onBackPressedSupport();
    }

    public static NewsCommentDetailFragment newInstance(CommentVo commentVo, String ctype, String commentId) {
        NewsCommentDetailFragment fragment = new NewsCommentDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.TYPE, ctype);
        bundle.putParcelable(AppConstant.COMMENT, commentVo);
        bundle.putString(AppConstant.ID, commentId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onRetryClick() {
        mPresenter.getNewsCommentById(commentId);
    }

    @Override
    public void onGetCommentVoSuccess(CommentVo commentVo) {
        List<ReplyVo> replyVos = commentVo.getReplyList();
        int size = replyVos == null ? 0 : replyVos.size();
        mItems.add(commentVo);
        mItems.add(getString(R.string.tips_reply_subtitle_template, size));
        mItems.addAll(replyVos);
        mMultiTypeAdapter.notifyDataSetChanged();
    }


    private void getReplyInfo(CommentVo commentVo) {
        replyComment = new CommentVo();
        replyComment.setId(commentVo.getId());
        replyComment.setUserId(commentVo.getUserId());
        replyComment.setUserNickname(commentVo.getUserNickname());
        replyComment.setUserProfile(commentVo.getUserProfile());
    }

    private void getReplyInfo(ReplyVo replyVo) {
        replyComment = new CommentVo();
        replyComment.setId(replyVo.getId());
        replyComment.setUserId(replyVo.getUserId());
        replyComment.setUserNickname(replyVo.getUserNickname());
        replyComment.setUserProfile(replyVo.getUserProfile());
    }

    private void clearReplyInfo() {
        replyComment = null;
    }

    @OnClick(R.id.comment_detail_tv)
    public void onCommentDetailTvClick() {
        if (commentVo == null) {
            showMessage(getString(R.string.tips_please_wait));
            return;
        }
        getReplyInfo(commentVo);
        showCommentDialog(getString(R.string.tips_comment_detail_tv_template, commentVo.getUserNickname()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void addCommentSuccess(ReplyVo replyVo) {
        clearReplyInfo();
        if (commentVo.getObjectId().equals(replyVo.getObjectId())
                && commentVo.getId().equals(replyVo.getReplyId())) {
            mItems.add(replyVo);
            mMultiTypeAdapter.notifyItemInserted(mItems.size() - 1);
        }
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }
}
