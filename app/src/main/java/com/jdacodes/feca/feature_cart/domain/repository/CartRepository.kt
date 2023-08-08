package com.jdacodes.feca.feature_cart.domain.repository

import com.jdacodes.feca.core.util.Resource
import com.jdacodes.feca.feature_cart.domain.model.CartProduct
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getAllCartItems(id: Int): Flow<Resource<List<CartProduct>>>
}