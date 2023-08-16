package com.jdacodes.feca.feature_profile.data

import com.jdacodes.feca.feature_auth.data.dto.UserResponseDto
import com.jdacodes.feca.feature_profile.domain.model.User

internal fun UserResponseDto.toDomain(): User {
    return User(
        address = address,
        email = email,
        id = id,
        name = name,
        password = password,
        phone = phone,
        username = username
    )
}

