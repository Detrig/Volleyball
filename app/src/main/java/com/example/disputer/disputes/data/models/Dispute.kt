package com.example.disputer.disputes.data.models

import android.net.Uri

data class Dispute(
    val id: Int,
    val header: String,
    val description: String,
    val moneyToEnter: Int,
    val bank : Int,
    val date: String,
    val image: Uri?,
    val status: DisputeStatus,
    val answerChoice: List<String>
)

enum class DisputeStatus {
    ACTIVE, AWAITING_RESPONSE, ENDED
}