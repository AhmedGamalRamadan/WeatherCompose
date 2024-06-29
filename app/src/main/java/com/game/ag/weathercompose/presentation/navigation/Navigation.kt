package com.game.ag.weathercompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.game.ag.weathercompose.presentation.screen.details.WeatherScreen
import com.game.ag.weathercompose.presentation.screen.home.HomeScreen
import com.game.ag.weathercompose.util.Screen


@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.rout
    ) {

        composable(Screen.HomeScreen.rout) {
            HomeScreen(navController)
        }

        composable(Screen.WeatherScreen.rout + "/{city}",
            arguments = listOf(
                navArgument("city") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            WeatherScreen(
                backStackEntry,
                navController
            )
        }

    }

}