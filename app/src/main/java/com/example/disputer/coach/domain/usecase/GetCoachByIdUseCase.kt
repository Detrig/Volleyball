package com.example.disputer.coach.domain.usecase

import com.example.disputer.coach.data.Coach
import com.example.disputer.coach.domain.repo.CoachRepository

class GetCoachByIdUseCase(private val coachRepository: CoachRepository) {
    suspend operator fun invoke(coachId: String) =
        coachRepository.getCoachById(coachId)
}