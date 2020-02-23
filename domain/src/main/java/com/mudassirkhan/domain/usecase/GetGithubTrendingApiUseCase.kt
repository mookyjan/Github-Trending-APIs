package com.mudassirkhan.domain.usecase

import com.mudassirkhan.domain.Schedulers
import com.mudassirkhan.domain.UseCase
import com.mudassirkhan.domain.entity.GithubTrendingEntity
import com.mudassirkhan.domain.gateway.TrendingRepositoriesGateWay
import io.reactivex.Single

class GetGithubTrendingApiUseCase (schedulers: Schedulers,
                                   private val trendingRepositoryGateway: TrendingRepositoriesGateWay)
    : UseCase<Boolean,List<GithubTrendingEntity>>(schedulers) {


    override fun buildUseCaseObservable(refresh : Boolean?): Single<List<GithubTrendingEntity>> {

        return trendingRepositoryGateway.getGithubTrendingRepositories(refresh)
    }

}