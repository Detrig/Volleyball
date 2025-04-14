package com.example.disputer.children.data

import com.example.disputer.children.domain.repo.ChildrenDataSource
import com.example.disputer.training.data.Training
import com.example.disputer.core.Resource
import com.example.disputer.parent.data.Parent
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseChildrenDataSource(
    private val fireStore: FirebaseFirestore
    // private val coachRepository: CoachRepository
) : ChildrenDataSource {

    private companion object {
        const val STUDENTS_COLLECTION = "student"
        const val TRAININGS_COLLECTION = "training"
        const val PARENTS_COLLECTION = "parent"
        const val COACHES_COLLECTION = "coach"
    }

    override suspend fun getChildrenById(childrenId: String): Resource<Student> {
        return try {
            val document = fireStore.collection(STUDENTS_COLLECTION)
                .document(childrenId)
                .get()
                .await()

            if (document.exists()) {
                document.toObject(Student::class.java)?.let { student ->
                    Resource.Success(student.copy(uid = document.id))
                } ?: Resource.Error("Failed to parse student data")
            } else {
                Resource.Error("Student not found")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load student")
        }
    }

//    override suspend fun addChildren(children: Student): Resource<Unit> {
//        return try {
//            // Если uid пустой - создаем новый документ
//            if (children.uid.isEmpty()) {
//                fireStore.collection(STUDENTS_COLLECTION)
//                    .add(children)
//                    .await()
//            } else {
//                // Если uid указан - обновляем существующий документ
//                fireStore.collection(STUDENTS_COLLECTION)
//                    .document(children.uid)
//                    .set(children)
//                    .await()
//            }
//            Resource.Success(Unit)
//        } catch (e: Exception) {
//            Resource.Error(e.localizedMessage ?: "Failed to add/update student")
//        }
//    }

    override suspend fun getChildrenTrainings(children: Student): Resource<List<Training>> {
        return try {
            if (children.trainingIds.isEmpty()) {
                return Resource.Success(emptyList())
            }

            val trainings = fireStore.collection(TRAININGS_COLLECTION)
                .whereIn("id", children.trainingIds)
                .get()
                .await()
                .documents
                .mapNotNull { document ->
                    document.toObject(Training::class.java)?.copy(id = document.id)
                }

            Resource.Success(trainings)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load student trainings")
        }
    }

    override suspend fun addChildren(parentId: String, child: Student): Resource<Pair<String, Unit>> {
        return try {
            if (child.uid.isNotEmpty()) {
                // Обновление существующего ребенка
                val childRef = fireStore.collection(STUDENTS_COLLECTION).document(child.uid)
                childRef.set(child).await()
                Resource.Success(child.uid to Unit)
            } else {
                // Новый ребенок
                val result = fireStore.runTransaction { transaction ->
                    val parentRef = fireStore.collection(PARENTS_COLLECTION).document(parentId)
                    val parentSnapshot = transaction.get(parentRef)

                    if (!parentSnapshot.exists()) {
                        throw Exception("Parent not found")
                    }

                    val childRef = fireStore.collection(STUDENTS_COLLECTION).document()
                    val childWithParent = child.copy(parentId = parentId, uid = childRef.id)
                    transaction.set(childRef, childWithParent)

                    val currentChildIds = parentSnapshot.toObject(Parent::class.java)?.childIds ?: emptyList()
                    val updatedChildIds = currentChildIds + childRef.id
                    transaction.update(parentRef, "childIds", updatedChildIds)

                    childRef.id
                }.await()

                Resource.Success(result to Unit)
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to add/update child: ${e.message}")
        }
    }

    override suspend fun deleteChildren(children: Student): Resource<Student> {
        return try {
            require(children.uid.isNotEmpty()) { "Training ID must not be empty" }

            fireStore.collection(STUDENTS_COLLECTION)
                .document(children.uid)
                .delete()
                .await()
            Resource.Success(children)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to delete training")
        }
    }
}