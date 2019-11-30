package com.swensun.potato

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

/**
 * @author sunwen
 * @Date 2019-11-30
 * @Project Potato
 */
class MainActivityTest {

    @Rule @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun findHello() {
        onView(withId(R.id.hello))
            .check(matches(withText("hello")))
        onView(withId(R.id.hello)).perform(click()).check(matches(withText("hello")))

    }
}