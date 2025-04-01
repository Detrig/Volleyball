package com.example.disputer.authentication.data

data class Coach(
    val uid: String,
    val name: String,
    val phoneNumber: String,
    val qualification: String,
    val email: String,
    val imageUrl: String = "",
    val trainings: List<Training> = arrayListOf(),
    val students: List<Student> = arrayListOf(),
    val address: List<String> = arrayListOf(),
    val telegram: String = ""
)