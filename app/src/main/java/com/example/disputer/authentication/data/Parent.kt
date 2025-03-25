package com.example.disputer.authentication.data

data class Parent(
    val uid: String,
    val name: String,
    val childs: List<Child>,
    val email: String,
    val phoneNumber: String,
    val trainings: List<Training>
)