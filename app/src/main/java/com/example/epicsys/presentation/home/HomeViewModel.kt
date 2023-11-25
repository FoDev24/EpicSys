package com.example.epicsys.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epicsys.domain.model.AirlineItem
import com.example.epicsys.domain.repository.AirlineRepository
import com.example.epicsys.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AirlineRepository
) : ViewModel() {

    private val _airlines = MutableStateFlow<Resource<List<AirlineItem>>>(Resource.Unspecified())
    val airlines = _airlines.asStateFlow()

    init {
        getAllAirlines()
    }

    private fun getAllAirlines() {
        viewModelScope.launch { _airlines.emit(Resource.Loading()) }

        viewModelScope.launch {
            repository.getAllAirlines()?.let { _airlines.emit(it)}
        }
    }

    fun upsertAirlineDb(airline: AirlineItem) {
        viewModelScope.launch {
            repository.insertDbAirline(airline)
        }
    }
}