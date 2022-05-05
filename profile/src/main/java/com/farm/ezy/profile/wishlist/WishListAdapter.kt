package com.farm.ezy.profile.wishlist

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.farm.ezy.core.models.wishlist.DiffUtilWishlistGet
import com.farm.ezy.core.models.wishlist.WishlistGet
import com.farm.ezy.core.utils.loadImageDefault
import com.farm.ezy.profile.databinding.RowWishListBinding

class WishListAdapter(
    private val buy: (WishlistGet) -> Unit,
    private val delete: (WishlistGet) -> Unit,
) :
    ListAdapter<WishlistGet, WishListAdapter.WishListViewHolder>(DiffUtilWishlistGet()) {

    inner class WishListViewHolder(
        private val binding: RowWishListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                showHideViews()
            }
            binding.buttonBuy.setOnClickListener {
                val pos = absoluteAdapterPosition
                if (pos != RecyclerView.NO_POSITION)
                    buy.invoke(getItem(pos))
            }
            binding.buttonRemove.setOnClickListener {
                val pos = absoluteAdapterPosition
                if (pos != RecyclerView.NO_POSITION)
                    delete.invoke(getItem(pos))
            }

        }

        private fun showHideViews() = binding.apply {
            TransitionManager.beginDelayedTransition(
                binding.constraintLayoutOrders,
                AutoTransition()
            )
            val v =
                if (binding.relativeLayoutContent.visibility == View.GONE) View.VISIBLE else View.GONE
            binding.relativeLayoutContent.visibility = v

        }

        fun bind(wishlist: WishlistGet) = binding.apply {
            textViewItemName.text = wishlist.name
            textViewPrice.text =
                binding.root.context.resources.getString(
                    com.farm.ezy.core.R.string.price,
                    wishlist.price
                )
            textViewQuantity.text =
                binding.root.context.resources.getString(
                    com.farm.ezy.core.R.string.quantity,
                    wishlist.quantity.toString()
                )
            wishlist.img_link?.loadImageDefault(
                binding.root,
                binding.imageViewItem,
                binding.progressBarRowOrders
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder =
        WishListViewHolder(
            RowWishListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}