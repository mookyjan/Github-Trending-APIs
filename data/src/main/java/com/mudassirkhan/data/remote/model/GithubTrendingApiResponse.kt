package com.mudassirkhan.data.remote.model

import com.mudassirkhan.domain.entity.GithubTrendingEntity
import com.squareup.moshi.Json

data class GithubTrendingApiResponse(
    @Json(name ="author")
    val author: String,
    @Json(name ="avatar")
    val avatar: String?=null,
    @Json(name ="currentPeriodStars")
    val currentPeriodStars: Int?=0,
    @Json(name ="description")
    val description: String?=null,
    @Json(name ="forks")
    val forks: Int?=0,
    @Json(name ="language")
    val language: String ?= null,
    @Json(name ="languageColor")
    val languageColor: String?=null,
    @Json(name ="name")
    val name: String? =null,
    @Json(name ="stars")
    val stars: Int ?=0,
    @Json(name ="url")
    val url: String? =null
)


fun GithubTrendingApiResponse.mapToDomain(): GithubTrendingEntity =
    GithubTrendingEntity(author, avatar , currentPeriodStars, description, forks, language, languageColor, name, stars, url)

fun List<GithubTrendingApiResponse>.mapToDomain(): List<GithubTrendingEntity> = map { it.mapToDomain() }