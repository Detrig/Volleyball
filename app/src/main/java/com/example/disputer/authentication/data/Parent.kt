package com.example.disputer.authentication.data

data class Parent(
    val uid: String,
    val name: String,
    val childs: List<Student>,
    val email: String,
    val phoneNumber: String,
    val trainings: List<Training>
)