package com.mudassirkhan.data.repository


import com.mudassirkhan.data.local.TrendingLocalDataSource
import com.mudassirkhan.data.local.model.GithubTrendingLocalEntity
import com.mudassirkhan.data.remote.TrendingRemoteDataSource
import com.mudassirkhan.data.remote.model.mapToLocal
import com.mudassirkhan.data.util.IPreference
import io.reactivex.Single

class TrendingRepository(
    private val trendingLocalDataSource: TrendingLocalDataSource,
    private val trendingRemoteDataSource: TrendingRemoteDataSource,
    private val iPreference: IPreference
) {

    fun getTrendingRepositories(refresh: Boolean? = false): Single<List<GithubTrendingLocalEntity>> {

        val local = trendingLocalDataSource.getGithubTrendingRepositories()
            .filter { !it.isEmpty() }.toSingle()

        val remote = trendingRemoteDataSource.getTrendingRepositoriesList()
            .map { it.mapToLocal() }
            .doOnSuccess {
                iPreference.setSavedTime(System.currentTimeMillis())
                trendingLocalDataSource.insertAll(it)
            }

        var isSameData = false
        //check if time passed is more than 2 hours or force refresh data
        if (iPreference.isFetchNeeded() || refresh!!)
            isSameData = true

        return Single.just(isSameData)
            .flatMap {
                if (it)
                    trendingLocalDataSource.deleteAllData()
                Single.concat(local.onErrorResumeNext(remote), remote)
                    .firstElement().toSingle()
            }


    }
}