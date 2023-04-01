package com.example.josue.pokeapp.data.network

import com.example.josue.pokeapp.data.network.model.OfficialArtwork
import com.example.josue.pokeapp.data.network.model.Other
import com.example.josue.pokeapp.data.network.model.ResponsePokeApi
import com.example.josue.pokeapp.data.network.model.Sprites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonApiService @Inject constructor(private val api: PokeApi) {
    suspend fun getPokemonByID(id: Int): ResponsePokeApi {
        return withContext(Dispatchers.IO) {
            val response = api.getPokemonById(id)
            response.body() ?: ResponsePokeApi(0, 0, 0, "", Sprites(Other(OfficialArtwork(""))))
        }
    }
}

