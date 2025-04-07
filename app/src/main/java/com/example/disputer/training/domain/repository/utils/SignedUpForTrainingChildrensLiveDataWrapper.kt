package com.example.disputer.training.domain.repository.utils

import com.example.disputer.children.data.Student
import com.example.disputer.core.ListLiveDataWrapper

interface SignedUpForTrainingChildrensLiveDataWrapper : ListLiveDataWrapper.All<Student> {

    class Base : SignedUpForTrainingChildrensLiveDataWrapper, ListLiveDataWrapper.Abstract<Student>()
}