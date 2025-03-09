package com.example.disputer.authentication.domain

import com.example.disputer.authentication.data.UserRepository

class IsEmailVerifiedUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke() = userRepository.isEmailVerified()
}