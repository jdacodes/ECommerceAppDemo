package com.jdacodes.feca.feature_auth.domain.repository

import com.jdacodes.feca.core.util.Resource
import com.jdacodes.feca.feature_auth.data.remote.request.LoginRequest

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest, rememberMe: Boolean): Resource<Unit>
    suspend fun autoLogin(): Resource<Unit>
    suspend fun logout(): Resource<Unit>
}