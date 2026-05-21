package com.example.dexfold.ui.pokemondetail

import com.example.dexfold.domain.model.Pokemon

sealed class PokemonDetailUiState {
    object Loading : PokemonDetailUiState()
    data class Success(val pokemon: Pokemon) : PokemonDetailUiState()
    data class Error(val message: String) : PokemonDetailUiState()
}