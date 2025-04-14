package com.example.disputer.parent.domain.usecase

import com.example.disputer.children.data.Student
import com.example.disputer.core.Resource
import com.example.disputer.parent.data.Parent
import com.example.disputer.parent.domain.repository.ParentRepository

class UpdateParentUseCase(
    private val parentRepository: ParentRepository
) {
    suspend operator fun invoke(parent: Parent) : Resource<Unit> {
        return parentRepository.updateParent(parent)
    }
}