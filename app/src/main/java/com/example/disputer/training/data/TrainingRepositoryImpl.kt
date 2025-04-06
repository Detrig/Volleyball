package com.example.disputer.training.data

import com.example.disputer.core.Resource
import com.example.disputer.training.domain.repository.TrainingDataSource
import com.example.disputer.training.domain.repository.TrainingsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

class TrainingRepositoryImpl(
    private val dataSource: TrainingDataSource
) : TrainingsRepository {

    override suspend fun getFutureTrainings(): Resource<List<Training>> =
        dataSource.getFutureTrainings()


    override suspend fun getPastTrainings(): Resource<List<Training>> =
        dataSource.getPastTrainings()

    override suspend fun getAllTrainings(): Resource<List<Training>> =
        dataSource.getAllTrainings()

    override suspend fun addTraining(training: Training): Resource<Training> =
        dataSource.addTraining(training)

    override suspend fun deleteTraining(training: Training): Resource<Training> =
        dataSource.deleteTraining(training)

}