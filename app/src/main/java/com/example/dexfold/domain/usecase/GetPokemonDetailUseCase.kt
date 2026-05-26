package com.example.dexfold.domain.usecase

import com.example.dexfold.domain.model.Pokemon
import com.example.dexfold.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): Result<Pokemon> {
        return repository.getPokemonDetail(id)
    }
}