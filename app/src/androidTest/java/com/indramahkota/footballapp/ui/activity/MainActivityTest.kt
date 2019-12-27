package com.indramahkota.footballapp.ui.activity

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
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.countRecyclerViewItem
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @Rule
    @JvmField
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun toActivityTestRecycleView() {
        onView(withId(R.id.rv_league))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rv_league))
            .check(countRecyclerViewItem(10))

        onView(withId(R.id.rv_league)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))

        onView(withId(R.id.rv_league)).perform(
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