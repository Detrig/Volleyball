package com.example.disputer.parents.data

import com.example.disputer.authentication.data.Student
import com.example.disputer.authentication.data.Training
import com.example.disputer.core.Resource
import com.example.disputer.parents.domain.repository.ParentDataSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseParentDataSource(
    private val fireStore: FirebaseFirestore
) : ParentDataSource {

    private companion object {
        const val PARENTS_COLLECTION = "parents"
        const val STUDENTS_COLLECTION = "students"
        const val TRAININGS_COLLECTION = "trainings"
    }

    override suspend fun getParent(id: String): Resource<Parent> {
        return try {
            val document = fireStore.collection(PARENTS_COLLECTION)
                .document()
                .get()
                .await()

            if (document.exists()) {
                document.toObject(Parent::class.java)?.let { parent ->
                    Resource.Success(parent.copy(uid = document.id))
                } ?: Resource.Error("Failed to parse parent data")
            } else {
                Resource.Error("Parent not found")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun createParent(parent: Parent): Resource<Unit> {
        return try {
            fireStore.collection(PARENTS_COLLECTION)
                .add(parent)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to create parent")
        }
    }

    override suspend fun updateParent(parent: Parent): Resource<Unit> {
        return try {
            require(parent.uid.isNotEmpty()) { "Parent ID must not be empty" }

            fireStore.collection(PARENTS_COLLECTION)
                .document(parent.uid)
                .set(parent)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to update parent")
        }
    }

    override suspend fun getParentsList(): Resource<List<Parent>> {
        return try {
            val querySnapshot = fireStore.collection(PARENTS_COLLECTION)
                .get()
                .await()

            val parents = querySnapshot.documents.mapNotNull { document ->
                document.toObject(Parent::class.java)?.copy(uid = document.id)
            }
            Resource.Success(parents)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load parents")
        }
    }

    override suspend fun getParentChildren(parentId: String): Resource<List<Student>> {
        TODO("Not yet implemented")
    }

    override suspend fun getParentTrainings(parentId: String): Resource<List<Training>> {
        TODO("Not yet implemented")
    }

}
