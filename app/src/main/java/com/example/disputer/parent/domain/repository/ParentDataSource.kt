package com.example.disputer.parent.domain.repository

import com.example.disputer.children.data.Student
import com.example.disputer.core.Resource
import com.example.disputer.parent.data.Parent

interface ParentDataSource {
    suspend fun getParent(id: String): Resource<Parent>
    suspend fun createParent(parent: Parent): Resource<Unit>
    suspend fun updateParent(parent: Parent): Resource<Unit>
    suspend fun getParentsList(): Resource<List<Parent>>
    suspend fun getParentChildren(parentId: String): Resource<List<Student>>
    suspend fun removeChildFromParent(parentId: String, childId: String): Resource<Unit>
}