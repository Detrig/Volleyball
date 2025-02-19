package com.example.avito2_0

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.avito2_0.auth.AuthPage
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class ScenarioAuthTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var authPage : AuthPage;

    @Before
    fun setup() {
        val authMap : Map<String, String> = mapOf(
            Pair("alexzaitsev04@mail.ru", "pass_2"),
            Pair("test@mail.ru", "pass_2")
        )
        authPage = AuthPage(authMap)
    }
    /**
       * happy pass
     */
    @Test
    fun testcase1() {
        authPage.assertLoginInitialState()

        authPage.clickRegisterTextView()
        authPage.assertRegistrationInitialState()

        authPage.inputRegistration(mail = "alexzaitsev04@mail.ru", password = "pass_2", repeatPassword = "pass_2")
        authPage.clickRegisterButton()
        authPage.asertLoginInitialState()

        authPage.inputLogin(mail = "alexzaitsev04@mail.ru", password = "pass_2")
        authPage.clickLoginButton()
        authPage.assertAdvertisementInitialState()
    }

    /**
     * forgot password
     */
    @Test
    fun testcase2() {
        authPage.assertLoginInitialState()

        authPage.clickForgotPassTextView()
        authPage.assertForgotPasswordInitialState()

        authPage.inputRestore(email = "alexzaitsev04@")
        authPage.assertForgotPasswordErrorState(errorText = "Incorrect email")

        authPage.inputRestore(email = "alexzaitsev04@mail.ru")
        authPage.assertLoginInitialState()
    }

    /**
     * unsuccess log in
     */
    @Test
    fun testcase3() {
        authPage.assertLoginInitialState()

        authPage.inputLogin(email = "alexZ", password = "pass_2")
        authPage.assertLoginErrorState(errorText = "incorrect password or email")

        authPage.clickRegisterTextView()
        authPage.assertRegistrationInitialState()

        authPage.inputRegistration(mail = "test@mail.ru", password = "pass_3", repeatPassword = "pass_2")
        authPage.clickRegisterButton()
        authPage.assertRegistrationErrorState(errorText = "password don't match")

        authPage.inputRegistration(mail = "test@mail.ru", password = "pass3", repeatPassword = "pass3")
        authPage.clickRegisterButton()
        authPage.assertRegistrationErrorState(errorText = "password do not meet requirements")

        authPage.inputRegistration(mail = "test@mail.ru", password = "pass_3", repeatPassword = "pass_3")
        authPage.clickRegisterButton()
        authPage.assertLoginInitialState()
    }
}