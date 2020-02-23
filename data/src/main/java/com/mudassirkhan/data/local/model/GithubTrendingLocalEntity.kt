package com.mudassirkhan.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mudassirkhan.domain.entity.GithubTrendingEntity

@Entity(tableName = "trendingList")
data class GithubTrendingLocalEntity(

    @PrimaryKey
    val author: String,

    val avatar: String?,

//    val builtBy: List<BuiltBy>,

    val currentPeriodStars: Int?,

    val description: String?,

    val forks: Int?,

    val language: String ?=null,

    val languageColor: String?,

    val name: String?,

    val stars: Int?,

    val url: String?
)

data class BuiltBy(
    val avatar: String,
    val href: String,
    val username: String
)

fun GithubTrendingLocalEntity.mapToDomain(): GithubTrendingEntity =
    GithubTrendingEntity(author, avatar , currentPeriodStars, description, forks, language, languageColor, name, stars, url)

fun List<GithubTrendingLocalEntity>.mapToDomain(): List<GithubTrendingEntity> = map { it.mapToDomain() }
