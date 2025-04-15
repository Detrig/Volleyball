package com.example.disputer.training.domain.repository.utils

import com.example.disputer.children.data.Student
import com.example.disputer.core.ListLiveDataWrapper

interface SignUpForTrainingChildrensByCoachLiveDataWrapper : ListLiveDataWrapper.All<Student> {
    class Base : SignUpForTrainingChildrensByCoachLiveDataWrapper, ListLiveDataWrapper.Abstract<Student>()
}