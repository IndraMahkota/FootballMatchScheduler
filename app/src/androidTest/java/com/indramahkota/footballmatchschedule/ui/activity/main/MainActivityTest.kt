package com.indramahkota.footballmatchschedule.ui.activity.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.countRecyclerViewItem
import com.indramahkota.footballmatchschedule.ui.activity.favorite.FavoriteActivity
import com.indramahkota.footballmatchschedule.ui.activity.match.MatchActivity
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @Rule
    @JvmField
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun toActivityTestRecycleView() {
        onView(withId(R.id.main_rv))
            .check(matches(isDisplayed()))

        onView(withId(R.id.main_rv))
            .check(countRecyclerViewItem(10))

        onView(withId(R.id.main_rv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))

        onView(withId(R.id.main_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))

        intended(hasComponent(MatchActivity::class.java.name))
    }

    @Test
    fun toActivityTestFavoriteButton() {
        onView(withId(R.id.favorite_menu))
            .check(matches(isDisplayed()))

        onView(withId(R.id.favorite_menu))
            .perform(click())

        intended(hasComponent(FavoriteActivity::class.java.name))
    }
}