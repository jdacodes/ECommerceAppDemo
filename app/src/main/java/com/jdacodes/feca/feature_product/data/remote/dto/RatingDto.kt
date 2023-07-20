package com.jdacodes.feca.feature_product.data.remote.dto

import com.jdacodes.feca.feature_product.domain.model.Rating

data class RatingDto(
    val count: Int? = null,
    val rate: Double? = null
) {
    fun toRating(): Rating {
        return Rating(
            count = count,
            rate = rate
        )
    }
}