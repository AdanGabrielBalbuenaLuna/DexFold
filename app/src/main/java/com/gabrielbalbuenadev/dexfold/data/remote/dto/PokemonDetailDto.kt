package com.gabrielbalbuenadev.dexfold.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonDetailDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,

    // 👇 Altura en decímetros (ej: 7 = 0.7m)
    @SerializedName("height") val height: Int,

    // 👇 Peso en hectogramos (ej: 69 = 6.9kg)
    @SerializedName("weight") val weight: Int,

    // 👇 Lista de tipos: fire, water, grass, etc.
    @SerializedName("types") val types: List<TypeSlotDto>,

    // 👇 Las imágenes del pokémon
    @SerializedName("sprites") val sprites: SpritesDto,

    // 👇 Estadísticas: hp, attack, defense, etc.
    @SerializedName("stats") val stats: List<StatSlotDto>
)

// 👇 La API anida los tipos así:
// { "slot": 1, "type": { "name": "fire", "url": "..." } }
data class TypeSlotDto(
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: TypeDto
)

data class TypeDto(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

// 👇 Las imágenes están anidadas así:
// { "front_default": "https://..." }
data class SpritesDto(
    @SerializedName("front_default") val front_default: String?,
    @SerializedName("front_shiny") val front_shiny: String?
)

// 👇 Los stats están anidados así:
// { "base_stat": 45, "stat": { "name": "hp" } }
data class StatSlotDto(
    @SerializedName("base_stat") val base_stat: Int,
    @SerializedName("stat") val stat: StatDto
)

data class StatDto(
    @SerializedName("name") val name: String
)
