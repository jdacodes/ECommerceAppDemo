package com.jdacodes.feca.feature_product.presentation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.jdacodes.feca.R

interface FecaDestination {
    val route: String
    val resourceId: Int
}

object Products : FecaDestination {
    override val route = "products_list"
    override val resourceId: Int = R.string.products_list
}

object SingleProduct : FecaDestination {
    override val route = "single_product"
    override val resourceId: Int = R.string.single_product
    const val productIdArg = "product_id"
    val routeWithArgs = "$route/{$productIdArg}"
    val arguments = listOf(
        navArgument(productIdArg) { type = NavType.IntType }
    )
//    val deepLinks = listOf(
//        navDeepLink { uriPattern = "feca://$route/{$productIdArg}" }
//    )
}

val productScreens = listOf(Products, SingleProduct)
