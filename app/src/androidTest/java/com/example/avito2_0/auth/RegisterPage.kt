package com.example.avito2_0.auth

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.avito2_0.R
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher

class RegisterPage {

    fun assertRegistrationInitialState() {
        emailEditTextUi.assertEnable()
        passwordEditTextUi.assertEnable()
        repeatPasswordEditTextUi.assertEnable()
        sendEmailUi.assertEnable()
        registerButtonUi.assertEnable()
        loginTextViewUi.assertEnable()
    }

    fun inputRegistration(email: String, password: String, repeatPassword: String) {
        emailEditTextUi.input(email)
        passwordEditTextUi.input(password)
        repeatPasswordEditTextUi.input(repeatPassword)
    }

    fun clickRegisterButton() {
        registerButtonUi.click()
    }

    fun assertRegistrationErrorState(errorText: String) {
        toastUi.assertToastDisplayed(errorText)
        passwordEditTextUi.assertErrorHighlight()
    }


    private val containerIdMatcher : Matcher<View> = withParent(withId(R.id.rootLayout))
    private val containerClassTypeMatcher : Matcher<View> = withParent(isAssignableFrom(LinearLayout::class.java))

    private val emailEditTextUi = EmailEditTextUi(
        id = R.id.emailEditText,
        text = "",
        hintResId = R.string.emailEditTextHint,
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val passwordEditTextUi = PasswordEditTextUi(
        id = R.id.passwordEditText,
        text  = "",
        hintResId = R.string.passwordHint,
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val repeatPasswordEditTextUi = PasswordEditTextUi(
        id = R.id.repeatPasswordEditText,
        text  = "",
        hintResId = R.string.passwordHint,
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val sendEmailUi = TextViewUi(
        id = R.id.sendEmailTV,
        text = R.string.dontGetEmail,
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val registerButtonUi = ButtonUi(
        id = R.id.registerButton,
        text = R.string.register,
        colorHex = "#000000",
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val loginTextViewUi = TextViewUi(
        id = R.id.loginTV,
        text = R.string.login,
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val toastUi = ToastUi()

}
