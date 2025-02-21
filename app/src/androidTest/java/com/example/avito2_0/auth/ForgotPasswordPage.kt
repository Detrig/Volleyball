package com.example.avito2_0.auth

import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.avito2_0.R

class ForgotPasswordPage {

    private val containerIdMatcher = withParent(withId(R.id.rootLayout))
    private val containerClassTypeMatcher = withParent(isAssignableFrom(LinearLayout::class.java))

    fun assertForgotPasswordInitialState() {
        emailEditTextUi.assertEnable()
        recoverPasswordButtonUi.assertEnable()
        loginTextViewUi.assertEnable()
        registerTextView.assertEnable()
    }

    fun inputRestore(email: String) {
        emailEditTextUi.input(email)
    }

    fun assertForgotPasswordErrorState(errorText: String) {
        toastUi.assertToastDisplayed(errorText)
    }

    private val emailEditTextUi = EmailEditTextUi(
        id = R.id.emailEditText,
        text = "",
        hintResId = R.string.emailEditTextHint,
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val recoverPasswordButtonUi = ButtonUi(
        id = R.id.recoverPasswordButton,
        text = R.string.recoverPassword,
        colorHex = "#000000",
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val loginTextViewUi = TextViewUi(
        id = R.id.loginTv,
        text = R.string.login,
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val registerTextView = TextViewUi(
        id = R.id.registerTV,
        text = R.string.register,
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val toastUi = ToastUi()
}