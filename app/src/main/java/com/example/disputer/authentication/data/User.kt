package com.example.disputer.authentication.data

data class User(
    val uid: String,
    val email: String,
    val password : String? = null,
    var isCoach: Boolean = false,
    var isParent: Boolean = true
)
