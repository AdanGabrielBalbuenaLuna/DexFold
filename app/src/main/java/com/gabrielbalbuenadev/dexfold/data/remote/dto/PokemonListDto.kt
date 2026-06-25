package com.gabrielbalbuenadev.dexfold.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonListDto(
    // 👇 Total de pokémon en la API
    @SerializedName("count") val count: Int,

    // 👇 URL para la siguiente página (null si es la última)
    @SerializedName("next") val next: String?,

    // 👇 Lista con nombre y url de cada pokémon
    @SerializedName("results") val results: List<PokemonResultDto>
)

data class PokemonResultDto(
    @SerializedName("name") val name: String,

    // 👇 "https://pokeapi.co/api/v2/pokemon/1/"
    // De esta URL extraeremos el ID del pokémon
    @SerializedName("url") val url: String
)
