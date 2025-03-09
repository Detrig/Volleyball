package com.example.disputer.auth.ui_elements.abstract

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.not

abstract class AbstractEditText(
    protected val interaction : ViewInteraction
) {
    fun input(text : String) {
        interaction
            .perform(clearText())
            .perform(click())
            .perform(typeText(text))
            .perform(closeSoftKeyboard())
            .check(matches(withText(text)))
    }

    fun assertEnable() {
        interaction.check(matches(isEnabled()))
    }

    fun assertDisable() {
        interaction.check(matches(not(isEnabled())))
    }

    fun withTextHere(text: String) {
        interaction.check(matches(withText(text)))
    }
}