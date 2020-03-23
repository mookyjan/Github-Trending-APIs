package com.mudassirkhan.trendinggithubapis.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mudassirkhan.domain.entity.GithubTrendingEntity
import com.mudassirkhan.domain.usecase.GetGithubTrendingApiUseCase
import com.mudassirkhan.trendinggithubapis.ui.model.mapToModel
import com.mudassirkhan.data.util.IPreference
import com.mudassirkhan.domain.usecase.GetLastApiCallUseCase
import com.mudassirkhan.trendinggithubapis.utils.IResourceProvider
import io.reactivex.Single
import org.hamcrest.core.Is
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@Suppress("IllegalIdentifier")
@RunWith(MockitoJUnitRunner::class)
/**
 * Enable the option to mock final classes is still a bit experimental, and requires a manual activation.
 * @see https://antonioleiva.com/mockito-2-kotlin/
 */
class TrendingRepositoriesViewModelTest{

    @Mock
    private lateinit var getHackerNewsListUseCase: GetGithubTrendingApiUseCase

    @Mock
    private lateinit var getLastApiCallUseCase: GetLastApiCallUseCase

    private lateinit var newsListViewModel: TrendingRepositoriesViewModel
    @Mock
    private lateinit var iResourceProvider: IResourceProvider

    @Mock
    private lateinit var iPreference: IPreference

    //to update the value of mutableLive data instantly
    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this);

        this.newsListViewModel = TrendingRepositoriesViewModel( getHackerNewsListUseCase,
            getLastApiCallUseCase
            ,iResourceProvider)
    }

    @Test
    @Throws(Exception::class)
    fun `Given trending repo list, When call repo list, Should update repositoriesList`() {

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
        Mockito.`when`(getHackerNewsListUseCase.execute(true)).thenReturn(Single.just(domainItems))

        // When

        newsListViewModel.loadTrendingRepositories(true).ob

        // Should
        assertThat(newsListViewModel.repositoriesList, Is.`is`(domainItems.mapToModel()))
        //to check the size of the list
        val expectedValue = 1// 2 ids

        assertNotNull(newsListViewModel.repositoriesList)

        assertEquals(expectedValue, newsListViewModel.repositoriesList.size)
        //empty field will be empty
//        assertEquals(newsListViewModel.empty.value,true)
    }

    @Test
    @Throws(Exception::class)
    fun `Given error emission, When load repo list with error, Should update error`() {

        // Given

        val error = RuntimeException("Error emission")
        Mockito.`when`(getHackerNewsListUseCase.execute(true)).thenReturn(Single.error(error))

        // When

        newsListViewModel.loadTrendingRepositories(true)

        // Should

        assertThat(newsListViewModel.error.get(), Is.`is`(error.message))
    }

    @Test
    @Throws(Exception::class)
    fun `Given unknown error emission, When load repo list with unknown error, Should update error`() {

        // Given

        val error = "Unknown error"
//        Mockito.`when`(application.getString(ArgumentMatchers.anyInt())).thenReturn(error)
        Mockito.`when`(getHackerNewsListUseCase.execute(true)).thenReturn(Single.error(RuntimeException()))

        // When

        newsListViewModel.loadTrendingRepositories(true)

        // Should

        assertThat(newsListViewModel.error.get(), Is.`is`(error))
    }


    @Test
    @Throws(Exception::class)
    fun `Given trending repo list, When call refresh, Should update repositoriesList`() {

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
        Mockito.`when`(getHackerNewsListUseCase.execute(true)).thenReturn(Single.just(domainItems))

        // When

        newsListViewModel.refresh()

        // Should
        assertThat(newsListViewModel.repositoriesList, Is.`is`(domainItems.mapToModel()))
        //to check the size of the list
        val expectedValue = 1// 2 ids

        assertNotNull(newsListViewModel.repositoriesList)

        assertEquals(expectedValue, newsListViewModel.repositoriesList.size)

    }


    @Test fun `testing isFetchedNeeded() difference time should return 2`(){
        //given
        val expectedValue = 2L

        val currentTime =System.currentTimeMillis()
        val lastUpdateTime = System.currentTimeMillis()- TimeUnit.HOURS.toMillis(2)
        val minutes  = TimeUnit.MILLISECONDS.toHours( currentTime- lastUpdateTime!!)

        //then assert
        assert(minutes==expectedValue)
    }

    @Test
    fun `sort list items by names`(){


    }

    @Test
    fun `check for last time called Api`(){
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
        Mockito.`when`(getHackerNewsListUseCase.execute(false)).thenReturn(Single.just(domainItems))


        val lastApiCallTime =30L
        val timeDiff30Min = "time difference is 30 min"
        val timeDiff60Min ="time difference is 60 min"
        Mockito.`when`(getLastApiCallUseCase.execute()).thenReturn(Single.just(lastApiCallTime))

        // When

        newsListViewModel.calculateTimeDiff()

        // Should

        if (lastApiCallTime.equals(30L))
            assertThat(newsListViewModel.lastUpdateValue.value, Is.`is`(timeDiff30Min))
        else if (lastApiCallTime.equals(60L))
            assertThat(newsListViewModel.lastUpdateValue.value, Is.`is`(timeDiff60Min))
    }


}