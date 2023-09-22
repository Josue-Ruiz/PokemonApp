package com.example.josue.pokeapp.data.network.model

import com.google.gson.annotations.SerializedName

data class ResponsePokeApi(
    @SerializedName("id") val id: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("name") val name: String,
    @SerializedName("sprites") val sprites: Sprites,
)

data class Sprites(
    @SerializedName("other") val other: Other,
)

data class Other(
    @SerializedName("official-artwork") val officialArtwork: OfficialArtwork,
)

data class OfficialArtwork(
    @SerializedName("front_default") val frontDefault: String,
)
