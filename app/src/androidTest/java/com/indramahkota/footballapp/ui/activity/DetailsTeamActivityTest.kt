package com.indramahkota.footballapp.ui.activity

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.indramahkota.footballapp.AndroidTestFakeData.generateTeamEntity
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.ui.activity.DetailsTeamActivity.Companion.PARCELABLE_TEAM_DATA
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsTeamActivityTest {

    @Test
    fun toMatchDetailsActivityTest() {
        val data = generateTeamEntity()

        val activityRule = ActivityTestRule(DetailsTeamActivity::class.java)
        val intent = Intent().putExtra(PARCELABLE_TEAM_DATA, data)
        activityRule.launchActivity(intent)

        onView(withId(R.id.ivTeamLogo))
            .check(matches(isDisplayed()))
        onView(withId(R.id.ivTeamLogo))
            .perform(click())

        onView(withId(R.id.strTeam))
            .check(matches(isDisplayed()))
        onView(withId(R.id.strTeam))
            .perform(click())

        onView(withId(R.id.strDescription))
            .check(matches(isDisplayed()))
        onView(withId(R.id.strDescription))
            .perform(click())
    }
}