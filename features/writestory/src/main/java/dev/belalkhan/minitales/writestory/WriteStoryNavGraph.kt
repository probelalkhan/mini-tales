package dev.belalkhan.minitales.writestory

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

const val writeStoryRoute = "write-story"

fun NavGraphBuilder.writeStoryNavGraph(navController: NavController) {
    navigation("index", writeStoryRoute) {
        composable("index") {
            WriteStoryScreen(hiltViewModel(), navController)
        }
    }
}
