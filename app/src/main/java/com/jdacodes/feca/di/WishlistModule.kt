package com.jdacodes.feca.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.jdacodes.feca.feature_wishlist.data.local.WishlistDatabase
import com.jdacodes.feca.feature_wishlist.data.repository.WishlistRepositoryImpl
import com.jdacodes.feca.feature_wishlist.data.util.Converters
import com.jdacodes.feca.feature_wishlist.domain.repository.WishlistRepository
import com.jdacodes.feca.feature_wishlist.util.Constant.WISHLIST_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WishlistModule {

    @Provides
    @Singleton
    fun provideConverters(gson: Gson) = Converters(gson)

    @Provides
    @Singleton
    fun provideWishlistDatabase(
        @ApplicationContext context: Context,
        converters: Converters
    ): WishlistDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            WishlistDatabase::class.java,
            WISHLIST_DATABASE
        )
            .fallbackToDestructiveMigration()
            .addTypeConverter(converters)
            .build()
    }

    @Provides
    @Singleton
    fun provideWishlistRepository(wishlistDatabase: WishlistDatabase): WishlistRepository {
        return WishlistRepositoryImpl(wishlistDatabase.wishlistDao)
    }

}