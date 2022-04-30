package com.farm.ezy.core.models.user

import androidx.annotation.Keep

@Keep
data class UserGet(
    val number: String? = null,
    val uId: String? = null,
    val name: String? = null,
    val profileImage: String? = null,
)