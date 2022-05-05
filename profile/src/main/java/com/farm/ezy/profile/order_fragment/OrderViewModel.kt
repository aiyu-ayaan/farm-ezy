package com.farm.ezy.profile.order_fragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.farm.ezy.core.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {
    val uid = state.get<String>("uid")
    fun getOrders(uid: String) = userRepository.getOrders(uid)

    fun getOrderDetail(type: String, path: String) =
        userRepository.getOrderDetails(type, path)
}