package com.jdacodes.feca.feature_profile.domain.model

import com.jdacodes.feca.feature_auth.data.dto.Address
import com.jdacodes.feca.feature_auth.data.dto.Name

data class User(
    val address: Address? = null,
    val email: String? = null,
    val id: Int? = null,
    val name: Name? = null,
    val password: String? = null,
    val phone: String? = null,
    val username: String? = null
)
