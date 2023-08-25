package com.jdacodes.feca.feature_wishlist.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WishlistDao {

    @Insert
    suspend fun insertToWishlist(wishlistEntity: WishlistEntity)

    @Query("SELECT * FROM wishlist_table ORDER BY id DESC")
    fun getWishlist(): LiveData<List<WishlistEntity>>

    @Query("SELECT * FROM wishlist_table WHERE id  == :id")
    fun getOneWishlistItem(id: Int): LiveData<WishlistEntity?>

    @Query("SELECT liked FROM wishlist_table WHERE id = :id")
    fun inWishlist(id: Int): LiveData<Boolean>

    @Delete
    suspend fun deleteWishlist(wishlistEntity: WishlistEntity)

    @Query("DELETE FROM wishlist_table")
    suspend fun deleteAllWishlist()
}