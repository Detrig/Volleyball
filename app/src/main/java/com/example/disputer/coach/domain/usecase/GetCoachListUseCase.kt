package com.example.disputer.coach.domain.usecase

import com.example.disputer.coach.domain.repo.CoachRepository

class GetCoachListUseCase(private val coachRepository: CoachRepository) {
    suspend operator fun invoke() =
        coachRepository.getAllCoachs()
}