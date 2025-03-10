package com.example.disputer.authentication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.presentation.state.login.LoginUiState
import com.example.disputer.authentication.domain.LoginUseCase
import com.example.disputer.authentication.presentation.state.login.LoginUiStateLiveDataWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val loginUiStateLiveDataWrapper: LoginUiStateLiveDataWrapper,
    private val loginUseCase: LoginUseCase,
    private val viewModelScope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    fun init(): LoginUiState {
        return LoginUiState.Initial
    }

    fun login(email: String, password: String) {

        loginUiStateLiveDataWrapper.update(LoginUiState.Loading)

        viewModelScope.launch(dispatcher) {
            try {
                loginUseCase.invoke(email, password)
            } catch (e: Exception) {
                withContext(dispatcherMain) {
                    loginUiStateLiveDataWrapper.update(LoginUiState.Error(errorText = e.message))
                }
            }
        }
    }

    fun liveDataUiState() = loginUiStateLiveDataWrapper.liveData()
}