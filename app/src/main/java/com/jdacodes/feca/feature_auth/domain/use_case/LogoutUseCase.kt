package com.jdacodes.feca.feature_auth.domain.use_case

import com.jdacodes.feca.core.util.Resource
import com.jdacodes.feca.feature_auth.domain.repository.LoginRepository

class LogoutUseCase(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): Resource<Unit> {
        return loginRepository.logout()
    }
}