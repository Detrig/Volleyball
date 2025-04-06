package com.example.disputer.parents.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.disputer.R
import com.example.disputer.children.Student
import com.example.disputer.databinding.ChildRcViewItemBinding

class ChildrenRcViewAdapter(private val listener : OnChildClickListener) : RecyclerView.Adapter<ChildrenRcViewAdapter.ViewHolder>() {

    val list : ArrayList<Student> = arrayListOf()

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val binding = ChildRcViewItemBinding.bind(view)

        fun bind(student: Student, listener : OnChildClickListener) = with(binding) {

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

    interface OnChildClickListener {
        fun onClick(student: Student)
    }
}