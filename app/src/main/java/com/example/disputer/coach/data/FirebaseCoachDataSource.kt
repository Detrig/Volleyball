package com.example.disputer.coach.data

import android.util.Log
import com.example.disputer.coach.domain.repo.CoachDataSource
import com.example.disputer.core.Resource
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

    override suspend fun updateCoach(coach: Coach): Resource<Unit> {
        return try {
            Log.d("VB-04", "FirebaseCoachDataSource ${coach.toString()}")
//            if (coach.uid.isEmpty()) {
//                fireStore.collection(COACHES_COLLECTION)
//                    .add(coach)
//                    .await()
            val result = fireStore.collection(COACHES_COLLECTION)
                .document(coach.uid)
                .set(coach)
                .await()

            Log.d("VB-04", "result $result")
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to add/update coach")
        }
    }

    override suspend fun getAllCoachs(): Resource<List<Coach>> {
        return try {
            val querySnapshot = fireStore.collection(COACHES_COLLECTION)
                .get()
                .await()

            val coachs = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Coach::class.java)?.copy(uid = document.id)
            }
            Resource.Success(coachs)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load coachs")
        }
    }

}