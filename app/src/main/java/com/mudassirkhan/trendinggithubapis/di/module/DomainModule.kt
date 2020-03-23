package com.mudassirkhan.trendinggithubapis.di.module

import com.mudassirkhan.domain.Schedulers
import com.mudassirkhan.domain.gateway.TrendingRepositoriesGateWay
import com.mudassirkhan.domain.usecase.GetGithubTrendingApiUseCase
import com.mudassirkhan.domain.usecase.GetLastApiCallUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideTrendingListUseCase(schedulers: Schedulers,trendingRepositoriesGateWay: TrendingRepositoriesGateWay)
     = GetGithubTrendingApiUseCase(schedulers,trendingRepositoriesGateWay)

    @Provides
    fun provideGetLastApiCallUseCase(schedulers: Schedulers,trendingRepositoriesGateWay: TrendingRepositoriesGateWay)
            = GetLastApiCallUseCase(schedulers,trendingRepositoriesGateWay)
}