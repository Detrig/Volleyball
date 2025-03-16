package com.example.disputer.disputes.presentation.activedispute

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.disputer.databinding.FragmentActiveDisputeBinding
import com.example.disputer.disputes.domain.models.DisputeItemUiState

interface ActiveDisputeUiState {

    fun update(binding: FragmentActiveDisputeBinding)

    abstract class Abstract(
        private val emptyPlaceHolderVisible: Int,
        private val errorMessageVisible: Int,
        private val progressBarVisible: Int,
        private val recyclerViewVisible: Int
    ) : ActiveDisputeUiState {

        override fun update(binding: FragmentActiveDisputeBinding) = with(binding) {
            emptyPlaceHolder.visibility = emptyPlaceHolderVisible
            errorPlaceHolder.visibility = errorMessageVisible
            progressBar.visibility = progressBarVisible
            rcView.visibility = recyclerViewVisible
        }
    }

    object Empty : Abstract(View.VISIBLE, View.GONE, View.GONE, View.GONE)

    object Error : Abstract(View.GONE, View.VISIBLE, View.GONE, View.GONE)

    object Loading : Abstract(View.GONE, View.GONE, View.VISIBLE, View.GONE)

    data class Success(private val disputesList : List<DisputeItemUiState>) : Abstract(View.GONE, View.GONE, View.GONE, View.VISIBLE) {
        override fun update(binding: FragmentActiveDisputeBinding) {
            super.update(binding)

            val adapter = binding.rcView.adapter as? DisputeRcViewAdapter
            adapter?.update(disputesList)
        }
    }

}

