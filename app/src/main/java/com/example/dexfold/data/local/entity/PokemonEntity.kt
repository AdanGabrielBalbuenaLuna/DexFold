package com.example.dexfold.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// 👇 Le dice a Room que esta clase es una tabla en la BD
@Entity(tableName = "pokemon")
data class PokemonEntity(

    // 👇 Llave primaria, el ID único de cada pokémon
    @PrimaryKey
    val id: Int,

    val name: String,

    // 👇 La URL de la imagen que extraemos del sprite
    val imageUrl: String,

    // 👇 Los tipos separados por coma "fire,flying"
    // Room no guarda listas directamente, las aplanamos
    val types: String,

    val height: Int,
    val weight: Int,

    // 👇 Los stats también los aplanamos
    // "hp:45,attack:49,defense:49"
    val stats: String
)