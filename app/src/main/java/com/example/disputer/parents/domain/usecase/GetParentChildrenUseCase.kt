package com.example.disputer.parents.domain.usecase

import com.example.disputer.children.data.Student
import com.example.disputer.core.Resource
import com.example.disputer.parents.domain.repository.ParentRepository

class GetParentChildrenUseCase(
    private val parentRepository: ParentRepository
) {
    suspend operator fun invoke(parentId: String) : Resource<List<Student>> {
        return parentRepository.getParentChildren(parentId)
    }
}