package com.jdacodes.feca.feature_product.data.remote.dto

import com.jdacodes.feca.feature_product.data.local.entity.ProductEntity

data class ProductDto(
    val category: String? = "",
    val description: String? = "",
    val id: Int? = null,
    val image: String? = "",
    val price: Double? = null,
    val rating: RatingDto,
    val title: String? = ""
) {
    fun toProductEntity() : ProductEntity {
       return ProductEntity(
           category = category,
           description = description,
           id = id,
           image = image,
           price = price,
           rating = rating.toRating(),
           title = title


       )
    }
}