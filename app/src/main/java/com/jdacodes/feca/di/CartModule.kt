package com.jdacodes.feca.di

import com.google.gson.Gson
import com.jdacodes.feca.core.util.Constants
import com.jdacodes.feca.feature_auth.data.local.AuthPreferences
import com.jdacodes.feca.feature_cart.data.remote.CartApiService
import com.jdacodes.feca.feature_cart.data.repository.CartRepositoryImpl
import com.jdacodes.feca.feature_cart.domain.repository.CartRepository
import com.jdacodes.feca.feature_cart.domain.use_case.GetCartItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {
    @Provides
    @Singleton
    fun provideCartApiService(): CartApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CartApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        cartApiService: CartApiService
    ): CartRepository {
        return CartRepositoryImpl(
            cartApiService
        )
    }

    @Provides
    @Singleton
    fun provideGetCartItemsUseCase(
        cartRepository: CartRepository,
        authPreferences: AuthPreferences,
        gson: Gson
    ): GetCartItemsUseCase {
        return GetCartItemsUseCase(cartRepository, authPreferences, gson)
    }
}