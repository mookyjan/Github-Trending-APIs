package com.mudassirkhan.domain.gateway

import com.mudassirkhan.domain.entity.GithubTrendingEntity
import io.reactivex.Single

interface TrendingRepositoriesGateWay {

    fun getGithubTrendingRepositories( refresh: Boolean? = false): Single<List<GithubTrendingEntity>>

    fun getLastTimeAPICall() : Single<Long>
}