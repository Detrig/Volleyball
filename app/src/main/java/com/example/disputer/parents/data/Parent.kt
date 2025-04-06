package com.example.disputer.parents.data

data class Parent(
    var uid: String = "",
    var name: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var image: String = "",
    var childIds: List<String> = emptyList()
)