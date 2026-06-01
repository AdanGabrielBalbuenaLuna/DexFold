package com.gabrielbalbuenadev.dexfold.ui.navigation

object Routes {
    const val POKEMON_LIST   = "pokemon_list"
    const val POKEMON_DETAIL = "pokemon_detail/{pokemonId}"

    // 👇 Función helper para construir la ruta con el ID
    // En lugar de escribir "pokemon_detail/$id" en cada lugar
    fun pokemonDetail(id: Int) = "pokemon_detail/$id"
}