package com.example.dexfold.ui.pokemondetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dexfold.domain.model.Pokemon
import com.example.dexfold.domain.model.PokemonStats
import com.example.dexfold.ui.pokemonlist.TypeChip

@Composable
fun PokemonDetailScreen(
    pokemonId: Int,
    viewModel: PokemonDetailViewModel,
    onBackClick: () -> Unit
) {
    // 👇 Cuando llega el ID, cargamos el detalle
    LaunchedEffect(pokemonId) {
        viewModel.loadPokemonDetail(pokemonId)
    }

    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is PokemonDetailUiState.Loading -> {
                CircularProgressIndicator()
            }

            is PokemonDetailUiState.Success -> {
                val pokemon = (uiState as PokemonDetailUiState.Success).pokemon
                PokemonDetail(
                    pokemon = pokemon,
                    onBackClick = onBackClick
                )
            }

            is PokemonDetailUiState.Error -> {
                val message = (uiState as PokemonDetailUiState.Error).message
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun PokemonDetail(
    pokemon: Pokemon,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // 👇 Header con imagen y botón de regreso
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
            )

            // 👇 Botón de regreso
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar"
                )
            }
        }

        // 👇 Información del pokémon
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Número y nombre
            Text(
                text = "#${pokemon.id.toString().padStart(3, '0')}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.headlineMedium
            )

            // Tipos
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                pokemon.types.forEach { type ->
                    TypeChip(type = type)
                }
            }

            // Altura y peso
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatInfo(label = "Altura", value = "${pokemon.height}m")
                StatInfo(label = "Peso", value = "${pokemon.weight}kg")
            }

            // Stats
            Text(
                text = "Stats Base",
                style = MaterialTheme.typography.titleMedium
            )
            PokemonStats(stats = pokemon.stats)
        }
    }
}

// 👇 Composable para altura y peso
@Composable
fun StatInfo(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// 👇 Composable para las barras de stats
@Composable
fun PokemonStats(stats: PokemonStats) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        StatBar(label = "HP",              value = stats.hp)
        StatBar(label = "Attack",          value = stats.attack)
        StatBar(label = "Defense",         value = stats.defense)
        StatBar(label = "Sp. Attack",      value = stats.specialAttack)
        StatBar(label = "Sp. Defense",     value = stats.specialDefense)
        StatBar(label = "Speed",           value = stats.speed)
    }
}

// 👇 Barra individual de cada stat
@Composable
fun StatBar(label: String, value: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.width(100.dp),
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = value.toString(),
            modifier = Modifier.width(40.dp),
            style = MaterialTheme.typography.labelMedium
        )
        // 👇 Barra de progreso del stat
        // 255 es el máximo posible en cualquier stat
        LinearProgressIndicator(
            progress = { value / 255f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
        )
    }
}