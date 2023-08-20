package com.jdacodes.feca.feature_product.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val category: String? = "",
    val description: String? = "",
    val id: Int? = null,
    val image: String? = "",
    val price: Double? = null,
    val rating: Rating,
    val title: String? = ""
): Parcelable

