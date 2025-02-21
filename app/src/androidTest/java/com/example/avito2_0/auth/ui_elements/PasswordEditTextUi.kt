package com.example.avito2_0.auth.ui_elements

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.avito2_0.auth.ui_elements.abstract.AbstractEditText
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not

class PasswordEditTextUi(
    id: Int,
    text: String,
    hintResId: Int,
    containerClassTypeMatcher: Matcher<View>,
    containerIdMatcher: Matcher<View>
) : AbstractEditText(
    onView(
        allOf(
            withId(id),
            withText(text),
            withHint(hintResId),
            containerIdMatcher,
            containerClassTypeMatcher
        )
    )
) {
    fun assertErrorHighlight() {
        TODO("Not yet implemented")
    }
}



