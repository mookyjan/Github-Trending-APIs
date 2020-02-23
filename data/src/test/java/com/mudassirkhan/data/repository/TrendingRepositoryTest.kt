package com.mudassirkhan.data.repository


import com.mudassirkhan.data.local.TrendingLocalDataSource
import com.mudassirkhan.data.local.model.GithubTrendingLocalEntity
import com.mudassirkhan.data.remote.TrendingRemoteDataSource
import com.mudassirkhan.data.remote.model.GithubTrendingApiResponse
import com.mudassirkhan.data.remote.model.mapToLocal
import com.mudassirkhan.githubtrendingapis.utils.IPreference
import io.reactivex.Completable
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
class TrendingRepositoryTest{


    @Mock
    private lateinit var remoteDataSource: TrendingRemoteDataSource

    @Mock
    private lateinit var trendingLocalDataSource: TrendingLocalDataSource

    private lateinit var trendingListRepository: TrendingRepository
    @Mock
    private lateinit var iPreference: IPreference

    @Before
    fun setUp() {
        trendingListRepository =
            TrendingRepository( trendingLocalDataSource,remoteDataSource,iPreference)
    }


    @Test
    @Throws(Exception::class)
    fun `get list of trending repo and insert it into db `() {

        // Given
        val refresh = true
        val localItems = listOf<GithubTrendingLocalEntity>(GithubTrendingLocalEntity(
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
        ))
        val remoteItems = listOf<GithubTrendingApiResponse>(GithubTrendingApiResponse(
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
        ))
        // Empty local data is expected due to deletion of local source
        Mockito.`when`(trendingLocalDataSource.getGithubTrendingRepositories()).thenReturn(Completable.complete().toSingleDefault(
            listOf()))
        // Fetch remote data
        Mockito.`when`(remoteDataSource.getTrendingRepositoriesList()).thenReturn(Single.just(remoteItems))


        // When

        val testObserver = trendingListRepository.getTrendingRepositories(refresh)
            .test().await()

        // Should

        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertValue(localItems)

        val inOrder = Mockito.inOrder(trendingLocalDataSource)
        // Delete local source by type
        inOrder.verify(trendingLocalDataSource).deleteAllData()
        // Call both data sources
        Mockito.verify(trendingLocalDataSource).getGithubTrendingRepositories()
        Mockito.verify(remoteDataSource).getTrendingRepositoriesList()
        // Insert into local source
        inOrder.verify(trendingLocalDataSource).insertAll(localItems)
    }

    @Test
    @Throws(Exception::class)
    fun `get trending list and  map it`() {

        //Given
        val responseOfComment = listOf<GithubTrendingApiResponse>(GithubTrendingApiResponse(
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
        ))

        // Empty local data is expected due to deletion of local source
        Mockito.`when`(trendingLocalDataSource.getGithubTrendingRepositories()).thenReturn(Completable.complete().toSingleDefault(
            listOf()))
        Mockito.`when`(remoteDataSource.getTrendingRepositoriesList()).thenReturn(Single.just(responseOfComment))

        //when
        val testObserver = trendingListRepository.getTrendingRepositories(true)
            .test().await()



        //should
        testObserver.assertComplete()
            .assertNoErrors()
            .assertValue(responseOfComment.mapToLocal())
    }
}