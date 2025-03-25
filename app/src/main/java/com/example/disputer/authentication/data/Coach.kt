package com.example.disputer.authentication.data

data class Coach(
    val uid: String,
    val name: String,
    val trainings: List<Training>,
    val childs: List<Child>,
    val address: List<String>,
    val email: String,
    val phoneNumber: String,
    val telegram: String
)