package com.example.avito2_0.auth.ui_elements

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not

class ProgressUi(
    id: Int,
    containerClassTypeMatcher: Matcher<View>,
    containerIdMatcher: Matcher<View>
) {

    private val interaction : ViewInteraction = onView(
        allOf(
            withId(id),
            containerIdMatcher,
            containerClassTypeMatcher
        )
    )

    fun assertNotLoading() {
        interaction.check(matches(not(isDisplayed())))
    }

    fun assertLoading() {
        interaction.check(matches(isDisplayed()))
    }

    fun simulateLoading(durationMs: Long = 3000) {
        interaction.perform(waitFor(durationMs))
    }

    private fun waitFor(durationMs: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isDisplayed()

            override fun getDescription(): String = "Ждем $durationMs миллисекунд"

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadForAtLeast(durationMs)
            }
        }
    }
}
