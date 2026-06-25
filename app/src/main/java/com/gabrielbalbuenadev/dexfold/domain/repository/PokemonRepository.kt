package com.gabrielbalbuenadev.dexfold.domain.repository

// YA NO USAMOS LA ENTITY
import com.gabrielbalbuenadev.dexfold.domain.model.Pokemon

// 👇 Esta interface vive en domain/ porque es un CONTRATO
// domain no sabe cómo se implementa, solo qué puede pedir
// 👇 Este es el CONTRATO
// domain define QUÉ puede pedir, sin saber CÓMO se implementa
interface PokemonRepository {

    // 👇 Ahora retorna modelos limpios, no Entities
    suspend fun getPokemonList(
        limit: Int = 20,
        offset: Int = 0
    ): Result<List<Pokemon>>

    suspend fun   getPokemonDetail(id: Int): Result<Pokemon>
}
