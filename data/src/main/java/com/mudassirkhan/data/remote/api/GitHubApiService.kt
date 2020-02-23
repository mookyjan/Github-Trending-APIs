package com.mudassirkhan.data.remote.api

import com.mudassirkhan.data.remote.model.GithubTrendingApiResponse
import io.reactivex.Single
import retrofit2.http.GET

interface GitHubApiService {

    @GET("/repositories")
    fun getGithubTrendingRepositoriesList() : Single<List<GithubTrendingApiResponse>>
}