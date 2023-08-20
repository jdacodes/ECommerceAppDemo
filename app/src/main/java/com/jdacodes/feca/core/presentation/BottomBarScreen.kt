package com.jdacodes.feca.core.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.jdacodes.feca.destinations.AccountScreenDestination
import com.jdacodes.feca.destinations.CartScreenDestination
import com.jdacodes.feca.destinations.Destination
import com.jdacodes.feca.destinations.HomeScreenDestination


sealed class BottomBarScreen(
    var icon: ImageVector,
    var destination: Destination
) {
    object Home : BottomBarScreen(
        icon = Icons.Default.Home,
        destination = HomeScreenDestination
    )

    object Profile : BottomBarScreen(
        icon = Icons.Default.Person,
        destination = AccountScreenDestination
    )

    object Cart : BottomBarScreen(
        icon = Icons.Default.ShoppingCart,
        destination = CartScreenDestination
    )
}
