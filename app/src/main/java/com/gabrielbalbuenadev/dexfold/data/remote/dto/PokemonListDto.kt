package com.gabrielbalbuenadev.dexfold.data.remote.dto

data class PokemonListDto(
    // 👇 Total de pokémon en la API
    val count: Int,

    // 👇 URL para la siguiente página (null si es la última)
    val next: String?,

    // 👇 Lista con nombre y url de cada pokémon
    val results: List<PokemonResultDto>
)

data class PokemonResultDto(
    val name: String,

    // 👇 "https://pokeapi.co/api/v2/pokemon/1/"
    // De esta URL extraeremos el ID del pokémon
    val url: String
)