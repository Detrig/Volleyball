package com.example.disputer.training.data

data class Training(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = "",
    val maxPersonCount: Int = 30,
    val addressInfo: String = "",
    val address: String = "",
    val group: String = "",
    val birthYear: String = "",
    val coachName: String = "",
    val coachId: String = "",
    val studentIdsList : List<String> = emptyList()
)