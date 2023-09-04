package com.jdacodes.feca.feature_product.data.repository

import com.jdacodes.feca.core.util.Resource
import com.jdacodes.feca.feature_product.data.local.ProductDao
import com.jdacodes.feca.feature_product.data.remote.NetworkApi
import com.jdacodes.feca.feature_product.domain.model.Product
import com.jdacodes.feca.feature_product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ProductRepositoryImpl(
    private val api: NetworkApi,
    private val dao: ProductDao
) : ProductRepository {
    override fun getProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        //emit from local db
        val products = dao.getProducts().map { it.toProduct() }
        emit(Resource.Loading(data = products))

        try {
            val remoteProducts = api.getProducts()
            dao.deleteProducts(remoteProducts.map { it.title })
            dao.insertProducts(remoteProducts.map { it.toProductEntity() })
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops something went wrong!",
                    data = products
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.",
                    data = products
                )
            )
        }

        //emit to UI
        val newProducts = dao.getProducts().map { it.toProduct() }
        emit(Resource.Success(newProducts))

    }

    override fun getProductsByTitle(title: String): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        //emit from local db
        val products = dao.getProductByTitle(title).map { it.toProduct() }
        emit(Resource.Loading(data = products))

        try {
            val remoteProducts = api.getProducts()
            dao.deleteProducts(remoteProducts.map { it.title })
            dao.insertProducts(remoteProducts.map { it.toProductEntity() })
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops something went wrong!",
                    data = products
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.",
                    data = products
                )
            )
        }
        //emit to UI
        val newProducts = dao.getProductByTitle(title).map { it.toProduct() }
        emit(Resource.Success(newProducts))
    }

    override suspend fun getProductCategories(): List<String> {
        return api.getProductCategories()
    }
}