package com.example.disputer.children.domain.usecases

import com.example.disputer.children.data.Student
import com.example.disputer.children.domain.repo.ChildrenRepository
import com.example.disputer.core.Resource

class GetChildrenByIdUseCase(
    private val childrenRepository: ChildrenRepository
) {
    suspend operator fun invoke(id: String) : Resource<Student> =
        childrenRepository.getChildrenById(id)
}