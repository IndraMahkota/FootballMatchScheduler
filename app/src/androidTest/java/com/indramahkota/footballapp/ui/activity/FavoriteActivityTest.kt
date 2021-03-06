package com.indramahkota.footballapp.ui.activity

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.selectTabAtPosition
import org.junit.Rule
import org.junit.Test

class FavoriteActivityTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(FavoriteActivity::class.java)

    @Test
    fun toFavoriteActivityTest() {
        onView(withId(R.id.tabs))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(1))

        onView(withId(R.id.view_pager))
            .check(matches(isDisplayed()))
    }
}