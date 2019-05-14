package com.carreseller.utils

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.ViewFinder
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matcher

class ViewIdlingResource
/**
 * Constructor.
 *
 * @param viewMatcher The matcher to find the view.
 * @param idlerMatcher The matcher condition to be fulfilled to be considered idle.
 */
    (private val viewMatcher: Matcher<View>, private val idleMatcher: Matcher<View>) : IdlingResource {
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    /**
     * {@inheritDoc}
     */
    override fun isIdleNow(): Boolean {
        val view = getView(viewMatcher)
        val isIdle = idleMatcher.matches(view)

        if (isIdle && resourceCallback != null) {
            resourceCallback!!.onTransitionToIdle()
        }

        return isIdle
    }

    /**
     * {@inheritDoc}
     */
    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        this.resourceCallback = resourceCallback
    }

    /**
     * {@inheritDoc}
     */
    override fun getName(): String {
        return this.toString() + viewMatcher.toString()
    }

    companion object {

        /**
         * Tries to find the view associated with the given [<].
         */
        private fun getView(viewMatcher: Matcher<View>): View {
            try {
                val viewInteraction = onView(viewMatcher)
                val finderField = viewInteraction.javaClass.getDeclaredField("viewFinder")
                finderField.isAccessible = true
                val finder = finderField.get(viewInteraction) as ViewFinder
                return finder.view
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }

        fun waitUntilViewIsDisplayed(matcher: Matcher<View>) {
            val idlingResource = ViewIdlingResource(matcher, isDisplayed())
            try {
                IdlingRegistry.getInstance().register(idlingResource)
                // First call to onView is to trigger the idler.
                onView(withId(0)).check(doesNotExist())
            } finally {
                IdlingRegistry.getInstance().unregister(idlingResource)
            }
        }
    }
}
