package com.example.disputer.coach.data

import com.example.disputer.coach.domain.repo.CoachRepository
import com.example.disputer.core.Resource

class CoachRepositoryImpl(
    private val coachDataSource: FirebaseCoachDataSource
) : CoachRepository {

    override suspend fun getCoachById(coachId: String): Resource<Coach> =
        coachDataSource.getCoachById(coachId)

    override suspend fun updateCoach(coach: Coach): Resource<Unit> =
        coachDataSource.updateCoach(coach)

    override suspend fun getAllCoachs(): Resource<List<Coach>> =
        coachDataSource.getAllCoachs()
}