package com.mudassirkhan.githubtrendingapis.ui.model

import com.mudassirkhan.domain.entity.GithubTrendingEntity

data class TrendRepositoryModel (
    val author: String,

    val avatar: String?,

    val currentPeriodStars: Int?,

    val description: String?,

    val forks: Int?,

    val language: String?,

    val languageColor: String?,

    val name: String?,

    val stars: Int?,

    val url: String?,

    var expanded: Boolean =false
)

fun GithubTrendingEntity.mapToModel(): TrendRepositoryModel =
    TrendRepositoryModel(author, avatar , currentPeriodStars, description, forks, language, languageColor, name, stars, url)

fun List<GithubTrendingEntity>.mapToModel(): List<TrendRepositoryModel> = map { it.mapToModel() }

fun TrendRepositoryModel.mapToDomain(): GithubTrendingEntity =
    GithubTrendingEntity(author, avatar , currentPeriodStars, description, forks, language, languageColor, name, stars, url)

fun List<TrendRepositoryModel>.mapToDomain(): List<GithubTrendingEntity> = map { it.mapToDomain() }