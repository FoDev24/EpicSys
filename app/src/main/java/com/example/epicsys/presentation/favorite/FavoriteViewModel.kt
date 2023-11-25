package com.example.epicsys.presentation.favorite

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
class FavoriteViewModel @Inject constructor(
    private val repository: AirlineRepository
) : ViewModel() {
    private val _favAirlines = MutableStateFlow<Resource<List<AirlineItem>>>(Resource.Unspecified())
    val favAirlines = _favAirlines.asStateFlow()



    fun showFavAirlines(){
        viewModelScope.launch { _favAirlines.emit(Resource.Loading()) }

        try{
            viewModelScope.launch {
                if(repository.showAllDbAirlines().isNotEmpty())
                    _favAirlines.emit(Resource.Success(repository.showAllDbAirlines()))
            }
        } catch (e:Exception){
            viewModelScope.launch { _favAirlines.emit(Resource.Error(e.message.toString())) }
        }
    }

    fun deleteFavMeal(airline: AirlineItem ){
        try{
            viewModelScope.launch {
                repository.deleteDbAirline(airline)
            }
        } catch (e:Exception){
            viewModelScope.launch { _favAirlines.emit(Resource.Error(e.message.toString())) }
        }
    }
}