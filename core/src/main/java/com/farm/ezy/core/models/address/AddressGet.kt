package com.farm.ezy.core.models.address

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.farm.ezy.core.utils.EntityMapper
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

@Keep
data class AddressGet(
    val name: String? = null,
    val number: String? = null,
    val posterCode: String? = null,
    val houseNo: String? = null,
    val area: String? = null,
    val landMark: String? = null,
    val town: String? = null,
    val state: String? = null,
    val path: String? = null,
    val created: Long? = null
)

@Keep
@Parcelize
data class AddressSet(
    val name: String,
    val number: String,
    val posterCode: String,
    val houseNo: String,
    val area: String,
    val landMark: String,
    val town: String,
    val state: String,
    var path: String,
    val created: Long = System.currentTimeMillis()
) : Parcelable

class DiffUtilsAddressGet : DiffUtil.ItemCallback<AddressGet>() {
    override fun areItemsTheSame(oldItem: AddressGet, newItem: AddressGet): Boolean =
        newItem == oldItem

    override fun areContentsTheSame(oldItem: AddressGet, newItem: AddressGet): Boolean =
        newItem == oldItem

}

class AddressMapper @Inject constructor() : EntityMapper<AddressGet, AddressSet> {
    override fun mapFromEntity(entity: AddressGet): AddressSet =
        AddressSet(
            entity.name!!,
            entity.number!!,
            entity.posterCode!!,
            entity.houseNo!!,
            entity.area!!,
            entity.landMark!!,
            entity.town!!,
            entity.state!!,
            entity.path!!,
            entity.created!!
        )


    override fun mapToEntity(domainEntity: AddressSet): AddressGet =
        AddressGet(
            domainEntity.name,
            domainEntity.number,
            domainEntity.posterCode,
            domainEntity.houseNo,
            domainEntity.area,
            domainEntity.landMark,
            domainEntity.town,
            domainEntity.state,
            domainEntity.path,
            domainEntity.created
        )

}