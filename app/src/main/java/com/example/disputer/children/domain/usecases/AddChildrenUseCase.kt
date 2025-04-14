package com.example.disputer.children.domain.usecases

import com.example.disputer.children.data.Student
import com.example.disputer.children.domain.repo.ChildrenRepository
import com.example.disputer.core.Resource

class AddChildrenUseCase(
    private val childrenRepository: ChildrenRepository
) {
    suspend operator fun invoke(parentId: String, children: Student) : Resource<Pair<String, Unit>> =
        childrenRepository.addChildren(parentId, children)
}