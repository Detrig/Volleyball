package com.example.disputer.about_trainings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.disputer.core.AbstractFragment
import com.example.disputer.databinding.FragmentAboutTrainingsBinding

class AboutTrainingsFragment : AbstractFragment<FragmentAboutTrainingsBinding>() {
    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAboutTrainingsBinding =
        FragmentAboutTrainingsBinding.inflate(inflater, container, false)

}