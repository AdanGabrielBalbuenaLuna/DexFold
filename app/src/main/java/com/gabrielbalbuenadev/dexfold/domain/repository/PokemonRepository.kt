package com.gabrielbalbuenadev.dexfold.domain.repository

// YA NO USAMOS LA ENTITY
import com.gabrielbalbuenadev.dexfold.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

// 👇 Esta interface vive en domain/ porque es un CONTRATO
// domain no sabe cómo se implementa, solo qué puede pedir
// 👇 Este es el CONTRATO
// domain define QUÉ puede pedir, sin saber CÓMO se implementa
interface PokemonRepository {

    // 👇 Ahora retorna modelos limpios, no Entities
    // 👇 Antes retornaba Result<List<Pokemon>>
    // Ahora retorna Flow<List<Pokemon>>
    fun getPokemonList(
        limit: Int = 20,
        offset: Int = 0
    ): Flow<List<Pokemon>>

    // 👇 El detalle puede quedarse como suspend
    // porque solo necesita UN valor, no múltiples
    suspend fun   getPokemonDetail(id: Int): Result<Pokemon>
}
