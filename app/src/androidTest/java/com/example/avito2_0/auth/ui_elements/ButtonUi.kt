package com.example.avito2_0.auth.ui_elements

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.avito2_0.auth.ButtonColorMatcher
import com.example.avito2_0.auth.ui_elements.abstract.AbstractButton
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not

class ButtonUi(
    id: Int,
    text: Int,
    colorHex: String,
    containerClassTypeMatcher: Matcher<View>,
    containerIdMatcher: Matcher<View>) : AbstractButton(
        onView(
            allOf(
                withId(id),
                withText(text),
                containerIdMatcher,
                containerClassTypeMatcher,
                ButtonColorMatcher(colorHex)
            )
        )
    ) {

    fun assertEnable() {
        interaction.check(matches(isEnabled()))
    }

    fun assertDisable() {
        interaction.check(matches(not(isEnabled())))
    }
}


