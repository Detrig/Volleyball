package com.example.disputer.coach.data

import com.example.disputer.children.Student
import com.example.disputer.training.data.Training

data class Coach(
    val uid: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val qualification: String = "",
    val email: String = "",
    val imageUrl: String = "",
    val telegram: String = "",
    val trainings: List<Training> = emptyList(),
    val students: List<Student> = emptyList(),
    val address: List<String> = emptyList()
)