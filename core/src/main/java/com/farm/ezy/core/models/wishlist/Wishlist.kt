package com.farm.ezy.core.models.wishlist

import androidx.recyclerview.widget.DiffUtil
import com.farm.ezy.core.utils.EntityMapper
import javax.inject.Inject

data class WishlistGet(
    val name: String? = null,
    val name_hi: String? = null,
    val img_link: String? = null,
    val price: String? = null,
    val quantity: Int? = null,
    val type: String? = null,
    val path: String? = null,
    val itemPath: String? = null,
    val addedOn: Long? = null
)

data class WishlistSet(
    val name: String,
    val name_hi: String,
    val img_link: String,
    val price: String,
    val quantity: Int,
    val type: String,
    val path: String,
    val itemPath: String,
    val addedOn: Long = System.currentTimeMillis()
)

class DiffUtilWishlistGet : DiffUtil.ItemCallback<WishlistGet>() {
    override fun areItemsTheSame(oldItem: WishlistGet, newItem: WishlistGet): Boolean =
        newItem == oldItem

    override fun areContentsTheSame(oldItem: WishlistGet, newItem: WishlistGet): Boolean =
        oldItem == newItem

}

class WishListMapper @Inject constructor() : EntityMapper<WishlistGet, WishlistSet> {
    override fun mapFromEntity(entity: WishlistGet): WishlistSet =
        WishlistSet(
            entity.name!!,
            entity.name_hi!!,
            entity.img_link!!,
            entity.price!!,
            entity.quantity!!,
            entity.type!!,
            entity.path!!,
            entity.itemPath!!,
            entity.addedOn!!
        )


    override fun mapToEntity(domainEntity: WishlistSet): WishlistGet =
        WishlistGet()
}