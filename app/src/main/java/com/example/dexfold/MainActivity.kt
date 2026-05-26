package com.example.dexfold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.dexfold.ui.navigation.DexFoldNavHost
import com.example.dexfold.ui.theme.DexFoldTheme
import dagger.hilt.android.AndroidEntryPoint

// 👇 Le dice a Hilt que puede inyectar en esta Activity
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DexFoldTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 👇 Por ahora solo esto
                    // En Fase 6 con Hilt agregamos NavController y ViewModels
                    Box(modifier = Modifier.padding(innerPadding)) {
                        DexFoldNavHost(navController = navController)
                    }
                }
            }
        }
    }
}