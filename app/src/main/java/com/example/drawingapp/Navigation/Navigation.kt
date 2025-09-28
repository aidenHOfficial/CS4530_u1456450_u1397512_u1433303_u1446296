package com.example.drawingapp.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.example.drawingapp.screens.SplashScreen
import com.example.drawingapp.screens.DrawingScreen

@Composable
fun AppNavHost(navController: NavHostController, startDestination: String = "launch") {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("launch") {
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(3000)
                navController.navigate("drawing") {
                    popUpTo("launch") {
                        inclusive = true
                    }
                }
            }
            SplashScreen()
        }

        composable("drawing") {
            DrawingScreen(navController)
        }
    }
}