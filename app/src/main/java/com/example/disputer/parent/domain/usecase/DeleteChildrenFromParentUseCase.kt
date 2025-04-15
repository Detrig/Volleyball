package com.example.disputer.parent.domain.usecase

import com.example.disputer.children.data.Student
import com.example.disputer.core.Resource
import com.example.disputer.parent.domain.repository.ParentRepository

class DeleteChildrenFromParentUseCase(
    private val parentRepository: ParentRepository
) {
    suspend operator fun invoke(parentId: String, childId: String) : Resource<Unit> {
        return parentRepository.removeChildFromParent(parentId, childId)
    }
}