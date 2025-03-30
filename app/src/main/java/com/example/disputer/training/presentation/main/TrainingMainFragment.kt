package com.example.disputer.training.presentation.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.disputer.authentication.data.Shop
import com.example.disputer.authentication.data.Training
import com.example.disputer.authentication.presentation.main.MainActivity
import com.example.disputer.core.AbstractFragment
import com.example.disputer.databinding.FragmentTrainingMainBinding
import androidx.core.net.toUri

class TrainingMainFragment : AbstractFragment<FragmentTrainingMainBinding>() {

    private lateinit var trainingAdapter: TrainingsRecyclerViewAdapter
    private lateinit var shopAdapter: ShopsRcViewAdapter
    private val trainingList = arrayListOf(
        Training(
            id = "1",
            time = "17:00",
            date = "Пн, 5 июня",
            addressInfo = "Спортивный комплекс 'Триумф', зал волейбола",
            address = "ул. Хабарова, 27, г. Якутск"
        ),
        Training(
            id = "2",
            time = "19:30",
            date = "Вт, 6 июня",
            addressInfo = "СК '50 лет Победы', основной зал",
            address = "ул. Петра Алексеева, 6, г. Якутск",
            coachName = "Алексей"
        ),
        Training(
            id = "3",
            time = "18:00",
            date = "Ср, 7 июня",
            addressInfo = "СВФУ им. М.К. Аммосова, спортивный зал КФЕН",
            address = "ул. Кулаковского, 48, г. Якутск"
        ),
        Training(
            id = "4",
            time = "16:00",
            date = "Чт, 8 июня",
            addressInfo = "Дворец спорта 'Эллэй Боотур' Дворец спорта 'Эллэй Боотур' Дворец спорта 'Эллэй Боотур'",
            address = "ул. Лермонтова, 60/2, г. Якутск"
        ),
        Training(
            id = "5",
            time = "20:00",
            date = "Пт, 9 июня",
            addressInfo = "Школа №5, спортивный зал",
            address = "ул. Каландарашвили, 15, г. Якутск ул. Каландарашвили, 15, г. Якутск"
        ),
        Training(
            id = "6",
            time = "15:30",
            date = "Сб, 10 июня",
            addressInfo = "ФОК 'Модун'",
            address = "ул. Петра Алексеева, 205/1, г. Якутск"
        ),
        Training(
            id = "7",
            time = "14:00",
            date = "Вс, 11 июня",
            addressInfo = "Спортзал ЯГСХА",
            address = "ул. Красильникова, 15, г. Якутск"
        )
    )

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
    ): FragmentTrainingMainBinding =
        FragmentTrainingMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).showHeaderBottomNav()

        initRcView()
        trainingAdapter.update(trainingList)
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