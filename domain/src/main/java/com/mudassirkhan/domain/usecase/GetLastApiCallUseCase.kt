package com.mudassirkhan.domain.usecase

import com.mudassirkhan.domain.Schedulers
import com.mudassirkhan.domain.UseCase
import com.mudassirkhan.domain.gateway.TrendingRepositoriesGateWay
import io.reactivex.Single

class GetLastApiCallUseCase  (schedulers: Schedulers,
                              private val trendingRepositoryGateway: TrendingRepositoriesGateWay
) : UseCase<Void, Long>(schedulers) {

    override fun buildUseCaseObservable(params : Void?): Single<Long> {

        return trendingRepositoryGateway.getLastTimeAPICall()
    }
}