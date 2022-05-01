package com.farm.ezy.core.models.order

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Orders(
    val name: String? = null,
    val name_hi: String? = null,
    val imageLink: String? = null,
    val buyOn: Long? = null,
    val cod: Boolean? = null,
    val location: String? = null,
    val number: String? = null,
    val path: String? = null,
    val price: String? = null,
    val quantity: Int? = null
) : Parcelable

class DiffUtilOrders : DiffUtil.ItemCallback<Orders>() {
    override fun areItemsTheSame(oldItem: Orders, newItem: Orders): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Orders, newItem: Orders): Boolean=
        oldItem == newItem

}