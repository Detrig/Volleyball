//package com.example.disputer
//
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Test
//
//class RegistrationViewModelTest {
//
//    private lateinit var viewModel : RegistrationViewModel
//
//    @Before
//    fun setUp() {
//        viewModel = RegistrationViewModel(authRepository = FakeAuthRepository())
//    }
//
//    @Test
//    fun initialState() {
//        val actual : RegistrationUiState = viewModel.init()
//        val expected = RegistrationUiState.Initial()
//        assertEquals(expected, actual)
//    }
//
////    @Test
////    fun inputValidEmailAndPasswordState() {
////        val actual : RegistrationUiState = viewModel.inputRegistration(
////            email = "alexzaitsev04@mail.ru",
////            password = "pass_2"
////        )
////        val expected = RegistrationUiState.Input(
////            email = "alexzaitsev04@mail.ru",
////            password = "pass_2"
////        )
////        assertEquals(expected, actual)
////    }
//
//    @Test
//    fun checkValidEmailAndPasswordState() {
//        val actual : RegistrationUiState = viewModel.register(
//            email = "alexzaitsev04@mail.ru",
//            password = "pass_2",
//            repeatPassword = "pass_2"
//        )
//        val expected = RegistrationUiState.Success()
//        assertEquals(expected, actual)
//    }
//
//    @Test
//    fun checkInvalidEmailAndPasswordState() {
//        val actual : RegistrationUiState = viewModel.register(
//            email = "test@mail.ru",
//            password = "pass3",
//            repeatPassword = "pass3"
//        )
//        val expected = RegistrationUiState.Error("Password do not meet requirements")
//        assertEquals(expected, actual)
//    }
//}