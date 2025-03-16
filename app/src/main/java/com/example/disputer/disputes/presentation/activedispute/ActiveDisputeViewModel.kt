package com.example.disputer.disputes.presentation.activedispute

import androidx.lifecycle.ViewModel
import com.example.disputer.disputes.domain.models.DisputeItemUiState
import com.example.disputer.core.Navigation
import com.example.disputer.disputes.data.DisputesRepository
import com.example.disputer.disputes.domain.mappers.DisputeToUiStateMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActiveDisputeViewModel(
    private val navigation: Navigation,
    private val activeDisputeUiLiveDataWrapper: ActiveDisputeUiLiveDataWrapper,
    private val disputesRepository: DisputesRepository,
    private val viewModelScope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    fun init() {
        activeDisputeUiLiveDataWrapper.update(ActiveDisputeUiState.Loading)
    }

    fun loadDisputes() {
        activeDisputeUiLiveDataWrapper.update(ActiveDisputeUiState.Loading)
        viewModelScope.launch(dispatcher) {
            try {
                val disputes = disputesRepository.getAllDisputes()
                withContext(dispatcherMain) {
                    if (disputes.isNotEmpty()) {
                        val disputesMapped = DisputeToUiStateMapper.mapList(disputes)
                        activeDisputeUiLiveDataWrapper.update(
                            ActiveDisputeUiState.Success(
                                disputesMapped
                            )
                        )
                    } else {
                        activeDisputeUiLiveDataWrapper.update(ActiveDisputeUiState.Empty)
                    }
                }
            } catch (e: Exception) {
                withContext(dispatcherMain) {
                    activeDisputeUiLiveDataWrapper.update(ActiveDisputeUiState.Error)
                }
            }

        }
    }

    fun liveData() = activeDisputeUiLiveDataWrapper.liveData()
}