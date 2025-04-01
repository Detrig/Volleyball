package com.example.disputer.auth.page_objects

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.example.disputer.R
import com.example.disputer.auth.ui_elements.ButtonUi
import com.example.disputer.auth.ui_elements.EmailEditTextUi
import com.example.disputer.auth.ui_elements.PasswordEditTextUi
import com.example.disputer.auth.ui_elements.TextViewUi
import com.example.disputer.auth.ui_elements.ToastUi
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

    fun checkRegistrationText(email: String, password: String, repeatPassword: String) {
        emailEditTextUi.withTextHere(email)
        passwordEditTextUi.withTextHere(password)
        repeatPasswordEditTextUi.withTextHere(repeatPassword)
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
        hintResId = R.string.repeatPasswordHint,
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val sendEmailUi = TextViewUi(
        id = R.id.textView,
        text = R.string.dontGetEmail,
        containerClassTypeMatcher,
        containerIdMatcher
    )

    private val registerButtonUi = ButtonUi(
        id = R.id.registerButton,
        text = R.string.register,
        colorHex = "#FF000000",
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
