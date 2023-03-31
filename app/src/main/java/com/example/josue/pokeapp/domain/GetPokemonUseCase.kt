package com.example.josue.pokeapp.domain

import com.example.josue.pokeapp.data.PokemonRepository
import com.example.josue.pokeapp.domain.model.Pokemondata
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(private val repository: PokemonRepository) {
    suspend operator fun invoke(id: Int): Pokemondata {
        return repository.getPokemon(id)
    }
}