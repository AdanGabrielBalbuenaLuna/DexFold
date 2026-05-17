package com.example.dexfold.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dexfold.data.local.PokemonDao
import com.example.dexfold.data.local.entity.PokemonEntity

// 👇 entities → todas las tablas que tendrá la BD
// 👇 version  → si cambias la estructura, incrementas esto
@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // 👇 Room implementa esto automáticamente
    abstract fun pokemonDao(): PokemonDao

    companion object {
        // 👇 Nombre del archivo de la BD en el dispositivo
        const val DATABASE_NAME = "dexfold.db"
    }
}