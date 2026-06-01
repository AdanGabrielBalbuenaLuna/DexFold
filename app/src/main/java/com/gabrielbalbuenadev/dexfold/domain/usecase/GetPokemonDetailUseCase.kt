package com.gabrielbalbuenadev.dexfold.domain.usecase

import com.gabrielbalbuenadev.dexfold.domain.model.Pokemon
import com.gabrielbalbuenadev.dexfold.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): Result<Pokemon> {
        return repository.getPokemonDetail(id)
    }
}