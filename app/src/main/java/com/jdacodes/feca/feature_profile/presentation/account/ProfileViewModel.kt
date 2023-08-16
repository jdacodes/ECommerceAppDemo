package com.jdacodes.feca.feature_profile.presentation.account

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jdacodes.feca.core.util.Resource
import com.jdacodes.feca.core.util.UiEvents
import com.jdacodes.feca.feature_auth.data.dto.UserResponseDto
import com.jdacodes.feca.feature_auth.domain.use_case.LogoutUseCase
import com.jdacodes.feca.feature_auth.presentation.graphs.AuthScreen
import com.jdacodes.feca.feature_profile.data.repository.ProfileRepository
import com.jdacodes.feca.feature_profile.data.toDomain
import com.jdacodes.feca.feature_profile.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val logoutUseCase: LogoutUseCase,
    private val gson: Gson
) : ViewModel() {

    private val _profileState = mutableStateOf(User())
    val profileState: State<User> = _profileState

    fun getProfile() {
        viewModelScope.launch {
            profileRepository.getUserProfile().collectLatest { data ->
                Log.d("ProfileViewModel", "Data: $data")
                val user = gson.fromJson(data, UserResponseDto::class.java)
                _profileState.value = user.toDomain()
            }
        }

    }

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun logout() {
        viewModelScope.launch {
            val result = logoutUseCase()
            Log.d("ProfileViewModel", "Result: ${result.message}")
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        // TODO: Provide correct route when logged out successful
                        UiEvents.NavigateEvent(route = AuthScreen.Login.route)
                    )
                }

                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvents.SnackBarEvent(
                            message = result.message ?: "Unknown error occurred"
                        )
                    )
                }

                else -> {}
            }
        }
    }
}