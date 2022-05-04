package com.farm.ezy.home.show_all

import androidx.lifecycle.*
import com.farm.ezy.core.models.items.ItemGet
import com.farm.ezy.core.repositories.HomeRepository
import com.farm.ezy.core.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowAllViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val repository: HomeRepository
) : ViewModel() {

    val type = state.get<String>("type")

    private val _dataState: MutableLiveData<DataState<List<ItemGet>>> =
        MutableLiveData()
    val dataState: LiveData<DataState<List<ItemGet>>>
        get() = _dataState

    fun getData(type: String) = viewModelScope.launch {
        repository.getAll(type).onEach { dataState ->
            _dataState.value = dataState
        }.launchIn(viewModelScope)
    }
}