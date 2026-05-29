package com.example.dexfold.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TwoPaneLayout(
    // 👇 Recibe composables como parámetros
    // esto permite que TwoPaneLayout sea reutilizable
    // con cualquier contenido
    listContent: @Composable () -> Unit,
    detailContent: @Composable () -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {

        // 👇 Panel izquierdo — lista de pokémon
        Box(
            modifier = Modifier
                .weight(0.4f)  // 40% del ancho
                .fillMaxHeight()
        ) {
            listContent()
        }

        // 👇 Divisor visual entre paneles
        HorizontalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        // 👇 Panel derecho — detalle del pokémon
        Box(
            modifier = Modifier
                .weight(0.6f)  // 60% del ancho
                .fillMaxHeight()
        ) {
            detailContent()
        }
    }
}

// 👇 Pantalla vacía cuando no hay pokémon seleccionado
// en modo dos paneles
@Composable
fun EmptyDetailPane() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "<-",
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = "Selecciona un Pokémon",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}