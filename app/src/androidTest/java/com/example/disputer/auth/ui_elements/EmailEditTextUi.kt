package com.example.disputer.auth.ui_elements

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.disputer.auth.ui_elements.abstract.AbstractEditText
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class EmailEditTextUi(
    id : Int,
    text: String,
    hintResId: Int,
    containerClassTypeMatcher : Matcher<View>,
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
)
