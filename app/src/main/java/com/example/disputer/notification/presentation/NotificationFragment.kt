package com.example.disputer.notification.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentNotificationsBinding
import com.example.disputer.notification.data.NotificationData

class NotificationFragment : AbstractFragment<FragmentNotificationsBinding>() {

    private lateinit var viewModel: NotificationViewModel
    private lateinit var rcViewAdapter: NotificationRcViewAdapter

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNotificationsBinding =
        FragmentNotificationsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(NotificationViewModel::class.java)
        initRcView()
        viewModel.getAllNotificationsForCurrentUser()

        viewModel.notificationsLiveData().value?.let {
            rcViewAdapter.update(ArrayList(it))
        }

        viewModel.notificationsLiveData().observe(viewLifecycleOwner) {
            rcViewAdapter.update(ArrayList(it))
        }
    }

    private fun initRcView() {
        rcViewAdapter = NotificationRcViewAdapter(object : NotificationRcViewAdapter.OnNotificationClickListener {
            override fun onClick(NotificationData: NotificationData) {

            }
        })
        binding.notificationsRcView.adapter = rcViewAdapter
    }
}