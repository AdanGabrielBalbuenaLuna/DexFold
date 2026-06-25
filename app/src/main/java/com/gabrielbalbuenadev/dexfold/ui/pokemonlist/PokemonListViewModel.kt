package com.gabrielbalbuenadev.dexfold.ui.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbalbuenadev.dexfold.domain.usecase.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    // 👇 MutableStateFlow es privado, solo el ViewModel puede modificarlo
    // Empieza en Loading porque apenas arranca la pantalla
    private val _uiState = MutableStateFlow<PokemonListUiState>(
        PokemonListUiState.Loading
    )

    // 👇 StateFlow público, la UI solo puede leerlo, no modificarlo
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    // 👇 Se llama automáticamente cuando se crea el ViewModel
    init {
        loadPokemonList()
    }

    fun loadPokemonList() {
        // 👇 Lanzamos la coroutine dentro del scope del ViewModel
        viewModelScope.launch {

            // 👇 Paso 1 — Avisamos que estamos cargando
            _uiState.value = PokemonListUiState.Loading

            // 👇 Antes: val result = getPokemonListUseCase()
            // Ahora: collect() recibe múltiples emisiones
            getPokemonListUseCase()
                .catch { e ->
                    // 👇 Si el Flow falla, mostramos error
                    _uiState.value = PokemonListUiState.Error(
                        e.message ?: "Error desconocido"
                    )
                }
                .collect { pokemon ->
                    // 👇 Se llama DOS veces:
                    // 1. con datos locales (inmediato)
                    // 2. con datos frescos (después de API)
                    _uiState.value = if (pokemon.isEmpty()) {
                        PokemonListUiState.Loading
                    } else {
                        PokemonListUiState.Success(pokemon)
                    }
                }
        }
    }
}
