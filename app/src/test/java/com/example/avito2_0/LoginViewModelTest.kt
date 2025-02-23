package com.example.avito2_0

import android.util.Patterns
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class LoginViewModelTest {

    private lateinit var viewModel : LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(repository = FakeRepository())
    }


    /**
     * LoginInitialState
     */
    @Test
    fun initialState() {
        val actual : LoginUiState = viewModel.init()
        val expected : LoginUiState = LoginUiState.Initial()
        assertEquals(expected, actual)
    }

    /**
     * Check input email and password
     */
    @Test
    fun inputValidEmailAndPasswordState() {
        val actual : LoginUiState = viewModel.inputLogin(mail = "alexzaitsev04@mail.ru", password = "pass_2")
        val expected = LoginUiState.Input(
            mail = "alexzaitsev04@mail.ru", password = "pass_2"
        )
        assertEquals(expected, actual)
    }

    /**
     * Valid email and password
     * -> LoadingState
     */
    @Test
    fun validEmailAndPasswordState() {
        val actual : LoginUiState = viewModel.login(
            mail = "alexzaitsev04@mail.ru", password = "pass_2"
        )
        val expected = LoginUiState.Loading()
        assertEquals(expected, actual)
    }
}

private class FakeAuthRepository : AuthRepository {

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


