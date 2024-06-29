package com.game.ag.weathercompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.game.ag.weathercompose.presentation.screen.HomeScreen
import com.game.ag.weathercompose.util.Screen


@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.rout){

        composable(Screen.HomeScreen.rout){
            HomeScreen()
        }

    }

}