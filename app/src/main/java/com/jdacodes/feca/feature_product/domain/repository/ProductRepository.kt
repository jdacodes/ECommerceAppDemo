package com.jdacodes.feca.feature_product.domain.repository

import com.jdacodes.feca.core.util.Resource
import com.jdacodes.feca.feature_product.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(): Flow<Resource<List<Product>>>

    fun getProductsByTitle(title: String): Flow<Resource<List<Product>>>

    suspend fun getProductCategories(): Flow<List<String>>
}