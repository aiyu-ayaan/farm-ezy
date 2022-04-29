package com.farm.ezy.core.user

import androidx.annotation.Keep

@Keep
data class User(
    val number: String,
    val name: String,
    val profileImage: String,
    val uId: String
)