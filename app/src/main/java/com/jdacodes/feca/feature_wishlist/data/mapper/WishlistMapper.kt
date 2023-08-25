package com.jdacodes.feca.feature_wishlist.data.mapper

import com.jdacodes.feca.feature_product.domain.model.Product
import com.jdacodes.feca.feature_wishlist.data.local.RatingEntity
import com.jdacodes.feca.feature_wishlist.data.local.WishlistEntity
import com.jdacodes.feca.feature_wishlist.domain.model.Rating
import com.jdacodes.feca.feature_wishlist.domain.model.Wishlist

internal fun RatingEntity.toDomain(): Rating {

    return Rating(
        count = count,
        rate = rate
    )
}

internal fun Rating.toEntity(): RatingEntity {
    return RatingEntity(
        count = count,
        rate = rate
    )
}

internal fun Rating.toProductRating(): com.jdacodes.feca.feature_product.domain.model.Rating {
    return com.jdacodes.feca.feature_product.domain.model.Rating(
        count = count,
        rate = rate
    )
}

internal fun com.jdacodes.feca.feature_product.domain.model.Rating.toWishlistRating(): Rating {
    return Rating(
        count = count,
        rate = rate
    )
}

internal fun WishlistEntity.toDomain(): Wishlist {
    return Wishlist(
        image = image,
        price = price,
        title = title,
        category = category,
        description = description,
        rating = rating.toDomain(),
        id = id,
        liked = liked
    )
}

internal fun Wishlist.toEntity(): WishlistEntity {
    return WishlistEntity(
        image = image,
        price = price,
        title = title,
        category = category,
        description = description,
        rating = rating.toEntity(),
        liked = liked,
        id = id
    )
}

internal fun Wishlist.toProduct(): Product {
    return Product(
        category = category,
        description = description,
        id = id,
        image = image,
        price = price,
        rating = rating.toProductRating(),
        title = title
    )
}

internal fun Product.toProduct(): Wishlist {
    return Wishlist(
        image = image,
        price = price,
        title = "",
        category = "",
        description = "",
        rating = Rating(count = 0, rate = 0.0),
        id = 0,
        liked = false
    )

}
