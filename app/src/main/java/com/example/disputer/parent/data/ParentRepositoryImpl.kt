package com.example.disputer.parent.data

import com.example.disputer.children.data.Student
import com.example.disputer.training.data.Training
import com.example.disputer.core.Resource
import com.example.disputer.parent.domain.repository.ParentDataSource
import com.example.disputer.parent.domain.repository.ParentRepository

class ParentRepositoryImpl(
    private val dataSource: ParentDataSource,
//        private val studentDataSource: StudentDataSource,
//        private val trainingDataSource: TrainingDataSource
) : ParentRepository {

    override suspend fun getParent(id: String): Resource<Parent> {
        return dataSource.getParent(id)
    }

    override suspend fun getParentsList() = dataSource.getParentsList()

    override suspend fun createParent(parent: Parent): Resource<Unit> {
        return dataSource.createParent(parent)
    }

    override suspend fun updateParent(parent: Parent) = dataSource.updateParent(parent)

    override suspend fun getParentChildren(parentId: String): Resource<List<Student>> =
        dataSource.getParentChildren(parentId)


//        // Сложные методы могут комбинировать данные
//        override suspend fun getParentChildren(parentId: String): Resource<List<Student>> {
//            return when (val parentRes = dataSource.getParent(parentId)) {
//                is Resource.Error -> Resource.Error(parentRes.message.toString())
//                is Resource.Success -> {
//                    studentDataSource?.getStudentsByParent(parentId)
//                        ?: Resource.Error("Student data source not available")
//                }
//                else -> Resource.Loading()
//            }
//        }

    override suspend fun getParentTrainings(parentId: String): Resource<List<Training>> {
        TODO("Not yet impelemnted")
    }

    override suspend fun removeChildFromParent(
        parentId: String,
        childId: String
    ): Resource<Unit> =
        dataSource.removeChildFromParent(parentId, childId)

}
