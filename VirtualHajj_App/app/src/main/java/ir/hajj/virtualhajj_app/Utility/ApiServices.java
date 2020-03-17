package ir.hajj.virtualhajj_app.Utility;

import java.util.List;

import ir.hajj.virtualhajj_app.Models.Post;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface ApiServices {
    @GET("GetItems")
    Call<List<Post>> GetItems(@Query("id") int id);

    @GET("GetNews")
    Call<List<Post>> GetNews();

    @GET("ShowItem")
    Call<Post> ShowItem(@Query("id") int id);

    @GET("ShowNews")
    Call<Post> ShowNews(@Query("id") int id);
//
//    @GET("groups")
//    Call<ResponseBody> getNewsGrpups(@Query("access-token") String Token, @Query("LastModify") String LastModify);
//
//    @GET("latest-posts")
//    Call<ResponseBody> getNews(@Query("access-token") String Token, @Query("GroupID") int GroupID, @Query("PageCount") int PageCount, @Query("PageNumber") int PageNumber, @Query("LastModify") String LastModify);
//
//    @GET("view")
//    Call<ResponseBody> ShowNews(@Query("access-token") String Token, @Query("id") int ID, @Query("LastModify") String LastModify);
//
//    @FormUrlEncoded
//    @POST("set-comment")
//    Call<ResponseBody> setComment(@Query("access-token") String Token, @Query("id") int ID, @Field("author") String author, @Field("email") String email, @Field("content") String content);
//
//    @GET("get-comments")
//    Call<ResponseBody> getComments(@Query("access-token") String Token, @Query("id") int ID, @Query("LastModify") String LastModify);
//
//    @FormUrlEncoded
//    @POST("contact")
//    Call<ResponseBody> contact(@Query("access-token") String Token, @Field("name") String name, @Field("email") String email, @Field("subject") String subject, @Field("body") String body);
//
//    @GET("search")
//    Call<ResponseBody> search(@Query("access-token") String Token, @Query("s") String s, @Query("PageNumber") int PageNumber, @Query("LastModify") String LastModify);
//
//    @GET("about")
//    Call<ResponseBody> about(@Query("access-token") String Token);
//
//    @GET("systems-list")
//    Call<ResponseBody> systemsList(@Query("access-token") String Token, @Query("LastModify") String LastModify, @Query("id") int ID, @Query("id2") int ID2);
//
//    @GET("centers-list")
//    Call<ResponseBody> centersList(@Query("access-token") String Token, @Query("LastModify") String LastModify, @Query("id") int ID);
//
//    @GET("educational-guide-list")
//    Call<ResponseBody> educationalgGuideList(@Query("access-token") String Token, @Query("LastModify") String LastModify, @Query("id") int ID);
//
//    @GET("app-update")
//    Call<ResponseBody> appUpdate(@Query("access-token") String Token);
}
