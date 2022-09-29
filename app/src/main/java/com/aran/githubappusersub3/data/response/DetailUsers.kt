package com.aran.githubappusersub3.data.response

import com.google.gson.annotations.SerializedName

data class DetailUsers (
    val avatar_url: String,
    val name: String,
    val login: String,
    val bio: String,
    val location: String,
    val followers: Int,
    val following: Int,
    val public_repos: Int,
    val followers_url: String,
    val following_url: String,
    val repos_url: String,
    val id: Int,

    @field:SerializedName("html_url")
    var user_url: String? = null

        )