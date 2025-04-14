package com.example.disputer.children.domain.usecases

import com.example.disputer.children.data.Student
import com.example.disputer.children.domain.repo.ChildrenRepository
import com.example.disputer.core.Resource
import com.example.disputer.training.data.Training

class GetChildrenTrainings(
    private val childrenRepository: ChildrenRepository
) {
    suspend operator fun invoke(children: Student): Resource<List<Training>> =
        childrenRepository.getChildrenTrainings(children)
}