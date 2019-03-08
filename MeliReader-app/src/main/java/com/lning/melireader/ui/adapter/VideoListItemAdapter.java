package com.lning.melireader.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.lning.melireader.R;
import com.lning.melireader.app.constant.AppConstant;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.repository.http.bean.VideoNewsListVo;
import com.lning.melireader.core.utils.GlideUtils;
import com.lning.melireader.core.utils.JsonUtils;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.http.bean.VideoInfo;
import com.lning.melireader.widget.SampleCoverVideo;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

/**
 * Created by Ning on 2019/2/22.
 */

public class VideoListItemAdapter extends RecyclerView.Adapter<VideoListItemAdapter.ViewHolder> {

    private final OnItemClickListener mListener;
    private final List<VideoNewsListVo> mList;
    private Context context;

    public VideoListItemAdapter(Context context, List<VideoNewsListVo> videos, OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
        this.context = context;
        this.mList = videos;
    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(Context context, final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, true, true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_video_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final VideoNewsListVo item = mList.get(position);
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
        holder.adapterVideoListSubCb.setText(holder.itemView.getResources().getString(item.isSubscripted() ?
                R.string.tips_already_subscripted : R.string.tips_not_subscript));
        holder.adapterVideoListCommentTv.setText(item.getCommentCount() + "");
        holder.adapterVideoListIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnItemClick(null, item.getChannelId());
            }
        });
        holder.adapterVideoListNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnItemClick(null, item.getChannelId());
            }
        });
        holder.adapterVideoListShareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.adapterVideoListCommentIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.d(holder.getAdapterPosition() + "");
                mListener.OnItemClick(null, item, holder.adapterVideoListPlayerGsy.getPlayPosition());
            }
        });
        holder.adapterVideoListToolbarCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnItemClick(null, item, holder.adapterVideoListPlayerGsy.getPlayPosition());
            }
        });
        holder.adapterVideoListSubCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setSubscripted(holder.adapterVideoListSubCb.isChecked());
                mListener.OnItemCheckChange(null, item.getChannelId(), holder.adapterVideoListSubCb.isChecked());
            }
        });
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
                        holder.adapterVideoListPlayerGsy.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
                    }
                }).build(holder.adapterVideoListPlayerGsy);
        holder.adapterVideoListPlayerGsy.getTitleTextView().setVisibility(View.VISIBLE);
        holder.adapterVideoListPlayerGsy.getBackButton().setVisibility(View.GONE);
        holder.adapterVideoListPlayerGsy.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(context, holder.adapterVideoListPlayerGsy);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SampleCoverVideo adapterVideoListPlayerGsy;
        AppCompatImageView adapterVideoListIconIv;
        AppCompatTextView adapterVideoListNameTv;
        AppCompatCheckBox adapterVideoListSubCb;
        ConstraintLayout adapterVideoListToolbarCl;
        AppCompatImageView adapterVideoListCommentIv;
        AppCompatTextView adapterVideoListCommentTv;
        AppCompatImageView adapterVideoListShareIv;
        GSYVideoOptionBuilder gsyVideoOptionBuilder;
        AppCompatImageView adapterVideoThumbnailIv;

        public ViewHolder(View itemView) {
            super(itemView);
            adapterVideoListToolbarCl = itemView.findViewById(R.id.adapter_video_list_toolbar_cl);
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
