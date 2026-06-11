package com.gabrielbalbuenadev.dexfold.domain.usecase

import com.gabrielbalbuenadev.dexfold.domain.model.Pokemon
import com.gabrielbalbuenadev.dexfold.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// 👇 Recibe el Repository por constructor
// No sabe si los datos vienen de Room o de Retrofit
// Solo sabe QUÉ pedir, no CÓMO se obtiene
class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    // 👇 Ya no es suspend, Flow no necesita serlo
    operator fun invoke(
        limit: Int = 20,
        offset: Int = 0
    ): Flow<List<Pokemon>> {
        return repository.getPokemonList(limit, offset)
    }
}