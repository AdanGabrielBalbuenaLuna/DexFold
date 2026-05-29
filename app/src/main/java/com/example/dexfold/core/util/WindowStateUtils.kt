package com.example.dexfold.core.util

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker

// 👇 Representa todos los posibles estados de nuestra app
sealed class WindowState {
    // 📱 Teléfono plegado — un solo panel
    object Folded : WindowState()
    // 📖 Desplegado completamente — dos paneles
    object Expanded : WindowState()
    // 📚 A medias — modo libro (bisagra horizontal)
    object HalfOpened : WindowState()
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class) // <--- Add this
// 👇 Función que detecta el estado actual del dispositivo
@Composable
fun rememberWindowState(activity: ComponentActivity): WindowState {
    var windowState by remember { mutableStateOf<WindowState>(WindowState.Folded) }

    // 👇 WindowSizeClass — ¿cuánto espacio tenemos?
    val windowSizeClass = calculateWindowSizeClass(activity)

    LaunchedEffect(Unit) {
        WindowInfoTracker
            .getOrCreate(activity)
            .windowLayoutInfo(activity)
            .collect { layoutInfo ->

                // 👇 Buscamos si hay una bisagra (FoldingFeature)
                val foldingFeature = layoutInfo.displayFeatures
                    .filterIsInstance<FoldingFeature>()
                    .firstOrNull()

                windowState = when {
                    // 👇 Desplegado completamente y bisagra plana
                    foldingFeature?.state == FoldingFeature.State.FLAT &&
                            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded
                        -> WindowState.Expanded

                    // 👇 Bisagra a medias (modo libro)
                    foldingFeature?.state == FoldingFeature.State.HALF_OPENED
                        -> WindowState.HalfOpened

                    // 👇 Cualquier otro caso es plegado normal
                    else -> WindowState.Folded
                }
            }
    }

    return windowState
}
