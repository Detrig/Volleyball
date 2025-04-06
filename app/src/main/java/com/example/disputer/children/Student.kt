package com.example.disputer.children

import com.example.disputer.training.data.Training

data class Student(
    val uid: String,
    val name: String,
    val age: Int,
    val phoneNumber: String = "",
    val parentId: String,
    val trainings: List<Training>
)