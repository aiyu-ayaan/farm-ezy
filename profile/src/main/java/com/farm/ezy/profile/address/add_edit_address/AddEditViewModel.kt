package com.farm.ezy.profile.address.add_edit_address

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.farm.ezy.core.models.address.AddressSet
import com.farm.ezy.core.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val repository: UserRepository
) : ViewModel() {
    val address = state.get<AddressSet>("address") ?: AddressSet(
        "", "", "", "", "", "", "", "", ""
    )
    val uid = state.get<String>("uid")

}