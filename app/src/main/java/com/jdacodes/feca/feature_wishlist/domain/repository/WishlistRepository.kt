package com.jdacodes.feca.feature_wishlist.domain.repository

import androidx.lifecycle.LiveData
import com.jdacodes.feca.feature_wishlist.data.local.WishlistEntity
import com.jdacodes.feca.feature_wishlist.domain.model.Wishlist

interface WishlistRepository {
    suspend fun insertToWishlist(wishlist: Wishlist)
    fun getWishlist(): LiveData<List<WishlistEntity>>
    fun inWishlist(id: Int): LiveData<Boolean>
    fun getOneWishlistItem(id: Int): LiveData<WishlistEntity?>
    suspend fun deleteOneWishlist(wishlist: Wishlist)
    suspend fun deleteAllWishlist()
}