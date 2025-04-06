package com.example.disputer.authentication.data

import android.util.Log
import com.example.disputer.authentication.domain.repository.CurrentUserRepository
import com.example.disputer.coach.domain.CoachDataSource
import com.example.disputer.core.Resource
import com.example.disputer.parents.domain.repository.ParentDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CurrentUserRepositoryImpl(
    private val currentUserDataSource: FirebaseCurrentDataSource
) : CurrentUserRepository {
    override suspend fun getCurrentUser(): Resource<AuthUser> =
        currentUserDataSource.getCurrentUser()
}