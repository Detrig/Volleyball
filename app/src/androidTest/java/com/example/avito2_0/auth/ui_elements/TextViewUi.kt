package com.example.avito2_0.auth.ui_elements

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.avito2_0.auth.ui_elements.abstract.AbstractButton
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not

class TextViewUi(
    id: Int,
    text: Int,
    containerClassTypeMatcher: Matcher<View>,
    containerIdMatcher: Matcher<View>
) : AbstractButton(
    onView(
        allOf(
            withId(id),
            withText(text),
            containerIdMatcher,
            containerClassTypeMatcher
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
