package com.example.virtualwizard.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.virtualwizard.ui.screens.*

object Routes {
    const val LOADING_SCREEN = "loading_screen"
    const val MAIN_MENU = "main_menu"
    const val SETTINGS = "settings"
    const val DEFEAT = "defeat"
    const val VICTORY = "victory"
    const val GAMEPLAY = "gameplay/{state}"
    const val FIGHTING_SCREEN = "fighting_screen/{state}"
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOADING_SCREEN
    ) {
        // Loading Screen
        composable(Routes.LOADING_SCREEN) {
            LoadingScreen(onNavigateToMenu = { navController.navigate(Routes.MAIN_MENU) })
        }

        // Main Menu
        composable(Routes.MAIN_MENU) {
            MainMenu(
                onResumeGame = { navController.navigate(Routes.GAMEPLAY.replace("{state}", "resume")) },
                onStartNewGame = { navController.navigate(Routes.GAMEPLAY.replace("{state}", "new")) },
                onOptions = { navController.navigate(Routes.SETTINGS) },
                onExit = { exitApp() }
            )
        }

        // Settings
        composable(Routes.SETTINGS) {
            Settings(onBackToMenu = { navController.navigateUp() })
        }

        // Defeat Screen
        composable(Routes.DEFEAT) {
            Defeat(onBackToMenu = { navController.navigate(Routes.MAIN_MENU) })
        }

        // Victory Screen
        composable(Routes.VICTORY) {
            Victory(onBackToMenu = { navController.navigate(Routes.MAIN_MENU) })
        }

        // Gameplay Screen (state-based navigation)
        composable(Routes.GAMEPLAY) { backStackEntry ->
            val state = backStackEntry.arguments?.getString("state")
            Gameplay(
                state = state ?: "1",
                onNavigateToFighting = { navController.navigate(Routes.FIGHTING_SCREEN.replace("{state}", state ?: "1")) }
            )
        }

        // Fighting Screen (state-based navigation)
        composable(Routes.FIGHTING_SCREEN) { backStackEntry ->
            val state = backStackEntry.arguments?.getString("state")
            FightingScreen(
                state = state ?: "1",
                onVictory = { onVictory(navController) },
                onDefeat = { onDefeat(navController) }
            )
        }
    }
}

@Composable
fun Victory(onBackToMenu: () -> Unit) {
    TODO("Not yet implemented")
}

@Composable
fun Defeat(onBackToMenu: () -> Unit) {
    TODO("Not yet implemented")
}

// Function to navigate to the Victory screen
private fun onVictory(navController: NavHostController) {
    navController.navigate(Routes.VICTORY)
}

// Function to navigate to the Defeat screen
private fun onDefeat(navController: NavHostController) {
    navController.navigate(Routes.DEFEAT)
}

// Logic to exit the app
private fun exitApp() {
    // Call finish() or System.exit(0) as appropriate for your app
    android.os.Process.killProcess(android.os.Process.myPid())
    System.exit(0)
}
