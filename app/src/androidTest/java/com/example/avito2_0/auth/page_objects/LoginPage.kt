package com.example.avito2_0.auth.page_objects

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.avito2_0.R
import com.example.avito2_0.auth.ui_elements.ButtonUi
import com.example.avito2_0.auth.ui_elements.EmailEditTextUi
import com.example.avito2_0.auth.ui_elements.PasswordEditTextUi
import com.example.avito2_0.auth.ui_elements.ProgressUi
import com.example.avito2_0.auth.ui_elements.TextViewUi
import com.example.avito2_0.auth.ui_elements.ToastUi
import org.hamcrest.Matcher

class LoginPage(authMap : Map<String, String>) {  // email/password

    fun assertLoginInitialState() {
        emailEditTextUi.assertEnable()
        passwordEditTextUi.assertEnable()
        registerTextView.assertEnable()
        loginButton.assertEnable()
        forgotPasswordTextView.assertEnable()
        progressUi.assertNotLoading()
    }

    fun clickRegisterTextView() {
        registerTextView.click()
    }

    fun inputLogin(mail: String, password: String) {
        emailEditTextUi.input(mail)
        passwordEditTextUi.input(password)
    }

    fun clickLoginButton() {
        loginButton.click()
    }

    fun clickForgotPassTextView() {
        forgotPasswordTextView.click()
    }

    fun assertLoginErrorState(errorText: String) {
        toastUi.assertToastDisplayed(errorText)
        progressUi.assertNotLoading()
    }

    fun assertLoadingState() {
        emailEditTextUi.assertDisable()
        passwordEditTextUi.assertDisable()
        registerTextView.assertDisable()
        loginButton.assertDisable()
        forgotPasswordTextView.assertDisable()

        progressUi.assertLoading()
        progressUi.simulateLoading(3000)
        progressUi.assertNotLoading()
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

    private val loginButton = ButtonUi(
        id = R.id.loginButton,
        text = R.string.login,
        colorHex = "#000000",
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val registerTextView = TextViewUi(
        id = R.id.registerTV,
        text = R.string.register,
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val forgotPasswordTextView = TextViewUi(
        id = R.id.forgotPasswordTV,
        text = R.string.forgotPassword,
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val progressUi = ProgressUi(
        id = R.id.progressBar,
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val toastUi = ToastUi()

}