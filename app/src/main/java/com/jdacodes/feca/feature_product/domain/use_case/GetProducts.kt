package com.jdacodes.feca.feature_product.domain.use_case

import com.jdacodes.feca.core.util.Resource
import com.jdacodes.feca.feature_product.domain.model.Product
import com.jdacodes.feca.feature_product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProducts(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<Resource<List<Product>>> {
        //Validation goes here
        return repository.getProducts()
    }
}
