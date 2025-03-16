package com.example.disputer.disputes.presentation.activedispute

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.disputer.disputes.data.models.DisputeStatus
import com.example.disputer.disputes.domain.models.DisputeItemUiState
import com.example.disputer.R
import com.example.disputer.databinding.DisputeItemBinding

class DisputeRcViewAdapter(
    private val listener: OnDisputeClickListener
) : RecyclerView.Adapter<DisputeRcViewAdapter.DisputeViewHolder>() {

    private val disputes: MutableList<DisputeItemUiState> = mutableListOf()

    fun update(newDisputes : List<DisputeItemUiState>) {
        val diffUtil = DiffUtilCallBack(disputes, newDisputes)
        val diff = DiffUtil.calculateDiff(diffUtil)

        Log.d("ADAPTER", newDisputes.toString())
        disputes.clear()
        disputes.addAll(newDisputes)

        diff.dispatchUpdatesTo(this)
    }

    class DisputeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = DisputeItemBinding.bind(view)


        fun bind(dispute: DisputeItemUiState, listener : OnDisputeClickListener) {
            binding.disputeHeaderTV.text = dispute.header
            binding.disputeDateTV.text = dispute.date
            binding.disputeImage.setImageURI(dispute.image)
            binding.disputeBet.text = dispute.moneyToEnter.toString()
            binding.bank.text = dispute.bank.toString()
            when (dispute.status) {
                DisputeStatus.ACTIVE -> {
                    binding.statusImageView.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.green))
                    binding.statusTV.text = "Активный"
                }

                DisputeStatus.AWAITING_RESPONSE -> {
                    binding.statusImageView.setBackgroundColor(ContextCompat.getColor(binding.root.context,R.color.orange))
                    binding.statusTV.text = "В ожидании"
                }

                DisputeStatus.ENDED -> {
                    binding.statusImageView.setBackgroundColor(ContextCompat.getColor(binding.root.context,R.color.gray))
                    binding.statusTV.text = "Завершен"
                }
            }

            itemView.setOnClickListener {
                listener.onClick(dispute)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisputeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dispute_item, parent, false)
        return DisputeViewHolder(view)
    }

    override fun getItemCount(): Int = disputes.size

    override fun onBindViewHolder(holder: DisputeViewHolder, position: Int) {
        holder.bind(disputes[position], listener)
    }

    class DiffUtilCallBack(
        private val oldList : List<DisputeItemUiState>,
        private val newList : List<DisputeItemUiState>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }

    interface OnDisputeClickListener {
        fun onClick(dispute: DisputeItemUiState)
    }
}