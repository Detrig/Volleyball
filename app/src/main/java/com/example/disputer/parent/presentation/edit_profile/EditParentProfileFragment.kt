package com.example.disputer.parent.presentation.edit_profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.disputer.R
import com.example.disputer.children.data.Student
import com.example.disputer.children.presentation.list.ChildrenRcViewAdapter
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ImageUtils
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentEditParentProfileBinding
import com.example.disputer.parent.data.Parent
import java.io.ByteArrayOutputStream

class EditParentProfileFragment : AbstractFragment<FragmentEditParentProfileBinding>() {

    private lateinit var viewModel: EditParentProfileViewModel
    private lateinit var childrenAdapter: ChildrenRcViewAdapter
    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val base64 = ImageUtils.uriToBase64(requireContext(), it)
            viewModel.setSelectedImage(base64)
            displayImage(base64)
        }
    }

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditParentProfileBinding =
        FragmentEditParentProfileBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(EditParentProfileViewModel::class.java)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.infoScreen()
                }
            }
        )

        initRcView()
        loadRcViewList()
        setUpViews()
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.saveButton.setOnClickListener {
            val parent = Parent(
                uid = viewModel.getCurrentParent()?.uid ?: "",
                name = binding.nameEditText.text.toString(),
                email = binding.emailEditText.text.toString(),
                phoneNumber = binding.phoneEditText.text.toString(),
                childIds =  viewModel.parentChildsListLiveData().value?.map { it.uid } ?: listOf()
            )
            viewModel.updateParent(parent)
        }

        binding.changeImageButton.setOnClickListener {
            selectImageFromGallery()
        }
    }

    private fun setUpViews() {
        var currentParent = viewModel.getCurrentParent()

        currentParent?.let {
            if (it.image.isNotEmpty()) {
                displayImage(it.image)
            }
            viewModel.setSelectedImage(it.image)

            binding.nameEditText.setText(it.name)
            binding.phoneEditText.setText(it.phoneNumber)
            binding.emailEditText.setText(it.email)

            binding.addChildButton.setOnClickListener {
                viewModel.addChildrenScreen()
            }

            //Update children live data
            viewModel.getParentChilds(it.uid)
            viewModel.parentChildsListLiveData().observe(viewLifecycleOwner) {
                childrenAdapter.update(ArrayList(it))
            }
        }
    }

    private fun initRcView() {
        childrenAdapter = ChildrenRcViewAdapter(object: ChildrenRcViewAdapter.OnChildrenClickListener {
            override fun onClick(student: Student) {
                viewModel.addChildrenScreen(student)
            }
        })
        binding.childrenRcView.adapter = childrenAdapter
    }

    private fun loadRcViewList() {
        viewModel.parentChildsListLiveData().value?.let {
            childrenAdapter.update(ArrayList(it))
        }
    }

    private fun selectImageFromGallery() {
        imagePickerLauncher.launch("image/*")
    }

    private fun displayImage(base64: String) {
        val bitmap = ImageUtils.base64ToBitmap(base64)
        bitmap?.let {
            Glide.with(binding.profileImage.context)
                .load(it)
                .centerCrop()
                .placeholder(R.drawable.user)
                .into(binding.profileImage)
        } ?: run {
            binding.profileImage.setImageResource(R.drawable.user)
        }
    }
}