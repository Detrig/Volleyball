package com.example.disputer.coach.data

import android.util.Log
import com.example.disputer.coach.domain.CoachDataSource
import com.example.disputer.core.Resource
import com.example.disputer.training.data.Training
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseCoachDataSource(
    private val fireStore: FirebaseFirestore
) : CoachDataSource {

    private companion object {
        const val COACHES_COLLECTION = "coach"
        const val TRAININGS_COLLECTION = "training"
    }

    override suspend fun getCoachById(coachId: String): Resource<Coach> {
        return try {
            val document = fireStore.collection(COACHES_COLLECTION)
                .document(coachId)
                .get()
                .await()
            if (document.exists()) {
                document.toObject(Coach::class.java)?.let { coach ->
                    Resource.Success(coach.copy(uid = document.id))
                } ?: Resource.Error("Failed to parse coach data")
            } else {
                Resource.Error("Coach not found")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to get coach")
        }
    }

    override suspend fun addCoach(coach: Coach): Resource<Unit> {
        return try {
            if (coach.uid.isEmpty()) {
                fireStore.collection(COACHES_COLLECTION)
                    .add(coach)
                    .await()
            } else {
                fireStore.collection(COACHES_COLLECTION)
                    .document(coach.uid)
                    .set(coach)
                    .await()
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to add/update coach")
        }
    }

    override suspend fun getCoachTrainings(coach: Coach): Resource<List<Training>> {
        TODO("Not yet implemented")
    }
}