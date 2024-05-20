package com.example.europrofile

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class AccountFragmentTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkAccountFragment() {
        onView(withId(R.id.tab_layout))
        onView(withId(R.id.news_pager))
        onView(withId(R.id.work_examples))
    }

}