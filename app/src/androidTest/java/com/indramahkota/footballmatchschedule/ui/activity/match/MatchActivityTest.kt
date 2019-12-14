package com.indramahkota.footballmatchschedule.ui.activity.match

import android.content.Intent
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.indramahkota.footballmatchschedule.EspressoIdlingResource
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.data.source.locale.entity.LeagueEntity
import com.indramahkota.footballmatchschedule.selectTabAtPosition
import com.indramahkota.footballmatchschedule.ui.activity.match.MatchActivity.Companion.PARCELABLE_LEAGUE_DATA
import org.junit.After
import org.junit.Before
import org.junit.Test

class MatchActivityTest {

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoIdlingResource)
    }

    @Test
    fun toMatchActivityTest() {
        val data = LeagueEntity("4328", "English Premier League", "")

        val activityRule = ActivityTestRule(MatchActivity::class.java)
        val intent = Intent().putExtra(PARCELABLE_LEAGUE_DATA, data)
        activityRule.launchActivity(intent)

        onView(withId(R.id.image_league))
            .check(matches(isDisplayed()))
        onView(withId(R.id.image_league))
            .perform(click())

        onView(withText("Sport"))
            .check(matches(isDisplayed()))
        onView(withText("Sport"))
            .perform(click())
        onView(withId(R.id.strSportData))
            .check(matches(isDisplayed()))
        onView(withId(R.id.strSportData))
            .perform(click())
        onView(withId(R.id.strSportData))
            .check(matches(withText("Soccer")))

        onView(withText("Country"))
            .check(matches(isDisplayed()))
        onView(withText("Country"))
            .perform(click())
        onView(withId(R.id.strCountryData))
            .check(matches(isDisplayed()))
        onView(withId(R.id.strCountryData))
            .perform(click())
        onView(withId(R.id.strCountryData))
            .check(matches(withText("England")))

        onView(withText("Website"))
            .check(matches(isDisplayed()))
        onView(withText("Website"))
            .perform(click())
        onView(withId(R.id.strWebsiteData))
            .check(matches(isDisplayed()))
        onView(withId(R.id.strWebsiteData))
            .check(matches(withText("www.premierleague.com")))

        onView(withId(R.id.tabLayout))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tabLayout)).perform(selectTabAtPosition(1))

        onView(withId(R.id.viewPager))
            .check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoIdlingResource)
    }
}