package com.jdacodes.feca.feature_wishlist.data.local

import androidx.room.Entity
import com.jdacodes.feca.feature_wishlist.util.Constant.RATING_TABLE_NAME

@Entity(tableName = RATING_TABLE_NAME)
data class RatingEntity(
    val count: Int,
    val rate: Double
)
