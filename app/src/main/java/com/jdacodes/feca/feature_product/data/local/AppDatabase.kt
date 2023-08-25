package com.jdacodes.feca.feature_product.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jdacodes.feca.feature_product.data.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 3,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract val dao: ProductDao
}