package com.example.disputer.authentication.data

data class Student(
    val uid: String,
    val name: String,
    val age: Int,
    val phoneNumber: String = "",
    val trainings: List<Training>
)