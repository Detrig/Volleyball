package com.example.disputer.children.data

import com.example.disputer.children.domain.repo.ChildrenRepository
import com.example.disputer.coach.data.Coach
import com.example.disputer.core.Resource
import com.example.disputer.parent.data.Parent
import com.example.disputer.training.data.Training

class ChildrenRepositoryImpl(
    private val childrenDataSource: FirebaseChildrenDataSource
) : ChildrenRepository {

    override suspend fun getChildrenById(childrenId: String): Resource<Student> =
        childrenDataSource.getChildrenById(childrenId)


//    override suspend fun addChildren(children: Student): Resource<Unit> =
//        childrenDataSource.addChildren(children)


    override suspend fun getChildrenTrainings(children: Student): Resource<List<Training>> =
        childrenDataSource.getChildrenTrainings(children)

    override suspend fun addChildren(
        parentId: String,
        child: Student
    ): Resource<Pair<String, Unit>> =
        childrenDataSource.addChildren(parentId, child)

    override suspend fun deleteChildren(children: Student): Resource<Student> =
        childrenDataSource.deleteChildren(children)

    override suspend fun getParentByChildId(childId: String): Resource<Parent> =
        childrenDataSource.getParentByChildId(childId)

}