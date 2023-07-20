package com.jdacodes.feca.feature_product.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdacodes.feca.feature_product.domain.model.Product
import com.jdacodes.feca.feature_product.domain.model.Rating

@Entity
data class ProductEntity(
    val category: String? = "",
    val description: String? = "",
    @PrimaryKey val id: Int? = null,
    val image: String? = "",
    val price: Double? = null,
    val rating: Rating?,
    val title: String? = ""
) {
    fun toProduct(): Product {
        return Product(
            category = category,
            description = description,
            id = id,
            image = image,
            price = price,
            rating = rating,
            title = title
        )
    }
}
