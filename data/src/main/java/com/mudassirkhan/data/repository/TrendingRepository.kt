package com.mudassirkhan.data.repository


import com.mudassirkhan.data.local.TrendingLocalDataSource
import com.mudassirkhan.data.local.model.GithubTrendingLocalEntity
import com.mudassirkhan.data.remote.TrendingRemoteDataSource
import com.mudassirkhan.data.remote.model.mapToLocal
import com.mudassirkhan.data.util.AppConstanst
import com.mudassirkhan.data.util.IPreference
import io.reactivex.Single
import java.util.concurrent.TimeUnit

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
                iPreference.setLong(AppConstanst.SAVED_AT_TIME,System.currentTimeMillis())
                trendingLocalDataSource.insertAll(it)
            }

        var isSameData = false
        //check if time passed is more than 2 hours or force refresh data
        if (isFetchNeeded() || refresh!!)
            isSameData = true

        return Single.just(isSameData)
            .flatMap {
                if (it)
                    trendingLocalDataSource.deleteAllData()
                Single.concat(local.onErrorResumeNext(remote), remote)
                    .firstElement().toSingle()
            }


    }

    /**
     * fetch data after two hours
     */
    fun isFetchNeeded():Boolean{
        val currentTime =System.currentTimeMillis()
        val lastUpdateTime = iPreference.getLong(AppConstanst.SAVED_AT_TIME)
        val hours = TimeUnit.MILLISECONDS.toHours( currentTime- lastUpdateTime!!)
        return hours>2
    }

    /**
     * method to check for last api and current time difference
     */

    fun checkLastApiCallTimeDiffInMin() :Single<Long>{
        val currentTime =System.currentTimeMillis()
        val previousApiCallTime = iPreference.getLong(AppConstanst.SAVED_AT_TIME)
        if (previousApiCallTime==0L)
            return Single.just(-1)
        else{
            val timeDiff = TimeUnit.MILLISECONDS.toMinutes(currentTime-previousApiCallTime)
            return Single.just(timeDiff)
        }

    }
}