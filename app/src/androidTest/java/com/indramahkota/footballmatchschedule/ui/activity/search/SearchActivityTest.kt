package com.indramahkota.footballmatchschedule.ui.activity.search

import android.content.Intent
import android.view.KeyEvent
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.indramahkota.footballmatchschedule.AndroidTestFakeData.generateListMatchEntity
import com.indramahkota.footballmatchschedule.R
import com.indramahkota.footballmatchschedule.countRecyclerViewItem
import com.indramahkota.footballmatchschedule.ui.activity.search.SearchActivity.Companion.PARCELABLE_DATA
import org.junit.Test

class SearchActivityTest {

    @Test
    fun toSearchActivityTest() {
        val data = generateListMatchEntity()

        val activityRule = ActivityTestRule(SearchActivity::class.java)
        val intent = Intent().putParcelableArrayListExtra(PARCELABLE_DATA, data)
        activityRule.launchActivity(intent)

        onView(withId(R.id.no_data))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rv_category))
            .check(matches(isDisplayed()))

        onView(withId(R.id.search_menu))
            .perform(click())

        Thread.sleep(100L)

        onView(withId(R.id.search_src_text))
            .perform(
                typeText("Totten"),
                pressKey(KeyEvent.KEYCODE_ENTER)
            )
        onView(withId(R.id.rv_category))
            .check(countRecyclerViewItem(1))

        Thread.sleep(1000L)
        onView(withId(R.id.search_src_text))
            .perform(
                clearText()
            )

        onView(withId(R.id.search_src_text))
            .perform(
                typeText("Southampton"),
                pressKey(KeyEvent.KEYCODE_ENTER)
            )
        onView(withId(R.id.rv_category))
            .check(countRecyclerViewItem(1))

        Thread.sleep(1000L)
        onView(withId(R.id.search_src_text))
            .perform(
                clearText()
            )

        onView(withId(R.id.search_src_text))
            .perform(
                typeText("Leicester"),
                pressKey(KeyEvent.KEYCODE_ENTER)
            )
        onView(withId(R.id.rv_category))
            .check(countRecyclerViewItem(2))

        Thread.sleep(1000L)
    }
}