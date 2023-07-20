package com.jdacodes.feca.feature_product.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jdacodes.feca.feature_product.data.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("DELETE FROM productentity WHERE title IN (:title)")
    suspend fun deleteProducts(title: List<String?>)

    @Query("SELECT * FROM productentity")
    suspend fun getProducts(): List<ProductEntity>

    @Query("SELECT * FROM productentity WHERE title LIKE '%' || :title || '%'")
    suspend fun getProductByTitle(title: String): List<ProductEntity>
}