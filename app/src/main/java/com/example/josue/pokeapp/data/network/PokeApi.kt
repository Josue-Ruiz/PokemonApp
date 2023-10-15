package com.example.josue.pokeapp.data.network

import com.example.josue.pokeapp.data.network.model.ResponsePokeApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApi {

    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") id: Int): Response<ResponsePokeApi>
}
