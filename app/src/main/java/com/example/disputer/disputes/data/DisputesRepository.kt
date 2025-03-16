package com.example.disputer.disputes.data

import com.example.disputer.disputes.data.models.Dispute
import com.example.disputer.disputes.data.models.DisputeStatus
import com.example.disputer.disputes.domain.models.DisputeItemUiState

interface DisputesRepository {

    suspend fun getAllDisputes() : List<Dispute>

    suspend fun getDisputeById(id : Int) : Dispute

    suspend fun getDisputeByName(header : String) : List<Dispute>

    suspend fun deleteDisputeById(id : Int)

    suspend fun addDispute(dispute : Dispute)

    suspend fun updateDispute(dispute : Dispute)

    class Base() : DisputesRepository {
        val list = mutableListOf(
            Dispute(
                1,
                "ПРЫЖОК",
                "ЗАПРЫГНЕТ?",
                1000,
                5000,
                "16.03.2025 - 24.03.2025",
                null,
                DisputeStatus.ACTIVE,
                listOf("Запрыгнет", "Не запрыгнет")
            ),
            Dispute(
                2,
                "ПРЫЖОК",
                "ЗАПРЫГНЕТ?",
                1000,
                5000,
                "9.03.2025 - 16.03.2025",
                null,
                DisputeStatus.AWAITING_RESPONSE,
                listOf("Запрыгнет", "Не запрыгнет")
            ),
            Dispute(
                3,
                "СОРЕВНОВАНИЕ",
                "ЗОВ?",
                1000,
                3000,
                "14.03.2025 - 15.03.2025",
                null,
                DisputeStatus.ENDED,
                listOf("Запрыгнет", "Не запрыгнет")
            )
        )
        override suspend fun getAllDisputes(): List<Dispute> {
            return list
        }

        override suspend fun getDisputeById(id: Int): Dispute {
            TODO("Not yet implemented")
        }

        override suspend fun getDisputeByName(header: String): List<Dispute> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteDisputeById(id: Int) {
            TODO("Not yet implemented")
        }

        override suspend fun addDispute(dispute: Dispute) {
            TODO("Not yet implemented")
        }

        override suspend fun updateDispute(dispute: Dispute) {
            TODO("Not yet implemented")
        }

    }
}