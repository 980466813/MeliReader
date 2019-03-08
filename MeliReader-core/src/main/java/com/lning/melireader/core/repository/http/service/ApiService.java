package com.lning.melireader.core.repository.http.service;

import com.lning.melireader.core.repository.http.bean.Result;

import java.util.Date;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ning on 2018/11/15.
 */

public interface ApiService {

    @Headers({"Domain-Name: SSO_URL"})
    @POST("user/info/{type}")
    @FormUrlEncoded
    Single<String> getUserInfo(@Path("type") String type, @Field("token") String token);

    @Headers({"Domain-Name: SSO_URL"})
    @POST("user/getbyid/{userId}/{type}")
    @FormUrlEncoded
    Single<String> getUserInfoByUserId(@Path("type") String type, @Field("token") String token,
                                       @Path("userId") String userId, @Field("ownerId") String ownerIdS);

    @Headers({"Domain-Name: SSO_URL"})
    @POST("user/login")
    @FormUrlEncoded
    Single<String> login(@Field("username") String username, @Field("password") String password,
                         @Field("mac") String mac, @Field("__from__") String from);

    @Headers({"Domain-Name: SSO_URL"})
    @POST("user/login")
    @FormUrlEncoded
    Single<String> register(@Field("username") String username, @Field("password") String password,
                            @Field("nickname") String nickname, @Field("code") String code);

    @Headers({"Domain-Name: SSO_URL"})
    @POST("user/verify")
    @FormUrlEncoded
    Single<String> verify(@Field("type") String type, @Field("obj1") String obj1, @Field("obj2") String obj2);

    @Headers({"Domain-Name: MANAGE_URL"})
    @POST("pic/upload")
    @Multipart
    Single<String> uploadPhoto(@Part MultipartBody.Part uploadFile);

    @Headers({"Domain-Name: SSO_URL", "Cookie-Name: MELI_TOKEN"})
    @GET("user/update")
    Single<String> updateUserInfo(@Query("id") String userId, @Query("roleId") Long rid, @Query("nickname") String nickname, @Query("birthday") Date birthday,
                                  @Query("profile") String image, @Query("address") String address, @Query("signature") String signature);

    @Headers({"Domain-Name: SSO_URL"})
    @POST("send/code/{mobile}")
    @FormUrlEncoded
    Single<String> sendVerificationCode(@Path("mobile") String mobile);

    @Headers({"Cookie-Name: MELI_TOKEN"})
    @POST("history/create")
    @FormUrlEncoded
    Single<String> insertHistory(@Field("userId") String userId, @Field("newsId") String newsId);

    @Headers({"Domain-Name: SSO_URL", "Cookie-Name: MELI_TOKEN"})
    @GET("user/logout")
    Single<String> logout(@Query("username") String username, @Query("mac") String mac, @Query("__from__") String from);

    @GET("history/listbyuserid/{userId}/{page}/{rows}/{ctype}")
    Single<String> getHistoryListByUserId(@Path("userId") String userId, @Path("page") Integer page,
                                          @Path("rows") Integer rows, @Path("ctype") String ctype);

    @Headers({"Cookie-Name: MELI_TOKEN"})
    @POST("history/deletebyid")
    @FormUrlEncoded
    Single<String> deleteHistoryUserAndNewsId(@Field("userId") String userId, @Field("newsId") String newsId);

    @Headers({"Cookie-Name: MELI_TOKEN"})
    @POST("history/deletebyid")
    @FormUrlEncoded
    Single<String> deleteHistoryByUserId(@Field("userId") String userId);


    @Headers({"Cookie-Name: MELI_TOKEN"})
    @POST("history/deletebyctype")
    @FormUrlEncoded
    Single<String> deleteHistoryListByCtype(@Field("userId") String userId, @Field("ctype") String ctype);


    @GET("collection/listbytag/{userId}/{page}/{rows}/{tag}")
    Single<String> getCollectionListByUserIdAndTag(@Path("userId") String userId, @Path("page") Integer page,
                                                   @Path("rows") Integer rows, @Path("tag") String tag);

    @GET("collection/listbyctype/{userId}/{page}/{rows}/{ctype}")
    Single<String> getCollectionListByUserId(@Path("userId") String userId, @Path("page") Integer page,
                                             @Path("rows") Integer rows, @Path("ctype") String ctype);

    @Headers({"Cookie-Name: MELI_TOKEN"})
    @POST("collection/create")
    @FormUrlEncoded
    Single<String> createCollection(@Field("userId") String userId, @Field("newsId") String newsId, @Field("tag") String tag);

    @Headers({"Cookie-Name: MELI_TOKEN"})
    @POST("collection/update")
    @FormUrlEncoded
    Single<String> updateCollection(@Field("userId") String userId, @Field("newsId") String newsId, @Field("tag") String tag);

    @Headers({"Cookie-Name: MELI_TOKEN"})
    @POST("collection/delete")
    @FormUrlEncoded
    Single<String> deleteCollection(@Field("userId") String userId, @Field("newsId") String newsId);


    @POST("news/listbydislikeIds/{page}/{rows}/{ctype}")
    @FormUrlEncoded
    Single<String> getNewsListVoByDislikeIds(@Field("channels") String channels, @Field("userId") String userId, @Path("ctype") String ctype, @Path("page") int page, @Path("rows") int rows, @Field("today") int today);


    @POST("news/listvideobydislikeIds/{page}/{rows}/{ctype}")
    @FormUrlEncoded
    Single<String> getVideoNewsListVoByDislikeIds(@Field("channels") String channels, @Field("userId") String userId, @Path("ctype") String ctype, @Path("page") int page, @Path("rows") int rows, @Field("today") int today);

    @POST("news/listrecommend/{page}/{rows}")
    @FormUrlEncoded
    Single<String> getRecommendNewsList(@Path("page") Integer page, @Path("rows") Integer rows, @Field("userId") String userId, @Field("tag") String dislikeIds,
                                        @Field("ctype") String ctype, @Field("today") int today);

    @POST("news/listbyuserattention/{page}/{rows}")
    @FormUrlEncoded
    Single<String> getUserAttentionNewsList(@Path("page") Integer page, @Path("rows") Integer rows, @Field("userId") String userId,
                                            @Field("ctype") String ctype, @Field("today") int today);


    @POST("news/listbychannel/{channelId}/{page}/{rows}/{ctype}")
    @FormUrlEncoded
    Single<String> getNewsListByChannelId(@Path("channelId") String channelId, @Field("userId") String userId, @Path("ctype") String ctype, @Path("page") int page, @Path("rows") int rows, @Field("today") int today);

    @GET("news/getbynewsId/{newsId}/{userId}")
    Single<String> getNewsVoByNewsId(@Path("newsId") String newsId, @Path("userId") String userId);


    @GET("comment/listcomments/{newsId}/{page}/{rows}/{userId}")
    Single<String> getNewsCommentsByNewsId(@Path("newsId") String newsId, @Path("userId") String userId
            , @Path("page") int page, @Path("rows") int rows);


    @Headers({"Cookie-Name: MELI_TOKEN"})
    @POST("comment/create")
    @FormUrlEncoded
    Single<String> addNewsComment(@Field("newsId") String newsId, @Field("userId") String userId,
                                  @Field("replyId") String replyId, @Field("content") String content);


    @Headers({"Cookie-Name: MELI_TOKEN"})
    @POST("comment/delete")
    @FormUrlEncoded
    Single<String> deleteNewsComment(@Field("newsId") String newsId, @Field("userId") String userId,
                                     @Field("replyId") String replyId);


    @POST("comment/getbyid/{commentId}")
    @FormUrlEncoded
    Single<String> getNewsCommentById(@Path("commentId") String commentId, @Field("userId") String userId);

    @POST("like/object")
    @FormUrlEncoded
    Single<String> likeObject(@Field("userId") String userId, @Field("objectId") String objectId);

    @POST("dislike/object")
    @FormUrlEncoded
    Single<String> dislikeObject(@Field("userId") String userId, @Field("objectId") String objectId);

    @Headers({"Domain-Name: SEARCH_URL"})
    @GET("q")
    Single<String> searchKeywords(@Query("keywords") String keywords, @Query("type") String ctype
            , @Query("page") int page, @Query("rows") int rows);


    @POST("news/listbyhascomment/{userId}/{page}/{rows}")
    @FormUrlEncoded
    Single<String> listNewsListVoWithCommentsByUserId(@Path("userId") String userId, @Field("ownerId") String ownerId,
                                                      @Path("page") int page, @Path("rows") int rows);

    @Headers({"Cookie-Name: MELI_TOKEN"})
    @POST("attention/create")
    @FormUrlEncoded
    Single<String> operateAttention(@Field("userId") String userId, @Field("objectId") String objectId);

    @GET("favourite/getdefault")
    Single<String> getDefaultFavouriteChannels();

}
