package com.example.disputer.parents.domain.repository

import com.example.disputer.authentication.data.Student
import com.example.disputer.authentication.data.Training
import com.example.disputer.core.Resource
import com.example.disputer.parents.data.Parent

interface ParentRepository {
    suspend fun getParent(id: String): Resource<Parent>
    suspend fun createParent(parent: Parent): Resource<Unit>
    suspend fun updateParent(parent: Parent): Resource<Unit>
    suspend fun getParentsList(): Resource<List<Parent>>
    suspend fun getParentChildren(parentId: String): Resource<List<Student>>
    suspend fun getParentTrainings(parentId: String): Resource<List<Training>>
}