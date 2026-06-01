package com.gabrielbalbuenadev.dexfold.core.util

import androidx.compose.ui.graphics.Color

object PokemonTypeColors {

    // 👇 Mapa de tipo → color
    // Colores basados en los colores oficiales de cada tipo
    private val typeColors = mapOf(
        "normal"   to Color(0xFFA8A878),
        "fire"     to Color(0xFFFF6B35),
        "water"    to Color(0xFF4FC3F7),
        "electric" to Color(0xFFFFD740),
        "grass"    to Color(0xFF81C784),
        "ice"      to Color(0xFF80DEEA),
        "fighting" to Color(0xFFE57373),
        "poison"   to Color(0xFFBA68C8),
        "ground"   to Color(0xFFDCB464),
        "flying"   to Color(0xFF90CAF9),
        "psychic"  to Color(0xFFF06292),
        "bug"      to Color(0xFFA5D6A7),
        "rock"     to Color(0xFFBCAAA4),
        "ghost"    to Color(0xFF7986CB),
        "dragon"   to Color(0xFF7E57C2),
        "dark"     to Color(0xFF78909C),
        "steel"    to Color(0xFF90A4AE),
        "fairy"    to Color(0xFFF48FB1)
    )

    // 👇 Retorna el color del tipo, gris si no existe
    fun getColor(type: String): Color {
        return typeColors[type.lowercase()] ?: Color(0xFFA8A878)
    }

    // 👇 Retorna el color primario del pokémon
    // basado en su primer tipo
    fun getPrimaryColor(types: List<String>): Color {
        return getColor(types.firstOrNull() ?: "normal")
    }

    // 👇 Versión más clara del color para fondos
    fun getBackgroundColor(type: String): Color {
        return getColor(type).copy(alpha = 0.2f)
    }
}