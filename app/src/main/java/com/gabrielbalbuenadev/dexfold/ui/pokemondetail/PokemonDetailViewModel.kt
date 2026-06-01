package com.gabrielbalbuenadev.dexfold.ui.pokemondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbalbuenadev.dexfold.domain.usecase.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonDetailUiState>(
        PokemonDetailUiState.Loading
    )

    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    fun loadPokemonDetail(id: Int) {
        viewModelScope.launch {
            _uiState.value = PokemonDetailUiState.Loading

            val result = getPokemonDetailUseCase(id)

            _uiState.value = when {
                result.isSuccess -> PokemonDetailUiState.Success(
                    pokemon = result.getOrNull()!!
                )
                result.isFailure -> PokemonDetailUiState.Error(
                    message = result.exceptionOrNull()?.message ?: "Error desconocido"
                )
                else -> PokemonDetailUiState.Error("Error desconocido")
            }
        }
    }
}