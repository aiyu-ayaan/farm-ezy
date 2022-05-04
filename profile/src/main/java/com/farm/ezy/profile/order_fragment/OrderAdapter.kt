package com.farm.ezy.profile.order_fragment

import android.animation.LayoutTransition
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.farm.ezy.core.models.order.DiffUtilOrders
import com.farm.ezy.core.models.order.Orders
import com.farm.ezy.core.utils.convertLongToTime
import com.farm.ezy.core.utils.loadImageDefault
import com.farm.ezy.profile.databinding.RowOrdersBinding

class OrderAdapter(
    private val listener: ((Orders) -> Unit)
) : ListAdapter<Orders, OrderAdapter.OrderItemView>(DiffUtilOrders()) {

    inner class OrderItemView(private val binding: RowOrdersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.constraintLayoutOrders.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            binding.root.setOnClickListener {
                val pos = absoluteAdapterPosition
                if (pos != RecyclerView.NO_POSITION)
                    showHideViews()
            }
            binding.textViewShowDetail.setOnClickListener {
                val pos = absoluteAdapterPosition
                if (pos != RecyclerView.NO_POSITION)
                    listener.invoke(getItem(pos))
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

        fun bind(order: Orders) {
            binding.apply {
                textViewItemName.text = order.name
                textViewDate.text =
                    binding.root.context.resources.getString(
                        com.farm.ezy.core.R.string.delivered_at,
                        order.buyOn!!.convertLongToTime()
                    )
                textViewPrice.text =
                    binding.root.context.resources.getString(
                        com.farm.ezy.core.R.string.money,
                        order.price
                    )
                textViewAddress.text =
                    binding.root.context.resources.getString(
                        com.farm.ezy.core.R.string.delivered_to,
                        order.location
                    )
                textViewQuantity.text =
                    binding.root.context.resources.getString(
                        com.farm.ezy.core.R.string.quantity,
                        order.quantity.toString()
                    )
                textViewPaymentType.text = binding.root.context.resources.getString(
                    com.farm.ezy.core.R.string.payment_type,
                    order.payment.toString()
                )
                order.imageLink!!.loadImageDefault(
                    binding.root,
                    binding.imageViewItem,
                    binding.progressBarRowOrders
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemView =
        OrderItemView(
            RowOrdersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: OrderItemView, position: Int) {
        holder.bind(getItem(position))
    }
}