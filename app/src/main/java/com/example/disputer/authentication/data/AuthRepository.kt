package com.example.disputer.authentication.data

import android.util.Patterns
import com.example.disputer.core.Result
import com.example.disputer.data.User

interface AuthRepository {

    fun login(mail: String, password: String): Result

    fun register(
        mail: String,
        password: String,
        repeatPassword: String
    ): Result

    fun forgotPassword(mail: String): Result

    class Base(
        private val userList : MutableList<User> = mutableListOf(
            User("alexzaitsev04@mail.ru", "pass_2"),
            User("test@mail.ru", "pass_3")
        )
    ) : AuthRepository {

        override fun login(mail: String, password: String): Result {
            val user = User(mail, password)

            userList.forEach {
                if (it == user)
                    return Result.Success
            }

            return Result.Error("Incorrect password or email")
        }

        override fun register(mail: String, password: String, repeatPassword: String): Result {
            TODO("Not yet implemented")
        }

        override fun forgotPassword(mail: String): Result {
            TODO("Not yet implemented")
        }
    }


    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.contains("@") && email.substringAfter("@").contains(".")
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6 && password.any { !it.isLetterOrDigit() }
    }
}
