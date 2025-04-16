package com.example.disputer.parent.presentation.edit_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.disputer.children.data.Student
import com.example.disputer.children.presentation.list.ChildrenRcViewAdapter
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentEditParentProfileBinding
import com.example.disputer.parent.data.Parent

class EditParentProfileFragment : AbstractFragment<FragmentEditParentProfileBinding>() {

    private lateinit var viewModel: EditParentProfileViewModel
    private lateinit var childrenAdapter: ChildrenRcViewAdapter

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
    }

    private fun setUpViews() {
        var currentParent = viewModel.getCurrentParent()

        currentParent?.let {
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


}