package com.example.dexfold.ui.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dexfold.domain.usecase.GetPokemonListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel(
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

            // 👇 Paso 2 — Llamamos el UseCase (suspend function)
            val result = getPokemonListUseCase()

            // 👇 Paso 3 — Aquí ocurre la conversión Result → UiState
            // que mencionamos antes 🎯
            _uiState.value = when {
                result.isSuccess -> PokemonListUiState.Success(
                    // 👇 getOrNull() extrae los datos del Result
                    pokemon = result.getOrNull() ?: emptyList()
                )
                result.isFailure -> PokemonListUiState.Error(
                    // 👇 message extrae el mensaje del error
                    message = result.exceptionOrNull()?.message ?: "Error desconocido"
                )
                // 👇 Kotlin necesita un else aunque Result
                // solo tiene dos estados
                else -> PokemonListUiState.Error("Error desconocido")
            }
        }
    }
}