package com.example.disputer.children.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disputer.children.data.Student
import com.example.disputer.core.AbstractFragment
import com.example.disputer.core.ProvideViewModel
import com.example.disputer.databinding.FragmentMyChildrensBinding

class ChildrenFragment : AbstractFragment<FragmentMyChildrensBinding>() {

    private lateinit var viewModel: ChildrenViewModel
    private lateinit var childrenRcViewAdapter: ChildrenRcViewAdapter

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyChildrensBinding =
        FragmentMyChildrensBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ProvideViewModel).viewModel(ChildrenViewModel::class.java)

        initRcView()
        setUpViews()
    }

    private fun initRcView() {
        childrenRcViewAdapter =
            ChildrenRcViewAdapter(object : ChildrenRcViewAdapter.OnChildrenClickListener {
                override fun onClick(children: Student) {
                    viewModel.addChildrenScreen(children)
                }
            })
        binding.childrenRcView.adapter = childrenRcViewAdapter
    }

    private fun setUpViews() {
        var currentParent = viewModel.getCurrentParent()

        currentParent?.let {

            binding.addChildButton.setOnClickListener {
                viewModel.addChildrenScreen()
            }

            //Update children live data
            viewModel.getParentChilds(it.uid)
            viewModel.parentChildsListLiveData().observe(viewLifecycleOwner) {
                childrenRcViewAdapter.update(ArrayList(it))
            }
        }
    }
}