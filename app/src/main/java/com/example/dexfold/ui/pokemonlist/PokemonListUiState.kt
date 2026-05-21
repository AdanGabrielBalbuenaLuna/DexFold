package com.example.dexfold.ui.pokemonlist

import com.example.dexfold.domain.model.Pokemon

sealed class PokemonListUiState {
    object Loading : PokemonListUiState()
    data class Success(val pokemon: List<Pokemon>) : PokemonListUiState()
    data class Error(val message: String) : PokemonListUiState()
}