package com.example.disputer.parents

data class Parent(
    val uid: String,
    val name: String,
    val email: String,
    val phoneNumber: String = "",
    val childIds: List<String>,
    val trainings: List<String>
)