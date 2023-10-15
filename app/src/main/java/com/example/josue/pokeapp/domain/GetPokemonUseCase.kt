package com.example.josue.pokeapp.domain

import com.example.josue.pokeapp.data.PokemonRepository
import com.example.josue.pokeapp.domain.model.Pokemondata
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(private val repository: PokemonRepository) {
    suspend operator fun invoke(): Pokemondata {
        val id = (1..1010).random()
        return repository.getPokemon(id)
    }
}
