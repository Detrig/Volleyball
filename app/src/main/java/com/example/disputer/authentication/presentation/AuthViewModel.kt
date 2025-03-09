package com.example.disputer.authentication.presentation

import com.example.disputer.authentication.domain.LoginUseCase
import com.example.disputer.authentication.domain.LogoutUseCase
import com.example.disputer.authentication.domain.RegistrationUseCase

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegistrationUseCase,
    private val logoutUseCase: LogoutUseCase,
) {
}