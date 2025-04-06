package com.example.disputer.training.presentation.training_parent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.disputer.authentication.data.Shop
import com.example.disputer.training.data.Training
import com.example.disputer.authentication.presentation.main.MainActivity
import com.example.disputer.core.AbstractFragment

import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentTrainingParentMainBinding

class TrainingParentMainFragment : AbstractFragment<FragmentTrainingParentMainBinding>() {

    private lateinit var trainingAdapter: TrainingsRecyclerViewAdapter
    private lateinit var shopAdapter: ShopsRcViewAdapter
    private lateinit var viewModel: TrainingParentMainViewModel

    private val shopsList = arrayListOf(
        Shop(
            "https://sun9-28.userapi.com/s/v1/ig2/c1AfiGNW03K0d2BN-YyjKuWhLnN30S8dcPCyLSuI0k21JlCMcIRw08aXncY3YXkx2vEBfhiEOnA1zRKlxhInZYHm.jpg?quality=96&as=32x32,48x48,72x72,108x108,160x160,240x240,360x360,480x480,540x540,640x640,720x720,1080x1080&from=bu&u=zzlqzlhXHEcXmXW8GvPyEJFSiscUwzlHkhyNJiNPwjc&cs=1080x1080",
            "https://google.com"
        ),
        Shop(
            "https://sun9-28.userapi.fsdfsdf/s/v1/ig80",
            "https://vk.com/wall-211429303_10"
        )
    )

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTrainingParentMainBinding =
        FragmentTrainingParentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).showHeaderBottomNav()

        viewModel = (activity as ProvideViewModel).viewModel(TrainingParentMainViewModel::class.java)
        initRcView()
//        viewModel.trainingsLiveData().observe(viewLifecycleOwner) { list ->
//            trainingAdapter.update(ArrayList(list))
//        }

        trainingAdapter.update(ArrayList(viewModel.trainingsLiveData().value ?: listOf<Training>()))
        shopAdapter.update(shopsList)
    }

    private fun initRcView() {
        trainingAdapter = TrainingsRecyclerViewAdapter(object :
            TrainingsRecyclerViewAdapter.OnTrainingClickListener {
            override fun onClick(training: Training) {
                Toast.makeText(requireContext(), training.coachName, Toast.LENGTH_SHORT).show()
            }

        })
        binding.rcViewTrainings.adapter = trainingAdapter

        shopAdapter = ShopsRcViewAdapter(object : ShopsRcViewAdapter.OnShopClickListener {
            override fun onClick(shop: Shop) {
                openUrl(shop.url)
            }
        })
        binding.rcViewShops.adapter = shopAdapter
    }

    private fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Ошибка при открытии ссылки: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}