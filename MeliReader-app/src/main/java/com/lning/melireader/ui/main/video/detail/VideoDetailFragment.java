package com.lning.melireader.ui.main.video.detail;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.lning.melireader.R;
import com.lning.melireader.app.component.DaggerAppFragmentComponent;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.app.module.FragmentModule;
import com.lning.melireader.contact.VideoDetailContact;
import com.lning.melireader.core.app.di.component.AppComponent;
import com.lning.melireader.core.app.listener.SimpleItemClickListenerAdapter;
import com.lning.melireader.core.repository.db.pojo.TagPojo;
import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.repository.http.bean.NewsVo;
import com.lning.melireader.core.repository.http.bean.SearchChannelItem;
import com.lning.melireader.core.ui.fragment.BaseFragment;
import com.lning.melireader.core.ui.fragment.RootFragment;
import com.lning.melireader.core.utils.AnimUtils;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.GlideUtils;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.core.utils.ScreenUtils;
import com.lning.melireader.core.utils.ToastUtils;
import com.lning.melireader.http.bean.VideoInfo;
import com.lning.melireader.presenter.VideoDetailPresenter;
import com.lning.melireader.ui.main.news.comment.NewsCommentFragment;
import com.lning.melireader.ui.main.news.detail.NewsDetailFragment;
import com.lning.melireader.ui.main.news.recommend.NewsRecommendFragment;
import com.lning.melireader.ui.main.user.detail.UserFragment;
import com.lning.melireader.ui.search.SearchResultFragment;
import com.lning.melireader.widget.CommentDialog;
import com.lning.melireader.widget.SampleCoverVideo;
import com.lning.melireader.widget.TagDialog;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import am.widget.wraplayout.WrapLayout;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import freemarker.template.utility.StringUtil;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import retrofit2.http.POST;

/**
 * Created by Ning on 2019/2/22.
 */

public class VideoDetailFragment extends BaseFragment<VideoDetailPresenter>
        implements VideoDetailContact.View {

    @BindView(R.id.video_detail_title_tv)
    AppCompatTextView videoDetailTitleTv;

    @BindView(R.id.video_detail_icon_iv)
    AppCompatImageView videoDetailIconIv;

    @BindView(R.id.video_detail_name_tv)
    AppCompatTextView videoDetailNameTv;

    @BindView(R.id.video_detail_sub_cb)
    AppCompatCheckBox videoDetailSubCb;

    @BindView(R.id.video_detail_arrow_iv)
    AppCompatImageView videoDetailArrowIv;

    @BindView(R.id.video_detail_tag_wl)
    WrapLayout videoDetailTagWl;

    @BindView(R.id.video_detail_nested_scroll)
    NestedScrollView videoDetailNestedScroll;

    @BindView(R.id.video_detail_date_tv)
    AppCompatTextView videoDetailDateTv;

    @BindView(R.id.video_detail_read_count_tv)
    AppCompatTextView videoDetailReadCountTv;

    @BindView(R.id.video_detail_info_ll)
    ConstraintLayout videoDetailInfoLl;

    @BindView(R.id.video_detail_player_gsy)
    SampleCoverVideo videoDetailPlayerGsy;

    @BindView(R.id.video_detail_comment_num_tv)
    AppCompatTextView videoDetailCommentNumTv;

    @BindView(R.id.video_detail_collection_iv)
    AppCompatImageView videoDetailCollectionIv;

    GSYVideoOptionBuilder gsyVideoOptionBuilder;
    private CommentDialog inputDialog;
    private TagDialog tagDialog;
    private List<TagPojo> tagPojos = new ArrayList<>();

    private NewsListVo newsListVo;
    private NewsVo newsVo;
    private int position;
    private NewsCommentFragment commentFragment;
    private NewsRecommendFragment recommendFragment;

    @Override
    protected void init(@Nullable Bundle savedInstanceState, View rootView) {
        initBasicData();
    }

    private void initBasicData() {
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
        Bundle bundle = getArguments();
        newsListVo = bundle.getParcelable(AppConstant.NEWSLIST_VO);
        position = bundle.getInt(AppConstant.POSITION, 0);
        if (newsListVo == null) {
            throw new IllegalArgumentException("新闻不能为空");
        }
        Observable.timer(200, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                mPresenter.checkNewsListCollected(newsListVo.getId());
            }
        });
        initBasicView(newsListVo);
        initListener();
        mPresenter.getNewsDetailByNewsId(newsListVo.getId());
    }

    private void initBasicView(NewsListVo newsListVo) {
        videoDetailTitleTv.setText(newsListVo.getTitle());
        videoDetailTitleTv.setMaxLines(1);
        GlideUtils.loadUserHead(videoDetailIconIv,
                String.format(AppConstant.IMAGE_URL, newsListVo.getPublisherProfile()));
        videoDetailInfoLl.setVisibility(View.GONE);
        videoDetailNameTv.setText(newsListVo.getPublisherName());
        videoDetailCommentNumTv.setText(newsListVo.getCommentCount() + "");
        videoDetailDateTv.setText(CommonUtils.formatPublishDate(newsListVo.getCreated()));
        boolean isTagEmpty = TextUtils.isEmpty(newsListVo.getDislikeNames());
        videoDetailTagWl.setVisibility(isTagEmpty ? View.GONE : View.VISIBLE);
        if (!isTagEmpty) {
            String[] ids = newsListVo.getDislikeIds().split(",");
            String[] tags = newsListVo.getDislikeNames().split(",");
            for (int i = 0; i < ids.length; i++) {
                videoDetailTagWl.addView(makeTagView(getContext(), ids[i], tags[i]));
            }
        }
    }

    private void initListener() {
        videoDetailNestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    LogUtils.d("BOTTOM SCROLL");
                    if (commentFragment == null || !commentFragment.isAdded()) {
                        commentFragment = NewsCommentFragment.newInstance(newsListVo.getId(), newsListVo.getCtype());
                        loadRootFragment(R.id.video_detail_comment_container, commentFragment);
                    }
                    if (recommendFragment == null || !recommendFragment.isAdded()) {
                        recommendFragment = NewsRecommendFragment.newInstance(newsListVo.getDislikeIds(), newsListVo.getCtype());
                    }
                }
            }
        });
    }

    @OnClick(R.id.video_detail_arrow_iv)
    public void onArrowClick() {
        if (newsVo == null) {
            return;
        }
        AnimUtils.rotate(videoDetailArrowIv, 0, 180, 500);
        boolean isVisible = videoDetailInfoLl.getVisibility() == View.VISIBLE;
        videoDetailInfoLl.setVisibility(isVisible ? View.GONE : View.VISIBLE);
        videoDetailTitleTv.setMaxLines(isVisible ? 1 : 4);
    }

    @OnClick({R.id.video_detail_comment_tv, R.id.video_detail_comment_iv})
    public void onCommentClick() {
        if (inputDialog == null || !inputDialog.isAdded()) {
            inputDialog = new CommentDialog(getString(R.string.tips_news_comment_hint), simpleItemClickListenerAdapter);
            inputDialog.show(getFragmentManager(), "commentDialog");
        }
    }

    @OnClick(R.id.video_detail_collection_iv)
    public void onCollectionClick() {
        LogUtils.d("onCollectionClick:newsVo:" + (newsVo == null));
        if (newsVo == null) {
            showMessage(getString(R.string.tips_please_wait));
            return;
        }
        if (newsVo.isCollected()) {
            mPresenter.cancelCollectNews(newsListVo.getId());
        } else {
            mPresenter.collectNews(newsVo);
        }
    }

    @OnClick({R.id.video_detail_icon_iv, R.id.video_detail_name_tv})
    public void onUserClick() {
        simpleItemClickListenerAdapter.OnItemClick(null, new SearchChannelItem(newsListVo.getChannelId(),
                newsListVo.getPublisherName(), newsListVo.getPublisherProfile()));
    }


    @OnClick(R.id.video_detail_sub_cb)
    public void onSubChanged() {
        mPresenter.operateAttention(videoDetailSubCb.isChecked(), newsListVo.getChannelId());
    }

    @Override
    public void onOperateAttentionSuccess(boolean attention) {
        setSubscriptCbStatus(attention);
        if (attention) {
            showMessage(getString(R.string.tips_attention_success));
        } else {
            showMessage(getString(R.string.tips_cancel_attention_success));
        }
    }

    public void setSubscriptCbStatus(boolean isSubscripted) {
        videoDetailSubCb.setChecked(isSubscripted);
        videoDetailSubCb.setText(getString(isSubscripted ? R.string.tips_already_subscripted : R.string.tips_not_subscript));
    }


    private AppCompatTextView makeTagView(Context context, final String id, final String tag) {
        AppCompatTextView tagView = new AppCompatTextView(context);
        tagView.setBackgroundResource(R.drawable.bg_common_tag_tv);
        int paSE = ScreenUtils.dip2px(context, 10);
        int paTB = ScreenUtils.dip2px(context, 3);
        tagView.setPadding(paSE, paTB, paSE, paTB);
        tagView.setText(tag);
        tagView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleItemClickListenerAdapter.OnItemClick(null, new Wrapper(id, tag));
            }
        });
        return tagView;
    }

    private void configVideo(String title, String url, int position) {
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setUrl(url)
                .setVideoTitle(title)
                .setCacheWithPlay(true)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(AppConstant.VIDEO_LIST_TAG)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!videoDetailPlayerGsy.isIfCurrentIsFullscreen()) {
                            GSYVideoManager.instance().setNeedMute(false);// 静音
                        }
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);// 全屏不静音
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                        videoDetailPlayerGsy.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
                    }
                }).build(videoDetailPlayerGsy);
        videoDetailPlayerGsy.getTitleTextView().setVisibility(View.VISIBLE);
        videoDetailPlayerGsy.getBackButton().setVisibility(View.VISIBLE);
        videoDetailPlayerGsy.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(getContext(), videoDetailPlayerGsy);
            }
        });
    }

    private void resolveFullBtn(Context context, StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, true, true);
    }

    SimpleItemClickListenerAdapter simpleItemClickListenerAdapter = new SimpleItemClickListenerAdapter() {
        @Override
        public void OnItemClick(View view, int position) {
            if (position == -1) {
                mPresenter.cancelAddNewsComment();
            } else if (position == -2) {
                // Toast点击事件
                if (tagPojos.size() == 0) {
                    mPresenter.getAllUserTags();
                } else {
                    showTagDialog();
                }
            } else {
                // 取消更新收藏
                onUpdateCollectionError();
            }
        }

        @Override
        public void OnItemClick(View view, Object object) {
            if (object instanceof VideoDetailFragment.Wrapper) {
                VideoDetailFragment.Wrapper wrapper = (VideoDetailFragment.Wrapper) object;
                start(SearchResultFragment.newInstance(wrapper.tag));
            } else if (object instanceof SearchChannelItem) {
                SearchChannelItem item = (SearchChannelItem) object;
                start(UserFragment.newInstance(item.getChannelId(), item.getChannelName(), item.getChannelImage()));
            }
        }

        @Override
        public void OnItemClick(View view, Object object, int position) {
            if (object instanceof String) {
                // 评论操作
                if (position == -1)
                    mPresenter.addNewsComment(newsListVo.getId(), object.toString());
                else if (position == -2)
                    mPresenter.updateCollection(newsListVo.getId(), object.toString());
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_detail;
    }

    public static VideoDetailFragment newInstance(NewsListVo newsListVo, int position) {
        VideoDetailFragment fragment = new VideoDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstant.NEWSLIST_VO, newsListVo);
        bundle.putInt(AppConstant.POSITION, position);
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
    public boolean onBackPressedSupport() {
        if (GSYVideoManager.backFromWindowFull(getActivity())) {
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onGetNewsVoSuccess(NewsVo newsVo) {
        this.newsVo = newsVo;
        String videoInfos = newsVo.getVideoUrls();
        String videoUrl = "";
        if (videoInfos.contains("{")) {
            VideoInfo videoInfo = JsonUtils.jsonToPojo(videoInfos, VideoInfo.class);
            videoUrl = videoInfo.getMain_url();
        } else if (videoUrl.contains(",")) {
            videoUrl = videoInfos.split(",")[0];
        } else {
            videoUrl = videoInfos;
        }
        videoDetailReadCountTv.setText(getString(R.string.tips_read_count_template, newsVo.getReadCount()));
        videoDetailSubCb.setChecked(newsVo.isSubscripted());
        videoDetailSubCb.setText(getString(newsVo.isSubscripted() ? R.string.tips_already_subscripted : R.string.tips_not_subscript));
        configVideo(newsVo.getTitle(), videoUrl, position);
        if (commentFragment == null || !commentFragment.isAdded()) {
            commentFragment = NewsCommentFragment.newInstance(newsListVo.getId(), newsListVo.getCtype());
            loadRootFragment(R.id.video_detail_comment_container, commentFragment);
        }
        if (recommendFragment == null || !recommendFragment.isAdded()) {
            recommendFragment = NewsRecommendFragment.newInstance(newsListVo.getDislikeIds(), newsListVo.getCtype());
            loadRootFragment(R.id.video_detail_recommend_container, recommendFragment);
        }
    }

    @Override
    public void onAddNewsCommentSuccess(CommentVo commentVo) {
        onAddNewsCommentError();
        EventBus.getDefault().post(commentVo);
    }

    @Override
    public void onAddNewsCommentError() {
        if (inputDialog != null && inputDialog.isAdded()) {
            inputDialog.hideSoftkeyboard();
            inputDialog.hideProgressDialog();
            inputDialog.dismiss();
            getFragmentManager().beginTransaction().remove(inputDialog).commit();
            inputDialog = null;
        }
    }

    @Override
    public void onOperateCollectNewsSuccess(boolean collected) {
        onGetCollectedSuccess(collected);
        if (collected) {
            ToastUtils.showCollectedSuccessToast(getContext(), simpleItemClickListenerAdapter);
        } else {
            onUpdateCollectionError();
        }
    }

    @Override
    public void onUpdateCollectionError() {
        if (tagDialog != null && tagDialog.isAdded()) {
            tagDialog.hideSoftkeyboard();
            tagDialog.hideProgressDialog();
            tagDialog.dismiss();
            getFragmentManager().beginTransaction().remove(tagDialog).commit();
            tagDialog = null;
        }
    }

    @Override
    public void onGetUserTagsSuccess(List<TagPojo> tagPojoList) {
        this.tagPojos = tagPojoList;
        showTagDialog();
    }

    @Override
    public void onDislikeObjectSuccess(boolean isDisliked) {
        newsVo.setDisliked(isDisliked);
        int dislikeCount = newsVo.getDislikeCount();
        newsVo.setDislikeCount(isDisliked ? dislikeCount + 1 : dislikeCount - 1 > 0 ? dislikeCount - 1 : 0);
    }

    @Override
    public void onGetCollectedSuccess(boolean collected) {
        videoDetailCollectionIv.setImageResource(collected ? R.mipmap.ic_center_collection_selected : R.mipmap.ic_center_collection);
    }


    class Wrapper {
        String tagId;
        String tag;

        public Wrapper(String tagId, String tag) {
            this.tagId = tagId;
            this.tag = tag;
        }
    }

    private void showTagDialog() {
        if (tagDialog == null || tagDialog.isCancelable()) {
            tagDialog = new TagDialog(tagPojos, getString(R.string.tips_create_tag), simpleItemClickListenerAdapter);
            tagDialog.show(getFragmentManager(), "tagDialog");
        }
    }
}
