package com.example.josue.pokeapp.data

import com.example.josue.pokeapp.data.network.model.ResponsePokeApi
import com.example.josue.pokeapp.domain.model.Pokemondata

fun ResponsePokeApi.toPokemonData(): Pokemondata = Pokemondata(
    id = id,
    name = name,
    height = height,
    weight = weight,
    urlImage = sprites.other.officialArtwork.frontDefault
)
