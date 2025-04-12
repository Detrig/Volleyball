package com.example.disputer.coach.domain.repo

import com.example.disputer.coach.data.Coach
import com.example.disputer.core.Resource

interface CoachDataSource {
    suspend fun getCoachById(coachId: String): Resource<Coach>
    suspend fun updateCoach(coach: Coach): Resource<Unit>
    suspend fun getAllCoachs() : Resource<List<Coach>>
}