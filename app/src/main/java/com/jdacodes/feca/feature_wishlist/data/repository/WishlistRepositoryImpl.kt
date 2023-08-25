package com.jdacodes.feca.feature_wishlist.data.repository

import androidx.lifecycle.LiveData
import com.jdacodes.feca.feature_wishlist.data.local.WishlistDao
import com.jdacodes.feca.feature_wishlist.data.local.WishlistEntity
import com.jdacodes.feca.feature_wishlist.data.mapper.toEntity
import com.jdacodes.feca.feature_wishlist.domain.model.Wishlist
import com.jdacodes.feca.feature_wishlist.domain.repository.WishlistRepository

class WishlistRepositoryImpl(
    private val wishlistDao: WishlistDao
) : WishlistRepository {
    override suspend fun insertToWishlist(wishlist: Wishlist) {
        wishlistDao.insertToWishlist(wishlist.toEntity())
    }

    override fun getWishlist(): LiveData<List<WishlistEntity>> {
        return wishlistDao.getWishlist()
    }

    override fun inWishlist(id: Int): LiveData<Boolean> {
        return wishlistDao.inWishlist(id)
    }

    override fun getOneWishlistItem(id: Int): LiveData<WishlistEntity?> {
        return wishlistDao.getOneWishlistItem(id)
    }

    override suspend fun deleteOneWishlist(wishlist: Wishlist) {
        wishlistDao.deleteWishlist(wishlist.toEntity())
    }

    override suspend fun deleteAllWishlist() {
        wishlistDao.deleteAllWishlist()
    }
}