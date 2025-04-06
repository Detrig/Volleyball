package com.example.disputer.shop.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentAddShopBinding
import com.example.disputer.shop.data.Shop

class AddShopFragment : AbstractFragment<FragmentAddShopBinding>() {

    private lateinit var viewModel : ShopViewModel
    private var selectedImageUri: Uri? = null


    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddShopBinding =
        FragmentAddShopBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(ShopViewModel::class.java)

        binding.selectImageButton.setOnClickListener {
            openImagePicker()
        }

        binding.saveButton.setOnClickListener {
            val shop = Shop(
                binding.titleTextView.text.toString(),
                binding.shopImageView.toString()
            )
            viewModel.addShop(shop)
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    companion object {
        private const val REQUEST_IMAGE_PICK = 1001
        private const val MAX_IMAGE_SIZE = 990_000 // ~1MB
    }
}