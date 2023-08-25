package com.jdacodes.feca.feature_product.data.remote.dto

import com.jdacodes.feca.feature_product.domain.model.Rating

data class RatingDto(
    val count: Int,
    val rate: Double
) {
    fun toRating(): Rating {
        return Rating(
            count = count,
            rate = rate
        )
    }
}