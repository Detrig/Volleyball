package com.example.disputer.parent.presentation

import android.view.View
import com.bumptech.glide.Glide
import com.example.disputer.children.data.Student
import com.example.disputer.databinding.FragmentParentProfileBinding
import com.example.disputer.parent.data.Parent
import com.example.disputer.parent.presentation.adapter.ChildrenRcViewAdapter
import com.example.disputer.training.presentation.training_parent.TrainingsRecyclerViewAdapter

interface ParentUiState {

    fun update(
        binding: FragmentParentProfileBinding,
        childrenRcViewAdapter: ChildrenRcViewAdapter,
        trainingsRcViewAdapter: TrainingsRecyclerViewAdapter
    ) {
        binding.profileLayout.visibility = View.VISIBLE
        binding.childrenLabel.visibility = View.VISIBLE
        binding.childrenRcView.visibility = View.VISIBLE
        binding.trainingsLabel.visibility = View.VISIBLE
        binding.trainingsRcView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    object Loading : ParentUiState {
        override fun update(
            binding: FragmentParentProfileBinding,
            childrenRcViewAdapter: ChildrenRcViewAdapter,
            trainingsRcViewAdapter: TrainingsRecyclerViewAdapter
        ) {
            binding.profileLayout.visibility = View.INVISIBLE
            binding.childrenLabel.visibility = View.INVISIBLE
            binding.childrenRcView.visibility = View.INVISIBLE
            binding.trainingsLabel.visibility = View.INVISIBLE
            binding.trainingsRcView.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    data class Success(
        private val parent: Parent,
        private val childs: List<Student>,
    ) : ParentUiState {
        override fun update(
            binding: FragmentParentProfileBinding,
            childrenRcViewAdapter: ChildrenRcViewAdapter,
            trainingsRcViewAdapter: TrainingsRecyclerViewAdapter
        ) {
            binding.parentName.text = parent.name
            binding.parentPhone.text = parent.phoneNumber
            Glide.with(binding.constraint.context)
                .load(parent.image)
                .into(binding.parentImage)

            childrenRcViewAdapter.update(ArrayList(childs))
        }
    }

    data class Error(val message: String) : ParentUiState
}
