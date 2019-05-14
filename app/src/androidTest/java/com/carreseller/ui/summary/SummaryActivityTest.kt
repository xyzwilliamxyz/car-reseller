package com.carreseller.ui.summary

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.carreseller.BaseInstrumentationTest
import com.carreseller.R
import com.carreseller.domain.BuiltDate
import com.carreseller.domain.MainCarType
import com.carreseller.domain.Manufacturer
import org.junit.Rule
import org.junit.Test

class SummaryActivityTest : BaseInstrumentationTest() {

    @get:Rule
    val activityRule = ActivityTestRule(SummaryActivity::class.java, false, false)

    @Test
    fun launchActivityAndCheckSummaryData_DataIsDisplayedAsExpected() {
        activityRule.launchActivity(getIntent())
        onView(withId(R.id.tv_manufacturer)).check(matches(withText("BMW")))
        onView(withId(R.id.tv_main_car_type)).check(matches(withText("Z3")))
        onView(withId(R.id.tv_built_date)).check(matches(withText("1996")))
    }

    private fun getIntent(): Intent {
        return SummaryActivity.getIntent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            Manufacturer("130", "BMW"), MainCarType("Z3"), BuiltDate("1996")
        )
    }
}
