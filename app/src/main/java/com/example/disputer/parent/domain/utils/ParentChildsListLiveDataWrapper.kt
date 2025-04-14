package com.example.disputer.parent.domain.utils

import com.example.disputer.children.data.Student
import com.example.disputer.core.ListLiveDataWrapper

interface ParentChildsListLiveDataWrapper : ListLiveDataWrapper.All<Student> {
    class Base : ParentChildsListLiveDataWrapper, ListLiveDataWrapper.Abstract<Student>()
}