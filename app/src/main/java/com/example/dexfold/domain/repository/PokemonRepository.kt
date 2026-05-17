package com.example.dexfold.domain.repository

import com.example.dexfold.data.local.entity.PokemonEntity

// 👇 Esta interface vive en domain/ porque es un CONTRATO
// domain no sabe cómo se implementa, solo qué puede pedir
interface PokemonRepository {

    // 👇 Retorna Result<T> de Kotlin — maneja éxito y error elegantemente
    suspend fun getPokemonList(
        limit: Int = 20,
        offset: Int = 0
    ): Result<List<PokemonEntity>>

    suspend fun getPokemonDetail(id: Int): Result<PokemonEntity>
}