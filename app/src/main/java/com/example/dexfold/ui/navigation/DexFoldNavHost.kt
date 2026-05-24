package com.example.dexfold.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.dexfold.ui.pokemondetail.PokemonDetailScreen
import com.example.dexfold.ui.pokemondetail.PokemonDetailViewModel
import com.example.dexfold.ui.pokemonlist.PokemonListScreen
import com.example.dexfold.ui.pokemonlist.PokemonListViewModel

@Composable
fun DexFoldNavHost(
    navController: NavHostController,
    pokemonListViewModel: PokemonListViewModel,
    pokemonDetailViewModel: PokemonDetailViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.POKEMON_LIST
    ) {
        // 👇 Kotlin permite omitir el nombre del parámetro
        // cuando es el único o el primero
        // es igual a: composable(route = Routes.POKEMON_LIST)
        composable(Routes.POKEMON_LIST) {
            PokemonListScreen(
                viewModel = pokemonListViewModel,
                onPokemonClick = { pokemonId ->
                    navController.navigate(Routes.pokemonDetail(pokemonId))
                }
            )
        }

        composable(
            route = Routes.POKEMON_DETAIL,
            arguments = listOf(
                navArgument("pokemonId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt("pokemonId") ?: return@composable
            PokemonDetailScreen(
                pokemonId = pokemonId,
                viewModel = pokemonDetailViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}