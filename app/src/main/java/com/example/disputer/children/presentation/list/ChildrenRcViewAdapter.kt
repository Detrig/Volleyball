package com.example.disputer.children.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.disputer.R
import com.example.disputer.children.data.Student
import com.example.disputer.core.ImageHelper
import com.example.disputer.databinding.ChildRcViewItemBinding

class ChildrenRcViewAdapter(private val listener : OnChildrenClickListener) : RecyclerView.Adapter<ChildrenRcViewAdapter.ViewHolder>() {

    val list : ArrayList<Student> = arrayListOf()

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val binding = ChildRcViewItemBinding.bind(view)

        fun bind(student: Student, listener : OnChildrenClickListener) = with(binding) {

            childName.text = student.name
            childAge.text = student.age.toString()

            if (student.photoBase64.isNotEmpty()) {
                try {
                    val bitmap = ImageHelper.base64ToBitmap(student.photoBase64)
                    bitmap?.let {
                        childImage.setImageBitmap(it)
                    } ?: run {
                        childImage.setImageResource(R.drawable.user)
                    }
                } catch (e: Exception) {
                    childImage.setImageResource(R.drawable.user)
                }
            } else {
                childImage.setImageResource(R.drawable.user)
            }

            itemView.setOnClickListener {
                listener.onClick(student)
            }
        }
    }

    fun update(newList : ArrayList<Student>) {
        val diffUtil = DiffUtilCallBack(list, newList)
        val diff = DiffUtil.calculateDiff(diffUtil)

        list.clear()
        list.addAll(newList)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.child_rc_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    class DiffUtilCallBack(
        private val old : List<Student>,
        private val new : List<Student>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = old.size

        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = old[oldItemPosition]
            val newItem = new[newItemPosition]

            return oldItem == newItem

        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = old[oldItemPosition]
            val newItem = new[newItemPosition]

            return oldItem == newItem
        }

    }

    interface OnChildrenClickListener {
        fun onClick(student: Student)
    }
}