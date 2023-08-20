package com.jdacodes.feca.core.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun CustomScaffold(
    navController: NavController,
    showBottomBar: Boolean = true,
    items: List<BottomBarScreen> = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Profile,
        BottomBarScreen.Cart,
    ),
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigation(
                    backgroundColor = MaterialTheme.colorScheme.background,
                    elevation = 5.dp
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { item ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = "Navigation Icon"
                                )
                            },
                            selectedContentColor = MaterialTheme.colorScheme.onSurface,
                            unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                            selected = currentDestination?.route?.contains(item.destination.route) == true,
                            onClick = {
                                navController.navigate(item.destination.route) {
                                    navController.graph.startDestinationRoute?.let { screen_route ->
                                        popUpTo(screen_route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }


        }
    ) { paddingValues ->
        content(paddingValues)

    }

}