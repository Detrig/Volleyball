package com.example.disputer.coach.domain

import com.example.disputer.coach.data.Coach
import com.example.disputer.core.Resource
import com.example.disputer.training.data.Training

interface CoachDataSource {
    suspend fun getCoachById(coachId: String): Resource<Coach>
    suspend fun addCoach(coach: Coach): Resource<Unit>

    suspend fun getCoachTrainings(coach: Coach): Resource<List<Training>>
}