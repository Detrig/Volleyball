package com.example.disputer.coach.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.disputer.R
import com.example.disputer.coach.data.Coach
import com.example.disputer.databinding.CoachRcViewItemBinding

class CoachRcViewAdapter(private val listener: OnCoachClickListener) :
    RecyclerView.Adapter<CoachRcViewAdapter.ViewHolder>() {

    private val list: ArrayList<Coach> = arrayListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CoachRcViewItemBinding.bind(view)

        fun bind(coach: Coach, listener: OnCoachClickListener) = with(binding) {
            coachNameTV.text = coach.name
            phoneNumberTV.text = coach.phoneNumber

            Glide.with(itemView.context)
                .load(coach.imageUrl)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(coachImage)

            coachQualification.text = coach.qualification

            itemView.setOnClickListener {
                listener.onClick(coach)
            }
        }
    }

    fun update(newList: ArrayList<Coach>) {
        val diffUtil = DiffUtilCallBack(list, newList)
        val diff = DiffUtil.calculateDiff(diffUtil)

        list.clear()
        list.addAll(newList)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.coach_rc_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    class DiffUtilCallBack(
        private val old: List<Coach>,
        private val new: List<Coach>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = old.size

        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = old[oldItemPosition]
            val newItem = new[newItemPosition]

            return oldItem.uid == newItem.uid && oldItem.uid == newItem.uid

        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = old[oldItemPosition]
            val newItem = new[newItemPosition]

            return oldItem.uid == newItem.uid && oldItem.uid == newItem.uid
        }

    }

    interface OnCoachClickListener {
        fun onClick(coach: Coach)
    }
}
