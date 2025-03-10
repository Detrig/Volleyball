package com.example.disputer.core

import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.presentation.viewmodel.LoginViewModel
import com.example.disputer.authentication.data.AuthRepository
import com.example.disputer.authentication.domain.LoginUseCase
import com.example.disputer.authentication.presentation.state.login.LoginUiStateLiveDataWrapper
import com.example.disputer.authentication.presentation.viewmodel.MainViewModel
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
        private val navigation = Navigation.Base()

        private val authRepository = AuthRepository.Base(fireBaseAuth, fireBaseFirestore)
        private val loginUiStateLiveDataWrapper = LoginUiStateLiveDataWrapper.Base()
        private val loginUseCase = LoginUseCase(authRepository)
        private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)



        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return when (viewModelClass) {
                LoginViewModel::class.java -> LoginViewModel(loginUiStateLiveDataWrapper, loginUseCase, viewModelScope)
                MainViewModel::class.java -> MainViewModel(navigation)

                else -> throw IllegalStateException("unknown viewModelClass $viewModelClass")
            } as T

        }
    }
}