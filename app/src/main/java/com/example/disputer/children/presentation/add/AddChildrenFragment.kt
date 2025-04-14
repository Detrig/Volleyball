package com.example.disputer.children.presentation.add

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.disputer.children.data.Student
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ImageHelper
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentAddChildrenBinding
import java.util.UUID

class AddChildrenFragment : AbstractFragment<FragmentAddChildrenBinding>() {

    private lateinit var viewModel: AddChildrenViewModel
    private var currentChildren = Student()
    private var selectedImageUri: Uri? = null
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                selectedImageUri = it
                loadSelectedImage(it)
            }
        }

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddChildrenBinding =
        FragmentAddChildrenBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(AddChildrenViewModel::class.java)

        setupViews()

        viewModel.addChildrenUiStateLiveData().observe(viewLifecycleOwner) { uiState ->
            uiState.update(binding)
        }
    }

    private fun setupViews() {
        currentChildren = viewModel.clickedChildrenLiveData().value ?: Student()
        Log.d("VB-10", "clickedChildren: $currentChildren")
        if (currentChildren.uid.isNotEmpty()) binding.deleteButton.visibility = View.VISIBLE

        with(binding) {
            nameEditText.setText(currentChildren.name)
            ageEditText.setText(currentChildren.age.toString())
            phoneEditText.setText(currentChildren.phoneNumber)
        }

        binding.selectPhotoButton.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.saveButton.setOnClickListener {
            saveChildWithPhoto()
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteChildren(currentChildren)
        }
    }

    private fun loadSelectedImage(uri: Uri) {
        try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            // Сжимаем изображение перед отображением
            val compressedBitmap = ImageHelper.compressBitmap(bitmap, 800)
            binding.childPhotoImageView.setImageBitmap(compressedBitmap)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Ошибка загрузки изображения", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun saveChildWithPhoto() {
        val name = binding.nameEditText.text.toString()
        val age = binding.ageEditText.text.toString().toIntOrNull() ?: 6
        val phone = binding.phoneEditText.text.toString()

        if (name.isEmpty() || age <= 0) {
            Toast.makeText(requireContext(), "Заполните обязательные поля", Toast.LENGTH_SHORT)
                .show()
            return
        }


        // Конвертируем изображение в base64 (если выбрано)
        val photoBase64 = selectedImageUri?.let { uri ->
            try {
                val inputStream = requireContext().contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()
                ImageHelper.bitmapToBase64(ImageHelper.compressBitmap(bitmap))
            } catch (e: Exception) {
                null
            }
        } ?: ""

        var children = Student()
        if (currentChildren.uid == "") {
            children = Student(
                name = name,
                age = age,
                phoneNumber = phone,
                parentId = viewModel.getCurrentParentId(),
                trainingIds = emptyList(),
                photoBase64 = photoBase64
            )
        } else {
            children = Student(
                uid = currentChildren.uid,
                name = name,
                age = age,
                phoneNumber = phone,
                parentId = viewModel.getCurrentParentId(),
                trainingIds = emptyList(),
                photoBase64 = photoBase64
            )
        }

        viewModel.addChild(children)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearClickedChildrenLiveData()
    }
}