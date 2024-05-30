package com.example.gameforgasir

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gameforgasir.ui.navigation.GameForGasIrNavHost


@Composable
fun GameOfGasIrApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    GameForGasIrNavHost(
        navController = navController,
        modifier = modifier
    )
}