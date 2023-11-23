package dev.belalkhan.minitales

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.belalkhan.minitales.auth.authNavGraph
import dev.belalkhan.minitales.auth.authRoute
import dev.belalkhan.minitales.home.HomeScreen
import dev.belalkhan.minitales.home.homeRoute
import dev.belalkhan.minitales.writestory.writeStoryNavGraph
import dev.belalkhan.minitales.writestory.writeStoryRoute

@Composable
fun MiniTalesNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = authRoute,
    ) {
        authNavGraph(
            navController = navController,
            onAuthSuccess = {
                navController.navigate(
                    dest = homeRoute,
                    popUpTo = authRoute,
                )
            },
        )

        composable(homeRoute) {
            HomeScreen(
                onLogout = {
                    navController.navigate(dest = authRoute, popUpTo = homeRoute)
                },
                onWriteStory = {
                    navController.navigate(writeStoryRoute) {
                        launchSingleTop = true
                    }
                },
                onSelectStory = {
                    // @Todo navigate to write story
                },
            )
        }

        writeStoryNavGraph(navController)
    }
}

fun NavHostController.navigate(dest: String, popUpTo: String, inclusive: Boolean = true) {
    navigate(dest) {
        popUpTo(popUpTo) {
            this.inclusive = inclusive
        }
    }
}
