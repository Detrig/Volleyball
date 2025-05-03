package com.example.disputer.notification.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.disputer.R
import com.example.disputer.databinding.NotificationRcViewItemBinding
import com.example.disputer.notification.data.NotificationData

class NotificationRcViewAdapter(private val listener : OnNotificationClickListener) : RecyclerView.Adapter<NotificationRcViewAdapter.ViewHolder>() {

    val list : ArrayList<NotificationData> = arrayListOf()

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val binding = NotificationRcViewItemBinding.bind(view)

        fun bind(notificationData: NotificationData, listener : OnNotificationClickListener) = with(binding) {

            binding.notificationsHeaderTV.text = notificationData.title
            binding.notificationMessageTV.text = notificationData.body

            itemView.setOnClickListener {
                listener.onClick(notificationData)
            }
        }
    }

    fun update(newList : ArrayList<NotificationData>) {
        val diffUtil = DiffUtilCallBack(list, newList)
        val diff = DiffUtil.calculateDiff(diffUtil)

        list.clear()
        list.addAll(newList)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_rc_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    class DiffUtilCallBack(
        private val old : List<NotificationData>,
        private val new : List<NotificationData>
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

    interface OnNotificationClickListener {
        fun onClick(NotificationData: NotificationData)
    }
}