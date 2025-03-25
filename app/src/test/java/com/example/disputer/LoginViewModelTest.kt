package com.example.disputer

import com.example.disputer.authentication.presentation.login.LoginUiState
import com.example.disputer.authentication.presentation.login.LoginViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class LoginViewModelTest {

    private lateinit var viewModel : LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(authRepository = FakeAuthRepository())
    }


    /**
     * LoginInitialState
     */
    @Test
    fun initialState() {
        val actual : LoginUiState = viewModel.init()
        val expected : LoginUiState = LoginUiState.Initial
        assertEquals(expected, actual)
    }

    /**
     * Check input email and password
     */
//    @Test
//    fun inputCorrectEmailAndPasswordState() {
//        val actual : LoginUiState = viewModel.inputLogin(mail = "alexzaitsev04@mail.ru", password = "pass_2")
//        val expected = LoginUiState.Input(
//            mail = "alexzaitsev04@mail.ru", password = "pass_2"
//        )
//        assertEquals(expected, actual)
//    }

    /**
     * Correct email and password
     * -> LoadingState
     */
    @Test
    fun checkCorrectEmailAndPasswordState() {
        var actual : LoginUiState = viewModel.login(
            email = "alexzaitsev04@mail.ru", password = "pass_2"
        )
        var expected = LoginUiState.Loading
        assertEquals(expected, actual)


        actual = viewModel.isLoginSuccess()
        expected = LoginUiState.Success()
        assertEquals(expected, actual)
    }


    /**
     * Incorrect email or password
     * -> LoadingState
     * ->
     */
    @Test
    fun checkIncorrectEmailOrPasswordState() {
        val actual : LoginUiState = viewModel.login(
            email = "alexZ", password = "pass_2"
        )
        val expected : LoginUiState = LoginUiState.Loading()
        assertEquals(expected, actual)

        actual = viewModel.isLoginSuccess()
        expected = LoginUiState.Error(errorText = "Incorrect password or email")
        assertEquals(expected, actual)
    }

}




