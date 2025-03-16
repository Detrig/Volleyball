package com.example.disputer.disputes.presentation.activedispute

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.disputer.disputes.domain.models.DisputeItemUiState
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentActiveDisputeBinding

class ActiveDisputeFragment : AbstractFragment<FragmentActiveDisputeBinding>() {

    private lateinit var rcViewAdapter: DisputeRcViewAdapter

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentActiveDisputeBinding {
        return FragmentActiveDisputeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcViewInit()

        val viewModel = (activity as ProvideViewModel).viewModel(ActiveDisputeViewModel::class.java)
        viewModel.loadDisputes()

        viewModel.liveData().observe(viewLifecycleOwner) { uiState ->
            uiState.update(binding)
        }
    }

    private fun rcViewInit() {
        rcViewAdapter = DisputeRcViewAdapter(object : DisputeRcViewAdapter.OnDisputeClickListener {
            override fun onClick(dispute: DisputeItemUiState) {
                Toast.makeText(context, "Dispute item click", Toast.LENGTH_SHORT).show()
            }

        })

        binding.rcView.adapter = rcViewAdapter

    }
}