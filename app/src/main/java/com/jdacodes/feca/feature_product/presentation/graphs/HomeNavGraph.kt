package com.jdacodes.feca.feature_product.presentation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jdacodes.feca.BottomBarScreen
import com.jdacodes.feca.ProductListElement
import com.jdacodes.feca.SingleProduct
import com.jdacodes.feca.SingleProductScreen
import com.jdacodes.feca.core.util.graphs.Graph
import com.jdacodes.feca.feature_product.presentation.ProductState
import com.jdacodes.feca.feature_product.presentation.ScreenContent
import com.jdacodes.feca.navigateSingleTopTo


@Composable
fun HomeNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    state: ProductState
) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
//            ScreenContent(
//                name = BottomBarScreen.Home.route,
//                onClick = {
//                    navController.navigate(Graph.DETAILS)
//                }
//            )
            ProductListElement(
                productItems = state.productItems,
                isLoading = state.isLoading,
                onProductClick = { id ->
                    navController.navigateToSingleProduct(id)
                }
            )
        }
        composable(route = BottomBarScreen.Profile.route) {
            ScreenContent(
                name = BottomBarScreen.Profile.route,
                onClick = { }
            )
        }
        composable(route = BottomBarScreen.Settings.route) {
            ScreenContent(
                name = BottomBarScreen.Settings.route,
                onClick = { }
            )
        }
        detailsNavGraph(navController = navController)
        composable(
            route = SingleProduct.routeWithArgs,
            arguments = SingleProduct.arguments
        ) { navBackStackEntry ->
            val productId = navBackStackEntry.arguments?.getInt(SingleProduct.productIdArg)
            SingleProductScreen(
                productItems = state.productItems,
                productId = productId,
                modifier = modifier
            )
        }
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route
    ) {
        composable(route = DetailsScreen.Information.route) {
            ScreenContent(name = DetailsScreen.Information.route) {
                navController.navigate(DetailsScreen.Overview.route)
            }
        }
        composable(route = DetailsScreen.Overview.route) {
            ScreenContent(name = DetailsScreen.Overview.route) {
                navController.popBackStack(
                    route = DetailsScreen.Information.route,
                    inclusive = false
                )
            }
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object Information : DetailsScreen(route = "INFORMATION")
    object Overview : DetailsScreen(route = "OVERVIEW")
}

private fun NavHostController.navigateToSingleProduct(id: Int) {
    this.navigateSingleTopTo("${SingleProduct.route}/$id")
}

fun NavHostController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        //When pressing back will pop the backstack to the start destination which is List
        // this is to avoid a large stack in the backstack
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // To make sure only one instance of the destination when tapping on the navigation
        launchSingleTop = true
        //when tapping the same tab in a row will keep prev data and user state without reloading
        // if there is no saved state with destination id, this has no effect
        restoreState = true

    }
}