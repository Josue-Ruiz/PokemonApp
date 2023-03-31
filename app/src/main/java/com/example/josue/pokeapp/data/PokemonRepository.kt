package com.example.josue.pokeapp.data

import com.example.josue.pokeapp.data.network.PokemonApiService
import com.example.josue.pokeapp.domain.model.Pokemondata
import javax.inject.Inject

class PokemonRepository @Inject constructor(private  val api: PokemonApiService) {
    suspend fun getPokemon(id:Int):Pokemondata{
        val  response = api.getPokemonByID(id)
        return response.toPokemonData()
    }
}