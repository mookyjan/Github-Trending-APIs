package com.mudassirkhan.githubtrendingapis.di.module

import com.mudassirkhan.domain.Schedulers
import com.mudassirkhan.domain.gateway.TrendingRepositoriesGateWay
import com.mudassirkhan.domain.usecase.GetGithubTrendingApiUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideTrendingListUseCase(schedulers: Schedulers,trendingRepositoriesGateWay: TrendingRepositoriesGateWay)
     = GetGithubTrendingApiUseCase(schedulers,trendingRepositoriesGateWay)

}