package com.example.disputer.children.domain.utils

import com.example.disputer.children.data.Student
import com.example.disputer.core.ListLiveDataWrapper

interface CurrentParentChildrenListLiveDataWrapper : ListLiveDataWrapper.All<Student> {
    class Base : CurrentParentChildrenListLiveDataWrapper, ListLiveDataWrapper.Abstract<Student>()
}