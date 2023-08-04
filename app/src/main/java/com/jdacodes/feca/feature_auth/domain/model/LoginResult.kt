package com.jdacodes.feca.feature_auth.domain.model

import com.jdacodes.feca.core.util.Resource

data class LoginResult(
    val passwordError: String? = null,
    val usernameError: String? = null,
    val result: Resource<Unit>? = null
)
