package com.example.josue.pokeapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.josue.pokeapp.domain.GetPokemonUseCase
import com.example.josue.pokeapp.domain.model.Pokemondata
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getPokemonUseCase: GetPokemonUseCase) :
    ViewModel() {
    private var _pokemon = MutableLiveData<Pokemondata>()
    val pokemon: LiveData<Pokemondata> get() = _pokemon

    fun onCreate(){
        viewModelScope.launch {
            val result = getPokemonUseCase(25)
            _pokemon.postValue(result)
        }
    }
}