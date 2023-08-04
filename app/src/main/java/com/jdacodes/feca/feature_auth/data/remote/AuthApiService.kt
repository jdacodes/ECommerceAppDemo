package com.jdacodes.feca.feature_auth.data.remote

import com.jdacodes.feca.feature_auth.data.dto.UserResponseDto
import com.jdacodes.feca.feature_auth.data.remote.request.LoginRequest
import com.jdacodes.feca.feature_auth.data.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("users/")
    suspend fun getAllUsers(): List<UserResponseDto>
}