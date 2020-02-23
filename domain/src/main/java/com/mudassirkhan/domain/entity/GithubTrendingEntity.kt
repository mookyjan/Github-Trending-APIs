package com.mudassirkhan.domain.entity


data class GithubTrendingEntity(

    val author: String,

    val avatar: String?=null,

    val currentPeriodStars: Int? =0,

    val description: String?=null,

    val forks: Int ?=0,

    val language: String?=null,

    val languageColor: String?=null,

    val name: String? =null,

    val stars: Int? =0,

    val url: String? =null
)


