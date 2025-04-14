package com.example.disputer.training.presentation.training_parent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.disputer.R
import com.example.disputer.training.data.Training
import com.example.disputer.databinding.TrainingsRcViewItemBinding

class TrainingsRecyclerViewAdapter(private val listener: OnTrainingClickListener) : RecyclerView.Adapter<TrainingsRecyclerViewAdapter.ViewHolder>() {

    val list : ArrayList<Training> = arrayListOf()

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val binding = TrainingsRcViewItemBinding.bind(view)

        fun bind(training: Training, listener : OnTrainingClickListener) = with(binding) {
            closableData.text = training.group
            time.text = training.time
            date.text = training.date
            addressInfoTV.text = training.addressInfo
            addressTV.text = training.address

            itemView.setOnClickListener {
                listener.onClick(training)
            }
        }
    }

    fun update(newList : ArrayList<Training>) {
        val diffUtil = DiffUtilCallBack(list, newList)
        val diff = DiffUtil.calculateDiff(diffUtil)

        list.clear()
        list.addAll(newList)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trainings_rc_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    class DiffUtilCallBack(
        private val old : List<Training>,
        private val new : List<Training>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = old.size

        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = old[oldItemPosition]
            val newItem = new[newItemPosition]

            return oldItem.id == newItem.id && oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = old[oldItemPosition]
            val newItem = new[newItemPosition]

            return oldItem.id == newItem.id && oldItem.id == newItem.id
        }

    }

    interface OnTrainingClickListener {
        fun onClick(training: Training)
    }
}