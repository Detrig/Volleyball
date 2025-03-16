package com.example.disputer.disputes.domain.models

import android.net.Uri
import com.example.disputer.disputes.data.models.DisputeStatus

data class DisputeItemUiState(
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