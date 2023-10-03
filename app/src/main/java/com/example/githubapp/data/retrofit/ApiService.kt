package com.example.githubapp.data.retrofit

import com.example.githubapp.data.response.FollowersUserResponseItem
import com.example.githubapp.data.response.FollowingUserResponseItem
import com.example.githubapp.data.response.GithubResponse
import com.example.githubapp.data.response.GithubUserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") login : String
    ) : Call<GithubResponse>

    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login : String
    ) : Call<GithubUserDetailResponse>

    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login") login : String?
    ) : Call <List<FollowersUserResponseItem>>

    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login") login : String?
    ) : Call <List<FollowingUserResponseItem>>
}