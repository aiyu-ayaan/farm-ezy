package com.farm.ezy.core.models.user

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class User(
    val number: String,
    val uId: String,
    val name: String = "",
    val profileImage: String = "",
) : Parcelable