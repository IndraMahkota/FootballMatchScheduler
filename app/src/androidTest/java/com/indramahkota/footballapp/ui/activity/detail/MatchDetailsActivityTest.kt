package com.indramahkota.footballapp.ui.activity.detail

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.indramahkota.footballapp.AndroidTestFakeData.generateMatchEntity
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.ui.activity.detail.match.MatchDetailsActivity
import com.indramahkota.footballapp.ui.activity.detail.match.MatchDetailsActivity.Companion.PARCELABLE_MATCH_DATA
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MatchDetailsActivityTest {

    @Test
    fun toMatchDetailsActivityTest() {
        val data = generateMatchEntity("Southampton", "West Ham", "2", "3")

        val activityRule = ActivityTestRule(MatchDetailsActivity::class.java)
        val intent = Intent().putExtra(PARCELABLE_MATCH_DATA, data)
        activityRule.launchActivity(intent)

        onView(withId(R.id.tvDate))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tvDate))
            .perform(click())

        onView(withId(R.id.tvVs))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tvVs))
            .perform(click())

        onView(withId(R.id.tvSkorTeam1))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tvSkorTeam1))
            .perform(click())

        onView(withId(R.id.tvSkorTeam2))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tvSkorTeam2))
            .perform(click())

        onView(withId(R.id.tvTeam1Name))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tvTeam1Name))
            .perform(click())

        onView(withId(R.id.ivTeam1Logo))
            .check(matches(isDisplayed()))
        onView(withId(R.id.ivTeam1Logo))
            .perform(click())

        onView(withId(R.id.tvTeam2Name))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tvTeam2Name))
            .perform(click())

        onView(withId(R.id.ivTeam2Logo))
            .check(matches(isDisplayed()))
        onView(withId(R.id.ivTeam2Logo))
            .perform(click())

        onView(withId(R.id.fab))
            .check(matches(isDisplayed()))
        onView(withId(R.id.fab))
            .perform(click())
    }
}