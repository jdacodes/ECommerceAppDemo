package com.jdacodes.feca.feature_auth.data.repository

import com.jdacodes.feca.core.util.Resource
import com.jdacodes.feca.feature_auth.data.dto.UserResponseDto
import com.jdacodes.feca.feature_auth.data.local.AuthPreferences
import com.jdacodes.feca.feature_auth.data.remote.AuthApiService
import com.jdacodes.feca.feature_auth.data.remote.request.LoginRequest
import com.jdacodes.feca.feature_auth.domain.repository.LoginRepository
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class LoginRepositoryImpl(
    private val authApiService: AuthApiService,
    private val authPreferences: AuthPreferences
) : LoginRepository {
    override suspend fun login(loginRequest: LoginRequest, rememberMe: Boolean): Resource<Unit> {
        return try {
            val response = authApiService.loginUser(loginRequest)
            getAllUsers(loginRequest.username)?.let { authPreferences.saveUserdata(it) }
            if (rememberMe) {
                authPreferences.saveAccessToken(response.token)
            }
            Resource.Success(Unit)
        } catch (e: IOException) {
            Resource.Error(message = "Could not reach the server, please check your internet connection!")
        } catch (e: HttpException) {
            Resource.Error(message = "An Unknown error occurred, please try again!")
        }
    }

    override suspend fun autoLogin(): Resource<Unit> {
        val accessToken = authPreferences.getAccessToken.first()
        return if (accessToken != "") {
            Resource.Success(Unit)
        } else {
            Resource.Error("")
        }
    }

    override suspend fun logout(): Resource<Unit> {
        return try {
            authPreferences.clearAccessToken()
            val fetchedToken = authPreferences.getAccessToken.first()

            if (fetchedToken.isEmpty()) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Unknown Error")
            }
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
    }

    private suspend fun getAllUsers(name: String): UserResponseDto? {
        val response = authApiService.getAllUsers()
        return response.find { it.username == name }
    }
}