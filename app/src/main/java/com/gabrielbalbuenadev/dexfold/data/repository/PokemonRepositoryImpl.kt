package com.gabrielbalbuenadev.dexfold.data.repository

import com.gabrielbalbuenadev.dexfold.data.local.PokemonDao
import com.gabrielbalbuenadev.dexfold.data.remote.PokemonApiService
import com.gabrielbalbuenadev.dexfold.domain.repository.PokemonRepository
import com.gabrielbalbuenadev.dexfold.data.local.entity.PokemonEntity
import com.gabrielbalbuenadev.dexfold.domain.model.Pokemon
import com.gabrielbalbuenadev.dexfold.domain.mapper.PokemonMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// 👇 Implementa el contrato definido en domain/
class PokemonRepositoryImpl @Inject constructor(
    private val apiService: PokemonApiService,
    private val pokemonDao: PokemonDao
) : PokemonRepository {

    override fun getPokemonList(
        limit: Int,
        offset: Int
    ): Flow<List<Pokemon>> = flow {
        // Antes: Result<List<PokemonEntity>>  ← Entity cruda de Room // Ahora: Result<List<Pokemon>>  ← modelo limpio de dominio

        // 👇 Paso 1 — Emite datos locales inmediatamente
        // aunque estén vacíos o incompletos
        val localPokemon = pokemonDao.getAllPokemon()
        emit(PokemonMapper.fromEntityList(localPokemon))

        // 👇 Paso 2 — Llama a la API en segundo plano
        try {
            val response = apiService.getPokemonList(limit, offset)

            val entities = response.results.map { result ->
                val id = result.url
                    .trimEnd('/')
                    .split("/")
                    .last()
                    .toInt()

                PokemonEntity(
                    id = id,
                    name = result.name,
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
                    types = "",
                    height = 0,
                    weight = 0,
                    stats = ""
                )
            }

            // 👇 Paso 3 — Actualiza Room con datos frescos
            pokemonDao.insertAllPokemon(entities)

            // 👇 Paso 4 — Emite los datos actualizados
            emit(PokemonMapper.fromEntityList(pokemonDao.getAllPokemon()))

        } catch (e: Exception) {
            // 👇 Si la API falla, no pasa nada
            // ya emitimos los datos locales en el Paso 1
            // el usuario ya ve algo en pantalla ✅
        }
    }

    // 👇 getPokemonDetail no cambia
    override suspend fun getPokemonDetail(id: Int): Result<Pokemon> {
        return try {
            // 👇 Paso 1 — ¿Tenemos el detalle en local?
            val localPokemon = pokemonDao.getPokemonById(id)

            // 👇 Si tiene tipos es que ya tiene el detalle completo
            if (localPokemon != null && localPokemon.types.isNotEmpty()) {
                // antes -> Result.success(localPokemon)
                Result.success(PokemonMapper.fromEntity(localPokemon))
            } else {
                // 👇 Paso 2 — Llamamos al detalle en la API
                val response = apiService.getPokemonDetail(id)

                // 👇 Paso 3 — Convertimos a Entity con todos los datos
                val entity = PokemonEntity(
                    id = response.id,
                    name = response.name,
                    imageUrl = response.sprites.front_default ?: "",
                    // 👇 List<TypeSlotDto> → "fire,flying"
                    types = response.types
                        .sortedBy { it.slot }
                        .joinToString(",") { it.type.name },
                    height = response.height,
                    weight = response.weight,
                    // 👇 List<StatSlotDto> → "hp:45,attack:49"
                    stats = response.stats
                        .joinToString(",") { "${it.stat.name}:${it.base_stat}" }
                )

                // 👇 Paso 4 — Actualizamos en Room
                pokemonDao.insertPokemon(entity)

                // Antes -> Result.success(entity)
                // 👇 Mapeamos la entity recién creada
                Result.success(PokemonMapper.fromEntity(entity))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}