package com.farm.ezy.profile.wishlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.farm.ezy.core.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val repository: UserRepository
) : ViewModel() {

    val uid = state.get<String>("uid")

    fun getWishLists(uid: String) = repository.getWishList(uid)

    fun getOrderDetail(type: String, path: String) =
        repository.getOrderDetails(type, path)
}