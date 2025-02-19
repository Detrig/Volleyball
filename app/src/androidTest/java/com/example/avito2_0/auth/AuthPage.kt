package com.example.avito2_0.auth

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.avito2_0.R
import org.hamcrest.Matcher

class AuthPage(authMap : Map<String, String>) { //email/password

    private val containerIdMatcher : Matcher<View> = withParent(withId(R.id.rootLayout))

    private val emailEditTextUi = EmailEditTextUi(
        text = "",
        hintResId = R.string.emailEditTextHint,
        containerIdMatcher
    )
}