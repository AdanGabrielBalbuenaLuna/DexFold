package com.example.dexfold.di

import com.example.dexfold.data.repository.PokemonRepositoryImpl
import com.example.dexfold.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // 👇 @Binds le dice a Hilt:
    // "cuando alguien pida PokemonRepository
    //  dales PokemonRepositoryImpl"
    @Binds
    @Singleton
    abstract fun bindPokemonRepository(
        impl: PokemonRepositoryImpl
    ): PokemonRepository
}