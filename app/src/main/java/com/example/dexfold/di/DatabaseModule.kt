package com.example.dexfold.di

import android.content.Context
import androidx.room.Room
import com.example.dexfold.core.database.AppDatabase
import com.example.dexfold.data.local.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
        // 👆 Hilt provee el Context de la app automáticamente
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(database: AppDatabase): PokemonDao {
        // 👆 Hilt inyecta AppDatabase que acabamos de definir arriba
        return database.pokemonDao()
    }
}