package com.gabrielbalbuenadev.dexfold.domain.usecase

import com.gabrielbalbuenadev.dexfold.domain.model.Pokemon
import com.gabrielbalbuenadev.dexfold.domain.repository.PokemonRepository
import javax.inject.Inject

// 👇 Recibe el Repository por constructor
// No sabe si los datos vienen de Room o de Retrofit
// Solo sabe QUÉ pedir, no CÓMO se obtiene
class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    // 👇 operator fun invoke permite llamar el UseCase como función
    // en lugar de getPokemonListUseCase.execute()
    // puedes hacer getPokemonListUseCase()
    suspend operator fun invoke(
        limit: Int = 20,
        offset: Int = 0
    ): Result<List<Pokemon>> {
        return repository.getPokemonList(limit, offset)
    }
}