package com.example.disputer.about_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.core.AbstractFragment
import com.example.disputer.databinding.FragmentAboutAppBinding

class AboutAppFragment : AbstractFragment<FragmentAboutAppBinding>() {

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAboutAppBinding =
        FragmentAboutAppBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}