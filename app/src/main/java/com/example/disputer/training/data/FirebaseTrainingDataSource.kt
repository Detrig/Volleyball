package com.example.disputer.training.data

import com.example.disputer.core.Resource
import com.example.disputer.training.domain.repository.TrainingDataSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FirebaseTrainingDataSource(
    private val firestore : FirebaseFirestore
) : TrainingDataSource {

    private companion object {
        const val TRAININGS_COLLECTION = "training"
        const val DATE_FIELD = "date"

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    override suspend fun getFutureTrainings(): Resource<List<Training>> {
        return try {
            val currentDate = dateFormat.format(Date())

            val querySnapshot = firestore.collection(TRAININGS_COLLECTION)
                .whereGreaterThanOrEqualTo(DATE_FIELD, currentDate)
                .orderBy(DATE_FIELD)
                .get()
                .await()

            val trainings = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Training::class.java)?.copy(id = document.id)
            }

            Resource.Success(trainings)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load future trainings")
        }
    }

    override suspend fun getPastTrainings(): Resource<List<Training>> {
        return try {
            val currentDate = dateFormat.format(Date()) // Текущая дата в формате строки

            val querySnapshot = firestore.collection(TRAININGS_COLLECTION)
                .whereLessThan(DATE_FIELD, currentDate)
                .orderBy(DATE_FIELD, Query.Direction.DESCENDING)
                .get()
                .await()

            val trainings = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Training::class.java)?.copy(id = document.id)
            }

            Resource.Success(trainings)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load past trainings")
        }
    }

    override suspend fun getAllTrainings(): Resource<List<Training>> {
        return try {
            val querySnapshot = firestore.collection(TRAININGS_COLLECTION)
                .orderBy(DATE_FIELD, Query.Direction.DESCENDING)
                .get()
                .await()

            val trainings = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Training::class.java)?.copy(id = document.id)
            }

            Resource.Success(trainings)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load all trainings")
        }
    }

    override suspend fun addTraining(training: Training): Resource<Training> {
        return try {
            val documentReference = if (training.id.isEmpty()) {
                firestore.collection(TRAININGS_COLLECTION).add(training).await()
            } else {
                firestore.collection(TRAININGS_COLLECTION)
                    .document(training.id)
                    .set(training)
                    .await()
                firestore.collection(TRAININGS_COLLECTION).document(training.id)
            }

            Resource.Success(training.copy(id = documentReference.id))
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to add training")
        }
    }

    override suspend fun deleteTraining(training: Training): Resource<Training> {
        return try {
            require(training.id.isNotEmpty()) { "Training ID must not be empty" }

            firestore.collection(TRAININGS_COLLECTION)
                .document(training.id)
                .delete()
                .await()

            Resource.Success(training)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to delete training")
        }
    }
}