package com.university.gami_android.repository

import com.university.gami_android.model.*
import retrofit2.Call
import retrofit2.http.*


interface UserRepository {
    @POST("user/register")
    fun saveUser(@Header("Authorization") auth: String, @Body user: User): Call<User>

    @GET("user/principal")
    fun login(@Header("Authorization") auth: String): Call<User>

    @GET("logout")
    fun logout(): Call<Void>

    @GET("user/principal")
    fun getPrincipal(@Header("Authorization") auth: String): Call<User>

    @PUT("user/changePassword")
    fun changePassword(@Body changePassword: ChangePassword): Call<Void>

    @POST("user/sendMail")
    fun sendMail(@Body sendMail: SendMail): Call<Boolean>

    @POST("user/join/{eventName}")
    fun joinEvent(@Header("Authorization") auth: String, @Path(value = "eventName") eventName: String): Call<Boolean>

    @DELETE("user/join/{eventName}")
    fun leftEvent(@Header("Authorization") auth: String, @Path(value = "eventName") eventName: String): Call<Void>

    @GET("user/events")
    fun getEvents(@Header("Authorization") auth: String): Call<List<Event>>

    @DELETE("user/join/{eventName}")
    fun removeEventJoined(@Header("Authorization") auth: String, @Path(value ="eventName") eventName: String): Call<Void>

    @GET("user/bookmarks")
    fun getBookmarks(@Header("Authorization") auth: String): Call<List<Event>>

    @DELETE("user/bookmark/{eventName}")
    fun removeBookmark(@Header("Authorization") auth: String, @Path(value ="eventName") eventName: String): Call<Void>
    
    @POST("user/bookmark/{eventName}")
    fun addBookmark(@Header("Authorization") auth: String, @Path(value ="eventName") eventName: String): Call<Void>

    @GET("user/{username}/photos")
    fun getPhotosByUser(@Header("Authorization") auth: String, @Path(value = "username") username: String): Call<List<Photo>>

    @PUT("user/{username}/photos")
    fun setUserPhotos(@Header("Authorization") auth: String, @Path(value = "username") username: String, @Body photo: Photo): Call<Void>

    @GET("user/byUsername/{username}")
    fun getUser(@Header("Authorization") auth: String, @Path(value = "username") username: String): Call<User>

    @GET("user/{username}/hostedEvents")
    fun getHostedEvents(@Header("Authorization") auth: String, @Path(value = "username") username: String): Call<List<Event>>

    @PUT("user")
    fun editUser(@Header("Authorization") auth: String, @Body user: User): Call<User>
}
