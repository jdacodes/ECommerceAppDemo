package com.jdacodes.feca.feature_cart.domain.model

data class CartProduct(
    val name: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String
)
