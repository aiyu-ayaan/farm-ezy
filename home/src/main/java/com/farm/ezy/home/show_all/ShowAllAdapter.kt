package com.farm.ezy.home.show_all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.farm.ezy.core.models.items.DiffUtilsItem
import com.farm.ezy.core.models.items.ItemGet
import com.farm.ezy.core.utils.loadImageDefault
import com.farm.ezy.home.databinding.RowHomeBinding

class ShowAllAdapter constructor(
    private val listener: (ItemGet) -> Unit
) : ListAdapter<ItemGet, ShowAllAdapter.HomeItemView>(DiffUtilsItem()) {

    inner class HomeItemView(
        private val binding: RowHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val pos = absoluteAdapterPosition
                if (pos != RecyclerView.NO_POSITION)
                    listener.invoke(getItem(pos))
            }
        }

        fun bind(item: ItemGet) = binding.apply {
            textViewItemName.text = item.name
            textViewPrice.text =
                binding.root.context.resources.getString(
                    com.farm.ezy.core.R.string.price,
                    item.price.toString()
                )
            item.img_link1?.loadImageDefault(
                binding.root,
                binding.imageViewItem,
                binding.progressBarRowItem
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemView =
        HomeItemView(
            RowHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HomeItemView, position: Int) {
        holder.bind(getItem(position))
    }
}