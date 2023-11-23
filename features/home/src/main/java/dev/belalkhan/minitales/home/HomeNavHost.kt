package dev.belalkhan.minitales.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.belalkhan.minitales.home.home.HomeTab
import dev.belalkhan.minitales.profile.ProfileScreen

const val homeRoute = "home"

sealed class BottomNavItem(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    data object Home : BottomNavItem("$homeRoute/index", R.string.home, Icons.Filled.Home)
    data object Fav : BottomNavItem("$homeRoute/favs", R.string.fav, Icons.Filled.Favorite)
    data object Profile : BottomNavItem("$homeRoute/profile", R.string.profile, Icons.Filled.Person)
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Fav,
    BottomNavItem.Profile,
)

@Composable
fun HomeNavHost(
    navController: NavHostController,
    onLogout: () -> Unit,
    onSelectStory: (Int) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
    ) {
        composable(route = BottomNavItem.Home.route) {
            HomeTab(
                viewModel = hiltViewModel(),
                onSelectStory = { id -> onSelectStory(id) },
            )
        }

        composable(route = BottomNavItem.Fav.route) {
            Text(text = "I am Fav screen")
        }

        composable(route = BottomNavItem.Profile.route) {
            ProfileScreen(
                viewModel = hiltViewModel(),
                onLogout = { onLogout() },
            )
        }
    }
}
