package com.aran.githubappusersub3.api

import com.aran.githubappusersub3.data.response.Repositories
import com.aran.githubappusersub3.data.response.UserResponse
import com.aran.githubappusersub3.data.response.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    @Headers("Authorization: token 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")
    fun getUsers(): Call<ArrayList<Users>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") q: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<Users>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<Users>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<Users>>

    @GET("users/{username}/repos")
    @Headers("Authorization: token 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")
    fun getUserRepository(
        @Path("username") username: String
    ): Call<ArrayList<Repositories>>

}