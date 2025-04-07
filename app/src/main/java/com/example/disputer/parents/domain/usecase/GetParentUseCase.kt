package com.example.disputer.parents.domain.usecase

import com.example.disputer.core.Resource
import com.example.disputer.parents.data.Parent
import com.example.disputer.parents.domain.repository.ParentRepository

class GetParentUseCase(private val repo: ParentRepository) {

    suspend operator fun invoke(id: String): Resource<Parent> {
        return repo.getParent(id)
    }

}