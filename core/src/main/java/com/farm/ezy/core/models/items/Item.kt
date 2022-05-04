package com.farm.ezy.core.models.items

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.farm.ezy.core.utils.EntityMapper
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

@Keep
data class ItemGet(
    val id: Int? = null,
    val name: String? = null,
    val name_hi: String? = null,
    val des: String? = null,
    val des_hi: String? = null,
    val price: Int? = null,
    val size: String? = null,
    val seller: String? = null,
    val dosage: String? = null,
    val currently_avl: Int? = null,
    val img_link1: String? = null,
    val img_link2: String? = null,
    val img_link3: String? = null,
    val img_link4: String? = null,
    val path: String? = null,
    val type: String? = null
)

@Keep
@Parcelize
data class ItemSet(
    val id: Int,
    val name: String,
    val name_hi: String,
    val des: String,
    val des_hi: String,
    val price: Int,
    val size: String,
    val seller: String,
    val dosage: String,
    val currently_avl: Int,
    val img_link1: String,
    val img_link2: String,
    val img_link3: String,
    val img_link4: String,
    val path: String,
    val type: String
) : Parcelable


class DiffUtilsItem : DiffUtil.ItemCallback<ItemGet>() {
    override fun areItemsTheSame(oldItem: ItemGet, newItem: ItemGet): Boolean =
        newItem == oldItem

    override fun areContentsTheSame(oldItem: ItemGet, newItem: ItemGet): Boolean =
        newItem == oldItem
}

class ItemMapper @Inject constructor() : EntityMapper<ItemGet, ItemSet> {
    override fun mapFromEntity(entity: ItemGet): ItemSet =
        ItemSet(
            entity.id!!,
            entity.name!!,
            entity.name_hi!!,
            entity.des!!,
            entity.des_hi!!,
            entity.price!!,
            entity.size!!,
            entity.seller!!,
            entity.dosage!!,
            entity.currently_avl!!,
            entity.img_link1!!,
            entity.img_link2 ?: "",
            entity.img_link3 ?: "",
            entity.img_link4 ?: "",
            entity.path!!,
            entity.type!!
        )

    override fun mapToEntity(domainEntity: ItemSet): ItemGet =
        ItemGet(
            domainEntity.id,
            domainEntity.name,
            domainEntity.name_hi,
            domainEntity.des,
            domainEntity.des_hi,
            domainEntity.price,
            domainEntity.size,
            domainEntity.seller,
            domainEntity.dosage,
            domainEntity.currently_avl,
            domainEntity.img_link1,
            domainEntity.img_link2,
            domainEntity.img_link3,
            domainEntity.img_link4,
            domainEntity.path,
            domainEntity.type
        )
}