package com.jdacodes.feca.feature_product.domain.use_case

import com.jdacodes.feca.core.util.Resource
import com.jdacodes.feca.feature_product.domain.model.Product
import com.jdacodes.feca.feature_product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProductsByTitle(
    private val repository: ProductRepository
) {
    operator fun invoke(title: String): Flow<Resource<List<Product>>> {
        //Validation goes here
        if (title.isBlank()) {
            return flow { }
        }
        return repository.getProductsByTitle(title)
    }
}