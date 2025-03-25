package com.example.disputer.authentication.data

data class Child(
    val uid: String,
    val name: String,
    val age: Int,
    val phoneNumber: String = "",
    val trainings: List<Training>
)