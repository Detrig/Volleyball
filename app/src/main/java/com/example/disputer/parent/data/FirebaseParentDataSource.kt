package com.example.disputer.parent.data

import com.example.disputer.children.data.Student
import com.example.disputer.core.Resource
import com.example.disputer.parent.domain.repository.ParentDataSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseParentDataSource(
    private val fireStore: FirebaseFirestore
) : ParentDataSource {

    private companion object {
        const val PARENTS_COLLECTION = "parent"
        const val STUDENTS_COLLECTION = "student"
    }

    override suspend fun getParent(id: String): Resource<Parent> {
        return try {
            val document = fireStore.collection(PARENTS_COLLECTION)
                .document(id)
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
        return try {
            // 1. Получаем документ родителя
            val parentDocument = fireStore.collection(PARENTS_COLLECTION)
                .document(parentId)
                .get()
                .await()

            if (!parentDocument.exists()) {
                return Resource.Error("Parent not found")
            }

            // 2. Получаем список ID детей
            val parent = parentDocument.toObject(Parent::class.java)
                ?: return Resource.Error("Failed to parse parent data")

            val childIds = parent.childIds
            if (childIds.isEmpty()) {
                return Resource.Success(emptyList())
            }

            // 3. Получаем документы всех детей
            val students = fireStore.collection(STUDENTS_COLLECTION)
                .whereIn("uid", childIds)
                .get()
                .await()
                .documents
                .mapNotNull { document ->
                    document.toObject(Student::class.java)?.copy(uid = document.id)
                }

            Resource.Success(students)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load parent's children")
        }
    }

    override suspend fun removeChildFromParent(parentId: String, childId: String): Resource<Unit> {
        return try {
            val parentDocRef = fireStore.collection(PARENTS_COLLECTION).document(parentId)
            val document = parentDocRef.get().await()

            if (!document.exists()) {
                return Resource.Error("Parent not found")
            }

            val parent = document.toObject(Parent::class.java)
                ?: return Resource.Error("Failed to parse parent data")

            val updatedChildIds = parent.childIds.toMutableList().apply {
                remove(childId)
            }

            val updatedParent = parent.copy(uid = document.id, childIds = updatedChildIds)
            parentDocRef.set(updatedParent).await()

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to remove child from parent")
        }
    }
}
