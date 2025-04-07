package com.example.disputer.training.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.disputer.core.Resource
import com.example.disputer.training.domain.repository.TrainingDataSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
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

    override fun observeTrainingsLiveData(): LiveData<Resource<List<Training>>> {
        return object : LiveData<Resource<List<Training>>>() {
            private var listener: ListenerRegistration? = null

            override fun onActive() {
                super.onActive()
                listener = firestore.collection(TRAININGS_COLLECTION)
                    .addSnapshotListener { snapshot, error ->
                        when {
                            error != null ->
                                postValue(Resource.Error(error.message ?: "Unknown error"))
                            snapshot != null -> {
                                val trainings = snapshot.documents.mapNotNull { doc ->
                                    doc.toObject(Training::class.java)?.copy(id = doc.id)
                                }
                                postValue(Resource.Success(trainings))
                            }
                        }
                    }
            }

            override fun onInactive() {
                super.onInactive()
                listener?.remove()
                listener = null
            }
        }
    }

    //Sign up
    override suspend fun signUpForTraining(
        trainingId: String,
        childIds: List<String>
    ): Resource<Unit> {
        return try {
            val trainingRef = firestore.collection(TRAININGS_COLLECTION).document(trainingId)

            firestore.runTransaction { transaction ->
                val training = transaction.get(trainingRef).toObject(Training::class.java)
                    ?: throw Exception("Training not found")

                val updatedStudentIds = training.studentIdsList.toMutableSet().apply {
                    addAll(childIds)
                }

                transaction.update(trainingRef, "studentIdsList", updatedStudentIds.toList())
            }.await()

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to sign up for training")
        }
    }

    //Crud trainings
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