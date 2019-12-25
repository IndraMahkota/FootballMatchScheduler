package com.indramahkota.footballapp.ui.activity

import android.content.Intent
import android.view.KeyEvent
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.indramahkota.footballapp.AndroidTestFakeData.generateListMatchEntity
import com.indramahkota.footballapp.R
import com.indramahkota.footballapp.countRecyclerViewItem
import com.indramahkota.footballapp.ui.activity.SearchActivity.Companion.PARCELABLE_DATA
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

        onView(withId(R.id.search_src_text))
            .perform(
                typeText("Totten"),
                pressKey(KeyEvent.KEYCODE_ENTER)
            )
        onView(withId(R.id.rv_category))
            .check(countRecyclerViewItem(1))
        onView(withId(R.id.search_src_text))
            .perform(clearText())

        onView(withId(R.id.search_src_text))
            .perform(
                typeText("Southampton"),
                pressKey(KeyEvent.KEYCODE_ENTER)
            )
        onView(withId(R.id.rv_category))
            .check(countRecyclerViewItem(1))
        onView(withId(R.id.search_src_text))
            .perform(clearText())

        onView(withId(R.id.search_src_text))
            .perform(
                typeText("Leicester"),
                pressKey(KeyEvent.KEYCODE_ENTER)
            )
        onView(withId(R.id.rv_category))
            .check(countRecyclerViewItem(2))
        onView(withId(R.id.search_src_text))
            .perform(clearText())
    }
}