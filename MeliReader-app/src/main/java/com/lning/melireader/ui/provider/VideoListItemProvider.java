package com.lning.melireader.ui.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.repository.http.bean.SearchChannelItem;
import com.lning.melireader.core.repository.http.bean.VideoNewsListVo;
import com.lning.melireader.core.utils.CommonUtils;
import com.lning.melireader.core.utils.GlideUtils;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.http.bean.VideoInfo;
import com.lning.melireader.widget.SampleCoverVideo;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import javax.microedition.khronos.opengles.GL;

import io.reactivex.internal.fuseable.HasUpstreamObservableSource;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Ning on 2019/2/22.
 */

public class VideoListItemProvider extends ItemViewBinder<VideoNewsListVo, VideoListItemProvider.ViewHolder> {

    private final OnItemClickListener mListener;

    public VideoListItemProvider(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_video_list_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final VideoNewsListVo item) {


        String videoUrls = item.getVideoUrls();
        if (TextUtils.isEmpty(videoUrls)) return;
        String videoUrl = "";
        if (videoUrls.contains("{")) {
            VideoInfo videoInfo = JsonUtils.jsonToPojo(videoUrls, VideoInfo.class);
            if (videoInfo != null) {
                videoUrl = videoInfo.getMain_url();
            }
        } else if (videoUrls.contains(",")) {
            videoUrl = videoUrls.split(",")[0];
        } else {
            videoUrl = videoUrls;
        }
        if (holder.adapterVideoThumbnailIv.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) holder.adapterVideoThumbnailIv.getParent();
            viewGroup.removeView(holder.adapterVideoThumbnailIv);
        }
        if (!TextUtils.isEmpty(item.getImage())) {
            String[] images = item.getImage().split(",");
            GlideUtils.loadImage(holder.adapterVideoThumbnailIv, String.format(AppConstant.IMAGE_URL, images[0]));
        } else {
            holder.adapterVideoThumbnailIv.setImageResource(R.mipmap.ic_empty_data);
        }
        GlideUtils.loadUserHead(holder.adapterVideoListIconIv, String.format(AppConstant.IMAGE_URL, item.getPublisherProfile()));
        holder.adapterVideoListNameTv.setText(item.getPublisherName());
        holder.adapterVideoListSubCb.setChecked(item.isSubscripted());
        holder.adapterVideoListCommentTv.setText(item.getCommentCount() + "");
        holder.adapterVideoListShareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.adapterVideoListNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnItemClick(view, new SearchChannelItem(item.getChannelId(), item.getPublisherName(), item.getImage()));
            }
        });
        holder.adapterVideoListIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnItemClick(view, new SearchChannelItem(item.getChannelId(), item.getPublisherName(), item.getImage()));
            }
        });
        holder.adapterVideoListCommentIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnItemClick(null, CommonUtils.parseNewsListVo(item));
            }
        });
        holder.adapterVideoListSubCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mListener.OnItemClick(null, item.getChannelId());
            }
        });
        if (holder.getAdapterPosition() % 2 == 0) {
            videoUrl = "https://res.exexm.com/cw_145225549855002";
        } else {
            videoUrl = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        }
        holder.gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setUrl(videoUrl)
                .setThumbImageView(holder.adapterVideoThumbnailIv)
                .setVideoTitle(item.getTitle())
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(AppConstant.VIDEO_LIST_TAG)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(0)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!holder.adapterVideoListPlayerGsy.isIfCurrentIsFullscreen()) {
                            GSYVideoManager.instance().setNeedMute(true);// 静音
                        }
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(true);// 全屏不静音
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                        holder.adapterVideoListPlayerGsy.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
                    }
                }).build(holder.adapterVideoListPlayerGsy);
        holder.adapterVideoListPlayerGsy.getTitleTextView().setVisibility(View.VISIBLE);
        holder.adapterVideoListPlayerGsy.getBackButton().setVisibility(View.GONE);
        holder.adapterVideoListPlayerGsy.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(holder.itemView.getContext(), holder.adapterVideoListPlayerGsy);
            }
        });
    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(Context context, final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, true, true);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ListGSYVideoPlayer adapterVideoListPlayerGsy;
        AppCompatImageView adapterVideoListIconIv;
        AppCompatTextView adapterVideoListNameTv;
        AppCompatCheckBox adapterVideoListSubCb;
        AppCompatImageView adapterVideoListCommentIv;
        AppCompatTextView adapterVideoListCommentTv;
        AppCompatImageView adapterVideoListShareIv;
        GSYVideoOptionBuilder gsyVideoOptionBuilder;
        AppCompatImageView adapterVideoThumbnailIv;

        public ViewHolder(View itemView) {
            super(itemView);
            adapterVideoThumbnailIv = new AppCompatImageView(itemView.getContext());
            gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
            adapterVideoListPlayerGsy = itemView.findViewById(R.id.adapter_video_list_player_gsy);
            adapterVideoListIconIv = itemView.findViewById(R.id.adapter_video_list_icon_iv);
            adapterVideoListNameTv = itemView.findViewById(R.id.adapter_video_list_name_tv);
            adapterVideoListSubCb = itemView.findViewById(R.id.adapter_video_list_sub_cb);
            adapterVideoListCommentIv = itemView.findViewById(R.id.adapter_video_list_comment_iv);
            adapterVideoListCommentTv = itemView.findViewById(R.id.adapter_video_list_comment_tv);
            adapterVideoListShareIv = itemView.findViewById(R.id.adapter_video_list_share_iv);
        }
    }
}
