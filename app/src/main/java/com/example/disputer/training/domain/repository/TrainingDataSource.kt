package com.example.disputer.training.domain.repository

import androidx.lifecycle.LiveData
import com.example.disputer.core.Resource
import com.example.disputer.training.data.Training

interface TrainingDataSource {
    suspend fun getFutureTrainings(): Resource<List<Training>>
    suspend fun getPastTrainings(): Resource<List<Training>>
    suspend fun getAllTrainings(): Resource<List<Training>>
    suspend fun addTraining(training: Training): Resource<Training>
    suspend fun deleteTraining(training: Training): Resource<Training>
    fun observeTrainingsLiveData() : LiveData<Resource<List<Training>>>

    suspend fun signUpForTraining(trainingId: String, childIds: List<String>) : Resource<Unit>
}