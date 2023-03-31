package com.example.josue.pokeapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.josue.pokeapp.core.VibrationUtility
import com.example.josue.pokeapp.domain.GetPokemonUseCase
import com.example.josue.pokeapp.domain.model.Pokemondata
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val getPokemonUseCase: GetPokemonUseCase,
) :
    ViewModel() {

    private val vibrator = VibrationUtility.from(context)
    private var _pokemon = MutableLiveData<Pokemondata>()
    val pokemon: LiveData<Pokemondata> get() = _pokemon
    private val _dialogState = MutableLiveData<Boolean>()
    val dialogState: LiveData<Boolean> = _dialogState

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