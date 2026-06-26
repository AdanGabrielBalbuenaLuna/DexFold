package com.gabrielbalbuenadev.dexfold.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HalfOpenLayout(
    listContent: @Composable () -> Unit,
    detailContent: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // 👇 Panel superior — lista de pokémon
        // ocupa 50% de la altura
        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
        ) {
            listContent()
        }

        // 👇 Divisor visual — representa la bisagra
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )

        // 👇 Panel inferior — detalle del pokémon
        // ocupa 50% de la altura
        Box(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth()
        ) {
            detailContent()
        }
    }
}