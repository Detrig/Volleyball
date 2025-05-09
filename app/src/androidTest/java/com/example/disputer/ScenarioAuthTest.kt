//package com.example.disputer
//
//import androidx.test.ext.junit.rules.ActivityScenarioRule
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.disputer.auth.page_objects.ForgotPasswordPage
//import com.example.disputer.auth.page_objects.LoginPage
//import com.example.disputer.auth.page_objects.RegisterPage
//import com.example.disputer.authentication.presentation.main.MainActivity
//import org.junit.Before
//
//import org.junit.Test
//import org.junit.runner.RunWith
//
//import org.junit.Rule
//
//
//@RunWith(AndroidJUnit4::class)
//class ScenarioAuthTest {
//
//    @get:Rule
//    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
//
//    private lateinit var loginPage : LoginPage;
//    private lateinit var registerPage : RegisterPage;
//    private lateinit var forgotPasswordPage: ForgotPasswordPage;
//
//    @Before
//    fun setup() {
//        val authMap : Map<String, String> = mapOf(
//            Pair("alexzaitsev04@mail.ru", "pass_2"),
//            Pair("test@mail.ru", "pass_2")
//        )
//        loginPage = LoginPage(authMap)
//    }
//    /**
//       * happy pass
//     */
//    @Test
//    fun testcase1() {
//        loginPage.assertLoginInitialState()
//
//        loginPage.clickRegisterTextView()
//        registerPage.assertRegistrationInitialState()
//
//        registerPage.inputRegistration(email = "alexzaitsev04@mail.ru", password = "pass_2", repeatPassword = "pass_2")
//        registerPage.clickRegisterButton()
//        loginPage.assertLoginInitialState()
//
//        loginPage.inputLogin(mail = "alexzaitsev04@mail.ru", password = "pass_2")
//        loginPage.clickLoginButton()
//        loginPage.assertLoadingState()
//       // loginPage.assertAdvertisementInitialState()
//    }
//
//    /**
//     * forgot password
//     */
//    @Test
//    fun testcase2() {
//        loginPage.assertLoginInitialState()
//
//        loginPage.clickForgotPassTextView()
//        forgotPasswordPage.assertForgotPasswordInitialState()
//
//        forgotPasswordPage.inputRestore(email = "alexzaitsev04@")
//        forgotPasswordPage.assertForgotPasswordErrorState(errorText = "Incorrect email")
//
//        forgotPasswordPage.inputRestore(email = "ZOV@yandex.com")
//        forgotPasswordPage.assertForgotPasswordErrorState(errorText = "Account not found")
//
//        forgotPasswordPage.inputRestore(email = "alexzaitsev04@mail.ru")
//        loginPage.assertLoginInitialState()
//    }
//
//    /**
//     * unsuccess log in
//     */
//    @Test
//    fun testcase3() {
//        loginPage.assertLoginInitialState()
//
//        loginPage.inputLogin(mail = "alexZ", password = "pass_2")
//        loginPage.clickLoginButton()
//        loginPage.assertLoadingState()
//        loginPage.assertLoginErrorState(errorText = "Incorrect password or email")
//
//        loginPage.clickRegisterTextView()
//        registerPage.assertRegistrationInitialState()
//
//        registerPage.inputRegistration(email = "test@mail.ru", password = "pass_3", repeatPassword = "pass_2")
//        activityScenarioRule.scenario.recreate()
//        registerPage.checkRegistrationText(email = "test@mail.ru", password = "pass_3", repeatPassword = "pass_2")
//        registerPage.clickRegisterButton()
//        registerPage.assertRegistrationErrorState(errorText = "password don't match")
//
//        registerPage.inputRegistration(email = "test@mail.ru", password = "pass3", repeatPassword = "pass3")
//        registerPage.clickRegisterButton()
//        registerPage.assertRegistrationErrorState(errorText = "Password do not meet requirements")
//
//        registerPage.inputRegistration(email = "test@mail.ru", password = "pass_3", repeatPassword = "pass_3")
//        registerPage.clickRegisterButton()
//        loginPage.assertLoginInitialState()
//    }
//}