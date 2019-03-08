package com.lning.melireader.ui.main.news.comment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.NewsCommentContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.ReplyVo;
import com.lning.melireader.core.ui.fragment.RootFragment;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.presenter.NewsCommentPresenter;
import com.lning.melireader.ui.adapter.CommentListItemAdapter;
import com.lning.melireader.widget.CommentDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Ning on 2019/2/19.
 */

public class NewsCommentFragment extends RootFragment<NewsCommentPresenter>
        implements NewsCommentContact.View, /*OnLoadMoreListener*/XRecyclerView.LoadingListener {

    @BindView(R.id.adapter_recommend_subtitle_tv)
    AppCompatTextView adapterRecommendSubTitleTv;

    @BindView(R.id.view_main)
    XRecyclerView mRecyclerView;

    private String newsId;
    private String ctype;

    private CommentListItemAdapter mAdapter;
    private LinkedList<CommentVo> list = new LinkedList<>();
    private CommentDialog commentDialog;
    private CommentVo replyComment = null;
    private int selectPosition = -3;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment_list;
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        super.init(savedInstanceState, view);
        initBasicData();
        initMultiTypeAdapter();
        mPresenter.initNewsCommentsByNewsId(newsId);
    }

    private void initBasicData() {
        Bundle bundle = getArguments();
        newsId = bundle.getString(AppConstant.NEWSLIST_ID, "");
        ctype = bundle.getString(AppConstant.TYPE, "all");
    }

    private void initMultiTypeAdapter() {
        adapterRecommendSubTitleTv.setText(getString(R.string.tips_news_detail_all_comment));
        mAdapter = new CommentListItemAdapter(list, simpleOnItemClickListener());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.LineScaleParty);
        mRecyclerView.setLoadingListener(this);
    }

    private OnItemClickListener simpleOnItemClickListener() {
        return new SimpleItemClickListenerAdapter() {
            @Override
            public void OnItemClick(View view, Object object, int position) {
                if (object instanceof String && view != null) {
                    // 点赞与否
//                    mAdapter.notifyItemChanged(position);
                    mPresenter.likeComment(object.toString());
                } else if (object instanceof CommentVo) {
                    // 回复评论 -1 回复根评论  其他 回复 下面回复
                    replyComment = (CommentVo) object;
                    selectPosition = position;
                    String hint = "";
                    if (position == -2) {
                        LogUtils.d(list.contains(replyComment) + "");
                        hint = getString(R.string.tips_news_comment_hint);
                    } else {
                        hint = getString(R.string.tips_comment_detail_tv_template, position >= 0 ?
                                replyComment.getReplyList().get(position).getUserNickname() : replyComment.getUserNickname());
                        ((SupportFragment) getParentFragment()).start(NewsCommentDetailFragment.newInstance(replyComment, ctype, replyComment.getId()));
                    }
                    showCommentDialog(hint);
                } else if (object instanceof String && position == -1) {
                    String replyId = replyComment.getId();
                    if (position >= 0) {
                        List<ReplyVo> replyVos = replyComment.getReplyList();
                        ReplyVo replyVo = replyVos.get(position);
                        replyId = replyVo.getReplyId();
                    }
                    mPresenter.addNewsComment(newsId, replyId, object.toString());
                }
            }

            @Override
            public void OnItemClick(View view, Object object) {
                if (object instanceof String) {
                    // 显示用户主页
                    LogUtils.d("显示用户主页");

                }
            }
        };
    }

    private void showCommentDialog(String hint) {
        if (commentDialog == null || !commentDialog.isAdded()) {
            commentDialog = new CommentDialog(hint, simpleOnItemClickListener());
            commentDialog.show(getFragmentManager(), "commentDialog");
        }
    }

    @Override
    public void onAddNewsCommentError() {
        resetCommentVo();
        if (commentDialog != null && commentDialog.isAdded()) {
            commentDialog.hideSoftkeyboard();
            commentDialog.hideProgressDialog();
            commentDialog.dismiss();
            getFragmentManager().beginTransaction().remove(commentDialog).commit();
            commentDialog = null;
        }
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAppFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    public static NewsCommentFragment newInstance(String newsId, String ctype) {
        NewsCommentFragment fragment = new NewsCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.NEWSLIST_ID, newsId);
        bundle.putString(AppConstant.TYPE, ctype);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onGetCommentsSuccess(List<CommentVo> commentVos, boolean isCurRefresh) {
        if (isCurRefresh) {
            mRecyclerView.notifyItemRemoved(list, 0);
            list.clear();
        }
        mRecyclerView.loadMoreComplete();
        int start = list.size();
        list.addAll(commentVos);
        mRecyclerView.notifyItemInserted(commentVos, start);
    }

    @Override
    public void onGetFullComments(String message) {
        mRecyclerView.loadMoreComplete();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.adapter_custom_footer_item, null);
        AppCompatTextView footerTv = view.findViewById(R.id.adapter_custom_footer_tv);
        footerTv.setText(message);
        mRecyclerView.setFootView(view, null);
        mRecyclerView.setLoadingMoreEnabled(false);
    }

    @Override
    public void onAddCommentSuccess(CommentVo commentVo) {
        if (selectPosition >= -1) {
            ReplyVo replyVo = this.replyComment.getReplyList().get(selectPosition);
            ReplyVo addReplyVo = CommonUtils.parseReplyVo(commentVo, replyVo);
            mAdapter.addReply(replyComment, addReplyVo);
            EventBus.getDefault().post(addReplyVo);
        } else if (selectPosition == -2) {
            ReplyVo addReplyVo = CommonUtils.parseReplyVo(commentVo, replyComment);
            mAdapter.addReply(replyComment, addReplyVo);
        }
        onAddNewsCommentError();
        resetCommentVo();
    }

    private void resetCommentVo() {
        selectPosition = -3;
        replyComment = null;
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onLoadMore() {
        mPresenter.setCurRefreshError(true);
        mPresenter.getNewsCommentsByNewsId(newsId, false);
    }

    @Override
    public void onDestroyView() {
        if (mRecyclerView != null) {
            mRecyclerView.destroy();
        }
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddComment(CommentVo commentVo) {
        mAdapter.addFirst(commentVo);
        showMain();
    }

    @Override
    public void showError(String msg) {
        if (mCurrentState == STATE_ERROR)
            return;
        if (!isErrorViewAdded) {
            isErrorViewAdded = true;
            mEmptyResource = R.layout.view_custom_error;
            View.inflate(mContext, mErrorResource, mParent);
            mViewError = mParent.findViewById(com.lning.melireader.core.R.id.view_error);
            if (!TextUtils.isEmpty(msg)) {
                ImageView imageView = mParent.findViewById(R.id.view_error_iv);
                imageView.setImageResource(R.mipmap.ic_empty_comment);
                TextView textView = mParent.findViewById(com.lning.melireader.core.R.id.view_error_msg);
                textView.setText(msg);
            }
            if (mViewError == null) {
                throw new IllegalStateException(
                        "A View should be named 'view_error' in ErrorLayoutResource.");
            }
            mViewError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRetryClick();
                }
            });
        }
        hideCurrentPage();
        mCurrentState = STATE_ERROR;
        mViewError.setVisibility(View.VISIBLE);
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }
}
