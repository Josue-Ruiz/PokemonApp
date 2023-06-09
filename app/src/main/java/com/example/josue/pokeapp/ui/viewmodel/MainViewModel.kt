package com.example.josue.pokeapp.ui.viewmodel

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.josue.pokeapp.core.LocationManagerLiveData
import com.example.josue.pokeapp.core.VibrationUtility
import com.example.josue.pokeapp.domain.GetPokemonUseCase
import com.example.josue.pokeapp.domain.model.Pokemondata
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val getPokemonUseCase: GetPokemonUseCase,
) :
    ViewModel() {

    private val vibrator = VibrationUtility.from(context)
    private val locationData = LocationManagerLiveData(context)
    private var _pokemon = MutableLiveData<Pokemondata>()
    val pokemon: LiveData<Pokemondata> get() = _pokemon
    private val _dialogState = MutableLiveData<Boolean>()
    val dialogState: LiveData<Boolean> = _dialogState

    private val _latitude = MutableStateFlow(0.0)
    val latitude: StateFlow<Double> = _latitude
    private val _longitude = MutableStateFlow(0.0)
    val longitude: StateFlow<Double> = _longitude

    init {
        onCreate()
    }

    private fun onCreate() {
        viewModelScope.launch {
            _dialogState.postValue(true)
            val result = getPokemonUseCase()
            if (result.id != 0) {
                vibrator?.vibrate(500L)
                _pokemon.postValue(result)
            }
            _dialogState.postValue(false)
        }
    }

    private fun calculateDistances(lat2: Double, lng2: Double) {
        val results = FloatArray(1)
        Location.distanceBetween(latitude.value, longitude.value, lat2, lng2, results)
        if (results[0] >= 0.5) {
            _latitude.value = lat2
            _longitude.value = lng2
            getRandomPokemon()
        }
    }

    fun setLocationData(latitude: Double, longitude: Double) {
        if (_latitude.value != 0.0 && _longitude.value != 0.0) {
            calculateDistances(latitude, longitude)
        }
        _latitude.value = latitude
        _longitude.value = longitude
    }

    fun getLocationData() = locationData

    fun getRandomPokemon() {
        viewModelScope.launch {
            _dialogState.postValue(true)
            val result = getPokemonUseCase()
            delay(300)
            if (result.id != 0) {
                vibrator?.vibrate(500L)
                _pokemon.postValue(result)
            }
            _dialogState.postValue(false)
        }
    }
}