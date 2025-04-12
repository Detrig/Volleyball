package com.example.disputer.children.domain.utils

import com.example.disputer.children.data.Student
import com.example.disputer.core.LiveDataWrapper

interface ClickedChildrenLiveDataWrapper : LiveDataWrapper.Mutable<Student> {

    class Base : ClickedChildrenLiveDataWrapper, LiveDataWrapper.Abstract<Student>()
}