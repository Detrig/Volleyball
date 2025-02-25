package com.example.avito2_0

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RegistrationViewModelTest {

    private lateinit var viewModel : RegistrationViewModel

    @Before
    fun setUp() {
        viewModel = RegistrationViewModel(repository = FakeAuthRepository())
    }

    @Test
    fun initialState() {
        val actual : RegistrationUiState = viewModel.init()
        val expected = RegistrationUiState.Initial()
        assertEquals(expected, actual)
    }

    @Test
    fun inputValid
}