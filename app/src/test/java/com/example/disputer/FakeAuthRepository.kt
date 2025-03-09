package com.example.disputer

import com.example.disputer.core.Result
import com.example.disputer.authentication.data.AuthRepository
import com.example.disputer.data.User

internal class FakeAuthRepository : AuthRepository {

    private val userList : MutableList<User> = mutableListOf(
        User("alexzaitsev04@mail.ru", "pass_2"),
        User("test@mail.ru", "pass_3")
    )


    override fun login(mail : String, password : String) : Result {
        val user = userList.find { it.email == mail }
        return if (user != null && user.password == password) {
            Result.Success
        } else {
            Result.Error("Invalid credential")
        }
    }

    override fun register(mail: String, password: String, repeatPassword: String): Result {
        val existingUser = userList.find { it.email == mail }

        if (existingUser != null) {
            return Result.Error("User already exists")
        }

        if (password != repeatPassword) {
            return Result.Error("Passwords don't match")
        }

        if (!isValidPassword(password)) {
            return Result.Error("Password must be at least 6 characters long and contain at least one special character")
        }

        if (!isValidEmail(mail)) {
            return Result.Error("Invalid email format")
        }

        userList.add(User(mail, password))
        return Result.Success
    }

    override fun forgotPassword(mail : String) : Result {
        val user = userList.find { it.email == mail }
        return if (user != null) {
            Result.Success
        } else {
            Result.Error("Email not found")
        }
    }
}