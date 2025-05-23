package com.example.disputer.training.domain.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.disputer.children.data.Student
import com.example.disputer.core.Resource
import com.example.disputer.training.data.Training

interface TrainingsRepository {
    suspend fun getFutureTrainings(): Resource<List<Training>>
    suspend fun getPastTrainings(): Resource<List<Training>>
    suspend fun getAllTrainings(): Resource<List<Training>>
    suspend fun addTraining(training: Training): Resource<Training>
    suspend fun deleteTraining(training: Training): Resource<Training>
    fun observeTrainingsLiveData() : LiveData<Resource<List<Training>>>

    suspend fun signUpForTraining(trainingId: String, childIds: List<String>) : Resource<Unit>
    suspend fun signOffTraining(
        trainingId: String,
        childIds: List<String>
    ): Resource<Unit>
    suspend fun getChildrensSignedUpForTraining(trainingId: String) : Resource<List<Student>>
}