package com.mudassirkhan.data.gateway

import com.mudassirkhan.data.local.model.mapToDomain
import com.mudassirkhan.data.repository.TrendingRepository
import com.mudassirkhan.domain.entity.GithubTrendingEntity
import com.mudassirkhan.domain.gateway.TrendingRepositoriesGateWay
import io.reactivex.Single

class TrendingRepositoriesGateWayImpl(private val trendingRepository: TrendingRepository) : TrendingRepositoriesGateWay {


    override fun getGithubTrendingRepositories(refresh: Boolean?): Single<List<GithubTrendingEntity>> {

         return trendingRepository.getTrendingRepositories(refresh)
             .map { it.mapToDomain() }
    }


    override fun getLastTimeAPICall(): Single<Long> {

        return trendingRepository.checkLastApiCallTimeDiffInMin()
    }
}