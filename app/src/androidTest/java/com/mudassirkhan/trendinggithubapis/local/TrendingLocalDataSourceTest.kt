package com.mudassirkhan.trendinggithubapis.local

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.mudassirkhan.data.local.TrendingLocalDataSource
import com.mudassirkhan.data.local.db.GithubTrendingApiDatabase
import com.mudassirkhan.data.local.model.GithubTrendingLocalEntity
import com.mudassirkhan.trendinggithubapis.local.test.TestDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrendingLocalDataSourceTest {

    private lateinit var database: GithubTrendingApiDatabase
    private lateinit var trendingRepoLocalDataSource: TrendingLocalDataSource


    @Before
    fun setUp() {

        database = TestDatabase.newSystemInstance(InstrumentationRegistry.getContext())
        trendingRepoLocalDataSource = TrendingLocalDataSource(database.githubTrendingApiDao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun whenInsertTrendingRepoOnLocalDataSource_ThoseRowsShouldBeRetrieved() {

        // Given

        val localItems = listOf(GithubTrendingLocalEntity( author = "OpenRCE",
            name = "Malware-Analysis-Training",
            avatar = "https://github.com/OpenRCE.png",
            description = "Retired beginner/intermediate malware analysis training materials from @pedramamini and @erocarrera.",
            url = "https://github.com/OpenRCE/Malware-Analysis-Training",
            language = "HTML",
            languageColor = "#e34c26",
            currentPeriodStars = 192,
            forks = 58,
            stars = 312))

        // When

        trendingRepoLocalDataSource.insertAll(localItems)

        val testObserver = trendingRepoLocalDataSource.getGithubTrendingRepositories()
            .test()

        // Then

        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertValue(localItems)
    }

}