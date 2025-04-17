package com.example.disputer.children.domain.repo

import com.example.disputer.children.data.Student
import com.example.disputer.training.data.Training
import com.example.disputer.core.Resource
import com.example.disputer.parent.data.Parent

interface ChildrenRepository {
    suspend fun getChildrenById(childrenId: String) : Resource<Student>
    //suspend fun addChildren(children: Student) : Resource<Unit>
    suspend fun getChildrenTrainings(children: Student) : Resource<List<Training>>
    suspend fun addChildren(parentId: String, child: Student): Resource<Pair<String, Unit>>
    suspend fun deleteChildren(children: Student) : Resource<Student>
    suspend fun getParentByChildId(childId: String): Resource<Parent>
}