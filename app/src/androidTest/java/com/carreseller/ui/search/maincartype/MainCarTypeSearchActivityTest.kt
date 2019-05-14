package com.carreseller.ui.search.maincartype

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.carreseller.BaseInstrumentationTest
import com.carreseller.R
import com.carreseller.domain.Manufacturer
import com.carreseller.utils.RecyclerViewItemCountAssertion
import com.carreseller.utils.RecyclerViewMatcher
import com.carreseller.utils.ViewIdlingResource.Companion.waitUntilViewIsDisplayed
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainCarTypeSearchActivityTest : BaseInstrumentationTest() {

    @get:Rule
    val activityRule = ActivityTestRule(MainCarTypeSearchActivity::class.java, false, false)

    private val mockServer = MockWebServer()

    @Before
    fun before() {
        mockServer.start(8080)
    }

    @After
    fun after() {
        mockServer.shutdown()
    }

    @Test
    fun launchActivity_ListHas15Items() {
        mockServer.setDispatcher(validDispatcher)
        activityRule.launchActivity(getIntent())
        onView(withId(R.id.rv_main_car_type)).check(RecyclerViewItemCountAssertion(15))
    }

    @Test
    fun launchActivityAndSwipeUp_ListHas20Items() {
        mockServer.setDispatcher(validDispatcher)
        activityRule.launchActivity(getIntent())
        onView(withId(R.id.rv_main_car_type)).perform(swipeUp())
        onView(withId(R.id.rv_main_car_type)).check(RecyclerViewItemCountAssertion(20))
    }

    @Test
    fun launchActivityAndCheckFirst3Items_ValuesAreAsExpected() {
        mockServer.setDispatcher(validDispatcher)
        activityRule.launchActivity(getIntent())
        onView(RecyclerViewMatcher(R.id.rv_main_car_type).atPositionOnView(0, R.id.tv_content)).check(matches(withText("1er")))
        onView(RecyclerViewMatcher(R.id.rv_main_car_type).atPositionOnView(1, R.id.tv_content)).check(matches(withText("2er")))
        onView(RecyclerViewMatcher(R.id.rv_main_car_type).atPositionOnView(2, R.id.tv_content)).check(matches(withText("3er")))
    }

    @Test
    fun launchActivityWithBrokenService_RetryIsShown() {
        mockServer.setDispatcher(errorDispatcher)
        activityRule.launchActivity(getIntent())
        waitUntilViewIsDisplayed(allOf(withId(com.google.android.material.R.id.snackbar_text), withText(activityRule.activity.getString(R.string.error_message))))
    }

    @Test
    fun launchActivityAndCheckBreadcumb_ManufacturerDisplayedAsExpected() {
        mockServer.setDispatcher(validDispatcher)
        activityRule.launchActivity(getIntent())
        onView(withId(R.id.tv_breadcumb_manufacturer)).check(matches(withText("BMW")))
    }

    @Test
    fun launchActivityAndClickOnEntry_OpenBuiltDateActivity() {
        mockServer.setDispatcher(validDispatcher)
        activityRule.launchActivity(getIntent())
        onView(allOf(withId(R.id.tv_content), withText("3er"))).perform(ViewActions.click())
        onView(withId(R.id.ll_built_date)).check(matches(ViewMatchers.isDisplayed()))
    }

    private fun getIntent(): Intent {
        return MainCarTypeSearchActivity.getIntent(InstrumentationRegistry.getInstrumentation().targetContext,
            Manufacturer("130", "BMW")
        )
    }
}


