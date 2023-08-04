package com.jdacodes.feca.feature_auth.domain.model

import com.jdacodes.feca.feature_auth.data.dto.Address
import com.jdacodes.feca.feature_auth.data.dto.Name

// TODO: User is never used 
data class User(
    val address: Address,
    val email: String,
    val id: Int,
    val name: Name,
    val password: String,
    val phone: String,
    val username: String,
)
