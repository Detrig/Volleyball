package com.example.disputer.children

import com.example.disputer.training.data.Training
import com.example.disputer.core.Resource

interface ChildrenDataSource {
    suspend fun getChildrenById(childrenId: String) : Resource<Student>
    suspend fun addChildren(children: Student) : Resource<Unit>
    suspend fun getChildrenTrainings(children: Student) : Resource<List<Training>>
    //suspend fun getChildrenCoachs(children: Student) : Resource<List<Coach>>
}