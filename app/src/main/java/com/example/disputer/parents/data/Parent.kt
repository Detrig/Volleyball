package com.example.disputer.parents.data

data class Parent(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val image: String = "",
    val childIds: List<String> = emptyList(),
    val trainings: List<String> = emptyList()
)