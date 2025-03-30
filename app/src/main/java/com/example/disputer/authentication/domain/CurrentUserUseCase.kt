package com.example.disputer.authentication.domain

import com.example.disputer.authentication.data.UserRepository

class CurrentUserUseCase(private val userRepository: UserRepository) {

    operator fun invoke() = userRepository.getCurrentUser()
}