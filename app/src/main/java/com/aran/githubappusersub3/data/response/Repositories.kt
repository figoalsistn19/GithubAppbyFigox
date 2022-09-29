package com.aran.githubappusersub3.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repositories(
    val name: String,
    val description: String,
    val language: String,
    val size: Int,
    val watchers: Int,
    val forks: Int
) : Parcelable
