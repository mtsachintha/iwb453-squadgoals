package com.singhastudios.momento

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("login") { LogInScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("onboarding") { OnboardingScreen(navController) }
        composable("add") { AddProductScreen(navController) }

    }
}
