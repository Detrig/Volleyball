package com.example.disputer.training.domain.repository

import com.example.disputer.core.Resource
import com.example.disputer.training.data.Training
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

interface TrainingsRepository {
    suspend fun getFutureTrainings(): Resource<List<Training>>
    suspend fun getPastTrainings(): Resource<List<Training>>
    suspend fun getAllTrainings(): Resource<List<Training>>
    suspend fun addTraining(training: Training): Resource<Training>
    suspend fun deleteTraining(training: Training): Resource<Training>
}