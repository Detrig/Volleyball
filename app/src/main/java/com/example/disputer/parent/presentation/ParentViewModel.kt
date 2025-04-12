package com.example.disputer.parent.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.disputer.children.data.Student
import com.example.disputer.core.Resource
import com.example.disputer.parent.domain.usecase.GetParentChildrenUseCase
import com.example.disputer.parent.domain.usecase.GetParentUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ParentViewModel(
    private val getParentUseCase: GetParentUseCase,
    private val getParentChildrenUseCase: GetParentChildrenUseCase,
    private val viewModelScope: CoroutineScope,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIo: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableLiveData<ParentUiState>()
    val uiState: LiveData<ParentUiState> = _uiState

    fun getParentById(id: String) {
        _uiState.value = ParentUiState.Loading
        viewModelScope.launch(dispatcherIo) {
            val state = when (val parent = getParentUseCase(id)) {
                is Resource.Success -> {
                    val children = getParentChildrenUseCase(id)
                    ParentUiState.Success(parent.data!!, children.data!!)
                }
                is Resource.Error -> ParentUiState.Error(parent.message!!)
            }
            withContext(dispatcherMain) {
                _uiState.value = state
            }
        }
    }

    fun getChildrenTrainings(children: Student) {

    }
}