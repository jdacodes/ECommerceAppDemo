package com.jdacodes.feca.feature_product.domain.use_case

import com.jdacodes.feca.feature_product.domain.repository.ProductRepository

class GetCategoriesUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<String> {
        val categories = repository.getProductCategories()
        return listOf("All") + categories
    }
}