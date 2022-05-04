package com.farm.ezy.core.models.order

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize

@Keep
data class Orders(
    val name: String? = null,
    val name_hi: String? = null,
    val imageLink: String? = null,
    val buyOn: Long? = null,
    val payment: String? = null,
    val location: String? = null,
    val number: String? = null,
    val path: String? = null,
    val price: String? = null,
    val quantity: Int? = null
)

@Keep
@Parcelize
data class OrdersSet(
    val name: String,
    val name_hi: String,
    val imageLink: String,
    val quantity: Int,
    val price: String,
    val number: String,
    val location: String,
    val buyOn: Long,
    val path: String,
    val payment: String = "Cod"
) : Parcelable

class DiffUtilOrders : DiffUtil.ItemCallback<Orders>() {
    override fun areItemsTheSame(oldItem: Orders, newItem: Orders): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Orders, newItem: Orders): Boolean =
        oldItem == newItem

}