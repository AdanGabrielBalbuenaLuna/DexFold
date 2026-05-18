package com.example.dexfold.domain.model

// 👇 Modelo limpio, solo lo que la UI necesita
// Sin snake_case, sin campos innecesarios, sin anotaciones de Room o Retrofit
data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    // 👇 Lista limpia, no el String "grass,poison" de Room
    val types: List<String>,
    val height: Float,      // ya convertido a metros
    val weight: Float,      // ya convertido a kg
    val stats: PokemonStats
)