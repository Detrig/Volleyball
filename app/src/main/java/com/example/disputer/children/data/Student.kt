package com.example.disputer.children.data

import com.example.disputer.training.data.Training

data class Student(
    val uid: String = "",
    val name: String = "",
    val age: Int = 6,
    val phoneNumber: String = "",
    val parentId: String = "",
    val trainingIds: List<String> = listOf<String>(),
    val photoBase64: String = ""
)