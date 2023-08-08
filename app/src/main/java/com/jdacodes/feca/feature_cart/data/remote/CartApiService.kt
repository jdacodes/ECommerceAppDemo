package com.jdacodes.feca.feature_cart.data.remote

import com.jdacodes.feca.feature_cart.data.remote.dto.UserCartResponseDto
import com.jdacodes.feca.feature_product.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CartApiService {
    @GET("carts/user/{id}")
    suspend fun cartItems(
        @Path("id") id: Int
    ): List<UserCartResponseDto>
    @GET("/products/{id}")
    suspend fun product(
        @Path("id") id: Int
    ): ProductDto
}