package com.example.disputer.authentication.presentation.login

import androidx.lifecycle.ViewModel
import com.example.disputer.authentication.domain.IsLoggedInUseCase
import com.example.disputer.authentication.domain.LoginUseCase
import com.example.disputer.authentication.presentation.forgotpassword.ForgotPasswordScreen
import com.example.disputer.authentication.presentation.register.RegisterScreen
import com.example.disputer.core.Navigation
import com.example.disputer.training.presentation.info.InfoScreen
import com.example.disputer.training.presentation.main.TrainingMainScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val navigation : Navigation,
    private val loginUiStateLiveDataWrapper: LoginUiStateLiveDataWrapper,
    private val loginUseCase: LoginUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val viewModelScope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    fun init() {
        loginUiStateLiveDataWrapper.update(LoginUiState.Initial)
    }

    fun login(email: String, password: String) {

        loginUiStateLiveDataWrapper.update(LoginUiState.Loading)

        viewModelScope.launch(dispatcher) {
            try {
                loginUseCase.invoke(email, password)
                withContext(dispatcherMain) {
                    trainingsMainScreen()
                }
            } catch (e: Exception) {
                withContext(dispatcherMain) {
                    loginUiStateLiveDataWrapper.update(LoginUiState.Error(errorText = e.message))
                }
            }
        }
    }

//    fun onLoginSuccess() {
//        if (isLoggedInUseCase.invoke()) {
//            navigation.update(TrainingMainScreen)
//        }
//    }

    fun liveDataUiState() = loginUiStateLiveDataWrapper.liveData()

    fun trainingsMainScreen() = navigation.update(TrainingMainScreen)
    fun registerScreen() = navigation.update(RegisterScreen)
    fun forgotPasswordScreen() = navigation.update(ForgotPasswordScreen)
}