package com.carreseller.ui.search.manufacturer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.carreseller.BaseInstrumentationTest
import com.carreseller.R
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
class ManufacturerSearchActivityTest : BaseInstrumentationTest() {

    @get:Rule
    val activityRule = ActivityTestRule(ManufacturerSearchActivity::class.java, false, false)

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
        activityRule.launchActivity(null)
        onView(withId(R.id.rv_manufacturers)).check(RecyclerViewItemCountAssertion(15))
    }

    @Test
    fun launchActivityAndSwipeUp_ListHas30Items() {
        mockServer.setDispatcher(validDispatcher)
        activityRule.launchActivity(null)
        onView(withId(R.id.rv_manufacturers)).perform(swipeUp())
        onView(withId(R.id.rv_manufacturers)).check(RecyclerViewItemCountAssertion(30))
    }

    @Test
    fun launchActivityAndCheckFirst3Items_ValuesAreAsExpected() {
        mockServer.setDispatcher(validDispatcher)
        activityRule.launchActivity(null)
        onView(RecyclerViewMatcher(R.id.rv_manufacturers).atPositionOnView(0, R.id.tv_content)).check(matches(withText("Bentley")))
        onView(RecyclerViewMatcher(R.id.rv_manufacturers).atPositionOnView(1, R.id.tv_content)).check(matches(withText("BMW")))
        onView(RecyclerViewMatcher(R.id.rv_manufacturers).atPositionOnView(2, R.id.tv_content)).check(matches(withText("Buick")))
    }

    @Test
    fun launchActivityAndClickOnEntry_OpenMainCarTypeActivity() {
        mockServer.setDispatcher(validDispatcher)
        activityRule.launchActivity(null)
        onView(allOf(withId(R.id.tv_content), withText("BMW"))).perform(click())
        onView(withId(R.id.ll_main_car_type)).check(matches(isDisplayed()))
    }

    @Test
    fun launchActivityWithBrokenService_RetryIsShown() {
        mockServer.setDispatcher(errorDispatcher)
        activityRule.launchActivity(null)
        waitUntilViewIsDisplayed(allOf(withId(com.google.android.material.R.id.snackbar_text), withText(activityRule.activity.getString(R.string.error_message))))
    }
}


