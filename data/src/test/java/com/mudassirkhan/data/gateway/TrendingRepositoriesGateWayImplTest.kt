package com.mudassirkhan.data.gateway

import com.mudassirkhan.data.local.model.GithubTrendingLocalEntity
import com.mudassirkhan.data.remote.model.GithubTrendingApiResponse
import com.mudassirkhan.data.remote.model.mapToLocal
import com.mudassirkhan.data.repository.TrendingRepository
import com.mudassirkhan.domain.entity.GithubTrendingEntity
import com.mudassirkhan.domain.gateway.TrendingRepositoriesGateWay
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@Suppress("IllegalIdentifier")
@RunWith(MockitoJUnitRunner::class)
class TrendingRepositoriesGateWayImplTest{

    @Mock
    private lateinit var trendingRepoListRepository: TrendingRepository


    private lateinit var trendingRepoGateway: TrendingRepositoriesGateWay

    @Before
    fun setUp() {
        trendingRepoGateway = TrendingRepositoriesGateWayImpl(trendingRepoListRepository)
    }

    @Test
    @Throws(Exception::class)
    fun `Given trending repo data, When get trending repo list, Should fetch data from repository then parse to domain data`() {

        // Given
        val domainItems = listOf<GithubTrendingEntity>(
            GithubTrendingEntity(
                author = "abc",
                avatar = "avatar",
                currentPeriodStars = 1,
                description = "description",
                forks = 12,
                url = "https://phys.org/news/2020-02-earth-cousins-upcoming-missions-biosignatures.html",
                language = "language",
                languageColor = "languageColor",
                name = "name",
                stars = 12
            )
        )
        val repositoryItems = listOf<GithubTrendingApiResponse>(
            GithubTrendingApiResponse(
                author = "abc",
                avatar = "avatar",
                currentPeriodStars = 1,
                description = "description",
                forks = 12,
                url = "https://phys.org/news/2020-02-earth-cousins-upcoming-missions-biosignatures.html",
                language = "language",
                languageColor = "languageColor",
                name = "name",
                stars = 12
            )
        )

        Mockito.`when`(trendingRepoListRepository.getTrendingRepositories()).thenReturn(Single.just(repositoryItems.mapToLocal()))

        // When

        val testObserver = trendingRepoGateway.getGithubTrendingRepositories()
            .test()

        // Should

        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertValue(domainItems)
    }
}