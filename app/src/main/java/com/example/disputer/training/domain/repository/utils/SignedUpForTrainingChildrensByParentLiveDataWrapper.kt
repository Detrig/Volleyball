package com.example.disputer.training.domain.repository.utils

import com.example.disputer.children.data.Student
import com.example.disputer.core.ListLiveDataWrapper

interface SignedUpForTrainingChildrensByParentLiveDataWrapper : ListLiveDataWrapper.All<Student> {
    class Base : SignedUpForTrainingChildrensByParentLiveDataWrapper, ListLiveDataWrapper.Abstract<Student>()
}