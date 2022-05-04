package com.farm.ezy.home

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
class HomeViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val repository: HomeRepository
) : ViewModel() {

    init {
        fetchData()
    }

    private val _dataStateInsecticides: MutableLiveData<DataState<List<ItemGet>>> =
        MutableLiveData()
    val dataStateInsecticides: LiveData<DataState<List<ItemGet>>>
        get() = _dataStateInsecticides

    private val _dataStateHerbicides: MutableLiveData<DataState<List<ItemGet>>> =
        MutableLiveData()

    val dataStateHerbicides: LiveData<DataState<List<ItemGet>>>
        get() = _dataStateHerbicides

    private val _dataStateGrowthPromoter: MutableLiveData<DataState<List<ItemGet>>> =
        MutableLiveData()

    val dataStateGrowthPromoter: LiveData<DataState<List<ItemGet>>>
        get() = _dataStateGrowthPromoter


    private fun fetchData() = viewModelScope.launch {
        repository.getInsecticides().onEach { dataState ->
            _dataStateInsecticides.value = dataState
        }.launchIn(viewModelScope)
        repository.getGrowthPromoter().onEach { dataState ->
            _dataStateGrowthPromoter.value = dataState
        }.launchIn(viewModelScope)

        repository.getHerbicides().onEach { dataState ->
            _dataStateHerbicides.value = dataState
        }.launchIn(viewModelScope)
    }

}