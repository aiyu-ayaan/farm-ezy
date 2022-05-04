package com.farm.ezy.home.item_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.farm.ezy.core.models.items.ItemSet
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val state: SavedStateHandle,
) : ViewModel() {
    val item = state.get<ItemSet>("item")
}