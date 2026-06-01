package com.gabrielbalbuenadev.dexfold.data.remote

import com.gabrielbalbuenadev.dexfold.data.remote.dto.PokemonDetailDto
import com.gabrielbalbuenadev.dexfold.data.remote.dto.PokemonListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// 👇 Aquí definimos todos los endpoints de la PokeAPI
interface PokemonApiService {

    // 👇 GET https://pokeapi.co/api/v2/pokemon?limit=20&offset=0
    @GET("pokemon")
    suspend fun getPokemonList(
        // 👇 Cuántos pokémon traer por página
        @Query("limit") limit: Int = 20,
        // 👇 Desde qué posición empezar (para paginación)
        @Query("offset") offset: Int = 0
    ): PokemonListDto

    // 👇 GET https://pokeapi.co/api/v2/pokemon/1
    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        // 👇 El {id} en la URL se reemplaza con este valor
        @Path("id") id: Int
    ): PokemonDetailDto
}