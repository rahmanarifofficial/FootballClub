package com.rahmanarif.footballclub

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.rahmanarif.footballclub.view.activity.MainActivity
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EventTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        val recyclerView = Espresso.onView(CoreMatchers.allOf(ViewMatchers.isDisplayed(), ViewMatchers.withId(R.id.next_event)))
        recyclerView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        recyclerView.perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, ViewActions.click()))

        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        Espresso.pressBack()
    }

    @Test
    fun testAppBehaviour() {
        val recyclerView = Espresso.onView(CoreMatchers.allOf(ViewMatchers.isDisplayed(), ViewMatchers.withId(R.id.next_event)))
        recyclerView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        recyclerView.perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())

        Espresso.pressBack()

        Espresso.onView(ViewMatchers.withId(R.id.bottom_navigation))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.favorite_menu)).perform(ViewActions.click())

    }
}