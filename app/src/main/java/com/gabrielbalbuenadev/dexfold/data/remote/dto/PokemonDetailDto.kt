package com.gabrielbalbuenadev.dexfold.data.remote.dto

data class PokemonDetailDto(
    val id: Int,
    val name: String,

    // 👇 Altura en decímetros (ej: 7 = 0.7m)
    val height: Int,

    // 👇 Peso en hectogramos (ej: 69 = 6.9kg)
    val weight: Int,

    // 👇 Lista de tipos: fire, water, grass, etc.
    val types: List<TypeSlotDto>,

    // 👇 Las imágenes del pokémon
    val sprites: SpritesDto,

    // 👇 Estadísticas: hp, attack, defense, etc.
    val stats: List<StatSlotDto>
)

// 👇 La API anida los tipos así:
// { "slot": 1, "type": { "name": "fire", "url": "..." } }
data class TypeSlotDto(
    val slot: Int,
    val type: TypeDto
)

data class TypeDto(
    val name: String,
    val url: String
)

// 👇 Las imágenes están anidadas así:
// { "front_default": "https://..." }
data class SpritesDto(
    val front_default: String?,
    val front_shiny: String?
)

// 👇 Los stats están anidados así:
// { "base_stat": 45, "stat": { "name": "hp" } }
data class StatSlotDto(
    val base_stat: Int,
    val stat: StatDto
)

data class StatDto(
    val name: String
)