package com.example.disputer.core

import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.presentation.login.LoginViewModel
import com.example.disputer.authentication.data.AuthRepository
import com.example.disputer.authentication.data.PasswordRepository
import com.example.disputer.authentication.domain.ForgotPasswordUseCase
import com.example.disputer.authentication.domain.LoginUseCase
import com.example.disputer.authentication.domain.RegistrationUseCase
import com.example.disputer.authentication.presentation.forgotpassword.ForgotPasswordUiStateLiveDataWrapper
import com.example.disputer.authentication.presentation.login.LoginUiStateLiveDataWrapper
import com.example.disputer.authentication.presentation.register.RegisterUiStateLiveDataWrapper
import com.example.disputer.authentication.presentation.forgotpassword.ForgotPasswordViewModel
import com.example.disputer.authentication.presentation.main.MainViewModel
import com.example.disputer.authentication.presentation.register.RegisterViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T

    class Base(
        private val clear: ClearViewModel
    ) : ProvideViewModel {
        private val fireBaseAuth = Firebase.auth
        private val fireBaseFirestore = Firebase.firestore
        private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        private val navigation = Navigation.Base()

        private val authRepository = AuthRepository.Base(fireBaseAuth, fireBaseFirestore)
        private val passwordRepository = PasswordRepository.Base(fireBaseAuth)

        private val loginUiStateLiveDataWrapper = LoginUiStateLiveDataWrapper.Base()
        private val loginUseCase = LoginUseCase(authRepository)

        private val registerUiStateLiveDataWrapper = RegisterUiStateLiveDataWrapper.Base()
        private val registerUseCase = RegistrationUseCase(authRepository)

        private val forgotPasswordUiStateLiveDataWrapper = ForgotPasswordUiStateLiveDataWrapper.Base()
        private val forgotPasswordUseCase = ForgotPasswordUseCase(passwordRepository)


        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return when (viewModelClass) {
                LoginViewModel::class.java -> LoginViewModel(
                    navigation,
                    loginUiStateLiveDataWrapper,
                    loginUseCase,
                    viewModelScope
                )
                MainViewModel::class.java -> MainViewModel(navigation)
                RegisterViewModel::class.java -> RegisterViewModel(
                    navigation,
                    registerUiStateLiveDataWrapper,
                    registerUseCase,
                    viewModelScope
                )
                ForgotPasswordViewModel::class.java -> ForgotPasswordViewModel(
                    navigation,
                    clear,
                    forgotPasswordUiStateLiveDataWrapper,
                    forgotPasswordUseCase,
                    viewModelScope
                )
                else -> throw IllegalStateException("unknown viewModelClass $viewModelClass")
            } as T

        }
    }
}