package com.indramahkota.footballapp.ui.activity

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
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