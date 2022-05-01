package com.farm.ezy.core.repositories

import androidx.annotation.Keep


@Keep
data class Component(
    val name: String,
    val link: String
)

object ComponentUse {
    val list = listOf<Component>(
        Component(
            "androidx.core:core-ktx",
            "https://developer.android.com/jetpack/androidx/releases/core"
        )
    )
}