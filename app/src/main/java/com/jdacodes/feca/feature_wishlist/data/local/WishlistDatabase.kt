package com.jdacodes.feca.feature_wishlist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jdacodes.feca.feature_wishlist.data.util.Converters

@TypeConverters(Converters::class)
@Database(
    entities = [WishlistEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WishlistDatabase : RoomDatabase() {
    abstract val wishlistDao: WishlistDao
}