package com.mudassirkhan.trendinggithubapis.di.module

import android.content.Context
import com.mudassirkhan.data.gateway.TrendingRepositoriesGateWayImpl
import com.mudassirkhan.data.local.TrendingLocalDataSource
import com.mudassirkhan.data.local.dao.GithubTrendingApiDao
import com.mudassirkhan.data.local.db.GithubTrendingApiDatabase
import com.mudassirkhan.data.remote.TrendingRemoteDataSource
import com.mudassirkhan.data.remote.api.GitHubApiService
import com.mudassirkhan.data.repository.TrendingRepository
import com.mudassirkhan.domain.gateway.TrendingRepositoriesGateWay
import com.mudassirkhan.data.util.IPreference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    internal fun provideGithubTrendingRemoteDataSource(githubTrendingApiService: GitHubApiService) =
        TrendingRemoteDataSource(githubTrendingApiService)


    @Provides
    @Singleton
    internal fun provideTrendingDatabase(context: Context) =
        GithubTrendingApiDatabase.newInstance(context)


    @Provides
    @Singleton
    internal fun provideGithubTrendingDao(trendingApiDatabase: GithubTrendingApiDatabase)
    = trendingApiDatabase.githubTrendingApiDao()

    @Provides
    @Singleton
    internal fun provideGithubTrendingLocalDataSource(githubTrendingApiDao: GithubTrendingApiDao)
    = TrendingLocalDataSource(githubTrendingApiDao)

    @Provides
    @Singleton
    internal fun provideTrendingRepository(localDataSource: TrendingLocalDataSource,remoteDataSource: TrendingRemoteDataSource,iPreference: IPreference)
    = TrendingRepository(localDataSource,remoteDataSource,iPreference)


    @Provides
    @Singleton
    internal fun provideTrendingGateway(trendingRepository: TrendingRepository): TrendingRepositoriesGateWay
    = TrendingRepositoriesGateWayImpl(trendingRepository)
}