package com.example.disputer.children.domain.usecases

import com.example.disputer.children.domain.repo.ChildrenRepository
import com.example.disputer.core.Resource
import com.example.disputer.parent.data.Parent

class GetParentByChildIdUseCase(
    private val childrenRepository: ChildrenRepository
) {
    suspend operator fun invoke(childId: String): Resource<Parent> =
        childrenRepository.getParentByChildId(childId)
}