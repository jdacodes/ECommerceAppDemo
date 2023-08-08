package com.jdacodes.feca.feature_cart.domain.use_case

import com.google.gson.Gson
import com.jdacodes.feca.core.util.Resource
import com.jdacodes.feca.feature_auth.data.dto.UserResponseDto
import com.jdacodes.feca.feature_auth.data.local.AuthPreferences
import com.jdacodes.feca.feature_cart.domain.model.CartProduct
import com.jdacodes.feca.feature_cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GetCartItemsUseCase(
    private val cartRepository: CartRepository,
    private val authPreferences: AuthPreferences,
    private val gson: Gson
) {
    suspend operator fun invoke(): Flow<Resource<List<CartProduct>>> {
        val data = authPreferences.getUserData.first()
        val user = gson.fromJson(data, UserResponseDto::class.java)
        return cartRepository.getAllCartItems(user.id)
    }
}