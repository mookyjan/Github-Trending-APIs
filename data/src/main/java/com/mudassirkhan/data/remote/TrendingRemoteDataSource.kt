package com.mudassirkhan.data.remote

import com.mudassirkhan.data.remote.api.GitHubApiService
import com.mudassirkhan.data.remote.model.GithubTrendingApiResponse
import io.reactivex.Single

class TrendingRemoteDataSource(private val apiService: GitHubApiService) {

    fun getTrendingRepositoriesList() : Single<List<GithubTrendingApiResponse>> = apiService.getGithubTrendingRepositoriesList()
}