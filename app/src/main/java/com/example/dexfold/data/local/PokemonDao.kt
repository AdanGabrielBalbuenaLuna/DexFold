package com.example.dexfold.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dexfold.data.local.entity.PokemonEntity

// 👇 Le dice a Room que esta interfaz es un DAO
@Dao
interface PokemonDao {

    // 👇 Trae todos los pokémon guardados en la BD
    @Query("SELECT * FROM pokemon")
    suspend fun getAllPokemon(): List<PokemonEntity>

    // 👇 Busca un pokémon específico por ID
    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getPokemonById(id: Int): PokemonEntity?

    // 👇 Guarda una lista de pokémon
    // REPLACE → si ya existe, lo sobreescribe (actualiza)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemon(pokemon: List<PokemonEntity>)

    // 👇 Guarda un solo pokémon (para el detalle)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonEntity)

    // 👇 Borra todo (útil para refrescar datos)
    @Query("DELETE FROM pokemon")
    suspend fun deleteAllPokemon()
}