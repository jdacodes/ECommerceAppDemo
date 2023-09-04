package com.jdacodes.feca.feature_product.data.remote

import com.jdacodes.feca.feature_product.data.remote.dto.ProductDto
import retrofit2.http.GET

interface NetworkApi {

    @GET("/products")
    suspend fun getProducts(): List<ProductDto>

    @GET("products/categories")
    suspend fun getProductCategories(): List<String>

}