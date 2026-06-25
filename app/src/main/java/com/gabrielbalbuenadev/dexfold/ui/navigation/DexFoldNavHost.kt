package com.gabrielbalbuenadev.dexfold.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gabrielbalbuenadev.dexfold.ui.pokemondetail.PokemonDetailScreen
import com.gabrielbalbuenadev.dexfold.ui.pokemondetail.PokemonDetailViewModel
import com.gabrielbalbuenadev.dexfold.ui.pokemonlist.PokemonListScreen
import com.gabrielbalbuenadev.dexfold.ui.pokemonlist.PokemonListViewModel
import androidx.hilt.navigation.compose.hiltViewModel // <--- Add this import
import com.gabrielbalbuenadev.dexfold.core.util.WindowState
import com.gabrielbalbuenadev.dexfold.ui.main.EmptyDetailPane
import com.gabrielbalbuenadev.dexfold.ui.main.TwoPaneLayout

@Composable
fun DexFoldNavHost(
    navController: NavHostController,
    windowState: WindowState  // 👈 recibe el estado del dispositivo
) {
    // 👇 Estado del pokémon seleccionado en modo dos paneles
    var selectedPokemonId by remember { mutableStateOf<Int?>(null) }

    // 👇 ¿Estamos en modo dos paneles?
    val isTwoPane = windowState is WindowState.Expanded

    if (isTwoPane) {
        // 👇 Modo desplegado — dos paneles simultáneos
        val listViewModel = hiltViewModel<PokemonListViewModel>()
        val detailViewModel = hiltViewModel<PokemonDetailViewModel>()

        TwoPaneLayout(
            listContent = {
                PokemonListScreen(
                    viewModel = listViewModel,
                    onPokemonClick = { pokemonId ->
                        // 👇 En dos paneles NO navegamos
                        // solo actualizamos el panel derecho
                        selectedPokemonId = pokemonId
                    }
                )
            },
            detailContent = {
                // 👇 Si hay pokémon seleccionado, mostramos detalle
                // si no, mostramos pantalla vacía
                if (selectedPokemonId != null) {
                    PokemonDetailScreen(
                        pokemonId = selectedPokemonId!!,
                        viewModel = detailViewModel,
                        // 👇 En dos paneles el botón back
                        // limpia la selección en lugar de navegar
                        onBackClick = { selectedPokemonId = null }
                    )
                } else {
                    EmptyDetailPane()
                }
            }
        )
    } else {
        // 👇 Modo plegado — navegación normal
    NavHost(
        navController = navController,
        startDestination = Routes.POKEMON_LIST
    ) {
        // 👇 Kotlin permite omitir el nombre del parámetro
        // cuando es el único o el primero
        // es igual a: composable(route = Routes.POKEMON_LIST)
        composable(
            route = Routes.POKEMON_LIST,
            // 👇 Cuando List entra (regresando del detalle)
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                )
            },
            // 👇 Cuando List sale (yendo al detalle)
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                )
            }
        ) {
            // 👇 Aquí están los delegates que prometí
            // Hilt crea el ViewModel automáticamente
            val pokemonListViewModel = hiltViewModel<PokemonListViewModel>()
            PokemonListScreen(
                viewModel = pokemonListViewModel,
                onPokemonClick = { pokemonId ->
                    // 👇 En un panel SÍ navegamos
                    navController.navigate(Routes.pokemonDetail(pokemonId))
                }
            )
        }

        composable(
            route = Routes.POKEMON_DETAIL,
            arguments = listOf(
                navArgument("pokemonId") { type = NavType.IntType }
            ),
            // 👇 Cuando Detail entra (viniendo de la lista)
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                )
            },
            // 👇 Cuando Detail sale (regresando a la lista)
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300)
                )
            }
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt("pokemonId") ?: return@composable
            // 👇 Hilt crea el ViewModel automáticamente
            val pokemonDetailViewModel = hiltViewModel<PokemonDetailViewModel>()
            PokemonDetailScreen(
                pokemonId = pokemonId,
                viewModel = pokemonDetailViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
}