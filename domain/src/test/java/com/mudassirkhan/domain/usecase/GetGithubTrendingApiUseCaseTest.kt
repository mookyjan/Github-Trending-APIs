package com.mudassirkhan.domain.usecase

import com.mudassirkhan.domain.Schedulers
import com.mudassirkhan.domain.entity.GithubTrendingEntity
import com.mudassirkhan.domain.gateway.TrendingRepositoriesGateWay
import com.mudassirkhan.domain.utils.TestSchedulers
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@Suppress("IllegalIdentifier")
@RunWith(MockitoJUnitRunner::class)
class GetGithubTrendingApiUseCaseTest{

    private lateinit var useCase: GetGithubTrendingApiUseCase
    private val mockGateway: TrendingRepositoriesGateWay = mock()
    private lateinit var schedulers: Schedulers

    @Before
    fun setUp() {
        schedulers = TestSchedulers()
        useCase = GetGithubTrendingApiUseCase(schedulers, mockGateway)
    }

    @Test
    fun `get the list of trending repo list`() {

        //Given
        val mockRepoList = listOf(GithubTrendingEntity( author = "abc",
            avatar = "avatar",
            currentPeriodStars = 1,
            description = "description",
            forks = 12,
            url = "https://phys.org/news/2020-02-earth-cousins-upcoming-missions-biosignatures.html",
            language = "language",
            languageColor = "languageColor",
            name = "name",
            stars = 12))
        Mockito.`when`(mockGateway.getGithubTrendingRepositories()).thenReturn(Single.just(mockRepoList))

        //when

        val testObserver = useCase.execute(false).test()

        //should

        testObserver.assertComplete()
            .assertNoErrors()
            .assertValue(mockRepoList)

        Mockito.verify(mockGateway).getGithubTrendingRepositories()

    }
}