package com.example.disputer.parents.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.disputer.core.Resource
import com.example.disputer.parents.domain.usecase.GetParentUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParentViewModel(
    private val getParentUseCase: GetParentUseCase,
    private val viewModelScope : CoroutineScope,
    private val dispatcherMain : CoroutineDispatcher = Dispatchers.Main,
    private val dispatcherIo : CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableLiveData<ParentUiState>()
    val uiState: LiveData<ParentUiState> = _uiState

    fun getParentById(id : String) {
        viewModelScope.launch {
            _uiState.value = ParentUiState.Loading
            _uiState.value = when (val result = getParentUseCase(id)) {
                is Resource.Success -> ParentUiState.Success(result.data!!)
                is Resource.Error -> ParentUiState.Error(result.message!!)
                is Resource.Loading -> ParentUiState.Loading
            }
        }
    }
}