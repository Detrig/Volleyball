package com.example.disputer

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ForgotPasswordViewModelTest {

    private lateinit var viewModel : ForgotPasswordViewModel

    @Before
    fun setUp() {
        viewModel = ForgotPasswordViewModel(repository = FakeAuthRepository())
    }

    /**
     * ForgotPasswordInitialState
     */
    @Test
    fun initialState() {
        val actual : ForgotPasswordUiState = viewModel.init()
        val expected = ForgotPasswordUiState.Initial()
        assertEquals(expected, actual)
    }

    @Test
    fun checkValidEmail() {
        val actual : ForgotPasswordUiState = viewModel.forgotPassword(
            email = "alexzaitsev04@mail.ru"
        )
        val expected = ForgotPasswordUiState.Success()
        assertEquals(expected, actual)
    }

    @Test
    fun checkInvalidEmail() {
        val actual : ForgotPasswordUiState = viewModel.forgotPassword(
            email = "alexzaitsev04@"
        )
        val expected = ForgotPasswordUiState.Error("Incorrect email")
        assertEquals(expected, actual)


        actual = viewModel.forgotPassword(
            email = "ZOV@yandex.com"
        )
        expected = ForgotPasswordUiState.Error("Account not found")
        assertEquals(expected, actual)
    }

}