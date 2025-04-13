package com.joey.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.joey.template.ui.viewmodel.DiceRollViewModel
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joey.template.ui.screen.PostScreen

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)


@Serializable
object Main

@Serializable
object Profile

@Serializable
object Friends

@Serializable
object About

val topLevelRoutes = listOf(
    TopLevelRoute("Profile", Profile, Icons.Default.Person),
    TopLevelRoute("Friends", Friends, Icons.Default.AccountBox)
)

// Define the ProfileScreen composable.
@Composable
fun MaiScreen(
    navToAbout: () -> Unit
) {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    Scaffold(
        bottomBar = {
            BottomAppBar {
                topLevelRoutes.forEach { topLevelRoute ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                topLevelRoute.icon,
                                contentDescription = topLevelRoute.name
                            )
                        },
                        label = { Text(topLevelRoute.name) },
                        selected = currentDestination?.hierarchy?.any {
                            it.hasRoute(
                                topLevelRoute.route::class
                            )
                        } == true,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Profile, Modifier.padding(innerPadding)) {
            composable<Profile> { PostScreen() }
            composable<Friends> { FriendsListScreen(navToAbout) }
        }
    }
}

// Define the FriendsListScreen composable.
@Composable
fun ProfileScreen(diceRollViewModel: DiceRollViewModel = viewModel()) {
    val diceRollUiState by diceRollViewModel.uiState.collectAsState()
    Column {
        Text(
            "${diceRollUiState.numberOfRolls}"
        )
        Text(
            "${diceRollUiState.firstDieValue}"
        )
        Text(
            "${diceRollUiState.secondDieValue}"
        )
        Button(
            onClick = {
                diceRollViewModel.rollDice()
            }
        ) {
            Text("rollDice")
        }
    }
}

// Define the FriendsListScreen composable.
@Composable
fun FriendsListScreen(navToAbout: () -> Unit) {
    Column {
        Text("Friends List")
        Button(onClick = navToAbout) {
            Text("navToAbout")
        }
    }
}// Define the FriendsListScreen composable.

@Composable
fun AboutScreen() {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("AboutScreen")
        }
    }

}

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
) {
    val navController: NavHostController = rememberNavController()
    NavHost(navController, startDestination = Main) {
        composable<Main> {
            MaiScreen(navToAbout = {
                navController.navigate(About)
            })
        }
        composable<About> {
            AboutScreen()
        }
    }
}