package com.example.josue.pokeapp.ui.viewmodel

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.josue.pokeapp.core.Constants.DELAY_DURATION
import com.example.josue.pokeapp.core.Constants.LONGEST_DEFINED_DISTANCE
import com.example.josue.pokeapp.core.Constants.VIBRATION_DURATION
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
    private val latitude: StateFlow<Double> = _latitude
    private val _longitude = MutableStateFlow(0.0)
    private val longitude: StateFlow<Double> = _longitude

    init {
        onCreate()
    }

    private fun onCreate() {
        viewModelScope.launch {
            _dialogState.postValue(true)
            val result = getPokemonUseCase()
            if (result.id != 0) {
                vibrator?.vibrate(VIBRATION_DURATION)
                _pokemon.postValue(result)
            }
            _dialogState.postValue(false)
        }
    }

    private fun calculateDistances(newLatObtained: Double, newLongObtained: Double) {
        val results = FloatArray(1)
        Location.distanceBetween(latitude.value, longitude.value, newLatObtained, newLongObtained, results)
        if (results[0] >= LONGEST_DEFINED_DISTANCE) {
            _latitude.value = newLatObtained
            _longitude.value = newLongObtained
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
            delay(DELAY_DURATION)
            if (result.id != 0) {
                vibrator?.vibrate(VIBRATION_DURATION)
                _pokemon.postValue(result)
            }
            _dialogState.postValue(false)
        }
    }
}
