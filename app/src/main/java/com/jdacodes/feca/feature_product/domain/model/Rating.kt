package com.jdacodes.feca.feature_product.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating(
    val count: Int? = null,
    val rate: Double? = null
): Parcelable
