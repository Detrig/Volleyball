package com.example.disputer.authentication.domain

import com.example.disputer.authentication.data.AuthRepository

class RegistrationUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String, isCoach: Boolean) =
        authRepository.register(email, password, isCoach)
}