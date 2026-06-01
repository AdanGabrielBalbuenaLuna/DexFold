package com.gabrielbalbuenadev.dexfold.domain.model

// 👇 Cada stat como propiedad nombrada, no "hp:45,attack:49"
data class PokemonStats(
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
)