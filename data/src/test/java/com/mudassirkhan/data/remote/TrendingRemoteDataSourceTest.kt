package com.mudassirkhan.data.remote

import com.mudassirkhan.data.NetworkModule
import com.mudassirkhan.data.remote.api.GitHubApiService
import com.mudassirkhan.data.remote.model.GithubTrendingApiResponse
import com.nhaarman.mockitokotlin2.any
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.File

@Suppress("IllegalIdentifier")
@RunWith(JUnit4::class)
class TrendingRemoteDataSourceTest {

    private lateinit var mockWebServer: MockWebServer

    private lateinit var networkModule: NetworkModule
    @Mock
    private lateinit var trendingRepoApiService: GitHubApiService

    private lateinit var remoteDataSource: TrendingRemoteDataSource

    @Mock
    private lateinit var okHttpClient: OkHttpClient


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        MockitoAnnotations.initMocks(this);
        mockWebServer.start()

        networkModule = NetworkModule()
        okHttpClient = networkModule.provideOkHttpClient()
        trendingRepoApiService =
            networkModule.provideHackerAPI(okHttpClient,mockWebServer.url("/").toString())

        remoteDataSource = TrendingRemoteDataSource(trendingRepoApiService)
    }

    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }

    @Test
    @Throws(Exception::class)
    fun `Given event type data, When get all, Should get those event types`() {

        // Given

        val items = listOf(
            GithubTrendingApiResponse(
                author = "OpenRCE",
                name = "Malware-Analysis-Training",
                avatar = "https://github.com/OpenRCE.png",
                description = "Retired beginner/intermediate malware analysis training materials from @pedramamini and @erocarrera.",
                url = "https://github.com/OpenRCE/Malware-Analysis-Training",
                language = "HTML",
                languageColor = "#e34c26",
                currentPeriodStars = 192,
                forks = 58,
                stars = 312
            )
        )
        val json = getJson("json/trending_repo.json")

        val response = MockResponse().setBody(json)
        mockWebServer.enqueue(response)

        // When

        val testObserver = remoteDataSource.getTrendingRepositoriesList()
            .test()

        // Then

        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertValue(items)
    }


    private fun getJson(path: String): String {
//        C:\Projects\Examples\Hacker News App\data\src\test\resources\json\trending_repo.json
        val file = File("src/test/resources/$path")
        return String(file.readBytes())
    }


}