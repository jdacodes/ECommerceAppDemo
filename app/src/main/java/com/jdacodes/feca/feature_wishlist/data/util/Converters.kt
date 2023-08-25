package com.jdacodes.feca.feature_wishlist.data.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jdacodes.feca.feature_wishlist.data.local.RatingEntity

@ProvidedTypeConverter
class Converters(private val gson: Gson) {

    @TypeConverter
    fun fromRating(ratingEntity: RatingEntity): String {
        return gson.toJson(ratingEntity)
    }

    @TypeConverter
    fun toRating(rating: String): RatingEntity {
        return gson.fromJson(rating, RatingEntity::class.java)
    }
}