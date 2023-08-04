package com.jdacodes.feca.feature_product.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.jdacodes.feca.core.util.Constants
import com.jdacodes.feca.feature_product.data.local.AppDatabase
import com.jdacodes.feca.feature_product.data.local.Converters
import com.jdacodes.feca.feature_product.data.remote.NetworkApi
import com.jdacodes.feca.feature_product.data.repository.ProductRepositoryImpl
import com.jdacodes.feca.feature_product.data.util.GsonParser
import com.jdacodes.feca.feature_product.domain.repository.ProductRepository
import com.jdacodes.feca.feature_product.domain.use_case.GetProductsByTitle
import com.jdacodes.feca.feature_product.domain.use_case.GetProducts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductModule {

    @Provides
    @Singleton
    fun provideGetProductsUseCase(repository: ProductRepository): GetProducts {
        return GetProducts(repository)
    }

    @Provides
    @Singleton
    fun provideGetProductsByTitleUseCase(repository: ProductRepository): GetProductsByTitle {
        return GetProductsByTitle(repository)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        db: AppDatabase,
        api: NetworkApi
    ): ProductRepository {
        return ProductRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app, AppDatabase::class.java, "product_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkApi(): NetworkApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkApi::class.java)
    }

}