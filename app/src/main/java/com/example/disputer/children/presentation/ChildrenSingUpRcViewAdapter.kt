package com.example.disputer.children.presentation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.disputer.R
import com.example.disputer.children.data.Student
import com.example.disputer.databinding.ChildPickRcViewItemBinding

class ChildrenSingUpRcViewAdapter(
    private val onSelectionChanged: (Set<Student>) -> Unit,
    private val alreadySignedUpIds: Set<String> = emptySet()
) : RecyclerView.Adapter<ChildrenSingUpRcViewAdapter.ViewHolder>() {

    private val list: ArrayList<Student> = arrayListOf()
    private val selectedChildren = mutableSetOf<String>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ChildPickRcViewItemBinding.bind(view)

        fun bind(
            student: Student,
            isSelected: Boolean,
            isAlreadySignedUp: Boolean,
            onCheckedChange: (Student, Boolean) -> Unit
        ) = with(binding) {
            childNameTextView.text = student.name
            childCheckBox.isChecked = isSelected

            if (isAlreadySignedUp) {
                childCheckBox.isChecked = true
                childCheckBox.isEnabled = false
                childNameTextView.setTextColor(Color.GRAY)
                root.isEnabled = false
            } else {
                childCheckBox.isEnabled = true
                childNameTextView.setTextColor(Color.BLACK)
                root.isEnabled = true
            }

            childCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (!isAlreadySignedUp) {
                    onCheckedChange(student, isChecked)
                }
            }

            root.setOnClickListener {
                if (!isAlreadySignedUp) {
                    childCheckBox.toggle()
                }
            }
        }
    }

    fun update(newList: List<Student>, signedUpIds: Set<String> = emptySet()) {
        val diffUtil = DiffUtilCallBack(list, newList)
        val diff = DiffUtil.calculateDiff(diffUtil)

        list.clear()
        list.addAll(newList)
        diff.dispatchUpdatesTo(this)
    }

    fun getSelectedChildren(): Set<Student> {
        return list.filter { selectedChildren.contains(it.uid) }.toSet()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.child_pick_rc_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = list[position]
        val isAlreadySignedUp = alreadySignedUpIds.contains(student.uid)

        holder.bind(
            student = student,
            isSelected = selectedChildren.contains(student.uid) || isAlreadySignedUp,
            isAlreadySignedUp = isAlreadySignedUp,
            onCheckedChange = { student, isChecked ->
                if (isChecked) {
                    selectedChildren.add(student.uid)
                } else {
                    selectedChildren.remove(student.uid)
                }
                onSelectionChanged(getSelectedChildren())
            }
        )
    }

    class DiffUtilCallBack(
        private val old: List<Student>,
        private val new: List<Student>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = old.size
        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition].uid == new[newItemPosition].uid
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition] == new[newItemPosition]
        }
    }
}