package com.example.dexfold.data.repository

import com.example.dexfold.data.local.PokemonDao
import com.example.dexfold.data.remote.PokemonApiService
import com.example.dexfold.domain.repository.PokemonRepository
import com.example.dexfold.data.local.entity.PokemonEntity
import com.example.dexfold.domain.model.Pokemon
import com.example.dexfold.domain.mapper.PokemonMapper
import javax.inject.Inject

// 👇 Implementa el contrato definido en domain/
class PokemonRepositoryImpl @Inject constructor(
    private val apiService: PokemonApiService,
    private val pokemonDao: PokemonDao
) : PokemonRepository {

    override suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ): Result<List<Pokemon>> {
        // Antes: Result<List<PokemonEntity>>  ← Entity cruda de Room // Ahora: Result<List<Pokemon>>  ← modelo limpio de dominio

        return try {
            // 👇 Paso 1 — ¿Tenemos datos locales?
            val localPokemon = pokemonDao.getAllPokemon()

            if (localPokemon.isNotEmpty()) {
                // 👇 Paso 2a — Sí hay datos, los retornamos
                // sin llamar a la API
                // antes - > Result.success(localPokemon)
                // 👇 Ahora mapeamos antes de retornar
                Result.success(PokemonMapper.fromEntityList(localPokemon))
            } else {
                // 👇 Paso 2b — No hay datos, llamamos a la API
                val response = apiService.getPokemonList(limit, offset)

                // 👇 Paso 3 — Convertimos cada resultado a Entity
                val entities = response.results.map { result ->
                    // Extraemos el ID de la URL
                    // "https://pokeapi.co/api/v2/pokemon/1/" → 1
                    val id = result.url
                        .trimEnd('/')
                        .split("/")
                        .last()
                        .toInt()

                    PokemonEntity(
                        id = id,
                        name = result.name,
                        // 👇 URL oficial de sprites de PokeAPI
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
                        types = "",   // se llena con getPokemonDetail
                        height = 0,
                        weight = 0,
                        stats = ""
                    )
                }

                // 👇 Paso 4 — Guardamos en Room
                pokemonDao.insertAllPokemon(entities)

                // 👇 Paso 5 — Retornamos los datos
                // antes -> Result.success(entities)
                // 👇 Mapeamos las entities recién creadas
                Result.success(PokemonMapper.fromEntityList(entities))
            }
        } catch (e: Exception) {
            // 👇 Cualquier error (red, BD) lo capturamos aquí
            Result.failure(e)
        }
    }

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