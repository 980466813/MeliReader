package com.lning.melireader.core.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lning.melireader.core.R;
import com.lning.melireader.core.repository.db.pojo.CollectionPojo;
import com.lning.melireader.core.repository.db.pojo.HistoryPojo;
import com.lning.melireader.core.repository.http.bean.CollectionVo;
import com.lning.melireader.core.repository.http.bean.CommentNewsListVo;
import com.lning.melireader.core.repository.http.bean.CommentVo;
import com.lning.melireader.core.repository.http.bean.HistoryVo;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import com.lning.melireader.core.repository.http.bean.NewsVo;
import com.lning.melireader.core.repository.http.bean.ReplyVo;
import com.lning.melireader.core.repository.http.bean.SearchItem;
import com.lning.melireader.core.repository.http.bean.VideoNewsListVo;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Ning on 2019/2/11.
 */

public class CommonUtils {

    private CommonUtils() {
    }

    public static boolean verifyPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|147)\\d{8}$");
        return pattern.matcher(phoneNumber).matches();
    }

    public static Boolean verifyPassword(String password) {
        return verifySpecialSign(password) && verifyLength(password, 6, 16);
    }

    public static Boolean verifyLength(String str, int minLen, int maxLen) {
        return !TextUtils.isEmpty(str) && str.length() >= minLen && str.length() <= maxLen;
    }

    public static Boolean verifySpecialSign(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern pattern = Pattern.compile(regEx);
        return !TextUtils.isEmpty(str) && pattern.matcher(str).matches();
    }

    public static boolean isBaseType(Object object) {
        Class className = object.getClass();
        if (className.equals(java.lang.Integer.class) ||
                className.equals(java.lang.Byte.class) ||
                className.equals(java.lang.Long.class) ||
                className.equals(java.lang.Double.class) ||
                className.equals(java.lang.Float.class) ||
                className.equals(java.lang.Character.class) ||
                className.equals(java.lang.Short.class) ||
                className.equals(java.lang.Boolean.class)) {
            return true;
        }
        return false;
    }

    public static boolean isBaseType(Class<?> clazz) {
        return clazz == String.class || clazz == Byte.TYPE
                || clazz == Long.TYPE || clazz == Double.TYPE
                || clazz == Float.TYPE || clazz == Character.TYPE
                || clazz == Short.TYPE || clazz == Boolean.TYPE || clazz == Integer.TYPE;
    }


    public static HistoryVo parseHistory(HistoryPojo historyPojo) {
        HistoryVo historyVo = new HistoryVo();
        NewsListVo newsListVo = historyPojo.getNewsListVo();
        historyVo.setPublisherName(newsListVo.getPublisherName());
        historyVo.setPublisherProfile(newsListVo.getPublisherProfile());
        historyVo.setNewsId(newsListVo.getId());
        historyVo.setUserId(historyPojo.getUserId());
        historyVo.setDislikeIds(newsListVo.getDislikeIds());
        historyVo.setDislikeNames(newsListVo.getDislikeNames());
        historyVo.setSource(newsListVo.getSource());
        historyVo.setDsource(newsListVo.getDsource());
        historyVo.setChannelId(newsListVo.getChannelId());
        historyVo.setCreated(historyPojo.getCreated());
        historyVo.setPublishDate(newsListVo.getCreated());
        historyVo.setCtype(newsListVo.getCtype());
        historyVo.setImage(newsListVo.getImage());
        historyVo.setTitle(newsListVo.getTitle());
        historyVo.setSummary(newsListVo.getSummary());
        historyVo.setCommentCount(newsListVo.getCommentCount());
        return historyVo;
    }

    public static CollectionVo parseCollection(CollectionPojo collectionPojo) {
        CollectionVo collectionVo = new CollectionVo();
        NewsListVo newsListVo = collectionPojo.getNewsListVo();
        collectionVo.setId(collectionPojo.getId());
        collectionVo.setUserId(collectionPojo.getUserId());
        collectionVo.setCreated(collectionPojo.getCreated());
        collectionVo.setUpdated(collectionPojo.getUpdated());
        collectionVo.setTag(collectionPojo.getTag());

        collectionVo.setNewsId(newsListVo.getId());
        collectionVo.setTitle(newsListVo.getTitle());
        collectionVo.setSummary(newsListVo.getSummary());
        collectionVo.setPublisherName(newsListVo.getPublisherName());
        collectionVo.setPublisherProfile(newsListVo.getPublisherProfile());
        collectionVo.setDislikeIds(newsListVo.getDislikeIds());
        collectionVo.setDislikeNames(newsListVo.getDislikeNames());
        collectionVo.setSource(newsListVo.getSource());
        collectionVo.setDsource(newsListVo.getDsource());
        collectionVo.setChannelId(newsListVo.getChannelId());
        collectionVo.setPublishDate(newsListVo.getCreated());
        collectionVo.setCtype(newsListVo.getCtype());
        collectionVo.setImage(newsListVo.getImage());
        collectionVo.setCommentCount(newsListVo.getCommentCount());
        return collectionVo;
    }

    public static NewsListVo parseNewsListVo(CommentNewsListVo newsVo) {
        NewsListVo newsListVo = new NewsListVo();
        newsListVo.setId(newsVo.getId());
        newsListVo.setTitle(newsVo.getTitle());
        newsListVo.setSummary(newsVo.getSummary());
        newsListVo.setImage(newsVo.getImage());
        newsListVo.setChannelId(newsVo.getChannelId());
        newsListVo.setDislikeIds(newsVo.getDislikeIds());
        newsListVo.setDislikeNames(newsVo.getDislikeNames());
        newsListVo.setSource(newsVo.getSource());
        newsListVo.setDsource(newsVo.getDsource());
        newsListVo.setPublisherName(newsVo.getPublisherName());
        newsListVo.setPublisherProfile(newsVo.getPublisherProfile());
        newsListVo.setCreated(newsVo.getCreated());
        newsListVo.setCtype(newsVo.getCtype());
        newsListVo.setCommentCount(newsVo.getCommentCount());
        return newsListVo;
    }

    public static NewsListVo parseNewsListVo(VideoNewsListVo newsVo) {
        NewsListVo newsListVo = new NewsListVo();
        newsListVo.setId(newsVo.getId());
        newsListVo.setTitle(newsVo.getTitle());
        newsListVo.setSummary(newsVo.getSummary());
        newsListVo.setImage(newsVo.getImage());
        newsListVo.setChannelId(newsVo.getChannelId());
        newsListVo.setDislikeIds(newsVo.getDislikeIds());
        newsListVo.setDislikeNames(newsVo.getDislikeNames());
        newsListVo.setSource(newsVo.getSource());
        newsListVo.setDsource(newsVo.getDsource());
        newsListVo.setPublisherName(newsVo.getPublisherName());
        newsListVo.setPublisherProfile(newsVo.getPublisherProfile());
        newsListVo.setCreated(newsVo.getCreated());
        newsListVo.setCtype(newsVo.getCtype());
        newsListVo.setCommentCount(newsVo.getCommentCount());
        return newsListVo;
    }

    public static NewsListVo parseNewsListVo(NewsVo newsVo) {
        NewsListVo newsListVo = new NewsListVo();
        newsListVo.setId(newsVo.getId());
        newsListVo.setTitle(newsVo.getTitle());
        newsListVo.setSummary(newsVo.getSummary());
        newsListVo.setImage(newsVo.getImageUrls());

        newsListVo.setChannelId(newsVo.getChannelId());
        newsListVo.setDislikeIds(newsVo.getDislikeIds());
        newsListVo.setDislikeNames(newsVo.getDislikeNames());
        newsListVo.setSource(newsVo.getSource());
        newsListVo.setDsource(newsVo.getDsource());

        newsListVo.setPublisherName(newsVo.getPublisherName());
        newsListVo.setPublisherProfile(newsVo.getPublisherProfile());
        newsListVo.setCreated(newsVo.getCreated());
        newsListVo.setCtype(newsVo.getCtype());
        newsListVo.setCommentCount(newsVo.getCommentCount());
        return newsListVo;
    }

    public static NewsListVo parseNewsListVo(CollectionVo newsVo) {
        NewsListVo newsListVo = new NewsListVo();
        newsListVo.setId(newsVo.getNewsId());
        newsListVo.setTitle(newsVo.getTitle());
        newsListVo.setSummary(newsVo.getSummary());
        newsListVo.setImage(newsVo.getImage());

        newsListVo.setChannelId(newsVo.getChannelId());
        newsListVo.setDislikeIds(newsVo.getDislikeIds());
        newsListVo.setDislikeNames(newsVo.getDislikeNames());
        newsListVo.setSource(newsVo.getSource());
        newsListVo.setDsource(newsVo.getDsource());

        newsListVo.setPublisherName(newsVo.getPublisherName());
        newsListVo.setPublisherProfile(newsVo.getPublisherProfile());
        newsListVo.setCreated(newsVo.getCreated());
        newsListVo.setCtype(newsVo.getCtype());
        newsListVo.setCommentCount(newsVo.getCommentCount());
        return newsListVo;
    }

    public static NewsListVo parseNewsListVo(SearchItem searchItem) {
        NewsListVo newsListVo = new NewsListVo();
        newsListVo.setId(searchItem.getId());
        newsListVo.setTitle(searchItem.getTitle());
        newsListVo.setSummary(searchItem.getSummary());
        newsListVo.setImage(searchItem.getImage());

        newsListVo.setChannelId(searchItem.getChannelId());
        newsListVo.setDislikeIds(searchItem.getDislikeIds());
        newsListVo.setDislikeNames(searchItem.getDislikeNames());
        newsListVo.setPublisherName(searchItem.getPublisherName());
        newsListVo.setPublisherProfile(searchItem.getPublisherProfile());
        newsListVo.setCreated(searchItem.getCreated());
        newsListVo.setCtype(searchItem.getCtype());
        newsListVo.setCommentCount(searchItem.getCommentCount());
        return newsListVo;
    }

    public static ReplyVo parseReplyVo(CommentVo commentVo, CommentVo replyVo) {
        ReplyVo addReply = new ReplyVo();
        addReply.setId(commentVo.getId());
        addReply.setUserId(commentVo.getUserId());
        addReply.setUserNickname(commentVo.getUserNickname());
        addReply.setUserProfile(commentVo.getUserProfile());
        addReply.setObjectId(commentVo.getObjectId());
        addReply.setCreated(commentVo.getCreated());
        addReply.setContent(commentVo.getContent());
        addReply.setLiked(false);
        addReply.setLikeCount(0);
        addReply.setReplyId(replyVo.getId());
        addReply.setReplyUserId(replyVo.getUserId());
        addReply.setReplyNickname(replyVo.getUserNickname());
        addReply.setReplyProfile(replyVo.getUserProfile());
        return addReply;
    }

    public static ReplyVo parseReplyVo(CommentVo commentVo, ReplyVo replyVo) {
        ReplyVo addReply = new ReplyVo();
        addReply.setId(commentVo.getId());
        addReply.setUserId(commentVo.getUserId());
        addReply.setUserNickname(commentVo.getUserNickname());
        addReply.setReplyProfile(commentVo.getUserProfile());
        addReply.setObjectId(commentVo.getObjectId());
        addReply.setCreated(commentVo.getCreated());
        addReply.setContent(commentVo.getContent());
        addReply.setLiked(false);
        addReply.setLikeCount(0);
        addReply.setReplyId(replyVo.getId());
        addReply.setReplyUserId(replyVo.getUserId());
        addReply.setReplyNickname(replyVo.getUserNickname());
        addReply.setReplyProfile(replyVo.getReplyProfile());
        return addReply;
    }

    public static String formatPublishDate(Date publishDate) {
        long current = System.currentTimeMillis() / 1000;
        long publish = publishDate.getTime() / 1000;
        long result = current - publish;
        if (result >= 14400) {
            return CommonUtils.formatDate("MM-dd", publishDate);
        } else if (result >= 3600 && result < 14400) {
            return result / 3600 + "小时前";
        } else if (result < 3600 && result >= 60) {
            return result / 60 + "分钟前";
        } else {
            return "刚刚";
        }
    }

    public static String formatDate(String fmt, Date date) {
        fmt = !TextUtils.isEmpty(fmt) ? fmt : "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(fmt, Locale.CHINA);
        return format.format(date);
    }

    public static Date formatDate(String fmt, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        fmt = !TextUtils.isEmpty(fmt) ? fmt : "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(fmt, Locale.CHINA);
        try {
            return format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射字段
     *
     * @param object    要反射的对象
     * @param fieldName 要反射的字段名称
     */
    public static Object getField(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }
}
