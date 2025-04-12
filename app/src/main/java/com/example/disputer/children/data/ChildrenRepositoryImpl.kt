package com.example.disputer.children.data

import com.example.disputer.children.domain.repo.ChildrenRepository
import com.example.disputer.coach.data.Coach
import com.example.disputer.core.Resource
import com.example.disputer.training.data.Training

class ChildrenRepositoryImpl(
    private val childrenDataSource: FirebaseChildrenDataSource
) : ChildrenRepository {

    override suspend fun getChildrenById(childrenId: String): Resource<Student> =
        childrenDataSource.getChildrenById(childrenId)


    override suspend fun addChildren(children: Student): Resource<Unit> =
        childrenDataSource.addChildren(children)


    override suspend fun getChildrenTrainings(children: Student): Resource<List<Training>> =
        childrenDataSource.getChildrenTrainings(children)

}