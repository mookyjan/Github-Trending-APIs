package com.mudassirkhan.trendinggithubapis

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.mudassirkhan.trendinggithubapis.ui.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    var recyclerView: RecyclerView? = null


    @Before
    fun setUp() {
        recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.rv_trending_repo_list)
    }

    @Test
    fun testIsMainActivityInView() {
        Espresso.onView(ViewMatchers.withId(R.id.ly_main_activity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testCaseSwipeRefreshRecyclerViewVisibility() {
        Espresso.onView(ViewMatchers.withId(R.id.ly_swipe_refresh))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testCaseSwipeRefresh(){
        Espresso.onView(ViewMatchers.withId(R.id.rv_trending_repo_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.ly_swipe_refresh)).perform(ViewActions.swipeDown())
    }

    @Test
    fun testCaseRecyclerViewClicked(){
        Espresso.onView(ViewMatchers.withId(R.id.rv_trending_repo_list))
            .inRoot(RootMatchers.withDecorView(
                Matchers.`is`(activityRule.activity.window.decorView)
            )).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))
    }

    @Test
    fun testCaseForRecyclerViewScroll(){

        //Get total count of recyclerView
        val itemCount = recyclerView?.adapter?.itemCount!!
        Espresso.onView(ViewMatchers.withId(R.id.rv_trending_repo_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // scroll to the end of page position
        Espresso.onView(ViewMatchers.withId(R.id.rv_trending_repo_list))
            .inRoot(RootMatchers.withDecorView(
                Matchers.`is`(activityRule.activity.window.decorView)
            )).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount -1))

    }


}