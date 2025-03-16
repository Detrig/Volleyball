package com.example.disputer.disputes.domain.mappers

import com.example.disputer.disputes.data.models.Dispute
import com.example.disputer.disputes.domain.models.DisputeItemUiState

object DisputeToUiStateMapper {
    fun map(dispute: Dispute): DisputeItemUiState {
        return DisputeItemUiState(
            id = dispute.id,
            header = dispute.header,
            description = dispute.description,
            moneyToEnter = dispute.moneyToEnter,
            bank = dispute.bank,
            date = dispute.date,
            image = dispute.image,
            status = dispute.status,
            answerChoice = dispute.answerChoice
        )
    }

    fun mapList(disputes: List<Dispute>): List<DisputeItemUiState> {
        return disputes.map { map(it) }
    }
}