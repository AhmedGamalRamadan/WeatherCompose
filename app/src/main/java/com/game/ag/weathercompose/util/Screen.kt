package com.game.ag.weathercompose.util

sealed class Screen(val rout: String){

    object HomeScreen : Screen("home")
}
