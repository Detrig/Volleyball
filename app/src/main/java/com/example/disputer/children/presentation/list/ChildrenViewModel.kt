package com.example.disputer.children.presentation.list

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.disputer.children.data.Student
import com.example.disputer.children.domain.utils.ClickedChildrenLiveDataWrapper
import com.example.disputer.children.presentation.add.AddChildrenScreen
import com.example.disputer.core.Navigation

class ChildrenViewModel(
    private val navigation: Navigation,
    private val clickedChildrenLiveDataWrapper: ClickedChildrenLiveDataWrapper
) : ViewModel() {

    fun addChildrenScreen(children: Student) {
        if (children.uid.isNotEmpty()) {
            clickedChildrenLiveDataWrapper.update(children)
            navigation.update(AddChildrenScreen)
        } else {
            navigation.update(AddChildrenScreen)
        }
    }
}
