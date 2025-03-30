package com.example.disputer.training.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.disputer.R
import com.example.disputer.authentication.data.Shop
import com.example.disputer.databinding.ShopRcViewItemBinding

class ShopsRcViewAdapter(private val listener : OnShopClickListener) : RecyclerView.Adapter<ShopsRcViewAdapter.ViewHolder>() {

    val list : ArrayList<Shop> = arrayListOf()

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val binding = ShopRcViewItemBinding.bind(view)

        fun bind(shop: Shop, listener : OnShopClickListener) = with(binding) {
            Glide.with(itemView.context)
                .load(shop.imageUrl)
                .placeholder(R.drawable.vbgame)
                .error(R.drawable.vbgame)
                .into(shopImage)

            itemView.setOnClickListener {
                listener.onClick(shop)
            }
        }
    }

    fun update(newList : ArrayList<Shop>) {
        val diffUtil = DiffUtilCallBack(list, newList)
        val diff = DiffUtil.calculateDiff(diffUtil)

        list.clear()
        list.addAll(newList)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shop_rc_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    class DiffUtilCallBack(
        private val old : List<Shop>,
        private val new : List<Shop>
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

    interface OnShopClickListener {
        fun onClick(shop: Shop)
    }
}