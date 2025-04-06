package com.example.disputer.coach.domain

import com.example.disputer.coach.data.Coach
import com.example.disputer.training.data.Training
import com.example.disputer.core.Resource

interface CoachRepository {
    suspend fun getCoachById(coachId: String) : Resource<Coach>
    suspend fun addCoach(coach: Coach)

    suspend fun getCoachTrainings(coach: Coach) : Resource<List<Training>>
}