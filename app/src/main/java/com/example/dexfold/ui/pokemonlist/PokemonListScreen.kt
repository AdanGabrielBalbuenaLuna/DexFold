package com.example.dexfold.ui.pokemonlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dexfold.domain.model.Pokemon

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel,
    onPokemonClick: (Int) -> Unit
) {
    // 👇 Kotlin permite omitir el nombre del parámetro
    // cuando es el único o el primero
    // es igual a: viewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    // 👇 Box para poder centrar el spinner y el error
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            // 👇 Mientras carga mostramos un spinner
            is PokemonListUiState.Loading -> {
                CircularProgressIndicator()
            }

            // 👇 Cuando llegaron los datos mostramos la lista
            is PokemonListUiState.Success -> {
                val pokemon = (uiState as PokemonListUiState.Success).pokemon
                PokemonList(
                    pokemonList = pokemon,
                    onPokemonClick = onPokemonClick
                )
            }

            // 👇 Si hubo error mostramos el mensaje
            is PokemonListUiState.Error -> {
                val message = (uiState as PokemonListUiState.Error).message
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

// 👇 Composable separado para la lista
// Separamos la lista de la pantalla para que sea reutilizable
@Composable
fun PokemonList(
    pokemonList: List<Pokemon>,
    onPokemonClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            items = pokemonList,
            // 👇 key mejora el rendimiento de LazyColumn
            // le dice a Compose cómo identificar cada elemento
            key = { pokemon -> pokemon.id }
        ) { pokemon ->
            PokemonCard(
                pokemon = pokemon,
                onPokemonClick = onPokemonClick
            )
        }
    }
}

// 👇 Composable para cada card de pokémon
@Composable
fun PokemonCard(
    pokemon: Pokemon,
    onPokemonClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            // 👇 clickable hace que la card sea tappable
            .clickable { onPokemonClick(pokemon.id) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 👇 Imagen del pokémon con Coil
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                // 👇 Número del pokémon
                Text(
                    text = "#${pokemon.id.toString().padStart(3, '0')}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // 👇 Nombre del pokémon
                Text(
                    text = pokemon.name,
                    style = MaterialTheme.typography.titleMedium
                )

                // 👇 Tipos del pokémon lado a lado
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    pokemon.types.forEach { type ->
                        TypeChip(type = type)
                    }
                }
            }
        }
    }
}

// 👇 Chip para cada tipo de pokémon
@Composable
fun TypeChip(type: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            text = type,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall
        )
    }
}