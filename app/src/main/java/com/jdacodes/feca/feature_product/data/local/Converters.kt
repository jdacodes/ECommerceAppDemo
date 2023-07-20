package com.jdacodes.feca.feature_product.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.jdacodes.feca.feature_product.data.util.JsonParser
import com.jdacodes.feca.feature_product.domain.model.Rating

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {

    @TypeConverter
    fun fromRatingJson(json: String): Rating? {
        return jsonParser.fromJson<Rating>(
            json,
            object : TypeToken<Rating>(){}.type
        )
    }

    @TypeConverter
    fun tomRatingJson(rating: Rating): String {
        return jsonParser.toJson(
            rating,
            object : TypeToken<Rating>(){}.type
        ) ?:"{}"
    }
}