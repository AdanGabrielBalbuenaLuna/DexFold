package com.example.dexfold.domain.usecase

import com.example.dexfold.domain.model.Pokemon
import com.example.dexfold.domain.repository.PokemonRepository

class GetPokemonDetailUseCase(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): Result<Pokemon> {
        return repository.getPokemonDetail(id)
    }
}