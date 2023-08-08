package com.jdacodes.feca.feature_cart.data.repository

import android.util.Log
import com.jdacodes.feca.core.util.Resource
import com.jdacodes.feca.feature_cart.data.remote.CartApiService
import com.jdacodes.feca.feature_cart.domain.model.CartProduct
import com.jdacodes.feca.feature_cart.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CartRepositoryImpl(
    private val cartApiService: CartApiService
) : CartRepository {
    override suspend fun getAllCartItems(id: Int): Flow<Resource<List<CartProduct>>> = flow {
        Log.d("CartRepositoryImpl", "Get all cart items called")
        emit(Resource.Loading())
        try {
            val response = cartApiService.cartItems(id)
            val cartItems = ArrayList<CartProduct>()
            response.forEach { it1 ->
                it1.cartProductDtos.forEach {
                    val productResponse = cartApiService.product(it.productId)
                    val cartProduct =
                        CartProduct(
                            productResponse.title,
                            productResponse.price,
                            it.quantity,
                            productResponse.image
                        )
                    cartItems.add(cartProduct)
                }
            }
            emit(Resource.Success(cartItems.toList().distinctBy { it.name }))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Could not reach server, please check your internet connection!"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Oops, something went wrong!"))
        }
    }
}