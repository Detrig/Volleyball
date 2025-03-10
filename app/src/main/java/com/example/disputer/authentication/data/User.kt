package com.example.disputer.authentication.data

data class User(
    val uid: String,
    val email: String,
    val password : String? = null,
    val isAdmin: Boolean = false
)
