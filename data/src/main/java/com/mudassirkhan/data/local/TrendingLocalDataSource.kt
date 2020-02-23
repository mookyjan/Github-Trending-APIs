package com.mudassirkhan.data.local

import com.mudassirkhan.data.local.dao.GithubTrendingApiDao
import com.mudassirkhan.data.local.model.GithubTrendingLocalEntity
import io.reactivex.Maybe
import io.reactivex.Single

class TrendingLocalDataSource (private val githubTrendingApiDao: GithubTrendingApiDao) {

    fun getGithubTrendingRepositories() : Single<List<GithubTrendingLocalEntity>> = githubTrendingApiDao.getGithubTrendingRepositories().toSingle()

    fun insertAll(list : List<GithubTrendingLocalEntity>) = githubTrendingApiDao.insertAll(*list.toTypedArray())

    fun deleteAllData() = githubTrendingApiDao.deleteAllRepositories()
}