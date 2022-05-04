package com.farm.ezy.core.models.order

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.farm.ezy.core.utils.EntityMapper
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

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
    val quantity: Int? = null,
    val type: String? = null
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
    val payment: String = "Cod",
    val type: String?
) : Parcelable

class DiffUtilOrders : DiffUtil.ItemCallback<Orders>() {
    override fun areItemsTheSame(oldItem: Orders, newItem: Orders): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Orders, newItem: Orders): Boolean =
        oldItem == newItem

}

class OrderMapper @Inject constructor() : EntityMapper<Orders, OrdersSet> {
    override fun mapFromEntity(entity: Orders): OrdersSet =
        OrdersSet(
            entity.name!!,
            entity.name_hi!!,
            entity.imageLink!!,
            entity.quantity!!,
            entity.price!!,
            entity.number!!,
            entity.location!!,
            entity.buyOn!!,
            entity.path!!,
            entity.payment!!,
            entity.type
        )

    override fun mapToEntity(domainEntity: OrdersSet): Orders =
        Orders()

}