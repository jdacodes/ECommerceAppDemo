package com.jdacodes.feca.feature_product.presentation

import com.jdacodes.feca.feature_product.domain.model.Product

data class ProductState(
    val productItems: List<Product> = emptyList(),
    val isLoading: Boolean = false
)
