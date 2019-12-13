package com.indramahkota.footballmatchschedule.ui.activity.favorite

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.selectTabAtPosition
import org.junit.Rule
import org.junit.Test

class FavoriteActivityTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(FavoriteActivity::class.java)

    @Test
    fun toFavoriteActivityTest() {
        onView(ViewMatchers.withId(R.id.tabs))
            .check(matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.tabs)).perform(selectTabAtPosition(1))

        onView(ViewMatchers.withId(R.id.view_pager))
            .check(matches(ViewMatchers.isDisplayed()))
    }
}