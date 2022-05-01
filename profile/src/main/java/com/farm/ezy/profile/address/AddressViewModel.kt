package com.farm.ezy.profile.address

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.farm.ezy.core.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val repository: UserRepository
) : ViewModel() {
    val uid = state.get<String>("uid")
    fun getAddress(uid: String) = repository.getAddress(uid)
}